package com.gcit.lms;

import com.gcit.lms.domain.*;
import com.gcit.lms.service.BorrowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BorrowerController {
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory.getLogger(BorrowerController.class);
  @Autowired
  BorrowerService service;

  @RequestMapping(value = "/validateCard", method = RequestMethod.POST)
  public String validateCard(Locale locale, Model model, @RequestParam(value = "cardNo") Integer cardNo) {
    boolean success = service.validateCard(cardNo) != null;
    model.addAttribute("message", success ? "Signed-in Successfully" : "Failed to Sign-in");
    return success ? "signin" : "checkcard";
  }

  @RequestMapping(value = "/checkout", method = RequestMethod.GET)
  public String checkout(Locale locale, Model model, @RequestParam(value = "cardNo") Integer cardNo,
                         @RequestParam(value = "branchId") Integer branchId, @RequestParam(value = "bookId") Integer bookId) {
    Loan loan = new Loan();
    Borrower borrower = new Borrower();
    borrower.setCardNo(cardNo);
    loan.setBorrower(borrower);

    Branch branch = new Branch();
    branch.setBranchId(branchId);
    loan.setBranch(branch);

    Book book = new Book();
    book.setBookId(bookId);
    loan.setBook(book);

    Calendar cal = Calendar.getInstance();
    Date dateOut = new Date(cal.getTime().getTime());
    loan.setDateOut(dateOut);

    cal.add(Calendar.DATE, 7);
    Date dueDate = new Date(cal.getTime().getTime());
    loan.setDueDate(dueDate);

    boolean success = service.checkout(loan);
    model.addAttribute("message", success ? "Check-out Successfully" : "Failed to Check-out");
    return "signin";
  }

  @RequestMapping(value = "/searchCheckoutBranches", method = RequestMethod.GET)
  @ResponseBody
  public String searchCheckoutBranches(Locale locale, Model model, @RequestParam(value = "searchString") String searchString) {
    searchString = "%" + searchString + "%";
    List<Branch> lst = service.getBranchesByName(searchString, -1, 5);
    int count = lst.size();
    int pages = count / 5;
    if (count % 5 != 0) pages++;
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= pages; i++) {
      sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>" + i + "</a></li>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/pageCheckoutBranches", method = RequestMethod.GET)
  @ResponseBody
  public String pageCheckoutBranches(Locale locale, Model model, @RequestParam(value = "searchString") String searchString,
                                     @RequestParam(value = "pageNo") int pageNo, @RequestParam(value = "cardNo") Integer cardNo) {
    searchString = "%" + searchString + "%";
    List<Branch> lst = service.getBranchesByName(searchString, pageNo, 5);
    StringBuilder sb = new StringBuilder();
    sb.append("<tr><th>Name</th><th>Address</th></tr>");
    for (Branch b : lst) {
      sb.append("<tr><td>" + b.getBranchName() + "</td>"
          + "<td>" + b.getBranchAddress() + "</td><td><button type='button' class='btn btn btn-info' "
          + "onclick=\"javascript:location.href='checkoutbook?cardNo=" + cardNo
          + "&branchId=" + b.getBranchId() + "'\">Choose</button></td></tr>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/searchCheckoutBooks", method = RequestMethod.GET)
  @ResponseBody
  public String searchCheckoutBooks(Locale locale, Model model, @RequestParam(value = "searchString") String searchString,
                                    @RequestParam(value = "branchId") Integer branchId) {
    searchString = "%" + searchString + "%";
    List<Book> lst = service.getBooksByTitleAndBranchId(searchString, -1, 5, branchId);
    int count = lst.size();
    int pages = count / 5;
    if (count % 5 != 0) pages++;
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= pages; i++) {
      sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>" + i + "</a></li>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/pageCheckoutBooks", method = RequestMethod.GET)
  @ResponseBody
  public String pageCheckoutBooks(Locale locale, Model model, @RequestParam(value = "searchString") String searchString,
                                  @RequestParam(value = "pageNo") int pageNo, @RequestParam(value = "cardNo") Integer cardNo,
                                  @RequestParam(value = "branchId") Integer branchId) {
    searchString = "%" + searchString + "%";
    int maxBook = 0;
    int maxGenre = 0;
    List<Book> lst = service.getBooksByTitleAndBranchId(searchString, pageNo, 5, branchId);
    for (Book b : lst) {
      if (maxBook < b.getAuthors().size())
        maxBook = b.getAuthors().size();
      if (maxGenre < b.getGenres().size())
        maxGenre = b.getGenres().size();
    }
    StringBuilder sb = new StringBuilder();
    sb.append("<tr><th>Title</th><th colspan='" + maxBook + "'>Author(s)</th><th>Publisher</th><th colspan='" + maxGenre + "'>Genre(s)</th></tr>");
    for (Book b : lst) {
      sb.append("<tr><td>" + b.getTitle() + "</td>");
      for (Author a : b.getAuthors())
        sb.append("<td>" + a.getAuthorName() + "</td>");
      for (int i = b.getAuthors().size(); i < maxBook; i++)
        sb.append("<td></td>");
      sb.append("<td>" + b.getPublisher().getPublisherName() + "</td>");
      for (Genre g : b.getGenres())
        sb.append("<td>" + g.getGenreName() + "</td>");
      for (int i = b.getGenres().size(); i < maxGenre; i++)
        sb.append("<td></td>");
      sb.append("<td><button type='button' class='btn btn btn-info' "
          + "onclick=\"javascript:location.href='checkout?cardNo="
          + cardNo + "&branchId=" + branchId + "&bookId=" + b.getBookId() + "'\">Choose</button></td></tr>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/checkin", method = RequestMethod.GET)
  public String checkin(Locale locale, Model model, @RequestParam(value = "cardNo") Integer cardNo,
                        @RequestParam(value = "branchId") Integer branchId, @RequestParam(value = "bookId") Integer bookId) {
    Loan loan = new Loan();
    Borrower borrower = new Borrower();
    borrower.setCardNo(cardNo);
    loan.setBorrower(borrower);

    Branch branch = new Branch();
    branch.setBranchId(branchId);
    loan.setBranch(branch);

    Book book = new Book();
    book.setBookId(bookId);
    loan.setBook(book);

    boolean success = service.checkin(loan);
    model.addAttribute("message", success ? "Check-in Successfully" : "Failed to Check-in");
    return "signin";
  }

  @RequestMapping(value = "/searchCheckinBranches", method = RequestMethod.GET)
  @ResponseBody
  public String searchCheckinBranches(Locale locale, Model model, @RequestParam(value = "searchString") String searchString,
                                      @RequestParam(value = "cardNo") Integer cardNo) {
    searchString = "%" + searchString + "%";
    List<Branch> lst = service.getBranchesByNameAndCardNo(searchString, -1, 5, cardNo);
    int count = lst.size();
    int pages = count / 5;
    if (count % 5 != 0) pages++;
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= pages; i++) {
      sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>" + i + "</a></li>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/pageCheckinBranches", method = RequestMethod.GET)
  @ResponseBody
  public String pageCheckinBranches(Locale locale, Model model, @RequestParam(value = "searchString") String searchString,
                                    @RequestParam(value = "pageNo") int pageNo, @RequestParam(value = "cardNo") Integer cardNo) {
    searchString = "%" + searchString + "%";
    List<Branch> lst = service.getBranchesByNameAndCardNo(searchString, pageNo, 5, cardNo);
    StringBuilder sb = new StringBuilder();
    sb.append("<tr><th>Name</th><th>Address</th></tr>");
    for (Branch b : lst) {
      sb.append("<tr><td>" + b.getBranchName() + "</td>"
          + "<td>" + b.getBranchAddress() + "</td><td><button type='button' class='btn btn btn-info' "
          + "onclick=\"javascript:location.href='checkinbook?cardNo=" + cardNo + "&branchId=" + b.getBranchId() + "'\">Choose</button></td></tr>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/searchCheckinBooks", method = RequestMethod.GET)
  @ResponseBody
  public String searchCheckinBooks(Locale locale, Model model, @RequestParam(value = "searchString") String searchString,
                                   @RequestParam(value = "branchId") Integer branchId, @RequestParam(value = "cardNo") Integer cardNo) {
    searchString = "%" + searchString + "%";
    List<Book> lst = service.getAllBooksLoanedByTitleAndCardNoAndBranchId(searchString, cardNo, branchId, -1, 5);
    int count = lst.size();
    int pages = count / 5;
    if (count % 5 != 0) pages++;
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= pages; i++) {
      sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>" + i + "</a></li>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/pageCheckinBooks", method = RequestMethod.GET)
  @ResponseBody
  public String pageCheckinBooks(Locale locale, Model model, @RequestParam(value = "searchString") String searchString,
                                 @RequestParam(value = "pageNo") int pageNo, @RequestParam(value = "cardNo") Integer cardNo,
                                 @RequestParam(value = "branchId") Integer branchId) {
    searchString = "%" + searchString + "%";
    int maxBook = 0;
    int maxGenre = 0;
    List<Book> lst = service.getAllBooksLoanedByTitleAndCardNoAndBranchId(searchString, cardNo, branchId, pageNo, 5);
    for (Book b : lst) {
      if (maxBook < b.getAuthors().size())
        maxBook = b.getAuthors().size();
      if (maxGenre < b.getGenres().size())
        maxGenre = b.getGenres().size();
    }
    StringBuilder sb = new StringBuilder();
    sb.append("<tr><th>Title</th><th colspan='" + maxBook + "'>Author(s)</th><th>Publisher</th><th colspan='" + maxGenre + "'>Genre(s)</th></tr>");
    for (Book b : lst) {
      sb.append("<tr><td>" + b.getTitle() + "</td>");
      for (Author a : b.getAuthors())
        sb.append("<td>" + a.getAuthorName() + "</td>");
      for (int i = b.getAuthors().size(); i < maxBook; i++)
        sb.append("<td></td>");
      sb.append("<td>" + b.getPublisher().getPublisherName() + "</td>");
      for (Genre g : b.getGenres())
        sb.append("<td>" + g.getGenreName() + "</td>");
      for (int i = b.getGenres().size(); i < maxGenre; i++)
        sb.append("<td></td>");
      sb.append("<td><button type='button' class='btn btn btn-info' "
          + "onclick=\"javascript:location.href='checkin?cardNo=" + cardNo + "&branchId=" + branchId + "&bookId=" + b.getBookId() + "'\">Choose</button></td></tr>");
    }
    return sb.toString();
  }
}