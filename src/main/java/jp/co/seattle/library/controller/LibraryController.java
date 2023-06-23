package jp.co.seattle.library.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.service.BooksService;

/**
 * Handles requests for the application home page.
 */
@Controller // APIの入り口
public class LibraryController {
	final static Logger logger = LoggerFactory.getLogger(LibraryController.class);

	@Autowired
	private BooksService booksService;

	/**
	 * Homeボタンからホーム画面に戻るページ
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/libraryBook", method = RequestMethod.GET)
	public String libraryHome(Model model) {
		//書籍の一覧情報を取得（タスク３）
		List<BookInfo> libraryBookList = booksService.libraryBookList();
		
		if (libraryBookList.isEmpty()) {
			String message = "貸し出し中の書籍がありません。";
			model.addAttribute("resultMessage", message);
		}else {
			model.addAttribute("bookList", libraryBookList);
		}
		return "favorite";
	}
}
	


