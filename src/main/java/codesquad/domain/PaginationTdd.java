package codesquad.domain;

public class PaginationTdd {
	
	private static final int SHOW_SIZE = 2;
	private final int PAGE_SIZE = 5;
	private int totalPageSize;


	public PaginationTdd(int totalPageSize) {
		this.totalPageSize = totalPageSize;
	}
	
	public static PaginationTdd of(int totalQuestionSize) {
		return new PaginationTdd(calcTotalPageSize(totalQuestionSize));
	}
	
	public static int calcNowPage(String nowPages) {
		if (nowPages == null) {
			return 0;
		}
		return Integer.parseInt(nowPages);
	}

	
	public static int calcTotalPageSize(int totalQuestionSize) {
		if (totalQuestionSize % SHOW_SIZE == 0) {
			return totalQuestionSize / SHOW_SIZE;
		}
		return totalQuestionSize / SHOW_SIZE + 1;
	}

	public boolean isFirstPageSet(int nowPage) {
		return nowPage < PAGE_SIZE;
	}

	public boolean isLastPageSet(int nowPage) {
		return calcNextPageSet(nowPage) >= totalPageSize;
	}

	public String makeleftMoveButton(int nowPage) {
		return "<li><a href=?page=" + (nowPage - PAGE_SIZE) + ">«</a></li>\r\n";
	}

	public String makeRightMoveButton(int nowPage) {
		return "<li><a href=?page=" + calcNextPageSet(nowPage) + ">»</a></li>\r\n";
	}

	public String makeNormalButton(int i) {
		return "<li><a href=?page=" + i + ">" + (i + 1) + "</a></li>";
	}

	public int calcNextPageSet(int nowPage) {
		return  (((nowPage / PAGE_SIZE) * PAGE_SIZE) + PAGE_SIZE);
	}

	public int calcFirstPageNumber(int nowPage) {
		return (nowPage/PAGE_SIZE)*PAGE_SIZE;
	}
	
	public int calcLastPageNumber(int nowPage) {
		if(calcFirstPageNumber(nowPage)+PAGE_SIZE > totalPageSize) {
			return totalPageSize;
		}
		return calcFirstPageNumber(nowPage) + PAGE_SIZE;
	}
	

	public String makeWholePagination(int nowPage) {
		String pagination ="";
		
		if(!isFirstPageSet(nowPage)) {
			pagination += makeleftMoveButton(nowPage);
		}
		
		for(int i = calcFirstPageNumber(nowPage); i < calcLastPageNumber(nowPage); i++) {
			pagination += "<li><a href=?page=" + i + ">" + (i + 1) + "</a></li>";
		}
		
		if(!isLastPageSet(nowPage)) {
			pagination += makeRightMoveButton(nowPage);
		}
		return pagination;
	}
	

}
