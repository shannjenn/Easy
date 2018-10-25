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
        Set<String> regexps = request.responseFormatMap.keySet();
        for (String regex : regexps) {
            String replacement = request.responseFormatMap.get(regex);
            response = response.replace(regex, replacement);
        }
        return response;
    }
}
