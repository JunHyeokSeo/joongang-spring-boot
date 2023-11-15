package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.BoardBean;
import com.example.demo.service.BoardServiceImpl;

@Controller
public class BoardController {

	@Autowired
	private BoardServiceImpl boardService;
	
	@RequestMapping("test")
	public String test(){
		System.out.println("컨트롤러 들어옴");
		return "test";
	}
	
	/* 게시판 글쓰기 폼 */
	@RequestMapping("board_write")
	public String board_write() {
		return "board_write";
	}

	/* 게시판 저장 */
	@RequestMapping("board_write_ok")
	public String board_write_ok(@ModelAttribute BoardBean board)
			throws Exception {
	
		boardService.insert(board);// 저장 메서드 호출	

		return "redirect:/board_list";
	}

	
	/* 게시판 목록 */
	@RequestMapping("board_list")
	public String list(Model model, 
			           HttpServletRequest request) throws Exception {

		List<BoardBean> boardlist = new ArrayList<BoardBean>();

		int page = 1;
		int limit = 10; // 한 화면에 출력할 레코드수

		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		// 데이터 갯수
		int listcount = boardService.getListCount();

		// 페이지 번호(page)를 DAO클래스에게 전달한다.
		boardlist = boardService.getBoardList(page); 

		// 총 페이지
		int maxpage = listcount / limit + ((listcount % limit == 0) ? 0 : 1);

		int startpage = ((page - 1) / 10) * limit + 1; 		// 1, 11, 21..
		int endpage = startpage + 10 - 1; 			   		// 10, 20, 30..

		if (endpage > maxpage)
			endpage = maxpage;		
		
		int num = listcount-(page-1)*10;

//		model.addAttribute("num", num);
		model.addAttribute("page", page);
		model.addAttribute("startpage", startpage);
		model.addAttribute("endpage", endpage);
		model.addAttribute("maxpage", maxpage);
		model.addAttribute("listcount", listcount);
		model.addAttribute("boardlist", boardlist);
		
		return "board_list";
	}

	
	/* 게시판 내용보기,삭제폼,수정폼,답변글폼 */
	@RequestMapping(value = "board_cont")
	public String board_cont(@RequestParam("board_num") int board_num,
			@RequestParam("page") String page,
			@RequestParam("state") String state, 
			Model model) throws Exception {
		System.out.println("board_num:"+board_num);

		if (state.equals("cont")) { 			// 상세보기할때 조회수 증가
			boardService.hit(board_num); 	
		}

		BoardBean board = boardService.board_cont(board_num);

		model.addAttribute("bcont", board);
		model.addAttribute("page", page);

		if (state.equals("cont")) {	            // 상세 페이지
			 String board_cont = board.getBoard_content().replace("\n","<br>");
			 model.addAttribute("board_cont", board_cont);
			 return "board_cont";	
		} else if (state.equals("edit")) {		// 수정폼
			return "board_edit";
		} else if (state.equals("del")) {		// 삭제폼
			return "board_del";
		} else if (state.equals("reply")) {		// 답변달기 폼
			return "board_reply";
		}
		return null;
	}

	
	/* 게시판 수정 */
	@RequestMapping(value = "/board_edit_ok", method = RequestMethod.POST)
	public String board_edit_ok(@ModelAttribute BoardBean b,
								@RequestParam("page") String page,
								Model model) throws Exception {

		// 수정 메서드 호출
		BoardBean board = boardService.board_cont(b.getBoard_num());
		int result = 0;
		
		if (!board.getBoard_pass().equals(b.getBoard_pass())) {// 비번이 같다면
			result = 1;
			model.addAttribute("result", result);
			
			return "updateResult";

		} else {
			// 수정 메서드 호출
			boardService.edit(b);			
		}	
		
		return "redirect:/board_cont?board_num=" + b.getBoard_num()
					+ "&page=" + page + "&state=cont";
	}

	
	/* 게시판 삭제 */
	@RequestMapping(value = "/board_del_ok", method = RequestMethod.POST)
	public String board_del_ok(@RequestParam("board_num") int board_num,
							   @RequestParam("page") int page,
							   @RequestParam("pwd") String board_pass,
							   Model model) throws Exception {

		BoardBean board = boardService.board_cont(board_num);
		int result=0;
		
		if (!board.getBoard_pass().equals(board_pass)) {
			result = 1;
			model.addAttribute("result", result);

			return "deleteResult";

		} else {
			boardService.del_ok(board_num);		
		}
		
		return "redirect:/board_list?page=" + page;
	}

	
	/* 게시판 답변달기 저장 */
	@RequestMapping(value = "/board_reply_ok", method = RequestMethod.POST)
	public String board_reply_ok(@ModelAttribute BoardBean b,
								 @RequestParam("page") String page) throws Exception {

		boardService.reply_ok(b);

		return "redirect:/board_list?page=" + page;
	}
	
	
}
