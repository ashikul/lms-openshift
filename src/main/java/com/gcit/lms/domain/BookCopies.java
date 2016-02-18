package com.gcit.lms.domain;

import java.io.Serializable;

public class BookCopies implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = -4204664396298869548L;
  private Book book;
  private LibraryBranch libraryBranch;
  private int noOfCopies;

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
   * @return the noOfCopies
   */
  public int getNoOfCopies() {
    return noOfCopies;
  }

  /**
   * @param noOfCopies the noOfCopies to set
   */
  public void setNoOfCopies(int noOfCopies) {
    this.noOfCopies = noOfCopies;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((book == null) ? 0 : book.hashCode());
    result = prime * result + ((libraryBranch == null) ? 0 : libraryBranch.hashCode());
    result = prime * result + noOfCopies;
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
    BookCopies other = (BookCopies) obj;
    if (book == null) {
      if (other.book != null)
        return false;
    }
    else if (!book.equals(other.book))
      return false;
    if (libraryBranch == null) {
      if (other.libraryBranch != null)
        return false;
    }
    else if (!libraryBranch.equals(other.libraryBranch))
      return false;
    return noOfCopies == other.noOfCopies;
  }

}
