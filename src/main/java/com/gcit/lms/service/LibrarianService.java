package com.gcit.lms.service;

import com.gcit.lms.dao.*;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Branch;
import com.gcit.lms.domain.Copies;
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
  BranchDAO branchdao;
  @Autowired
  PublisherDAO pdao;
  @Autowired
  GenreDAO gdao;
  @Autowired
  CopiesDAO cdao;

  public Branch getBranchById(int branchId) {
    return branchdao.getBranchById(branchId);
  }

  public Book getBookById(int bookId) {
    return bookdao.getBookById(bookId);
  }

  public List<Branch> getBranchesByName(String searchString, int pageNo, int pageSize) {
    return branchdao.getBranchesByName(searchString, pageNo, pageSize);
  }

  public List<Branch> getAllBranches(int pageNo, int pageSize) {
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
  public boolean updateCopies(Copies copies) {
    if (validateCopies(copies)) {
      cdao.updateCopies(copies);
      return true;
    }
    return false;
  }

  public Copies getCopiesByIds(int branchId, int bookId) {
    return cdao.getCopiesByIds(branchId, bookId);
  }

  @Transactional
  public boolean createCopies(Copies copies) {
    if (validateCopies(copies)) {
      cdao.createCopies(copies);
      return true;
    }
    return false;
  }

  @Transactional
  public void deleteCopies(Copies copies) {
    cdao.deleteCopies(copies);
  }

  private boolean validateCopies(Copies copies) {
    if (copies.getBook() == null || copies.getBranch() == null)
      return false;
    return !(copies.getBook().getBookId() == 0 || copies.getBranch().getBranchId() == 0);

  }
}
