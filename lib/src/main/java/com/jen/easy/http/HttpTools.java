package com.jen.easy.http;

import java.util.Set;

/**
 * 作者：ShannJenn
 * 时间：2018/10/25.
 * 说明：网络请求工具类
 */
class HttpTools {

    /**
     * @param request  not null
     * @param response not null
     * @return String
     */
    static String replaceResponse(HttpRequest request, String response) {
        Set<String> oldChars = request.responseReplaceMap.keySet();
        for (String oldChar : oldChars) {
            String replacement = request.responseReplaceMap.get(oldChar);
            response = response.replace(oldChar, replacement);
        }
        return response;
    }
}
