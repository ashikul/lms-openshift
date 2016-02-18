package com.gcit.lms.dao;

import com.gcit.lms.domain.Genre;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO extends BaseDAO implements ResultSetExtractor<List<Genre>> {

  public void createGenre(Genre genre) {
    template.update("insert into tbl_genre (genre_name) values(?)",
        genre.getGenreName());
  }

  public void updateGenre(Genre genre) {
    template.update("update tbl_genre set genre_name = ? where genre_id = ?",
        genre.getGenreName(), genre.getGenreId());
  }

  public void deleteGenre(Genre genre) {
    template.update("delete from tbl_genre where genre_id = ?"
            + " and genre_id not in (select genre_id from tbl_book_genres where genre_id = ?)",
        genre.getGenreId(), genre.getGenreId());
  }

  public List<Genre> getAllGenres() {
    return template.query("select * from tbl_genre", this);
  }

  public Genre getGenreById(int genre_id) {
    List<Genre> genres = template.query("select * from tbl_genre where genre_id = ?",
        new Object[]{genre_id}, this);

    if (genres != null && genres.size() > 0) {
      return genres.get(0);
    }
    return null;
  }

  public List<Genre> getGenresByBookId(int bookId) {
    return template.query("select * from tbl_genre where genre_id in (select genre_id from tbl_book_genres where bookId = ?)",
        new Object[]{bookId}, this);
  }

  @Override
  public List<Genre> extractData(ResultSet rs) {
    List<Genre> genres = new ArrayList<Genre>();

    try {
      while (rs.next()) {
        Genre g = new Genre();
        g.setGenreId(rs.getInt("genre_id"));
        g.setGenreName(rs.getString("genre_name"));

        genres.add(g);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return genres;
  }
}
