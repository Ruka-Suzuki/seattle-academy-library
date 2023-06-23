package jp.co.seattle.library.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 書籍基本情報格納DTO
 */
@Configuration
@Data
public class BookInfo {

	private int bookId;

	private String title;

	private String author;

	private String publisher;

	private String publishDate;

	private String thumbnail;
	
	private String favorite;
	
	private String genre;
	
	private String library;
	
	private String review;
	
	private String star;

	public BookInfo() {

	}

	// コンストラクタ
	public BookInfo(int bookId, String title, String author, String publisher, String publishDate, String thumbnail, String favorite, String genre, String review, String star) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.publishDate = publishDate;
		this.thumbnail = thumbnail;
		this.favorite = favorite;
		this.genre = genre;
		this.review = review;
		this.star = star;
	}

}