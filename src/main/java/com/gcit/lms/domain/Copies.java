package com.gcit.lms.domain;

import java.io.Serializable;

public class Copies implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = -4204664396298869548L;
  private Book book;
  private Branch branch;
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
   * @return the branch
   */
  public Branch getBranch() {
    return branch;
  }

  /**
   * @param branch the branch to set
   */
  public void setBranch(Branch branch) {
    this.branch = branch;
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
    result = prime * result + ((branch == null) ? 0 : branch.hashCode());
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
    Copies other = (Copies) obj;
    if (book == null) {
      if (other.book != null)
        return false;
    }
    else if (!book.equals(other.book))
      return false;
    if (branch == null) {
      if (other.branch != null)
        return false;
    }
    else if (!branch.equals(other.branch))
      return false;
    return noOfCopies == other.noOfCopies;
  }

}
