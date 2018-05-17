import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import codesquad.domain.PaginationTdd;


public class PaginationTest {

	PaginationTdd paginationTdd;
	
	
	@Before
	public void setUp() {
		paginationTdd = PaginationTdd.of(15);
	}
	
	@Test
	public void isFirstPageSetTest() {
		assertThat(paginationTdd.isFirstPageSet(0), is(true));
		assertThat(paginationTdd.isFirstPageSet(1), is(true));
		assertThat(paginationTdd.isFirstPageSet(2), is(true));
		assertThat(paginationTdd.isFirstPageSet(3), is(true));
		assertThat(paginationTdd.isFirstPageSet(4), is(true));
		assertThat(paginationTdd.isFirstPageSet(5), is(false));
		assertThat(paginationTdd.isFirstPageSet(6), is(false));
	}
	
	@Test
	public void calcNextPageSet() {
		assertThat(paginationTdd.calcNextPageSet(0), is(5));
		assertThat(paginationTdd.calcNextPageSet(5), is(10));
	}
	
	@Test
	public void isLastPageSet() {
		assertThat(paginationTdd.isLastPageSet(1), is(false));
		assertThat(paginationTdd.isLastPageSet(2), is(false));
		assertThat(paginationTdd.isLastPageSet(3), is(false));
		assertThat(paginationTdd.isLastPageSet(4), is(false));
		assertThat(paginationTdd.isLastPageSet(5), is(true));
		assertThat(paginationTdd.isLastPageSet(6), is(true));
		assertThat(paginationTdd.isLastPageSet(7), is(true));
		assertThat(paginationTdd.isLastPageSet(8), is(true));
	}
	
	@Test
	public void makeleftMoveButtonTest() {
		assertThat(paginationTdd.makeleftMoveButton(5), is("<li><a href=?page="+ 0 +">«</a></li>\r\n"));
		assertThat(paginationTdd.makeleftMoveButton(6), is("<li><a href=?page="+ 1 +">«</a></li>\r\n"));
		assertThat(paginationTdd.makeleftMoveButton(7), is("<li><a href=?page="+ 2 +">«</a></li>\r\n"));
		assertThat(paginationTdd.makeleftMoveButton(8), is("<li><a href=?page="+ 3 +">«</a></li>\r\n"));
		assertThat(paginationTdd.makeleftMoveButton(9), is("<li><a href=?page="+ 4 +">«</a></li>\r\n"));
	}
	
	@Test
	public void makeRightMoveButtonTest() {
		assertThat(paginationTdd.makeRightMoveButton(0), is("<li><a href=?page="+ 5 +">»</a></li>\r\n"));
		assertThat(paginationTdd.makeRightMoveButton(1), is("<li><a href=?page="+ 5 +">»</a></li>\r\n"));
		assertThat(paginationTdd.makeRightMoveButton(2), is("<li><a href=?page="+ 5 +">»</a></li>\r\n"));
		assertThat(paginationTdd.makeRightMoveButton(3), is("<li><a href=?page="+ 5 +">»</a></li>\r\n"));
		assertThat(paginationTdd.makeRightMoveButton(4), is("<li><a href=?page="+ 5 +">»</a></li>\r\n"));
	}
	
	@Test
	public void makePagination() {
		assertThat(paginationTdd.makeNormalButton(0), is("<li><a href=?page=" + 0 + ">" + 1 + "</a></li>"));
		assertThat(paginationTdd.makeNormalButton(1), is("<li><a href=?page=" + 1 + ">" + 2 + "</a></li>"));
		assertThat(paginationTdd.makeNormalButton(2), is("<li><a href=?page=" + 2 + ">" + 3 + "</a></li>"));
		assertThat(paginationTdd.makeNormalButton(3), is("<li><a href=?page=" + 3 + ">" + 4 + "</a></li>"));
		assertThat(paginationTdd.makeNormalButton(4), is("<li><a href=?page=" + 4 + ">" + 5 + "</a></li>"));
	}
	
	@Test
	public void calcFirstPageNumber() {
		assertThat(paginationTdd.calcFirstPageNumber(0), is(0));
		assertThat(paginationTdd.calcFirstPageNumber(1), is(0));
		assertThat(paginationTdd.calcFirstPageNumber(5), is(5));
		assertThat(paginationTdd.calcFirstPageNumber(6), is(5));
	}
	
	@Test
	public void calcLastPageNumber() {
		assertThat(paginationTdd.calcLastPageNumber(0), is(5));
		assertThat(paginationTdd.calcLastPageNumber(1), is(5));
		assertThat(paginationTdd.calcLastPageNumber(2), is(5));
		assertThat(paginationTdd.calcLastPageNumber(3), is(5));
		assertThat(paginationTdd.calcLastPageNumber(4), is(5));
		assertThat(paginationTdd.calcLastPageNumber(5), is(8));
		assertThat(paginationTdd.calcLastPageNumber(6), is(8));
		assertThat(paginationTdd.calcLastPageNumber(7), is(8));
	}
	
	@Test
	public void makeWholePagination() {
		String pagination = "<li><a href=?page=" + 0 + ">" + 1 + "</a></li>";
		pagination += "<li><a href=?page=" + 1 + ">" + 2 + "</a></li>";
		pagination += "<li><a href=?page=" + 2 + ">" + 3 + "</a></li>";
		pagination += "<li><a href=?page=" + 3 + ">" + 4 + "</a></li>";
		pagination += "<li><a href=?page=" + 4 + ">" + 5 + "</a></li>";
		pagination += "<li><a href=?page="+ 5 +">»</a></li>\r\n";
		assertThat(paginationTdd.makeWholePagination(1), is(pagination));
	}
	@Test
	public void makeWholePagination2() {
		String pagination = "<li><a href=?page="+ 0 +">«</a></li>\r\n";
		pagination += "<li><a href=?page=" + 5 + ">" + 6 + "</a></li>";
		pagination += "<li><a href=?page=" + 6 + ">" + 7 + "</a></li>";
		pagination += "<li><a href=?page=" + 7 + ">" + 8 + "</a></li>";
		assertThat(paginationTdd.makeWholePagination(5), is(pagination));
	}
	
	
	
	
}
