package jp.co.seattle.library.service;

import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 * booksテーブルに関する処理を実装する
 */
@Service
public class BooksService {
	final static Logger logger = LoggerFactory.getLogger(BooksService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 書籍リストを取得する
	 *
	 * @return 書籍リスト
	 */
	public List<BookInfo> getBookList() {

		// TODO 書籍名の昇順で書籍情報を取得するようにSQLを修正（タスク３）
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT id, title, author, publisher, publish_date, thumbnail_url ,favorite, genre, library, review, star FROM books ORDER BY title ASC;",
				new BookInfoRowMapper());

		return getedBookList;
	}

	/**
	 * 書籍IDに紐づく書籍詳細情報を取得する
	 *
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */
	public BookDetailsInfo getBookInfo(int bookId) {
		String sql = "SELECT id, title, author, publisher, publish_date, isbn, description, thumbnail_url, thumbnail_name, favorite, genre, library, review, star FROM books WHERE books.id = ? ORDER BY title ASC;";

		BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper(), bookId);

		return bookDetailsInfo;
	}

	/**
	 * 書籍を登録する
	 *
	 * @param bookInfo 書籍情報
	 * @return bookId 書籍ID
	 */
	public void registBook(BookDetailsInfo bookInfo) {
		// TODO 取得した書籍情報を登録し、その書籍IDを返却するようにSQLを修正（タスク４）
		String sql = "INSERT INTO books(title, author, publisher, publish_date, thumbnail_name, thumbnail_url, isbn, description, reg_date, upd_date, genre, review, star) VALUES (?,?,?,?,?,?,?,?,now(),now(),?,?,?) RETURNING id;";

		 jdbcTemplate.queryForObject(sql, int.class, bookInfo.getTitle(), bookInfo.getAuthor(),
				bookInfo.getPublisher(), bookInfo.getPublishDate(), bookInfo.getThumbnailName(),
				bookInfo.getThumbnailUrl(), bookInfo.getIsbn(), bookInfo.getDescription(), bookInfo.getGenre(), bookInfo.getReview(), bookInfo.getStar());
		
	}

	/**
	 * 書籍を削除する
	 * 
	 * @param bookId 書籍ID
	 */
	public void deleteBook(int bookId) {
		// TODO 対象の書籍を削除するようにSQLを修正（タスク6）
		String sql = "DELETE FROM books WHERE id = ?;";
		jdbcTemplate.update(sql, bookId);
	}

	/**
	 * 書籍情報を更新する
	 * 
	 * @param bookInfo
	 */
	public void updateBook(BookDetailsInfo bookInfo) {
		String sql;
		if (bookInfo.getThumbnailUrl() == null) {
			// TODO 取得した書籍情報を更新するようにSQLを修正（タスク５）

			sql = "UPDATE books SET title = ?, author = ?, publisher = ?, publish_date = ?, isbn = ?, description = ?, upd_date = now(), genre = ?, review = ?, star = ? WHERE id = ?;";
			jdbcTemplate.update(sql, bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublisher(),
					bookInfo.getPublishDate(), bookInfo.getIsbn(), bookInfo.getDescription(), bookInfo.getGenre(),bookInfo.getReview(), bookInfo.getStar(),
					bookInfo.getBookId());
		} else {
			// TODO 取得した書籍情報を更新するようにSQLを修正（タスク５）
			sql = "UPDATE books SET title = ?, author = ?, publisher = ?,publish_date = ?, thumbnail_name = ?, thumbnail_url = ?, isbn = ?, description = ?, upd_date = now(), genre = ?, review = ?, star = ? WHERE id = ?;";
			jdbcTemplate.update(sql, bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublisher(),
					bookInfo.getPublishDate(), bookInfo.getThumbnailName(), bookInfo.getThumbnailUrl(),
					bookInfo.getIsbn(), bookInfo.getDescription(), bookInfo.getGenre(), bookInfo.getReview(), bookInfo.getStar(),bookInfo.getBookId());
		}
	}

	/**
	 * 書籍リストを取得する
	 *
	 * @return 書籍リスト
	 */
	public List<BookInfo> searchBookList(String searchword, String searchwords) {

		// TODO 検索情報を取得
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books WHERE title like concat ('%',?,'%') OR genre like concat ('%',?,'%') ORDER BY title asc;",
				new BookInfoRowMapper(), searchword, searchwords);

		return getedBookList;
	}

	public void favoritebooks(int bookId) {
		String sql = "UPDATE books SET favorite= 'like' WHERE id = ?;";
		jdbcTemplate.update(sql, bookId);

	}

	public void unfavoritebooks(int bookId) {
		String sql = "UPDATE books SET favorite= 'unlike' WHERE id = ?;";
		jdbcTemplate.update(sql, bookId);
	}

	public List<BookInfo> favoriteBookList() {

		// TODO 書籍名の昇順で書籍情報を取得するようにSQLを修正（タスク３）
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books WHERE favorite = 'like' ORDER BY title ASC;",
				new BookInfoRowMapper());

		return getedBookList;
	}

	public void librarybooks(String value, int bookId) {
		String sql = "UPDATE books SET library= ? WHERE id = ?;";
		jdbcTemplate.update(sql, value, bookId);

	}

	public List<BookInfo> libraryBookList() {

		// TODO 書籍名の昇順で書籍情報を取得するようにSQLを修正（タスク３）
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books WHERE library = 'stock' ORDER BY title ASC;",
				new BookInfoRowMapper());

		return getedBookList;
	}

	public List<BookInfo> untitleBookList() {

		// TODO 書籍名の降順で書籍情報を取得する
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY title DESC;",
				new BookInfoRowMapper());

		return getedBookList;
	}

	public List<BookInfo> authorBookList() {

		// TODO 著者名の昇順で書籍情報を取得する
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY author ASC;",
				new BookInfoRowMapper());

		return getedBookList;
	}

	public List<BookInfo> unauthorBookList() {

		// TODO 著者名の降順で書籍情報を取得する
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY author DESC;",
				new BookInfoRowMapper());

		return getedBookList;
	}

	public List<BookInfo> dateBookList() {

		// TODO 出版年の昇順で書籍情報を取得する
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY publish_date ASC;",
				new BookInfoRowMapper());

		return getedBookList;
	}

	public List<BookInfo> undateBookList() {

		// TODO 出版年の降順で書籍情報を取得する
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY publish_date DESC;",
				new BookInfoRowMapper());

		return getedBookList;
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
		
	}
	/**
	 * APIの呼び出し
	 * @param bookInfo 書籍情報
	 * @param restTemplate 
	 * @return メッセージ
	 */
	public String callAPI(BookDetailsInfo bookInfo, RestTemplate restTemplate) {
		//RestTemplate restTemplate必要？
		
		//プロパティファイルからAPIのURLを取得
		ResourceBundle rb = ResourceBundle.getBundle("output");
		String url = rb.getString("url");
		
		try {
			//API呼び出し
			String responseMessage = restTemplate.postForObject(url, bookInfo, String.class);
			
			return responseMessage;
			
		}catch (Exception e) {
			//TODO 自動生成された　catchブロック
			e.printStackTrace();
			return "API接続に失敗しました";
			
		}
	}
}
