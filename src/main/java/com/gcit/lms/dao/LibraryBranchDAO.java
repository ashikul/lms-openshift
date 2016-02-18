package com.gcit.lms.dao;

import com.gcit.lms.domain.LibraryBranch;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryBranchDAO extends BaseDAO implements ResultSetExtractor<List<LibraryBranch>> {

  public void createBranch(LibraryBranch libraryBranch) {
    template.update("insert into tbl_library_branch (branchName, branchAddress) values (?, ?)",
        libraryBranch.getBranchName(), libraryBranch.getBranchAddress());
  }

  public void updateBranch(LibraryBranch libraryBranch) {
    template.update("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
        libraryBranch.getBranchName(), libraryBranch.getBranchAddress(), libraryBranch.getBranchId());
  }

  public void deleteBranch(LibraryBranch libraryBranch) {
    template.update("delete from tbl_library_branch where branchId = ?",
        libraryBranch.getBranchId());
  }

  public List<LibraryBranch> getAllBranches(int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_library_branch"), this);
  }

  public LibraryBranch getBranchById(int branchId) {
    List<LibraryBranch> libraryBranches = template.query("select * from tbl_library_branch where branchId = ?",
        new Object[]{branchId}, this);

    if (libraryBranches != null && libraryBranches.size() > 0) {
      return libraryBranches.get(0);
    }
    return null;
  }

  public List<LibraryBranch> getBranchesByName(String searchString, int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_library_branch where branchName like ?"),
        new Object[]{searchString}, this);
  }

  public List<LibraryBranch> getBranchesByNameAndCardNo(String searchString, int pageNo, int pageSize, int cardNo) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select distinct t1.* from tbl_library_branch t1 join tbl_book_loans t2 on t1.branchId = t2.branchId "
            + "where branchName like ? and cardNo = ? and dateIn is null"),
        new Object[]{searchString, cardNo}, this);
  }

  public int getBranchesCount() {
    return template.queryForObject("select count(*) as count from tbl_library_branch", Integer.class);
  }

  @Override
  public List<LibraryBranch> extractData(ResultSet rs) {
    List<LibraryBranch> libraryBranches = new ArrayList<LibraryBranch>();

    try {
      while (rs.next()) {
        LibraryBranch b = new LibraryBranch();
        b.setBranchId(rs.getInt("branchId"));
        b.setBranchName(rs.getString("branchName"));
        b.setBranchAddress(rs.getString("branchAddress"));

        libraryBranches.add(b);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return libraryBranches;
  }
}
