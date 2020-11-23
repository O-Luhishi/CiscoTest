package com.zonos.url_lookup_service.utils;

public class UrlUtil {

    public static String urlParser(String url) {
        if(url == null || url.length() == 0)
            return "";

        int doubleslash = url.indexOf("//");
        if(doubleslash == -1)
            doubleslash = 0;
        else
            doubleslash += 2;

        int end = url.indexOf('/', doubleslash);
        end = end >= 0 ? end : url.length();

        int port = url.indexOf(':', doubleslash);
        end = (port > 0 && port < end) ? port : end;

        String sanitised_url = url.substring(doubleslash, end);
        return sanitised_url.replaceFirst("^(http://)?(www\\.)?", "");

    }
}