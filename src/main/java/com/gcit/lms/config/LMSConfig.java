package com.gcit.lms.config;

import com.gcit.lms.dao.*;
import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.LibrarianService;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
public class LMSConfig {

//  private static String driver = "com.mysql.jdbc.Driver";
//  private static String url = "jdbc:mysql://localhost:3306/library";
//  private static String username = "root";
//  private static String pwd = "root";

  private static String driver = "com.mysql.jdbc.Driver";
//  private static String url = "jdbc:mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/";
//  private static String url = "jdbc:mysql://127.12.97.2:3306/lms";


  private static String url = "jdbc:mysql://"
      + System.getenv().get("OPENSHIFT_MYSQL_DB_HOST")
      + ":"
      + System.getenv().get("OPENSHIFT_MYSQL_DB_PORT")
      + "/lms";

  private static String username = "admina7BP5Md";
  private static String pwd = "4FMMTIgjNRIZ";

  /* Add Services */
  @Bean
  AdminService adminService() {
    return new AdminService();
  }

  @Bean
  LibrarianService libService() {
    return new LibrarianService();
  }

  @Bean
  BorrowerService borrowerService() {
    return new BorrowerService();
  }

  /* Add DAO */
  @Bean
  public AuthorDAO authorDAO() {
    return new AuthorDAO();
  }

  @Bean
  public BookDAO bookDAO() {
    return new BookDAO();
  }

  @Bean
  public BorrowerDAO borrowerDAO() {
    return new BorrowerDAO();
  }

  @Bean
  public LibraryBranchDAO branchDAO() {
    return new LibraryBranchDAO();
  }

  @Bean
  public BookCopiesDAO copiesDAO() {
    return new BookCopiesDAO();
  }

  @Bean
  public GenreDAO genreDAO() {
    return new GenreDAO();
  }

  @Bean
  public BookLoanDAO loanDAO() {
    return new BookLoanDAO();
  }

  @Bean
  public PublisherDAO publisherDAO() {
    return new PublisherDAO();
  }

  /* Connection and Template */
  @Bean
  public BasicDataSource dataSource() {
    BasicDataSource ds = new BasicDataSource();
    ds.setDriverClassName(driver);
    ds.setUrl(url);
    ds.setUsername(username);
    ds.setPassword(pwd);

    return ds;
  }

  @Bean
  public PlatformTransactionManager txManager1() {
    DataSourceTransactionManager tx = new DataSourceTransactionManager();
    tx.setDataSource(dataSource());
    return tx;
  }

  @Bean
  public JdbcTemplate template() {
    JdbcTemplate template = new JdbcTemplate();
    template.setDataSource(dataSource());
    return template;
  }
}
