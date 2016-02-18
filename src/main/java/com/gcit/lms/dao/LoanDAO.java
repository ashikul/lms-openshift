package com.gcit.lms.dao;

import com.gcit.lms.domain.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO extends BaseDAO implements ResultSetExtractor<List<Loan>> {
  @Autowired
  BorrowerDAO borrowerdao;
  @Autowired
  BookDAO bookdao;
  @Autowired
  BranchDAO branchdao;


  public void createLoan(Loan loan) {
    template.update("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) values(?, ?, ?, ?, ?)",
        loan.getBook().getBookId(), loan.getBranch().getBranchId(), loan.getBorrower().getCardNo(), loan.getDateOut(), loan.getDueDate());
  }

  public void updateLoan(Loan loan) {
    template.update("update tbl_book_loans set dueDate = ? where cardNo = ? and bookId = ? and branchId = ? and dateOut = ? and dateIn is null",
        loan.getDueDate(), loan.getBorrower().getCardNo(), loan.getBook().getBookId(), loan.getBranch().getBranchId(), loan.getDateOut());
  }

  public void checkin(Loan loan) {
    template.update("update tbl_book_loans set dateIn = curdate() where cardNo = ? and bookId = ? and branchId = ? and dateIn is null",
        loan.getBorrower().getCardNo(), loan.getBook().getBookId(), loan.getBranch().getBranchId());
  }

  public void deleteLoan(Loan loan) {
    template.update("delete from tbl_book_loans where cardNo = ? and bookId = ? and branchId = ? and dateOut = ?",
        loan.getBorrower().getCardNo(), loan.getBook().getBookId(), loan.getBranch().getBranchId());
  }

  public List<Loan> getLoansByCardNo(int cardNo) {
    return template.query("select * from tbl_book_loans where cardNo = ? and dateIn is null order by branchId",
        new Object[]{cardNo}, this);
  }

  public List<Loan> getLoansByCardNoAndBranchId(int cardNo, int branchId, int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_book_loans where cardNo = ? and branchId = ? and dateIn is null"),
        new Object[]{cardNo, branchId}, this);
  }

  public Loan getLoanByIds(int cardNo, int bookId, int branchId) {
    List<Loan> loans = template.query("select * from tbl_book_loans where cardNo = ? and bookId = ? and branchId = ? and dateIn is null",
        new Object[]{cardNo, bookId, branchId}, this);

    if (loans != null && loans.size() > 0) {
      return loans.get(0);
    }

    return null;
  }

  public List<Loan> getLoansByTitleAndCardNoAndBranchId(String searchString, int cardNo, int branchId, int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_book_loans t1 join tbl_book t2 on t1.bookId = t2.bookId "
            + "where cardNo = ? and branchId = ? and dateIn is null and title like ?"),
        new Object[]{cardNo, branchId, searchString}, this);
  }

  public List<Loan> getLoansByDueDate(String searchString, int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_book_loans where dueDate like ? and dateIn is null"),
        new Object[]{searchString}, this);
  }

  public List<Loan> getAllLoans(int pageNo, int pageSize) {
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
  public List<Loan> extractData(ResultSet rs) {
    List<Loan> loans = new ArrayList<Loan>();

    try {
      while (rs.next()) {
        Loan l = new Loan();
        l.setDateOut(rs.getDate("dateOut"));
        l.setDueDate(rs.getDate("dueDate"));
        l.setDateIn(rs.getDate("dateIn"));
        l.setBorrower(borrowerdao.getBorrowerById(rs.getInt("cardNo")));
        l.setBook(bookdao.getBookById(rs.getInt("bookId")));
        l.setBranch(branchdao.getBranchById(rs.getInt("branchId")));

        loans.add(l);
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

    return loans;
  }
}
