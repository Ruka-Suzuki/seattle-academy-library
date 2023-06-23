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
		model.addAttribute("bookList", getedBookList);
		if (getedBookList.isEmpty()) {
			model.addAttribute("resultMessage", "書籍データが0件です");
		}
		return "home";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchbooks(@RequestParam("search") String searchword, Model model) {
		//検索情報の取得
		List<BookInfo> searchedBookList = booksService.searchBookList(searchword, searchword);
		if (searchedBookList.isEmpty()) {
			String message = "書籍データが0件です";
			model.addAttribute("resultMessage", message);
		} else {
			model.addAttribute("bookList", searchedBookList);
		}
		return "home";
	}

	@RequestMapping(value = "/favorite", method = RequestMethod.GET)
	public String favoritebooks(@RequestParam("bookId") int bookId, Model model) {
		//お気に入りに追加

		booksService.favoritebooks(bookId);
		return "redirect:/home";
	}

	@RequestMapping(value = "/unfavorite", method = RequestMethod.GET)
	public String unfavoritebooks(@RequestParam("bookId") int bookId, Model model) {
		//お気に入りに追加

		booksService.unfavoritebooks(bookId);
		return "redirect:/home";

	}

	@RequestMapping(value = "/lend", method = RequestMethod.POST)
	public String librarybooks(@RequestParam("bookId") int bookId, @RequestParam("value") String value, Model model) {
		//お気に入りに追加

		booksService.librarybooks(value, bookId);
		return "redirect:/home";

	}

	@RequestMapping(value = "/turn", method = RequestMethod.GET)
	public String turnbooks(@RequestParam("turn") String turnsBook, Model model) {
		//検索情報の取得
		if (turnsBook.equals("タイトル降順")) {
			List<BookInfo> turnedBookList = booksService.untitleBookList();
			model.addAttribute("bookList", turnedBookList);
		}
		if (turnsBook.equals("著者名昇順")) {
			List<BookInfo> turnedBookList = booksService.authorBookList();
			model.addAttribute("bookList", turnedBookList);
		}
		if (turnsBook.equals("著者名降順")) {
			List<BookInfo> turnedBookList = booksService.unauthorBookList();
			model.addAttribute("bookList", turnedBookList);
		}
		if (turnsBook.equals("出版年昇順")) {
			List<BookInfo> turnedBookList = booksService.dateBookList();
			model.addAttribute("bookList", turnedBookList);
		}
		if (turnsBook.equals("出版年降順")) {
			List<BookInfo> turnedBookList = booksService.undateBookList();
			model.addAttribute("bookList", turnedBookList);
		}

		return "home";
	}

}
