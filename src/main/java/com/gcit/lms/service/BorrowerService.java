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
  BookLoanDAO ldao;
  @Autowired
  BookCopiesDAO cdao;
  @Autowired
  BorrowerDAO borrowerdao;
  @Autowired
  LibraryBranchDAO branchdao;
  @Autowired
  BookDAO bookdao;

  public int getBooksLoanedByCardNoAndBranchIdCount(int cardNo, int branchId) {
    return ldao.getLoansByCardNoAndBranchIdCount(cardNo, branchId);
  }

  public int getBranchesByCardNoCount(int cardNo) {
    return ldao.getLoansByCardNoCount(cardNo);
  }

  public List<LibraryBranch> getAllBranchesByCardNo(int cardNo) {
    List<BookLoan> bookLoen = ldao.getLoansByCardNo(cardNo);
    List<LibraryBranch> libraryBranches = new ArrayList<LibraryBranch>();
    for (BookLoan l : bookLoen) {
      LibraryBranch b = l.getLibraryBranch();
      if (!libraryBranches.contains(b)) libraryBranches.add(l.getLibraryBranch());
    }
    return libraryBranches;
  }

  public List<Book> getAllBooksLoanedByCardNoAndBranchId(int cardNo, int branchId, int pageNo, int pageSize) {
    List<BookLoan> bookLoen = ldao.getLoansByCardNoAndBranchId(cardNo, branchId, pageNo, pageSize);
    List<Book> books = new ArrayList<Book>();
    for (BookLoan l : bookLoen) {
      books.add(l.getBook());
    }
    return books;
  }

  @Transactional
  public boolean checkout(BookLoan bookLoan) {
    if (validateLoan(bookLoan)) {
      BookLoan l = ldao.getLoanByIds(bookLoan.getBorrower().getCardNo(), bookLoan.getBook().getBookId(), bookLoan.getLibraryBranch().getBranchId());
      if (l != null)
        return false;
      ldao.createLoan(bookLoan);
      BookCopies bookCopies = cdao.getCopiesByIds(bookLoan.getLibraryBranch().getBranchId(), bookLoan.getBook().getBookId());
      if (bookCopies.getNoOfCopies() > 1) {
        bookCopies.setNoOfCopies(bookCopies.getNoOfCopies() - 1);
        cdao.updateCopies(bookCopies);
      }
      else {
        cdao.deleteCopies(bookCopies);
      }
      return true;
    }
    return false;
  }

  @Transactional
  public boolean checkin(BookLoan bookLoan) {
    if (validateLoan(bookLoan)) {
      ldao.checkin(bookLoan);
      BookCopies bookCopies = cdao.getCopiesByIds(bookLoan.getLibraryBranch().getBranchId(), bookLoan.getBook().getBookId());
      if (bookCopies == null) {
        bookCopies = new BookCopies();
        bookCopies.setBook(bookLoan.getBook());
        bookCopies.setLibraryBranch(bookLoan.getLibraryBranch());
        bookCopies.setNoOfCopies(1);
        cdao.createCopies(bookCopies);
      }
      else {
        bookCopies.setNoOfCopies(bookCopies.getNoOfCopies() + 1);
        cdao.updateCopies(bookCopies);
      }
      return true;
    }
    return false;
  }

  private boolean validateLoan(BookLoan bookLoan) {
    if (bookLoan.getBorrower() == null || bookLoan.getBook() == null || bookLoan.getLibraryBranch() == null)
      return false;
    return !(bookLoan.getBorrower().getCardNo() == 0 || bookLoan.getBook().getBookId() == 0 || bookLoan.getLibraryBranch().getBranchId() == 0);

  }

  public Borrower validateCard(int cardNo) {
    return borrowerdao.getBorrowerById(cardNo);
  }

  public List<LibraryBranch> getAllBranches(int pageNo, int pageSize) {
    return branchdao.getAllBranches(pageNo, pageSize);
  }

  public int getBranchesCount() {
    return branchdao.getBranchesCount();
  }

  public int getBooksCountByBranchId(int branchId) {
    List<BookCopies> copies = cdao.getAllCopiesByBranchId(branchId, -1, 5);
    List<Book> books = new ArrayList<Book>();
    for (BookCopies c : copies) books.add(bookdao.getBookById(c.getBook().getBookId()));
    return books.size();
  }

  public List<Book> getAllBooksByBranchId(int branchId, int pageNo, int pageSize) {
    List<BookCopies> copies = cdao.getAllCopiesByBranchId(branchId, pageNo, pageSize);
    List<Book> books = new ArrayList<Book>();
    for (BookCopies c : copies) {
      books.add(bookdao.getBookById(c.getBook().getBookId()));
    }
    return books;
  }


  public List<Book> getBooksByTitleAndBranchId(String searchString, int pageNo, int pageSize, int branchId) {
    List<BookCopies> copies = cdao.getAllCopiesByTitleAndByBranchId(branchId, pageNo, pageSize, searchString);
    List<Book> books = new ArrayList<Book>();
    for (BookCopies c : copies) {
      books.add(bookdao.getBookById(c.getBook().getBookId()));
    }
    return books;
  }

  public List<LibraryBranch> getBranchesByName(String searchString, int pageNo, int pageSize) {
    return branchdao.getBranchesByName(searchString, pageNo, pageSize);
  }

  public List<LibraryBranch> getBranchesByNameAndCardNo(String searchString, int pageNo, int pageSize, int cardNo) {
    return branchdao.getBranchesByNameAndCardNo(searchString, pageNo, pageSize, cardNo);
  }

  public List<Book> getAllBooksLoanedByTitleAndCardNoAndBranchId(String searchString, int cardNo, int branchId,
                                                                 int pageNo, int pageSize) {
    List<BookLoan> bookLoen = ldao.getLoansByTitleAndCardNoAndBranchId(searchString, cardNo, branchId, pageNo, pageSize);
    List<Book> books = new ArrayList<Book>();
    for (BookLoan l : bookLoen) {
      books.add(l.getBook());
    }
    return books;
  }
}
