package com.gcit.lms.dao;

import com.gcit.lms.domain.Branch;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BranchDAO extends BaseDAO implements ResultSetExtractor<List<Branch>> {

  public void createBranch(Branch branch) {
    template.update("insert into tbl_library_branch (branchName, branchAddress) values (?, ?)",
        branch.getBranchName(), branch.getBranchAddress());
  }

  public void updateBranch(Branch branch) {
    template.update("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
        branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId());
  }

  public void deleteBranch(Branch branch) {
    template.update("delete from tbl_library_branch where branchId = ?",
        branch.getBranchId());
  }

  public List<Branch> getAllBranches(int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_library_branch"), this);
  }

  public Branch getBranchById(int branchId) {
    List<Branch> branchs = template.query("select * from tbl_library_branch where branchId = ?",
        new Object[]{branchId}, this);

    if (branchs != null && branchs.size() > 0) {
      return branchs.get(0);
    }
    return null;
  }

  public List<Branch> getBranchesByName(String searchString, int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_library_branch where branchName like ?"),
        new Object[]{searchString}, this);
  }

  public List<Branch> getBranchesByNameAndCardNo(String searchString, int pageNo, int pageSize, int cardNo) {
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
  public List<Branch> extractData(ResultSet rs) {
    List<Branch> branchs = new ArrayList<Branch>();

    try {
      while (rs.next()) {
        Branch b = new Branch();
        b.setBranchId(rs.getInt("branchId"));
        b.setBranchName(rs.getString("branchName"));
        b.setBranchAddress(rs.getString("branchAddress"));

        branchs.add(b);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return branchs;
  }
}
