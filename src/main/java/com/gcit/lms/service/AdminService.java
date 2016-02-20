package com.gcit.lms.service;

import com.gcit.lms.dao.*;
import com.gcit.lms.domain.*;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class AdminService {

  @Autowired
  BasicDataSource ds;
  @Autowired
  AuthorDAO adao;
  @Autowired
  BookDAO bookdao;
  @Autowired
  LibraryBranchDAO branchdao;
  @Autowired
  BorrowerDAO borrowerdao;
  @Autowired
  PublisherDAO pdao;
  @Autowired
  GenreDAO gdao;
  @Autowired
  BookLoanDAO ldao;


  public List<BookLoan> getLoansByDueDate(String searchString, int pageNo, int pageSize) {
    return ldao.getLoansByDueDate(searchString, pageNo, pageSize);
  }

  @Transactional
  public boolean updateLoan(BookLoan bookLoan) {
    if (validateLoan(bookLoan)) {
      ldao.updateLoan(bookLoan);
      return true;
    }
    return false;
  }

  public List<BookLoan> getAllLoans(int pageNo, int pageSize) {
    return ldao.getAllLoans(pageNo, pageSize);
  }

  private boolean validateLoan(BookLoan bookLoan) {
    if (bookLoan.getBorrower() == null || bookLoan.getBook() == null || bookLoan.getLibraryBranch() == null)
      return false;
    if (bookLoan.getBorrower().getCardNo() == 0 || bookLoan.getBook().getBookId() == 0 || bookLoan.getLibraryBranch().getBranchId() == 0)
      return false;
    return !(bookLoan.getDateOut() == null || bookLoan.getDueDate() == null);

  }

  public int getLoansCount() {
    return ldao.getLoansCount();
  }

  @Transactional
  public boolean createBorrower(Borrower borrower) {
    if (validateBorrower(borrower)) {
      borrowerdao.createBorrower(borrower);
      return true;
    }
    return false;
  }

  @Transactional
  public void deleteBorrower(Borrower borrower) {
    borrowerdao.deleteBorrower(borrower);
  }

  @Transactional
  public boolean updateBorrower(Borrower borrower) {
    if (validateBorrower(borrower)) {
      borrowerdao.updateBorrower(borrower);
      return true;
    }
    return false;

  }

  public Borrower getBorrowerById(int borrowerId) {
    return borrowerdao.getBorrowerById(borrowerId);
  }

  public List<Borrower> getBorrowersByName(String searchString, int pageNo, int pageSize) {
    return borrowerdao.getBorrowersByName(searchString, pageNo, pageSize);
  }

  public List<Borrower> getAllBorrowers(int pageNo, int pageSize) {
    return borrowerdao.getAllBorrowers(pageNo, pageSize);
  }

  private boolean validateBorrower(Borrower borrower) {
    if (borrower.getName() == null || borrower.getAddress() == null)
      return false;
    if (borrower.getName().length() > 45 || borrower.getAddress().length() > 45)
      return false;
    if (borrower.getName().matches("\\s*") || borrower.getAddress().matches("\\s*"))
      return false;

    //TODO: fix this validation
//    String phoneNumberPattern = "(\\d-)?(\\d{3}-)?\\d{3}-\\d{4}";
//    return !(!borrower.getPhone().matches("\\s*") && !borrower.getPhone().matches(phoneNumberPattern));
    return true;
  }

  public int getBorrowersCount() {
    return borrowerdao.getBorrowersCount();
  }

  @Transactional
  public boolean createBranch(LibraryBranch libraryBranch) {
    if (validateBranch(libraryBranch)) {
      branchdao.createBranch(libraryBranch);
      return true;
    }
    return false;
  }

  public int getBranchesCount() {
    return branchdao.getBranchesCount();
  }

  @Transactional
  public void deleteBranch(LibraryBranch libraryBranch) {
    branchdao.deleteBranch(libraryBranch);
  }

  @Transactional
  public boolean updateBranch(LibraryBranch libraryBranch) {
    if (validateBranch(libraryBranch)) {
      branchdao.updateBranch(libraryBranch);
      return true;
    }
    return false;
  }

  public LibraryBranch getBranchById(int branchId) {
    return branchdao.getBranchById(branchId);
  }

  public List<LibraryBranch> getBranchesByName(String searchString, int pageNo, int pageSize) {
    return branchdao.getBranchesByName(searchString, pageNo, pageSize);
  }

  public List<LibraryBranch> getAllBranches(int pageNo, int pageSize) {
    return branchdao.getAllBranches(pageNo, pageSize);
  }

  private boolean validateBranch(LibraryBranch libraryBranch) {
    if (libraryBranch.getBranchName() == null || libraryBranch.getBranchAddress() == null)
      return false;
    if (libraryBranch.getBranchName().length() > 45 || libraryBranch.getBranchAddress().length() > 45)
      return false;
    return !(libraryBranch.getBranchName().matches("\\s*") || libraryBranch.getBranchAddress().matches("\\s*"));

  }

  public List<Genre> getAllGenres() {
    return gdao.getAllGenres();
  }

  @Transactional
  public boolean createPublisher(Publisher publisher) {
    if (validatePublisher(publisher)) {
      pdao.createPublisher(publisher);
      return true;
    }
    return false;
  }

  public int getPublishersCount() {
    return pdao.getPublishersCount();
  }

  public Publisher getPublisherById(int publisherId) {
    return pdao.getPublisherById(publisherId);
  }

  public Publisher getPublisherByBookId(int bookId) {
    return pdao.getPublisherByBookId(bookId);
  }

  public List<Publisher> getPublishersByName(String searchString, int pageNo, int pageSize) {
    return pdao.getPublishersByName(searchString, pageNo, pageSize);
  }

  @Transactional
  public void deletePublisher(Publisher publisher) {
    pdao.deletePublisher(publisher);
  }

  @Transactional
  public boolean updatePublisher(Publisher publisher) {
    if (validatePublisher(publisher)) {
      pdao.updatePublisher(publisher);
      return true;
    }
    return false;
  }

  private boolean validatePublisher(Publisher publisher) {
    if (publisher.getPublisherName() == null || publisher.getPublisherAddress() == null)
      return false;
    if (publisher.getPublisherName().length() > 45 || publisher.getPublisherAddress().length() > 45)
      return false;
    //TODO: fix validation
//    if (publisher.getPublisherName().matches("\\s*") || publisher.getPublisherAddress().matches("\\s*"))
//      return false;
//
//    String phoneNumberPattern = "(\\d-)?(\\d{3}-)?\\d{3}-\\d{4}";
//    return !(!publisher.getPublisherPhone().matches("\\s*") && !publisher.getPublisherPhone().matches(phoneNumberPattern));
    return true;
  }

  public List<Publisher> getAllPublishers(int pageNo, int pageSize) {
    return pdao.getAllPublishers(pageNo, pageSize);
  }


  public List<Genre> getGenresByBookId(int bookId) {
    return gdao.getGenresByBookId(bookId);
  }

  @Transactional
  public boolean createBook(Book book) {
    if (validateBook(book)) {
      bookdao.createBook(book);
      return true;
    }
    return false;
  }

  @Transactional
  public boolean updateBook(Book book) {
    if (validateBook(book)) {
      bookdao.updateBook(book);
      return true;
    }
    return false;
  }

  @Transactional
  public void deleteBook(Book book) {
    bookdao.deleteBook(book);
  }

  public Book getBookById(int bookId) {
    return bookdao.getBookById(bookId);
  }

  public List<Book> getBooksByName(String searchString, int pageNo, int pageSize) {
    return bookdao.getBooksByName(searchString, pageNo, pageSize);

  }

  public int getBooksCount() {
    return bookdao.getBooksCount();
  }

  public List<Book> getAllBooks(int pageNo, int pageSize) {
    return bookdao.getAllBooks(pageNo, pageSize);
  }

  private boolean validateBook(Book book) {
    if (book.getTitle() == null)
      return false;
    if (book.getTitle().length() > 45)
      return false;
    if (book.getTitle().matches("\\s*"))
      return false;
    if (book.getAuthors() == null || book.getAuthors().size() == 0)
      return false;
    if (book.getPublisher() == null || book.getPublisher().getPublisherId() == 0)
      return false;
    return !(book.getGenres() == null || book.getGenres().size() == 0);
  }

  public List<Author> getAuthorsByBookId(int bookId) {
    return adao.getAuthorsByBookId(bookId);
  }

  @Transactional
  public boolean createAuthor(Author author) {
    if (validateAuthor(author)) {
      adao.createAuthor(author);
      return true;
    }
    return false;
  }

  public List<Author> getAuthors(int pageNo, int pageSize) {
    return adao.getAllAuthors(pageNo, pageSize);

  }

  public Author getAuthorById(int authorId) {
    return adao.getAuthorById(authorId);
  }

  @Transactional
  public boolean updateAuthor(Author author) {
    if (validateAuthor(author)) {
      adao.updateAuthor(author);
      return true;
    }
    return false;
  }

  @Transactional
  public void deleteAuthor(Author author) {
    adao.deleteAuthor(author);
  }

  public int getAuthorsCount() {
    return adao.getAuthorsCount();
  }

  public List<Author> getAllAuthors(int pageNo, int pageSize) {
    List<Author> authors = adao.getAllAuthors(pageNo, pageSize);
    return authors;
  }

  public List<Author> getAuthorsByName(String searchString, int pageNo, int pageSize) {
    return adao.getAuthorsByName(searchString, pageNo, pageSize);
  }

  private boolean validateAuthor(Author author) {
    if (author.getAuthorName() == null)
      return false;
    if (author.getAuthorName().length() > 45)
      return false;
    return !author.getAuthorName().matches("\\s*");
  }
}
