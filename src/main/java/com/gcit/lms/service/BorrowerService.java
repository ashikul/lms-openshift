package com.gcit.lms.service;

import com.gcit.lms.dao.*;
import com.gcit.lms.domain.*;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class BorrowerService {
  @Autowired
  BasicDataSource ds;
  @Autowired
  LoanDAO ldao;
  @Autowired
  CopiesDAO cdao;
  @Autowired
  BorrowerDAO borrowerdao;
  @Autowired
  BranchDAO branchdao;
  @Autowired
  BookDAO bookdao;

  public int getBooksLoanedByCardNoAndBranchIdCount(int cardNo, int branchId) {
    return ldao.getLoansByCardNoAndBranchIdCount(cardNo, branchId);
  }

  public int getBranchesByCardNoCount(int cardNo) {
    return ldao.getLoansByCardNoCount(cardNo);
  }

  public List<Branch> getAllBranchesByCardNo(int cardNo) {
    List<Loan> loans = ldao.getLoansByCardNo(cardNo);
    List<Branch> branches = new ArrayList<Branch>();
    for (Loan l : loans) {
      Branch b = l.getBranch();
      if (!branches.contains(b)) branches.add(l.getBranch());
    }
    return branches;
  }

  public List<Book> getAllBooksLoanedByCardNoAndBranchId(int cardNo, int branchId, int pageNo, int pageSize) {
    List<Loan> loans = ldao.getLoansByCardNoAndBranchId(cardNo, branchId, pageNo, pageSize);
    List<Book> books = new ArrayList<Book>();
    for (Loan l : loans) {
      books.add(l.getBook());
    }
    return books;
  }

  @Transactional
  public boolean checkout(Loan loan) {
    if (validateLoan(loan)) {
      Loan l = ldao.getLoanByIds(loan.getBorrower().getCardNo(), loan.getBook().getBookId(), loan.getBranch().getBranchId());
      if (l != null)
        return false;
      ldao.createLoan(loan);
      Copies copies = cdao.getCopiesByIds(loan.getBranch().getBranchId(), loan.getBook().getBookId());
      if (copies.getNoOfCopies() > 1) {
        copies.setNoOfCopies(copies.getNoOfCopies() - 1);
        cdao.updateCopies(copies);
      }
      else {
        cdao.deleteCopies(copies);
      }
      return true;
    }
    return false;
  }

  @Transactional
  public boolean checkin(Loan loan) {
    if (validateLoan(loan)) {
      ldao.checkin(loan);
      Copies copies = cdao.getCopiesByIds(loan.getBranch().getBranchId(), loan.getBook().getBookId());
      if (copies == null) {
        copies = new Copies();
        copies.setBook(loan.getBook());
        copies.setBranch(loan.getBranch());
        copies.setNoOfCopies(1);
        cdao.createCopies(copies);
      }
      else {
        copies.setNoOfCopies(copies.getNoOfCopies() + 1);
        cdao.updateCopies(copies);
      }
      return true;
    }
    return false;
  }

  private boolean validateLoan(Loan loan) {
    if (loan.getBorrower() == null || loan.getBook() == null || loan.getBranch() == null)
      return false;
    return !(loan.getBorrower().getCardNo() == 0 || loan.getBook().getBookId() == 0 || loan.getBranch().getBranchId() == 0);

  }

  public Borrower validateCard(int cardNo) {
    return borrowerdao.getBorrowerById(cardNo);
  }

  public List<Branch> getAllBranches(int pageNo, int pageSize) {
    return branchdao.getAllBranches(pageNo, pageSize);
  }

  public int getBranchesCount() {
    return branchdao.getBranchesCount();
  }

  public int getBooksCountByBranchId(int branchId) {
    List<Copies> copies = cdao.getAllCopiesByBranchId(branchId, -1, 5);
    List<Book> books = new ArrayList<Book>();
    for (Copies c : copies) books.add(bookdao.getBookById(c.getBook().getBookId()));
    return books.size();
  }

  public List<Book> getAllBooksByBranchId(int branchId, int pageNo, int pageSize) {
    List<Copies> copies = cdao.getAllCopiesByBranchId(branchId, pageNo, pageSize);
    List<Book> books = new ArrayList<Book>();
    for (Copies c : copies) {
      books.add(bookdao.getBookById(c.getBook().getBookId()));
    }
    return books;
  }


  public List<Book> getBooksByTitleAndBranchId(String searchString, int pageNo, int pageSize, int branchId) {
    List<Copies> copies = cdao.getAllCopiesByTitleAndByBranchId(branchId, pageNo, pageSize, searchString);
    List<Book> books = new ArrayList<Book>();
    for (Copies c : copies) {
      books.add(bookdao.getBookById(c.getBook().getBookId()));
    }
    return books;
  }

  public List<Branch> getBranchesByName(String searchString, int pageNo, int pageSize) {
    return branchdao.getBranchesByName(searchString, pageNo, pageSize);
  }

  public List<Branch> getBranchesByNameAndCardNo(String searchString, int pageNo, int pageSize, int cardNo) {
    return branchdao.getBranchesByNameAndCardNo(searchString, pageNo, pageSize, cardNo);
  }

  public List<Book> getAllBooksLoanedByTitleAndCardNoAndBranchId(String searchString, int cardNo, int branchId,
                                                                 int pageNo, int pageSize) {
    List<Loan> loans = ldao.getLoansByTitleAndCardNoAndBranchId(searchString, cardNo, branchId, pageNo, pageSize);
    List<Book> books = new ArrayList<Book>();
    for (Loan l : loans) {
      books.add(l.getBook());
    }
    return books;
  }
}
