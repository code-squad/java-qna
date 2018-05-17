package codesquad.domain;

public class Pagination {

	private final int PAGE_SIZE = 2;
	private final int SHOW_SIZE = 2;
	private final int CALC_VALUE = 1;
	private int totalPageSize;

	
	public Pagination(int questionSize) {
		totalPageSize = (questionSize / SHOW_SIZE) + CALC_VALUE;
	}

	public StringBuilder makePagination(int nowPage) {
		StringBuilder pagination = new StringBuilder();
		
		if(nowPage >= PAGE_SIZE) {
			pagination.append("<li><a href=?page=" + (calcNowPageSet(nowPage)-PAGE_SIZE) + ">«</a></li>\r\n");
		}

		for (int i = calcStartPage(nowPage); i < calcLastPage(nowPage); i++) {
			pagination.append("<li><a href=?page=" + i + ">" + (i + CALC_VALUE) + "</a></li>");
		}

		if (!isLastPageSet(nowPage)) {
			pagination.append("<li><a href=?page=" + (calcNowPageSet(nowPage)+PAGE_SIZE) + ">»</a></li>\r\n");
		}

		return pagination;
	}
	

	public boolean isLastPageSet(int nowPage) {
		return calcStartPage(nowPage) + PAGE_SIZE > totalPageSize-CALC_VALUE;
	}

	public int calcNowPageSet(int nowPage) {
		return (nowPage/PAGE_SIZE)*PAGE_SIZE;
	}
	
	public static int calcNowPage(String nowPages) {
		if (nowPages == null) {
			return 0;
		}
		return Integer.parseInt(nowPages);
	}

	public int calcStartPage(int nowPage) {
		return (nowPage / PAGE_SIZE) * PAGE_SIZE;
	}

	public int calcLastPage(int nowPage) {
		int last_page = calcStartPage(nowPage) + PAGE_SIZE;
		if (last_page > totalPageSize) {
			return totalPageSize;
		}
		return last_page;
	}

}
