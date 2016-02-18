package com.gcit.lms.dao;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.LibraryBranch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookCopiesDAO extends BaseDAO implements ResultSetExtractor<List<BookCopies>> {
  @Autowired
  BookDAO bookdao;
  @Autowired
  LibraryBranchDAO branchdao;

  public void createCopies(BookCopies bookCopies) {
    template.update("insert into tbl_book_copies values (?, ?, ?)",
        bookCopies.getBook().getBookId(), bookCopies.getLibraryBranch().getBranchId(), bookCopies.getNoOfCopies());
  }

  public void updateCopies(BookCopies bookCopies) {
    template.update("update tbl_book_copies set noOfCopies = ? where branchId = ? and bookId = ?",
        bookCopies.getNoOfCopies(), bookCopies.getLibraryBranch().getBranchId(), bookCopies.getBook().getBookId());
  }

  public void deleteCopies(BookCopies bookCopies) {
    template.update("delete from tbl_book_copies where branchId = ? and bookId = ?",
        bookCopies.getLibraryBranch().getBranchId(), bookCopies.getBook().getBookId());
  }

  public List<BookCopies> getAllCopies() {
    return template.query("select * from tbl_book_copies", this);
  }

  public BookCopies getCopiesByIds(int branchId, int bookId) {
    List<BookCopies> copies = template.query("select * from tbl_book_copies where branchId = ? and bookId = ?",
        new Object[]{branchId, bookId}, this);

    if (copies != null && copies.size() > 0) {
      return copies.get(0);
    }
    return null;
  }

  public List<BookCopies> getAllCopiesByBranchId(int branchId, int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_book_copies where branchId = ?"),
        new Object[]{branchId}, this);
  }

  public List<BookCopies> getAllCopiesByTitleAndByBranchId(int branchId, int pageNo, int pageSize, String searchString) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_book_copies t1 join tbl_book t2 on t1.bookId = t2.bookId where branchId = ? and title like ?"),
        new Object[]{branchId, searchString}, this);
  }

  @Override
  public List<BookCopies> extractData(ResultSet rs) {
    List<BookCopies> copies = new ArrayList<BookCopies>();

    try {
      while (rs.next()) {
        BookCopies c = new BookCopies();
        c.setNoOfCopies(rs.getInt("noOfCopies"));

        Book book = bookdao.getBookById(rs.getInt("bookId"));
        c.setBook(book);

        LibraryBranch libraryBranch = branchdao.getBranchById(rs.getInt("branchId"));
        c.setLibraryBranch(libraryBranch);

        copies.add(c);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return copies;
  }
}
