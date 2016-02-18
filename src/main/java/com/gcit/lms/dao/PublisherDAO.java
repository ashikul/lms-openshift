package com.gcit.lms.dao;

import com.gcit.lms.domain.Publisher;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublisherDAO extends BaseDAO implements ResultSetExtractor<List<Publisher>> {

  public void createPublisher(Publisher publisher) {
    template.update("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values(?, ?, ?)",
        publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone());
  }

  public void updatePublisher(Publisher publisher) {
    template.update("update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ?",
        publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone(), publisher.getPublisherId());
  }

  public void deletePublisher(Publisher publisher) {
    template.update("delete from tbl_publisher where publisherId = ?",
        publisher.getPublisherId());
  }

  public List<Publisher> getAllPublishers(int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_publisher"), this);
  }

  public Publisher getPublisherById(int publisherId) {
    List<Publisher> publishers = template.query("select * from tbl_publisher where publisherId = ?",
        new Object[]{publisherId}, this);

    if (publishers != null && publishers.size() > 0) {
      return publishers.get(0);
    }
    return null;
  }

  public List<Publisher> getPublishersByName(String searchString, int pageNo, int pageSize) {
    setPageNo(pageNo);
    setPageSize(pageSize);
    return template.query(addLimit("select * from tbl_publisher where publisherName like ?"),
        new Object[]{searchString}, this);
  }

  public int getPublishersCount() {
    return template.queryForObject("select count(*) as count from tbl_publisher", Integer.class);
  }

  public Publisher getPublisherByBookId(int bookId) {
    List<Publisher> publishers = template.query("select * from tbl_publisher where publisherId in (select pubId as publisherId from tbl_book where bookId = ?)",
        new Object[]{bookId}, this);

    if (publishers != null && publishers.size() > 0) {
      return publishers.get(0);
    }
    return null;
  }

  @Override
  public List<Publisher> extractData(ResultSet rs) {
    List<Publisher> publishers = new ArrayList<Publisher>();

    try {
      while (rs.next()) {
        Publisher p = new Publisher();
        p.setPublisherId(rs.getInt("publisherId"));
        p.setPublisherName(rs.getString("publisherName"));
        p.setPublisherAddress(rs.getString("publisherAddress"));
        p.setPublisherPhone(rs.getString("publisherPhone"));

        publishers.add(p);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return publishers;
  }
}
