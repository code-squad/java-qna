package com.codessquad.qna.question;

public class Question {
    private String name;    // 겹치는데 괜찮을까?
    private String question;
    private String context;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "Question{" +
                "name='" + name + '\'' +
                ", question='" + question + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}
