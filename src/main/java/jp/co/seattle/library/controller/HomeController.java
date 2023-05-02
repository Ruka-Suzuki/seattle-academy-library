package jp.co.seattle.library.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.service.BooksService;

/**
 * Handles requests for the application home page.
 */
@Controller // APIの入り口
public class HomeController {
	final static Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private BooksService booksService;

	/**
	 * Homeボタンからホーム画面に戻るページ
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String transitionHome(Model model) {
		//書籍の一覧情報を取得（タスク３）
		List<BookInfo> getedBookList = booksService.getBookList();
		model.addAttribute("bookList",getedBookList);
		if(getedBookList.isEmpty()) {
			String message = "書籍データが0件です";
			model.addAttribute("resultMessage",message);
		}
		return "home";
	} 
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchbooks (@RequestParam ("search")String searchword, Model model) {
		//検索情報の取得
		List<BookInfo> searchedBookList = booksService.searchBookList(searchword);
		if(searchedBookList.isEmpty()) {
			String message = "書籍データが0件です";
			model.addAttribute("resultMessage",message);
		}else {
			model.addAttribute("bookList",searchedBookList);
		}
		return "home";
	} 
} 
