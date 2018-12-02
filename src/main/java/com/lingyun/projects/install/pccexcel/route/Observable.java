package com.lingyun.projects.install.pccexcel.route;

public class Observable {
    private Publisher publisher ;

    public Observable(Publisher publisher) {
        this.publisher = publisher;
    }
    public void onSubsribe(Router observer) {
        this.publisher.navigateTo(observer);
    }
    public void onBack(Router observer) {
        this.publisher.back(observer);
    }
    public void onForward(Router observer) {
        this.publisher.forward(observer);
    }
}
