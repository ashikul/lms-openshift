package com.gcit.lms.dao;

import com.gcit.lms.domain.Borrower;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowerDAO extends BaseDAO implements ResultSetExtractor<List<Borrower>> {

  public void createBorrower(Borrower borrower) {
    template.update("insert into tbl_borrower (name, address, phone) values(?, ?, ?)",
        borrower.getName(), borrower.getAddress(), borrower.getPhone());
  }

  public void updateBorrower(Borrower borrower) {
    template.update("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?",
        borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo());
  }

  public void deleteBorrower(Borrower borrower) {
    template.update("delete from tbl_borrower where cardNo = ?",
        borrower.getCardNo());
  }

  public List<Borrower> getAllBorrowers(int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_borrower"), this);
  }

  public Borrower getBorrowerById(int cardNo) {
    List<Borrower> borrowers = new ArrayList<Borrower>();
    borrowers = template.query("select * from tbl_borrower where cardNo = ?",
        new Object[]{cardNo}, this);

    if (borrowers != null && borrowers.size() > 0) {
      return borrowers.get(0);
    }
    return null;
  }

  public List<Borrower> getBorrowersByName(String searchString, int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_borrower where name like ?"),
        new Object[]{searchString}, this);
  }

  public int getBorrowersCount() {
    return template.queryForObject("select count(*) as count from tbl_borrower", Integer.class);
  }

  @Override
  public List<Borrower> extractData(ResultSet rs) {
    List<Borrower> borrowers = new ArrayList<Borrower>();

    try {
      while (rs.next()) {
        Borrower b = new Borrower();
        b.setCardNo(rs.getInt("cardNo"));
        b.setName(rs.getString("name"));
        b.setAddress(rs.getString("address"));
        b.setPhone(rs.getString("phone"));

        borrowers.add(b);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return borrowers;
  }
}
