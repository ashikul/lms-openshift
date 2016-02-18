package com.gcit.lms;

import com.gcit.lms.dao.*;
import com.gcit.lms.service.AdministratorService;
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

  private static String driver = "com.mysql.jdbc.Driver";
  private static String url = "jdbc:mysql://localhost:3306/library";
  private static String username = "root";
  private static String pwd = "root";

  /* Add Services */
  @Bean
  AdministratorService adminService() {
    return new AdministratorService();
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
  public BranchDAO branchDAO() {
    return new BranchDAO();
  }

  @Bean
  public CopiesDAO copiesDAO() {
    return new CopiesDAO();
  }

  @Bean
  public GenreDAO genreDAO() {
    return new GenreDAO();
  }

  @Bean
  public LoanDAO loanDAO() {
    return new LoanDAO();
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
