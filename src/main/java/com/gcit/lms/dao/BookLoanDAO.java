package com.gcit.lms.dao;

import com.gcit.lms.domain.BookLoan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookLoanDAO extends BaseDAO implements ResultSetExtractor<List<BookLoan>> {
  @Autowired
  BorrowerDAO borrowerdao;
  @Autowired
  BookDAO bookdao;
  @Autowired
  LibraryBranchDAO branchdao;


  public void createLoan(BookLoan bookLoan) {
    template.update("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) values(?, ?, ?, ?, ?)",
        bookLoan.getBook().getBookId(), bookLoan.getLibraryBranch().getBranchId(), bookLoan.getBorrower().getCardNo(), bookLoan.getDateOut(), bookLoan.getDueDate());
  }

  public void updateLoan(BookLoan bookLoan) {
    template.update("update tbl_book_loans set dueDate = ? where cardNo = ? and bookId = ? and branchId = ? and dateOut = ? and dateIn is null",
        bookLoan.getDueDate(), bookLoan.getBorrower().getCardNo(), bookLoan.getBook().getBookId(), bookLoan.getLibraryBranch().getBranchId(), bookLoan.getDateOut());
  }

  public void checkin(BookLoan bookLoan) {
    template.update("update tbl_book_loans set dateIn = curdate() where cardNo = ? and bookId = ? and branchId = ? and dateIn is null",
        bookLoan.getBorrower().getCardNo(), bookLoan.getBook().getBookId(), bookLoan.getLibraryBranch().getBranchId());
  }

  public void deleteLoan(BookLoan bookLoan) {
    template.update("delete from tbl_book_loans where cardNo = ? and bookId = ? and branchId = ? and dateOut = ?",
        bookLoan.getBorrower().getCardNo(), bookLoan.getBook().getBookId(), bookLoan.getLibraryBranch().getBranchId());
  }

  public List<BookLoan> getLoansByCardNo(int cardNo) {
    return template.query("select * from tbl_book_loans where cardNo = ? and dateIn is null order by branchId",
        new Object[]{cardNo}, this);
  }

  public List<BookLoan> getLoansByCardNoAndBranchId(int cardNo, int branchId, int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_book_loans where cardNo = ? and branchId = ? and dateIn is null"),
        new Object[]{cardNo, branchId}, this);
  }

  public BookLoan getLoanByIds(int cardNo, int bookId, int branchId) {
    List<BookLoan> bookLoen = template.query("select * from tbl_book_loans where cardNo = ? and bookId = ? and branchId = ? and dateIn is null",
        new Object[]{cardNo, bookId, branchId}, this);

    if (bookLoen != null && bookLoen.size() > 0) {
      return bookLoen.get(0);
    }

    return null;
  }

  public List<BookLoan> getLoansByTitleAndCardNoAndBranchId(String searchString, int cardNo, int branchId, int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_book_loans t1 join tbl_book t2 on t1.bookId = t2.bookId "
            + "where cardNo = ? and branchId = ? and dateIn is null and title like ?"),
        new Object[]{cardNo, branchId, searchString}, this);
  }

  public List<BookLoan> getLoansByDueDate(String searchString, int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_book_loans where dueDate like ? and dateIn is null"),
        new Object[]{searchString}, this);
  }

  public List<BookLoan> getAllLoans(int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_book_loans where dateIn is null"), this);
  }

  public int getLoansCount() {
    return template.queryForObject("select count(*) as count from tbl_book_loans where dateIn is null", Integer.class);
  }

  public int getLoansByCardNoCount(int cardNo) {
    return template.queryForObject("select count(distinct branchId) as count from tbl_book_loans where dateIn is null and cardno = ?",
        new Object[]{cardNo}, Integer.class);
  }

  public int getLoansByCardNoAndBranchIdCount(int cardNo, int branchId) {
    return template.queryForObject("select count(*) as count from tbl_book_loans where dateIn is null and cardno = ? and branchId = ?",
        new Object[]{cardNo, branchId}, Integer.class);
  }

  @Override
  public List<BookLoan> extractData(ResultSet rs) {
    List<BookLoan> bookLoen = new ArrayList<BookLoan>();

    try {
      while (rs.next()) {
        BookLoan l = new BookLoan();
        l.setDateOut(rs.getDate("dateOut"));
        l.setDueDate(rs.getDate("dueDate"));
        l.setDateIn(rs.getDate("dateIn"));
        l.setBorrower(borrowerdao.getBorrowerById(rs.getInt("cardNo")));
        l.setBook(bookdao.getBookById(rs.getInt("bookId")));
        l.setLibraryBranch(branchdao.getBranchById(rs.getInt("branchId")));

        bookLoen.add(l);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return bookLoen;
  }
}
