package com.codessquad.qna.web;


import java.util.ArrayList;
import java.util.List;

public class PageUtil {

  private Integer previous;
  private List<Integer> pages;
  private Integer current;
  private Integer next;

  public PageUtil(int current, int pages) {
    this.previous = checkPrevious(current);
    this.current = current;
    this.pages = createList(pages);
    this.next = checkNext(current, pages);
  }

  private Integer checkNext(int current, int pages) {
    if (current == pages) {
      return current;
    }
    return current + 1;
  }

  private Integer checkPrevious(int current) {
    if (current < 1) {
      return current;
    }
    return current - 1;
  }

  private List<Integer> createList(Integer pages) {
    List<Integer> list = new ArrayList<>();
    int last = pages.intValue();
    for (int num = 1; num <= last; num++) {
      list.add(num);
    }
    return list;
  }

  public Integer getPrevious() {
    return previous;
  }

  public List<Integer> getPages() {
    return pages;
  }

  public Integer getNext() {
    return next;
  }
}
