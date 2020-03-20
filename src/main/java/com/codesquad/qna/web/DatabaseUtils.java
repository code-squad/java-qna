package com.codesquad.qna.web;

import com.codesquad.qna.domain.Question;

public class DatabaseUtils {

    public static void replaceEscapesToTags(Question question) {
        String modifiedContents = question.getContents().replaceAll("\r\n", "<br/>");
        question.setContents(modifiedContents);
    }

    public static void replaceTagsToEscapes(Question question) {
        String modifiedContents = question.getContents().replaceAll("<br/>", "\r\n");
        question.setContents(modifiedContents);
    }
}
