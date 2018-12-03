package com.lingyun.common.annotation.controller;

import com.lingyun.common.support.data.ServerProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.Map;
@Controller
public class RootController {
    private static final Logger logger = LoggerFactory.getLogger(RootController.class);

    private ApplicationContext applicationContext;
    private ServerProject mainProject;
    public RootController(ApplicationContext applicationContext) {
        this.applicationContext=applicationContext;

    }

}
