package com.codessquad.qna;

import org.springframework.data.domain.Page;

public class PageWrapper {
    private int index;
    private Page page;

    public PageWrapper(Page page, int index) {
        this.page = page;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Page getPage() {
        return page;
    }
}
