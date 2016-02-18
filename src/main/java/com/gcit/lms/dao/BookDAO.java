package com.gcit.lms.dao;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO extends BaseDAO implements ResultSetExtractor<List<Book>> {
  @Autowired
  AuthorDAO adao;
  @Autowired
  GenreDAO gdao;
  @Autowired
  PublisherDAO pdao;

  public void createBook(final Book book) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(new PreparedStatementCreator() {
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into tbl_book (title, pubId) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, book.getTitle());
        ps.setInt(2, book.getPublisher().getPublisherId());
        return ps;
      }
    }, keyHolder);
    int bookId = keyHolder.getKey().intValue();

    for (Author a : book.getAuthors())
      template.update("insert into tbl_book_authors (bookId, authorId) values (?,?)",
          bookId, a.getAuthorId());

    for (Genre g : book.getGenres())
      template.update("insert into tbl_book_genres (bookId, genre_id) values (?,?)",
          bookId, g.getGenreId());
  }

  public void updateBook(Book book) {
    template.update("update tbl_book set title = ?, pubId = ? where bookId = ?",
        book.getTitle(), book.getPublisher().getPublisherId(), book.getBookId());

    template.update("delete from tbl_book_authors where bookId = ?",
        book.getBookId());
    for (Author a : book.getAuthors())
      template.update("insert into tbl_book_authors values (?, ?)",
          book.getBookId(), a.getAuthorId());

    template.update("delete from tbl_book_genres where bookId = ?",
        book.getBookId());
    for (Genre g : book.getGenres())
      template.update("insert into tbl_book_genres (bookId, genre_id) values (?,?)",
          book.getBookId(), g.getGenreId());
  }

  public void deleteBook(Book book) {
    template.update("delete from tbl_book_authors where bookId = ?",
        book.getBookId());
    template.update("delete from tbl_book_genres where bookId = ?",
        book.getBookId());
    template.update("delete from tbl_book_copies where bookId = ?",
        book.getBookId());
    template.update("delete from tbl_book_loans where bookId = ?",
        book.getBookId());
    template.update("delete from tbl_book where bookId = ?",
        book.getBookId());
  }

  public List<Book> getAllBooks(int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_book"), this);
  }

  public Book getBookById(int bookId) {
    List<Book> books = template.query("select * from tbl_book where bookId = ?",
        new Object[]{bookId}, this);

    if (books != null && books.size() > 0) {
      return books.get(0);
    }
    return null;
  }

  public List<Book> getBooksByName(String searchString, int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_book where title like ?"),
        new Object[]{searchString}, this);
  }

  public int getBooksCount() {
    return template.queryForObject("select count(*) as count from tbl_book", Integer.class);
  }

  @Override
  public List<Book> extractData(ResultSet rs) {
    List<Book> books = new ArrayList<Book>();

    try {
      while (rs.next()) {
        Book b = new Book();
        b.setBookId(rs.getInt("bookId"));
        b.setTitle(rs.getString("title"));

        b.setPublisher(pdao.getPublisherById(rs.getInt("pubId")));

        List<Author> authors = template.query(
            "select * from tbl_author where authorId in (select authorId from tbl_book_authors where bookId = ?)",
            new Object[]{b.getBookId()}, adao);
        b.setAuthors(authors);

        List<Genre> genres = template.query(
            "select * from tbl_genre where genre_id in (select genre_id from tbl_book_genres where bookId = ?)",
            new Object[]{b.getBookId()}, gdao);
        b.setGenres(genres);

        books.add(b);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return books;
  }
}
