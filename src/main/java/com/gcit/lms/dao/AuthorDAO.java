package com.gcit.lms.dao;

import com.gcit.lms.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO extends BaseDAO implements ResultSetExtractor<List<Author>> {
  @Autowired
  BookDAO bdao;

  public void createAuthor(Author author) {
    template.update("insert into tbl_author (authorName) values(?)",
        author.getAuthorName());
  }

  public void updateAuthor(Author author) {
    template.update("update tbl_author set authorName = ? where authorId = ?",
        author.getAuthorName(), author.getAuthorId());
  }

  public void deleteAuthor(Author author) {
    template.update("delete from tbl_author where authorId = ?",
        author.getAuthorId());
  }

  public List<Author> getAllAuthors(int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_author"), this);
  }

  public Author getAuthorById(int authorId) {
    List<Author> authors = template.query("select * from tbl_author where authorId = ?",
        new Object[]{authorId}, this);

    if (authors != null && authors.size() > 0) {
      return authors.get(0);
    }
    return null;
  }

  public List<Author> getAuthorsByName(String searchString, int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_author where authorName like ?"),
        new Object[]{searchString}, this);
  }

  public List<Author> getAuthorsByBookId(int bookId) {
    return template.query("select * from tbl_author where authorId in (select authorId from tbl_book_authors where bookId = ?)",
        new Object[]{bookId}, this);
  }

  public int getAuthorsCount() {
    return template.queryForObject("select count(*) as count from tbl_author", Integer.class);
  }

  @Override
  public List<Author> extractData(ResultSet rs) {
    List<Author> authors = new ArrayList<Author>();

    try {
      while (rs.next()) {
        Author a = new Author();
        a.setAuthorId(rs.getInt("authorId"));
        a.setAuthorName(rs.getString("authorName"));

        authors.add(a);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return authors;
  }
}
