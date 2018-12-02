package com.lingyun.projects.install.pccexcel.route;

import javax.swing.*;
import java.util.List;

public interface Router {//观察者

    void navigateTo(String to);
    void back();
    void forward();
}
