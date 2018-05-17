package codesquad.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import codesquad.domain.PaginationTdd;
import codesquad.domain.QuestionRepository;

@Controller
public class HomeController {
	
	@Autowired
	private	QuestionRepository questionRepository;

	//localhost:8080/emp/list?page=0&size=3&sort=ename,desc
	@GetMapping("")
	public String home(Model model, String page, @PageableDefault(sort = {"id"}, direction = Direction.DESC, size=2) Pageable pageAble) {
		model.addAttribute("questions", questionRepository.findAll(pageAble));
		
//		Pagination pagination = new Pagination(questionRepository.findAll().size());
//		model.addAttribute("pagination", pagination.makePagination(Pagination.calcNowPage(page)));
		
		PaginationTdd pagination = PaginationTdd.of(questionRepository.findAll().size());
		model.addAttribute("pagination", pagination.makeWholePagination(PaginationTdd.calcNowPage(page)));
		return "index";
	}
}
