package com.zonos.url_lookup_service.utils;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class UrlUtilTest {

    @Test
    public void testEmptyParameter(){
        assertEquals("", UrlUtil.urlParser(null));
    }

    @Test
    public void testPublicIPv4AddressWithFilenameInPath(){
        assertEquals("8.8.8.8", UrlUtil.urlParser("https://8.8.8.8/malware_picture.jpg"));
    }

    @Test
    public void testPrefixStrippedFromHostname(){
        assertEquals("malwaredomain.com", UrlUtil.urlParser("https://www.malwaredomain.com"));
    }

    @Test
    public void testPortNumberStrippedFromHostname(){
        assertEquals("google.com", UrlUtil.urlParser("www.google.com:443"));
    }

    @Test
    public void testHostnameParsedCorrectlyFromCompleteUrl(){
        assertEquals("youtube.com", UrlUtil.urlParser("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
    }
}
