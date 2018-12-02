package com.lingyun.projects.install.pccexcel.route;

public class Publisher {//被观察者,或称发布者,

    public void navigateTo(Router observer){
            observer.navigateTo(null);
    }
    public void back(Router observer){
        observer.back();
    }
    public void forward(Router observer){
        observer.forward();
    }

}
