package com.gcit.lms.domain;

import java.io.Serializable;

public class Genre implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private int genreId;
  private String genreName;

  /**
   * @return the genreId
   */
  public int getGenreId() {
    return genreId;
  }

  /**
   * @param genreId the genreId to set
   */
  public void setGenreId(int genreId) {
    this.genreId = genreId;
  }

  /**
   * @return the genreName
   */
  public String getGenreName() {
    return genreName;
  }

  /**
   * @param genreName the genreName to set
   */
  public void setGenreName(String genreName) {
    this.genreName = genreName;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + genreId;
    result = prime * result + ((genreName == null) ? 0 : genreName.hashCode());
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
    Genre other = (Genre) obj;
    if (genreId != other.genreId)
      return false;
    if (genreName == null) {
      if (other.genreName != null)
        return false;
    }
    else if (!genreName.equals(other.genreName))
      return false;
    return true;
  }

}
