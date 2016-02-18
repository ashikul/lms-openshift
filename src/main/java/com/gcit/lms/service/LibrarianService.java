package com.gcit.lms.service;

import com.gcit.lms.dao.*;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.LibraryBranch;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class LibrarianService {
  @Autowired
  BasicDataSource ds;
  @Autowired
  AuthorDAO adao;
  @Autowired
  BookDAO bookdao;
  @Autowired
  LibraryBranchDAO branchdao;
  @Autowired
  PublisherDAO pdao;
  @Autowired
  GenreDAO gdao;
  @Autowired
  BookCopiesDAO cdao;

  public LibraryBranch getBranchById(int branchId) {
    return branchdao.getBranchById(branchId);
  }

  public Book getBookById(int bookId) {
    return bookdao.getBookById(bookId);
  }

  public List<LibraryBranch> getBranchesByName(String searchString, int pageNo, int pageSize) {
    return branchdao.getBranchesByName(searchString, pageNo, pageSize);
  }

  public List<LibraryBranch> getAllBranches(int pageNo, int pageSize) {
    return branchdao.getAllBranches(pageNo, pageSize);
  }

  public int getBranchesCount() {
    return branchdao.getBranchesCount();
  }

  public int getBooksCount() {
    return bookdao.getBooksCount();
  }

  public List<Book> getAllBooks(int pageNo, int pageSize) {
    return bookdao.getAllBooks(pageNo, pageSize);
  }

  public List<Book> getBooksByName(String searchString, int pageNo, int pageSize) {
    return bookdao.getBooksByName(searchString, pageNo, pageSize);
  }

  @Transactional
  public boolean updateCopies(BookCopies bookCopies) {
    if (validateCopies(bookCopies)) {
      cdao.updateCopies(bookCopies);
      return true;
    }
    return false;
  }

  public BookCopies getCopiesByIds(int branchId, int bookId) {
    return cdao.getCopiesByIds(branchId, bookId);
  }

  @Transactional
  public boolean createCopies(BookCopies bookCopies) {
    if (validateCopies(bookCopies)) {
      cdao.createCopies(bookCopies);
      return true;
    }
    return false;
  }

  @Transactional
  public void deleteCopies(BookCopies bookCopies) {
    cdao.deleteCopies(bookCopies);
  }

  private boolean validateCopies(BookCopies bookCopies) {
    if (bookCopies.getBook() == null || bookCopies.getLibraryBranch() == null)
      return false;
    return !(bookCopies.getBook().getBookId() == 0 || bookCopies.getLibraryBranch().getBranchId() == 0);

  }
}
