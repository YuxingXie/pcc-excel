package com.lingyun.common.support.util.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static boolean sessionJudge(String id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session.getAttribute(id) != null;
    }
}
