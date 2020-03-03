package com.codesquad.qna.model;

public class HtmlDocumentUtils {
    public static String getEntertoBrTag(String contents) {
        return contents.replace("\r\n", "<br>");
    }
}
