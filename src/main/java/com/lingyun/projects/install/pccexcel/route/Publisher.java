package com.lingyun.projects.install.pccexcel.route;

public class Publisher<T> {//被观察者,或称发布者,
    private T component;

    public Publisher(T component) {
        this.component = component;
    }

    public void navigateTo(Router observer){
            observer.navigateTo(null);
    }
    public void back(Router observer){
        observer.back();
    }
    public void forward(Router observer){
        observer.forward();
    }

    public T getComponent() {
        return component;
    }
}
