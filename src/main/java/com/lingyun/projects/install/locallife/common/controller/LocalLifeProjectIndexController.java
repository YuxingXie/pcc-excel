package com.lingyun.projects.install.locallife.common.controller;

import com.lingyun.common.support.util.web.HttpClientTools;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LocalLifeProjectIndexController {
    @RequestMapping("/")
    public String appIndex(){
        return "index";
    }
    @RequestMapping("/{projectName}/{viewName}")
    public String thymeleafTemplates(@PathVariable String projectName,@PathVariable String viewName){
        return "/projects/"+projectName+"/"+viewName;
    }

    @RequestMapping("/handyman/clients")
    public String clients(@RequestHeader("User-Agent")String userAgent, Model map){
        String clientOS = HttpClientTools.getClientOS(userAgent);
        System.out.println(userAgent);
        map.addAttribute("clientOS",clientOS);
        return "/projects/handyman/clients";
    }
}
