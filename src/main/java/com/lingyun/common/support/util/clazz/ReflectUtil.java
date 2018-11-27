package com.lingyun.common.support.util.clazz;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import com.lingyun.common.support.util.string.StringUtils;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtil {
//    private static Logger logger = LogManager.getLogger();

    public static <T> boolean methodExists(T t, String methodName) {
        Method[] methods = t.getClass().getMethods();
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获得带泛型参数类型的对象的泛型参数class
     *
     * @param typeCls 对象的class
     * @param <T>     对象的泛型参数
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getParameterizedType(Class typeCls) {

        Type genType = typeCls.getGenericSuperclass();
        while (true) {
            if (!(genType instanceof ParameterizedType)) {
                typeCls = typeCls.getSuperclass();
                if (genType == null) {
                    return null;
                }
                genType = typeCls.getGenericSuperclass();
            } else {
                break;
            }
        }
        return (Class<T>) ((ParameterizedType) genType).getActualTypeArguments()[0];

    }

    /**
     * 是否包装类
     * @param clz
     * @return
     */
    public static boolean isWrapClass(Class clz) {
        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获得getter方法名
     * name-->getName
     * @param fieldName
     * @param is_boolean
     * @return
     */
    public static String getGetterMethodName(String fieldName, boolean is_boolean) {
        if (!is_boolean)
            return "get" + StringUtils.firstUpperCase(fieldName);
        return "is" + StringUtils.firstUpperCase(fieldName);
    }

    public static String getGetterMethodName(String fieldName) {
        return getGetterMethodName(fieldName, false);
    }

    public static String getSetterMethodName(String fieldName) {
        return "set" + StringUtils.firstUpperCase(fieldName);
    }

    /**
     * 执行getter方法
     * t,name t.getName()
     * @param t
     * @param property
     * @param <T>
     * @return
     */
    public static <T> Object invokeGetter(T t, String property) {
        try {
            Method getter = t.getClass().getDeclaredMethod(getGetterMethodName(property));
            return getter.invoke(t);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> boolean isFieldExist(Class<T> clazz, String fieldName) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings({"rawtypes","unchecked","deprecation","serial"})
    public static <T> Object getValue(T t, String property, boolean property_is_boolean) {
        Class clazz = t.getClass();
        Method method;
        try {
            method = clazz.getDeclaredMethod(getGetterMethodName(property, property_is_boolean), (Class<?>) null);
            return method.invoke(t, (Object) null);
        } catch (SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }


    }
    @SuppressWarnings({"unchecked","deprecation","serial"})
    public static <E> void invokeSetter(E e, String fieldName, Object value) throws NoSuchMethodException {

        Class clazz = e.getClass();
        try {
            Method setter = clazz.getDeclaredMethod(getSetterMethodName(fieldName), value.getClass());
            setter.invoke(e, value);
        } catch (InvocationTargetException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 解析一个类的所有字段，直到所有元素都为基本数据类型及其包装类或字符串为止
     *
     * @param object
     */
    public static <E> void analysisBean(Object object) {
        //logger.info("-----begin analysis "+object.getClass().getName()+"--------");
        for (Field field : object.getClass().getDeclaredFields()) {
            String fieldName = field.getName();
//            logger.info("field name:" + fieldName);
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Type genericType = field.getGenericType();
            if (isSimpleObject(field)) {
//                logger.info("field is a primitive type type");
            } else if (field.getType().isArray()) {
                if (fieldValue != null) {
//                    logger.info("field is an array,field values are:");

                    Object[] fieldArrayObject = (Object[]) fieldValue;
                    for (Object fieldArrayObjectItem : fieldArrayObject) {
//                        logger.info(fieldArrayObjectItem + ",class is:" + fieldArrayObjectItem.getClass() + ",");
                    }
                    //logger.info("");
                } else {
//                    logger.info("field is an array,field value is null");
                }
            } else if (genericType instanceof ParameterizedType) {

                ParameterizedType parameterizedType = (ParameterizedType) genericType;
//                logger.info("field is a parameterized type:"+parameterizedType);
                Type[] actualTypes = parameterizedType.getActualTypeArguments();
                Type rawType = parameterizedType.getRawType();

                if (rawType == List.class) {
//                    logger.info("field is a java.util.List");
                }

                if (actualTypes.length > 0) {
                    Class class0 = (Class<?>) actualTypes[0];
//                    logger.info("field parameterized type:"+class0);
                }
                if (fieldValue != null && rawType == List.class) {
                    List<?> fieldValueList = (ArrayList) fieldValue;
                    for (Object fieldValueListItem : fieldValueList) {
                        analysisBean(fieldValueListItem);
                    }
                }

            } else {
                //logger.info("field is a simple class");
            }

            //logger.info("--------------------------------------------------------------");
        }
    }

     public static boolean isSimpleObject(Field field) {
        return field.getType().isPrimitive() || ReflectUtil.isWrapClass(field.getType()) || field.getType() == String.class;
    }
    public static boolean isComplexObject(Field field){
        return !isSimpleObject(field);
    }
}
