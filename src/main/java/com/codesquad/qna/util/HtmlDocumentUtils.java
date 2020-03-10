package com.codesquad.qna.util;

public class HtmlDocumentUtils {
    public static String getEntertoBrTag(String contents) {
        return contents.replace("\r\n", "<br>");
    }
}
