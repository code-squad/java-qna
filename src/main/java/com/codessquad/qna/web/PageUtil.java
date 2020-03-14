package com.codessquad.qna.web;


import java.util.ArrayList;
import java.util.List;

public class PageUtil {

  private static final int PAGE_COUNT = 6;

  private int previous;
  private List<Integer> pages;
  private int current;
  private int next;
  private int max;
  private int index;


  public PageUtil(int current, int index, int totalPages) {
    this.previous = 0;
    this.current = current;
    this.index = index;
    this.max = totalPages;
    this.pages = createList();
    this.next = createNext();
  }

  private List<Integer> createList() {
    List<Integer> list = new ArrayList<>();

    if (current / PAGE_COUNT < 1) {

      int start = 1;
      int last = 5;

      for (int num = start; num <= last; num++) {
        list.add(num);
      }
    } else {

      int multi = current / PAGE_COUNT < 2 ? 1 : current / PAGE_COUNT - 1;

      int start = multi * 5 + 1;
      int last = start + 4;

      if (last > max) {
        last = max;
      }

      for (int num = start; num <= last; num++) {
        list.add(num);
      }
      this.previous = start - 1;
    }

    return list;
  }

  private int createNext() {
    int size = pages.size() - 1;
    int next = pages.get(size);

    if (next == max) {
      return 0;
    }

    return next + 1;
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
