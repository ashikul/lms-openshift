package com.gcit.lms.controller;

import com.gcit.lms.domain.*;
import com.gcit.lms.service.LibrarianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Locale;


@Controller
public class LibrarianController {
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory.getLogger(LibrarianController.class);
  @Autowired
  LibrarianService service;

  @RequestMapping(value = "/searchLibBranches", method = RequestMethod.GET)
  @ResponseBody
  public String searchLibBranches(Locale locale, Model model, @RequestParam(value = "searchString") String searchString) {
    searchString = "%" + searchString + "%";
    List<LibraryBranch> lst = service.getBranchesByName(searchString, -1, 5);
    int count = lst.size();
    int pages = count / 5;
    if (count % 5 != 0) pages++;
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= pages; i++) {
      sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>" + i + "</a></li>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/pageLibBranches", method = RequestMethod.GET)
  @ResponseBody
  public String pageLibBranches(Locale locale, Model model, @RequestParam(value = "searchString") String searchString,
                                @RequestParam(value = "pageNo") int pageNo) {
    searchString = "%" + searchString + "%";

    List<LibraryBranch> lst = service.getBranchesByName(searchString, pageNo, 5);
    StringBuilder sb = new StringBuilder();
    sb.append("<tr><th>Name</th><th>Address</th></tr>");
    for (LibraryBranch b : lst) {
      sb.append("<tr><td>" + b.getBranchName() + "</td>"
          + "<td>" + b.getBranchAddress() + "</td><td><button type='button' class='btn btn btn-info' "
          + "onclick=\"javascript:location.href='choosebranch?id=" + b.getBranchId()
          + "&name=" + b.getBranchName() + "&address=" + b.getBranchAddress() + "'\">Choose</button></td></tr>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/searchLibBooks", method = RequestMethod.GET)
  @ResponseBody
  public String searchLibBooks(Locale locale, Model model, @RequestParam(value = "searchString") String searchString) {
    searchString = "%" + searchString + "%";
    List<Book> lst = service.getBooksByName(searchString, -1, 5);
    int count = lst.size();
    int pages = count / 5;
    if (count % 5 != 0) pages++;
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= pages; i++) {
      sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>" + i + "</a></li>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/pageLibBooks", method = RequestMethod.GET)
  @ResponseBody
  public String pageLibBooks(Locale locale, Model model, @RequestParam(value = "searchString") String searchString,
                             @RequestParam(value = "pageNo") int pageNo, @RequestParam(value = "branchId") Integer id) {
    searchString = "%" + searchString + "%";
    int maxBook = 0;
    int maxGenre = 0;
    List<Book> lst = service.getBooksByName(searchString, pageNo, 5);
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
          + "data-toggle='modal' data-target='#myModal1' "
          + "href='getCopies?branchId=" + id + "&bookId=" + b.getBookId() + "'>Choose</button></td>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/getCopies", method = RequestMethod.GET)
  public String getCopies(Locale locale, Model model, @RequestParam(value = "branchId") Integer branchId,
                          @RequestParam(value = "bookId") Integer bookId) {
    BookCopies c = service.getCopiesByIds(branchId, bookId);
    if (c == null) {
      c = new BookCopies();
      c.setBook(service.getBookById(bookId));
      c.setLibraryBranch(service.getBranchById(branchId));
    }
    model.addAttribute("copies", c);
    return "choosebook";
  }

  @RequestMapping(value = "/updateCopies", method = RequestMethod.POST)
  public String updateCopies(Locale locale, Model model, @RequestParam(value = "oldCopies") Integer oldCopies,
                             @RequestParam(value = "newCopies") String newCopies, @RequestParam(value = "branchId") Integer branchId,
                             @RequestParam(value = "bookId") Integer bookId, @RequestParam(value = "name") String name,
                             @RequestParam(value = "address") String address) {
    int newValue = 0;
    boolean success = true;
    try {
      newValue = Integer.parseInt(newCopies);
      BookCopies c = new BookCopies();
      c.setNoOfCopies(newValue);

      Book book = new Book();
      book.setBookId(bookId);
      c.setBook(book);

      LibraryBranch libraryBranch = new LibraryBranch();
      libraryBranch.setBranchId(branchId);
      c.setLibraryBranch(libraryBranch);

      if (oldCopies == 0 && newValue != 0) success = service.createCopies(c);
      else if (oldCopies != 0 && newValue != 0) success = service.updateCopies(c);
      else if (oldCopies != 0 && newValue == 0) service.deleteCopies(c);
    } catch (NumberFormatException e) {
      success = false;
    } finally {
      model.addAttribute("message", success ? "BookCopies Updated Sucessfully" : "Failed to Update BookCopies");
      model.addAttribute("service", service);
    }
    return "listbooks";
  }
}