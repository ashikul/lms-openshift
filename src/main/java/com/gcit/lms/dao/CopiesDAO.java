package com.gcit.lms.dao;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Branch;
import com.gcit.lms.domain.Copies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CopiesDAO extends BaseDAO implements ResultSetExtractor<List<Copies>> {
  @Autowired
  BookDAO bookdao;
  @Autowired
  BranchDAO branchdao;

  public void createCopies(Copies copies) {
    template.update("insert into tbl_book_copies values (?, ?, ?)",
        copies.getBook().getBookId(), copies.getBranch().getBranchId(), copies.getNoOfCopies());
  }

  public void updateCopies(Copies copies) {
    template.update("update tbl_book_copies set noOfCopies = ? where branchId = ? and bookId = ?",
        copies.getNoOfCopies(), copies.getBranch().getBranchId(), copies.getBook().getBookId());
  }

  public void deleteCopies(Copies copies) {
    template.update("delete from tbl_book_copies where branchId = ? and bookId = ?",
        copies.getBranch().getBranchId(), copies.getBook().getBookId());
  }

  public List<Copies> getAllCopies() {
    return template.query("select * from tbl_book_copies", this);
  }

  public Copies getCopiesByIds(int branchId, int bookId) {
    List<Copies> copies = template.query("select * from tbl_book_copies where branchId = ? and bookId = ?",
        new Object[]{branchId, bookId}, this);

    if (copies != null && copies.size() > 0) {
      return copies.get(0);
    }
    return null;
  }

  public List<Copies> getAllCopiesByBranchId(int branchId, int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_book_copies where branchId = ?"),
        new Object[]{branchId}, this);
  }

  public List<Copies> getAllCopiesByTitleAndByBranchId(int branchId, int pageNo, int pageSize, String searchString) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_book_copies t1 join tbl_book t2 on t1.bookId = t2.bookId where branchId = ? and title like ?"),
        new Object[]{branchId, searchString}, this);
  }

  @Override
  public List<Copies> extractData(ResultSet rs) {
    List<Copies> copies = new ArrayList<Copies>();

    try {
      while (rs.next()) {
        Copies c = new Copies();
        c.setNoOfCopies(rs.getInt("noOfCopies"));

        Book book = bookdao.getBookById(rs.getInt("bookId"));
        c.setBook(book);

        Branch branch = branchdao.getBranchById(rs.getInt("branchId"));
        c.setBranch(branch);

        copies.add(c);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return copies;
  }
}
