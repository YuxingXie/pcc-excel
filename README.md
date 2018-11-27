# 凌云网络官网 lingyunOfficalSite 



## 1 程序介绍



要实现的功能：

凌云官网不仅仅是官网，也是公司所有项目的展示平台。当我们开发一个项目的时候，我们可以在这套框架下开发，框架提供公用接口和服务类和配置类，
我们单独开发的项目遵循约定的规则开发，这样单独的项目和官网就可以实现类似于"热拔插"的能力。我们将项目代码专用的部分拷贝到特定的包或类路径下，
官网便有了展示该项目的能力，相当于热插入。当需要部署到项目生产环境时，我们删除掉其它不需要的项目，便成了单独的项目，相当于热拔出。

为了实现程序的最大兼容性、稳定性和安全性，我们需要确定一些原则：

* 默认约定为生产环境:程序采用约定大于配置，一般情况下，默认的配置将用于生产环境，而开发环境则需要制定配置。

* 跨平台:不可编写只能依赖于具体操作系统才可运行的代码。对于没有跨平台统一API的代码，比如文件系统，必须最少编写针对Linux,Unix、Mac OS和windows的代码。

* 技术前瞻性:在框架选择和业务实现方面，我们的项目必须选择具有前瞻性的框架和组件。具体说来就是，我们必须选择具有新特性的东西，但不必向后兼容。我们可以随时重构我们的
框架以使框架具备新特性。比如我们完全可以在程序*适当的*地方中尝试使用kotlin或groovy。

* 用户兼容性:我们必须考虑用户的不同群体，比如用户的iOS或Android版本。当然这也是有限度的，比如我们不会再考虑IE浏览器用户的感受了。

* 自动化:必须最大限度的减少人为的配置，除非这种配置是必不可少的。这是约定大于配置思想的另一个体现。

* 优雅的代码:这个主题比较宏大，它小到变量命名，大到设计模式，都体现出程序员的素养。总之，我们的目标不是简单的完成业务功能就OK，我们是要一套可继承的公司财产。


文档说明：

* 所有约定的地方都标记为黑体字

* 花括号内为变量或常量值

## 2 准备项目

在当前语义下，需要先明确两个概念：项目、应用程序。

项目指我们的官网应用程序上运行的一个个独立的模块，它有特定的uri前缀，它表示一个可独立部署的应用程序模块。比如访问/life表示我们访问一个叫life的生活百科项目。

应用程序指我们这个站点的可运行的程序，它可能包含了多个项目。除了/life访问生活百科项目，也可以通过/smswall访问微信上墙项目。应用程序运行需要一个jar包，以及一些
和各个项目相关的静态资源。


### 2.1 为应用程序命名

应用程序名称在gradle.properties文件中指定，这个名称用于应用程序的jar包名称的一部分，还用于应用程序生成上传文件的存储目录。当然，不指定的话程序也会
根据操作系统，工作目录等信息生成一个应用程序名。

注意后面所说的"项目"和"应用程序"的区别，这里应用程序是一个jar包，这个jar包中可能运行着多个项目，比如生活百科，微信市长热线等几个项目。

### 2.2 注册项目

公司开发的项目，我们称为project，在应用程序中实例化为一个com.lingyun.support.data.Project bean。当我们需要在应用程序中增加一个项目的时候，在
com.lingyun.configs.common.ProjectConfig注册一个Project bean。

Project.name会用作项目的根目录，比如当Project.name值为"life"时（我们开发的生活百科项目名称叫life），访问http://ip:port/life 就访问到了life项目。
```java
package com.lingyun.projects.install.life.config;
@Configuration
public class LifeProjectConfig extends ProjectConfig  {
    @Bean
    public Project projectLife(){
       return registerProject("life");
   }
   //other code
}
```

实际上，注册Project bean还有更好的用法，请见后文。

这里的包名体现了一个**非常重要的约定，项目代码必须放在com.lingyun.projects.install包中**,项目才能生效。


## 3 静态资源

在com.lingyun.configs.common.WebMvcConfig中注入刚注册的Project。这一步的目的是映射静态资源。我们不需要编写其它代码，框架自动生成约定的映射。
如果需要自定义映射方案，可以编写一个继承WebMvcConfig的配置类重写addResourceHandlers方法。

静态目录分两种：

* 1：工作目录(workingDirs)，是html、js等组成的前端程序的目录，它可以位于类路径(classpath)，也可位于文件系统(file)。

* 2：上传目录(uploadDir)，是项目用户上传文件到服务器的服务器文件系统目录，它必须是文件系统目录，不可以是类路径，除非你遵从传统的部署方案。

注册项目后启动程序，框架会自动检测和生成项目的上传目录。

可以使用你自定义的工作目录，在开发阶段把这个目录指定为前端项目的编译文件夹，这很方便测试。工作目录不指定的话，框架会自动指定工作目录为上传目录的父目录。
上传目录在什么地方，接下来马上说明。

一定要记得，每一个项目都有一个工作目录和上传目录，它们是相对于项目而非相对于应用程序。

### 3.1 上传目录

#### 3.1.1 上传文件目录及uri映射

前端上传的文件，服务器不需要指定保存目录，这个目录根据服务器操作系统和项目名称自动生成。

以Mac os和linux为例，如果当前用户为xieyuxing，项目名称为life，那么这个项目上传的文件将会在/Users/xieyuxing/appData/{appName}/life/upload目录下，这个目录就叫做
**_上传目录_**。该目录下还会有images和videos等文件夹区分不同的文件类型。在这些文件夹中，还会每日新建一个以日期命名的文件夹如20181010，存放每日文件。

而在windows下，则会检测系统的分区，如果存在D盘，上传目录将会是D://appData/{appName}/life/upload,如果不存在D盘则放上传目录为C://appData/{appName}/life/upload，
目录下的文件夹结构和linux相同。

过于细分的目录不利于管理和理解，所以文件上传目录和uri映射力求简单明了。

上传的文件uri映射规为：/{project.name}/upload/{fileType}/{currentTimeMillis}_{randomNumber}.{suffix}

{fileType}的值是一个枚举值code，见com.lingyun.support.code.UploadFileType。

举例：2018年10月10日，项目life上传的一个abc.jpg文件uri映射可能为：/life/upload/images/125115151515_123.jpg，它所在的文件位置为：
{project.uploadDir}/images/125115151515_123.jpg。
。


### 3.2 工作目录

#### 3.2.1 默认的工作目录

工作目录位置可以在类路径中，比如，它可以位于项目的src/main/resources/public/${project.name}中，类路径表示为"classpath:public/${project.name}",我们需要拷贝静态文件到这个目录。
这样，前后端代码都包含在应用程序的jar包中。

这里我们的一个理念是：**除非是服务端渲染的项目(jsp,thymeleaf)，我们永远永远不要将工作目录指定在类路径中**。


工作目录实际上是静态的前端代码文件目录，它之所以可以被放在类路径，是因为它不需要频繁改动。然而，如果放在类路径，部署时就会打包进入jar文件中，这时候，如果修改了前端代码，并需要
部署到生产环境，我们将不得不重新生成jar包，关闭服务器应用程序，并替换jar包，然后重启服务器应用程序。这种事情在我们之前的项目中出现了多次，从现在开始，它需要被杜绝。

为了避免这一复杂的部署操作，基于前后端代码分离的框架可以用另外一种工作目录方案。我们可以把工作目录放在上传目录的上一级目录中。前面举例中Windows下应用程序的life项目的上传目录为
D://appData/{appName}/life/upload,我们如果默认项目的上传目录的父目录即D://appData/{appName}/life为工作目录，则很轻松的实现了前后端分离部署。在生产环境下，我们只需将修改后的前端代码
拷贝到这个目录即可实现热部署，无需重启应用程序，无需重新打包应用程序。


#### 3.2.2 指定的工作目录

默认的工作目录在部署阶段很方便，但开发阶段却仍然强迫我们一遍遍的复制前端代码到工作目录。所以在开发阶段我们需要另一种工作方式。

使用指定的静态目录可以避免这个问题，后端人员在自己的开发电脑上安装前端开发工具，使用前端打包工具，将前端代码的工作目录指定为静态目录即可。后端开发人员可以随时从git更新前端项目而
无需打包拷贝。

指定工作目录很简单，我们在创建Project bean的时候带上这个目录就好了。
```java

@Configuration
public class ProjectConfig  {
    @Bean
    public Project projectLife(){
       return registerProject("life","/Users/xulei/WebstormProjects/life-home/www/");
   }
   //other project beans
}

```


## 4 Restful API

**API约定调用和返回值格式**，会大大降低沟通成本，也便于前端代码做格式化处理，统一代码风格。

### 4.1 API调用

项目下面的API URI格式为：/{project.name}/api/{domain}/{function}

举例说明。life项目，调用user领域的add方法API格式应为：/life/api/smswallUser/add。

function约定为：

* add:添加一个领域模型,方法为post

* modify:修改,方法为post

* modify/{propertyName}:修改一个指定属性的值,方法为post，表单中的其它属性会忽略

* remove/{id}:按id删除,方法为post、delete或get

* removeAll:删除全部,方法为post、delete或get

* find/{id}:按id查找,方法为post或get

* all:查找全部,方法为post或get

一些特殊业务方法需要自己定义function。另外，还会开发一些通用方法比如modifyAll。

### 4.2 API返回值

所有api返回值是具有统一格式的json对象，json字段说明：

* success:boolean,表名业务目标是否成功，不为空

* message:string,描述，可能为空

* level:string，字符串枚举，"info","warning","wrong"三个之一，可能为空;

* data:any,数据，可能是json对象，也可能是json列表，也可能是一个简单数据类型值，可能为空。

api返回对象的Java表述类为com.lingyun.support.data.ApiResponse。

## 5 问题

框架目前存在的几个问题，不能用优雅的方式解决，希望以后能找到方法，或者自己编写框架实现。主要问题有：

1：不同项目实体命名和映射问题。如果两个项目都使用了同一名称的表，比如User表，映射为各自的User类，实际上它们会使用同一张表。

解决的办法是实体类必须在应用程序中具有唯一名称。这不是一个优雅的办法，但在现成框架下也没有其它更好的办法。

2：控制器请求匹配的问题。项目都有统一的uri前缀，比如/life，/life/api等。举例：我们定义一个项目的父类控制器ProjectApiController,
加上注解@RequestMapping（"/life/api"）,然后我们定义一个领域user的控制器UserApiController继承自ProjectApiController,并加上
@RequestMapping("/user")注解。我们期望UserApiController能够匹配/life/api/user,但定义在父类控制器上的@RequestMapping注解会被子类覆盖，
所以我们不能定义通过这种方式实现。

我们目前的解决方案是，UserApiController不定义类级别的RequestMapping注解，而是在方法注解上加上@RequestMapping("/user/xxx")注解。
这样方法和类的注解就各自起作用了，但这也不是优雅的方式。

所以，一个很重要的事项就是：

**项目间的实体映射必须唯一，不可冲突**。

**我们约定，实体类映射的表名必须带上项目名前缀**，比如life项目的User实体，我们应该映射为life_user表，即加上@Table(name = "life_user")注解。
如果项目名是驼峰结构则解构为下划线结构。比如officalSite项目的User实体映射表应为@Table(name = "offical_site_user")

## 6 应用程序脚手架

脚手架程序最有名的应该是Google的脚手架框架，它基于freemarker模板引擎。

应用程序脚手架依赖一套可信赖的程序框架，通过模板引擎生成项目骨架文件，可大大节约开发时间，脚手架程序的可靠程度依赖于脚手架程序的自动化程度
以及骨架模板的可靠程度。

所以脚手架应用程序开发的前提是完善这一套官网应用程序，以此为模板开发脚手架程序。

另外前端也需要建立一套脚手架程序。


关于似乎有一个更好的方案。因为我们的项目基于gradle构建，gradle似乎可以充当脚手架程序的角色。但目前公司技术在gradle上比较短板，如果技术足够支撑，
可以尝试使用gradle作为脚手架程序，那便是相当完美了。

目前官网应用程序正在完善中，所以脚手架程序还未开始开发。


## 7 其它约定

* 所有的表示路径的变量，包括类路径、uri、文件路径，其值都不以"/"结尾；

* 存储URI或文件路径的变量，如果是基于应用程序的root路径，则其值以"/"开头，如果不是，则不以"/"开头；

* 存储类的变量，其值不以"/"开头；

* 文件路径变量应用相同的约定，但是Windows系统下的绝对路径肯定不是以"/"开头；



## 8 升级老项目

如果我们以前开发的项目需要纳入本程序，那么我们需要做如下步骤：

* 1.修改表名，表名都带上项目名前缀加下划线。如user表改名为life_user;

* 2.拷贝老项目的业务代码包中的文件至com.lingyun.projects.install.{project.name}包下，不要拷贝通用框架代码；

* 3.解决报错的代码，一般是包名，类名之类的冲突；

* 4.实体类的映射记得按约定规则改过来；

* 5.为项目添加一个通用控制器类，按约定api规则修改uri匹配；

* 6.上传文件拷贝到项目的上传目录中，这是比较麻烦的事情，因为文件可能所在的文件夹不一样，需要对照老代码的逻辑改过来，并且数据库保存的字段也需要改；

* 7.按之前的说明注册项目；

* 8.前端代码的对应api调用也需要修改过来。

## 9 TODO

问题超级严重严重，几乎囊括了程序代码的所有地方。比较急迫的问题：

* /life/api/image/add 检查文件保存代码是否正确

* 文件上传代码严重神经错乱；

* 工具类严重耦合业务类；

* 包分类乱七八糟；

* 该作为配置文件属性的不配置，不该配置的属性又在配置；

* 单元测试基本不能跑；

* 删除一些无用或者不知道干什么的类，filter,interceptor，servlet之类的，评估对框架的影响；

* 替代一些标记为过时的类和方法；

* 无处不在的bug。


## 10 项目重构笔记

老的项目需要重构才能适应新框架，特别在开发环境与生产环境下的异同，生产环境部署的注意事项一定要记录下来。

### 10.1 市民之家照片上墙系统重构笔记

#### 10.1.1 step1
新建com.lingyun.projects.install.smswall.domain包；

#### 10.1.2 step2
拷贝老项目com.lingyun.smzj.screen.domain包下的内容至com.lingyun.projects.install.smswall.domain下；

#### 10.1.3 step3
解决新旧包名冲突，去掉报错的import语句，在手动import进来，注意不要应用到别的项目的类，比如User类，要记得引入com.lingyun.projects.install.smswall.domain.smswallUser.entity.User；
这个时候留个疑问，同名的Bean会冲突吗？比如com.lingyun.projects.install.smswall.domain.smswallUser.repo.UserRepository和com.lingyun.projects.install.life.domain.smswallUser.repo.UserRepository
会不会冲突的问题；

#### 10.1.4 step4
BaseReturn这个类已改名为ApiResponse了，全局查找替换一下。这个类的构造方法已经改了，所以会留下报错的构造函数调用代码，因为返回值与业务逻辑相关，这个先放着。FileUpload这个
类的情况也类似，类名和方法名都改了，也先放着。HttpRequestor需要被HttpClientTools取代。RepositoryManager这个类也不存在了，代之以BeanManager。因为Websocket需要换组件，
所以MyWebSocket也需要被替代；把MessageLevelEnum拷贝过来，放入项目包内，这个枚举也迟早要废掉的；FileUpload类中定义的一些常量需要被Constant中的常量替换。

#### 10.1.5 step5
现在代码大面积报错，基本上都是以上几个类导致的，现在一个个解决。

**ApiResponse：**

这个类用于Api调用返回，所以应该出现在控制器中，但现在也有一部分出现在服务层，这个以后再重构，现在目标是保证代码正常。从控制器中找到使用该类的地方，把构造方法参数调整过来。可以
先不考虑泛型参数的问题，代码warning而已，以后再改。


**BeanManager:**

BeanManager是一个Bean持有类，一般在一些非spring bean(spring bean 可以注入bean所以不需要使用此类)中使用。这里有一个问题，BeanManager是根据bean名称从上下文中获取的，如果同名
（比如前面的userRepository等有几个同名的bean）框架该如何处理？这个先放下，等代码编译通过后运行调试。

**FileUpload：**

这是一个严重神经错乱的类，比一团麻还乱，所以改动它必须小心翼翼，要记录对生产环境的影响。它定义的一些常量将被移动到com.lingyun.support.data.Constant类中。

老代码FileUpload中定义的这几个常量如下：
```java
    public static final String path = "appData"+File.separator+"zhnx12345";
    public static final String videopath = File.separator+"videos";
    public static final String imagepath = File.separator+"images";
    public static final String file ="file:"+ File.separator;
    public static final String videopaths = file+ FileUpload.path+ FileUpload.videopath+ File.separator;
    public static final String imagepaths = file+ FileUpload.path+ FileUpload.imagepath+ File.separator;
```
在老代码中，一个项目就是一个应用程序，所以在应用程序级别可以指定项目的一些文件存储路径。但是新框架之下，项目只是应用程序的一个组件而已，
所以项目的文件存储路径必须相对于应用程序。

新的框架之下，应用程序有个根路径，它定义在Constant中：
```java
    public static final String APP_ROOT_DIR = getAppRootDir();
```
 常量APP_ROOT_DIR是一个根据操作系统及配置文件动态生成的一个路径常量，它代表应用程序的资源文件地址。而各个项目则以此路径作为根路径。
 各个子项目的相对根路径的这些路径都定义在一个Project类型的bean中。所以我们要注册一个Project bean,并注入到使用了FileUpload的地方。
 
 Project有一个属性workingDir(或许命名有点问题)，它表示前端代码的运行目录，就是那些js,css,html文件存放的地方。对于前后端分离的项目
 来说，它可以放在类路径，也可以放在静态资源路径。开发阶段，我们可以指定它为IDE工作目录下的某个目录从而方便开发。如果不指定，那么它
 位于APP_ROOT_DIR所代表的目录下的以项目命名的文件夹下(类似${Constant.APP_ROOT_DIR/project.name}目录)。
 
 根据老代码的定义，FileUpload.path应该对应新框架中的project.uploadDir。
 
 ProjectConfig中注册一个Project bean，它的名称是projectSmswall。
 ```java
    @Bean
    public Project projectSmswall(){
        return registerProject("smswall" );
    }
```

将该bean注入依赖它的其它bean中(这里就是使用了FileUpload的地方)：
 ```java
    @Resource
    private Project projectSmswall;
```
FileUpload.path替换为projectSmswall.getUploadDir()。

FileUpload.imagepath替换为File.separator+UploadFileType.IMAGES.getCode()

FileUpload.videopath替换为File.separator+UploadFileType.VIDEOS.getCode()

发现旧代码中许多错误，很多地方有重复的File.separator，这个需要调试.

另外我看到老代码中的com.lingyun.common.fileupload.FileUpload.fileUpload(...)方法时吐出了几十两血。我把这个方法放在了一个
叫NMDTools类中了。

**MyWebSocket**

这个类命名就有点草泥马，并且不好使用。我们需要将WebSocket框架重构，技术细节参见公司GitHub项目：https://github.com/YuxingXie/stomp-websocket

1.删除MyWebSocket类,MyWebSocket的onMessage方法实际上是发送消息到客户端端点。老websocket不需要建立客户端端点，使用session连接客户端，
新的stomp websocket需要定义客户端端点。在用到MyWebSocket.onMessage方法的地方注入org.springframework.messaging.simp.SimpMessagingTemplate,
bean名为simpMessageSendingOperations(不知道是否必须这个名字)。

示例，发送消息：
```java
    //"/topic/greetings"为客户端端点，由客户端定义
    simpMessageSendingOperations.convertAndSend("/topic/greetings", message);
```

到目前为止，代码编译错误消失。

#### 10.1.6 step6

运行以后bean命名冲突，可以使用注解加参数解决,但是Service注解需要注意，被注入的地方需要改名，例如：
```java
@RestController("smswallImageController")
public class ImageController {
    //..........
}
@Service("smswallImageService")
//
```

为了解决找不到simpMessageSendingOperations的问题，把build.gradle改了一下：
```java
    classpath("org.springframework.boot:spring-boot-gradle-plugin:2.0.3.RELEASE")
```
spring基础版本升级到了5.x.x,spring-data-jpa升级为了2.x.x.结果spring-data-jpa的编程接口已经变了，出现了编译错误。比如findOne没了，改为了findById,
返回值也变成了Optional<T>类型。先把这些API改过来。

PageRequest的构造函数标记为过时，改用PageRequest.of方法。

compile("org.thymeleaf:thymeleaf-spring4")改为compile("org.thymeleaf:thymeleaf-spring5")

启动，失败：
```text
2018-10-10 14:46:16.163  INFO 1394 --- [  restartedMain] org.hibernate.Version                    : HHH000412: Hibernate Core {5.2.17.Final}
2018-10-10 14:46:16.164  INFO 1394 --- [  restartedMain] org.hibernate.cfg.Environment            : HHH000206: hibernate.properties not found
2018-10-10 14:46:16.202  INFO 1394 --- [  restartedMain] o.hibernate.annotations.common.Version   : HCANN000001: Hibernate Commons Annotations {5.0.1.Final}
2018-10-10 14:46:17.057  INFO 1394 --- [  restartedMain] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.H2Dialect
2018-10-10 14:46:17.293  WARN 1394 --- [  restartedMain] org.hibernate.id.UUIDHexGenerator        : HHH000409: Using org.hibernate.id.UUIDHexGenerator which does not generate IETF RFC 4122 compliant UUID values; consider using org.hibernate.id.UUIDGenerator instead
2018-10-10 14:46:18.099  INFO 1394 --- [  restartedMain] o.h.h.i.QueryTranslatorFactoryInitiator  : HHH000397: Using ASTQueryTranslatorFactory
2018-10-10 14:46:19.818  INFO 1394 --- [  restartedMain] o.apache.catalina.core.StandardService   : Stopping service [Tomcat]
```

控制台打印似乎预示着与Hibernate相关。看看是否实体映射的问题。

修改了一下映射的表名，启动后打印了建表语句，但仍无法启动。继续找原因。

控制台的这句打印是WARN级别：
```text
Using org.hibernate.id.UUIDHexGenerator which does not generate IETF RFC 4122 compliant UUID values; consider using org.hibernate.id.UUIDGenerator instead
```

 @GenericGenerator(name ="system-uuid", strategy = "uuid")
 
 改为
 
 @GenericGenerator(name = "uuid", strategy = "uuid2")
 
 WARN消失，但仍无法启动，继续。。。
 
 发大招，改回spring-boot 1.5.x,代码改回去，启动报错信息，意思是无法找到simpMessageSendingOperations这个bean！
 
 查看了一下源码，只有一个命名为brokerMessagingTemplate的SimpMessagingTemplate bean。于是改下代码：
 ```java
    @Resource
     private SimpMessagingTemplate brokerMessagingTemplate;
```
启动，成功！

但是还没完，我们需要给控制器添加一个父类并继承它，去掉控制器类级别的RequestMapping，加在方法上。

另外，所有的实体类表名映射也加上项目前缀。

另外，给bean自定义别名的方式好呢，还是使用默认命名法，将类改名好呢？我认为修改类名的方式比较好，不会引入错误，也不需要额外配置。
还有就是jpa实体映射使用类名映射表名还是使用@Table注解好呢？我认为使用注解好。所以我们需要将Controller,Service,Repository类
改名，但不要改实体映射类名。但是，测试时发现jpa框架似乎用到了类的简单名作为映射依据，造成了映射错误，所以还是彻底改实体类类名的比较好。
 
 另外，把@Autowired,@Resource注入全部改为由构造方法注入。
 
 新旧表名对照：
 
 smswall_activity  --> smzj_activity
 
 smswall_animation --> animation
 
 smswall_arrangement  --> arrangement
 
 smswall_image --> smzj_image
 
 smswall_login_record --> smzj_login_record
 
 smswall_message -->  smzj_message
 
 smswall_wxuser --> wxuser
 
 进一步优化，现在框架代码与项目代码完全解耦。
 