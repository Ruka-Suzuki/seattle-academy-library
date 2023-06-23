<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=utf8"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>ホーム｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
</head>
<body class="wrapper">
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
    <main>
        <h1>Home</h1>
        <form action="search" class="search-form-003" method="get">
            <label> <input type="text" name="search" placeholder="タイトルまたはジャンルを入力">
            </label>
            <button type="submit" aria-label="検索"></button>
        </form>
        <a href="<%=request.getContextPath()%>/addBook" class="btn_add_book">書籍の追加</a> <a href="<%=request.getContextPath()%>/favoriteBook" class="btn_add_book">お気に入り登録済み</a><a href="<%=request.getContextPath()%>/libraryBook" class="btn_add_book">所蔵図書</a>
        <form action=turn method="get">
        <label class="selectbox-003">
             <select name="turn"  value="${bookInfo.bookId}">
                <option value="">選択してください</option>
                <option value="タイトル降順">タイトル降順</option>
                <option value="著者名昇順">著者名昇順</option>
                <option value="著者名降順">著者名降順</option>
                <option value="出版年昇順">出版年昇順</option>
                <option value="出版年降順">出版年降順</option>
            </select>
            <button class="turn_up">並び替え</button>
        </label>
        </form>
        <div class="content_body">
            <c:if test="${!empty resultMessage}">
                <div class="error_msg">${resultMessage}</div>
            </c:if>
            <div>
                <div class="booklist">
                    <c:forEach var="bookInfo" items="${bookList}">
                        <div class="books">
                            <form method="get" class="book_thumnail" action="editBook">
                                <a href="javascript:void(0)" onclick="this.parentNode.submit();"> <c:if test="${empty bookInfo.thumbnail}">
                                        <img class="book_noimg" src="resources/img/noImg.png">
                                    </c:if> <c:if test="${!empty bookInfo.thumbnail}">
                                        <img class="book_noimg" src="${bookInfo.thumbnail}">
                                    </c:if>
                                </a> <input type="hidden" name="bookId" value="${bookInfo.bookId}">
                            </form>
                            <ul>
                                <li class="book_title">${bookInfo.title}</li>
                                <li class="book_author">${bookInfo.author}(著)</li>
                                <li class="book_publisher">出版社：${bookInfo.publisher}</li>
                                <li class="book_publish_date">出版日：${bookInfo.publishDate}</li>
                                <li class="book_genre">ジャンル：${bookInfo.genre}</li>
                                <c:if test="${bookInfo.favorite != 'like'}">
                                    <form method="get" action="favorite" name="favorite">
                                        <p align="justify">
                                            <button class="btn_favorite">🤍この本をお気に入り</button>
                                            <input type="hidden" name="bookId" value="${bookInfo.bookId}">
                                        </p>
                                    </form>
                                </c:if>
                                <c:if test="${bookInfo.favorite == 'like'}">
                                    <form method="get" action="unfavorite" name="unfavorite">
                                        <p align="justify">
                                            <button class="btn_unfavorite">❤️‍🩹お気に入り登録済</button>
                                            <input type="hidden" name="bookId" value="${bookInfo.bookId}">
                                        </p>
                                    </form>
                                </c:if>
                            </ul>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </main>
</body>
</html>
