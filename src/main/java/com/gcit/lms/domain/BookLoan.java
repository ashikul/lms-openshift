package com.gcit.lms.domain;

import java.io.Serializable;
import java.sql.Date;

public class BookLoan implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = -53194864434024810L;
  private Borrower borrower;
  private Book book;
  private LibraryBranch libraryBranch;
  private Date dateOut;
  private Date dueDate;
  private Date dateIn;

  /**
   * @return the borrower
   */
  public Borrower getBorrower() {
    return borrower;
  }

  /**
   * @param borrower the borrower to set
   */
  public void setBorrower(Borrower borrower) {
    this.borrower = borrower;
  }

  /**
   * @return the book
   */
  public Book getBook() {
    return book;
  }

  /**
   * @param book the book to set
   */
  public void setBook(Book book) {
    this.book = book;
  }

  /**
   * @return the libraryBranch
   */
  public LibraryBranch getLibraryBranch() {
    return libraryBranch;
  }

  /**
   * @param libraryBranch the libraryBranch to set
   */
  public void setLibraryBranch(LibraryBranch libraryBranch) {
    this.libraryBranch = libraryBranch;
  }

  /**
   * @return the dateOut
   */
  public Date getDateOut() {
    return dateOut;
  }

  /**
   * @param dateOut the dateOut to set
   */
  public void setDateOut(Date dateOut) {
    this.dateOut = dateOut;
  }

  /**
   * @return the dueDate
   */
  public Date getDueDate() {
    return dueDate;
  }

  /**
   * @param dueDate the dueDate to set
   */
  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  /**
   * @return the dateIn
   */
  public Date getDateIn() {
    return dateIn;
  }

  /**
   * @param dateIn the dateIn to set
   */
  public void setDateIn(Date dateIn) {
    this.dateIn = dateIn;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((book == null) ? 0 : book.hashCode());
    result = prime * result + ((borrower == null) ? 0 : borrower.hashCode());
    result = prime * result + ((libraryBranch == null) ? 0 : libraryBranch.hashCode());
    result = prime * result + ((dateIn == null) ? 0 : dateIn.hashCode());
    result = prime * result + ((dateOut == null) ? 0 : dateOut.hashCode());
    result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    BookLoan other = (BookLoan) obj;
    if (book == null) {
      if (other.book != null)
        return false;
    }
    else if (!book.equals(other.book))
      return false;
    if (borrower == null) {
      if (other.borrower != null)
        return false;
    }
    else if (!borrower.equals(other.borrower))
      return false;
    if (libraryBranch == null) {
      if (other.libraryBranch != null)
        return false;
    }
    else if (!libraryBranch.equals(other.libraryBranch))
      return false;
    if (dateIn == null) {
      if (other.dateIn != null)
        return false;
    }
    else if (!dateIn.equals(other.dateIn))
      return false;
    if (dateOut == null) {
      if (other.dateOut != null)
        return false;
    }
    else if (!dateOut.equals(other.dateOut))
      return false;
    if (dueDate == null) {
      if (other.dueDate != null)
        return false;
    }
    else if (!dueDate.equals(other.dueDate))
      return false;
    return true;
  }

}
