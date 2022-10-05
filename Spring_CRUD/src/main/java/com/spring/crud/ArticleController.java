package com.spring.crud;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ArticleController {

	// 의존 주입
	@Autowired
	private ArticleService articleService;
	
	// @ModelAttribute("article")은 아래의 모든 메서드들에게
	// 다음의 모든 메서드들에게 request.setAttribute("article", articleService.selectById(vo))을 해주는 것과 같은 역할을 합니다.
	@ModelAttribute("article")
	public ArticleVO getArtcle() {
		ArticleVO vo = new ArticleVO();
		return articleService.selectById(vo);
	}
	
	// 1. 게시글 등록 기능의 insert() 메서드 정의
	@RequestMapping("/article/write.do")
	public String insert(HttpServletRequest request, HttpServletResponse response, ArticleVO vo) {
		return process(request, response, vo, "insert");
		
	}
	
	// 2. 최근 게시글 조회 기능의 selectById()메서드 정의
	@RequestMapping("/article/read.do")
	public String selectById(ArticleVO vo) {
		return "/WEB-INF/views/readArticle.jsp" ;
	}

	// 3. 최근 게시글 수정 기능의 update() 메서드 정의
	@RequestMapping("/article/modify.do")
	public String update(HttpServletRequest request, HttpServletResponse response, ArticleVO vo) {
		return process(request, response, vo, "update");
	}

	// 4. 최근 게시글 수정 기능의 delete() 메서드 정의
	@RequestMapping("/article/delete.do")
	public String delete(HttpServletRequest request, HttpServletResponse response, ArticleVO vo) {
		return process(request, response, vo, "delete");
	}
	
	// get, post, 그 밖의 요청들의 분기 처리를 해주는 역할을 하는 process()메서드 정의
	private String process(HttpServletRequest request, HttpServletResponse response, ArticleVO vo, String query) {
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return viewResolverForm(query);
		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			executeQuery(query, vo);
			return viewResolverSuccess(query);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			
			return null;
		}
	}

	// 폼 화면 경로를 만들어주는 viewResolverForm()메서드 정의
	private String viewResolverForm(String viewName) {
		return "/WEB-INF/views/" + viewName + "Form.jsp";
	}
	
	// 성공 화면 경로를 만들어주는 viewResolverSuccess()메서드 정의
	private String viewResolverSuccess(String viewName) {
		return "/WEB-INF/views/" + viewName + "Success.jsp";
	}
	
	// 입력한 쿼리문에 따라 다른 메서드를 실행 처리하는 executeQuery()메서드 정의
	private void executeQuery(String query, ArticleVO vo) {
		if(query == "insert") {
			articleService.insert(vo); 
		} else if (query == "update") {
			articleService.update(vo);
		} else if (query == "delete") {
			articleService.delete(vo);
		}
	}
	
}
