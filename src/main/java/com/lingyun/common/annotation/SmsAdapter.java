package com.lingyun.common.annotation;

import java.util.Map;

public interface SmsAdapter {
    String send(Map<String, String> codes);
    String sendValidCode(String mobile, String validCode);
}
