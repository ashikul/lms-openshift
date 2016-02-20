package com.gcit.lms.controller;

import com.gcit.lms.domain.*;
import com.gcit.lms.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Controller
public class AdminController {
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
  @Autowired
  AdminService service;

//  @RequestMapping(value = "/addAuthor", method = RequestMethod.POST)
//  public String addAuthor(Locale locale, Model model, @RequestParam(value = "authorName") String name) {
//    Author author = new Author();
//    author.setAuthorName(name);
//
//    boolean success = service.createAuthor(author);
//    model.addAttribute("message", success ? "Author Added Sucessfully" : "Failed to Add Author");
//    return "authors";
//  }

  @RequestMapping(value = "/addAuthor", method = {RequestMethod.GET, RequestMethod.POST})
  public String addAuthor(Locale locale, Model model, @RequestParam(value = "authorName", required = false) String name) {
    if (name == null) {
//      model.addAttribute("author", service.getAuthorById(id));
      return "admin/addauthor";
    }
    else {
      model.addAttribute("service", service);
    Author author = new Author();
    author.setAuthorName(name);

    boolean success = service.createAuthor(author);
    model.addAttribute("message", success ? "Author Added Sucessfully" : "Failed to Add Author");
      return "admin/viewauthors";
    }
  }

  @RequestMapping(value = "/editAuthor", method = {RequestMethod.GET, RequestMethod.POST})
  public String editAuthor(Locale locale, Model model, @RequestParam(value = "authorName", required = false) String name, @RequestParam(value = "authorId") int id) {
    if (name == null) {
      model.addAttribute("author", service.getAuthorById(id));
      return "admin/editauthor";
    }
    else {
      model.addAttribute("service", service);
      Author author = new Author();
      author.setAuthorId(id);
      author.setAuthorName(name);

      boolean success = service.updateAuthor(author);
      model.addAttribute("message", success ? "Author Edited Sucessfully" : "Failed to Edit Author");
      return "admin/viewauthors";
    }
  }

  @RequestMapping(value = "/deleteAuthor", method = RequestMethod.GET)
  public String deleteAuthor(Locale locale, Model model, @RequestParam(value = "authorId") int id) {
    model.addAttribute("service", service);
    Author author = new Author();
    author.setAuthorId(id);

    service.deleteAuthor(author);
    return "admin/viewauthors";
  }

  @RequestMapping(value = "/searchAuthors", method = RequestMethod.GET)
  @ResponseBody
  public String searchAuthor(Locale locale, Model model, @RequestParam(value = "searchString") String searchString) {
    searchString = "%" + searchString + "%";
    List<Author> lst = service.getAuthorsByName(searchString, -1, 5);
    int count = lst.size();
    int pages = count / 5;
    if (count % 5 != 0) pages++;
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= pages; i++) {
      sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>" + i + "</a></li>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/pageAuthors", method = RequestMethod.GET)
  @ResponseBody
  public String pageAuthor(Locale locale, Model model, @RequestParam(value = "searchString") String searchString,
                           @RequestParam(value = "pageNo") int pageNo) {
    searchString = "%" + searchString + "%";
    List<Author> lst = service.getAuthorsByName(searchString, pageNo, 5);
    StringBuilder sb = new StringBuilder();
    sb.append("<tr><th>Name</th></tr>");
    for (Author a : lst) {
      sb.append("<tr><td>" + a.getAuthorName() + "</td><td><button type='button' "
          + "class='btn btn btn-default'data-toggle='modal' data-target='#myModal1'"
          + "href='editAuthor?authorId=" + a.getAuthorId() + "'>EDIT</button></td><td>"
          + "<button type='button' class='btn btn btn-danger' "
          + "onclick=\"javascript:location.href='deleteAuthor?authorId=" + a.getAuthorId() + "'\">DELETE</button></td></tr>)");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/addBook", method = RequestMethod.POST)
  public String addBook(Locale locale, Model model, @RequestParam(value = "title") String title,
                        @RequestParam(value = "authorId", required = false) String[] authorIds, @RequestParam(value = "pubId") int pubId,
                        @RequestParam(value = "genre_id", required = false) String[] genre_ids) {
    Book book = new Book();
    book.setTitle(title);
    List<Author> authors = new ArrayList<Author>();
    for (int i = 0; authorIds != null && i < authorIds.length; i++) {
      Author a = new Author();
      a.setAuthorId(Integer.parseInt(authorIds[i]));
      authors.add(a);
    }
    book.setAuthors(authors);
    Publisher borrower = new Publisher();
    borrower.setPublisherId(pubId);
    book.setPublisher(borrower);
    List<Genre> genres = new ArrayList<Genre>();
    for (int i = 0; genre_ids != null && i < genre_ids.length; i++) {
      Genre g = new Genre();
      g.setGenreId(Integer.parseInt(genre_ids[i]));
      genres.add(g);
    }
    book.setGenres(genres);

    boolean success = service.createBook(book);
    model.addAttribute("message", success ? "Book Added Sucessfully" : "Failed to Add Book");
    return "removed/books";
  }

  @RequestMapping(value = "/editBook", method = {RequestMethod.GET, RequestMethod.POST})
  public String editBook(Locale locale, Model model, @RequestParam(value = "title", required = false) String title, @RequestParam(value = "bookId") int id,
                         @RequestParam(value = "authorId", required = false) String[] authorIds, @RequestParam(value = "pubId", required = false) Integer pubId,
                         @RequestParam(value = "genre_id", required = false) String[] genre_ids) {
    model.addAttribute("service", service);
    if (title == null) {
      model.addAttribute("book", service.getBookById(id));

      return "admin/editbook";
    }
    else {
      Book book = new Book();
      book.setBookId(id);
      book.setTitle(title);
      List<Author> authors = new ArrayList<Author>();
      for (int i = 0; authorIds != null && i < authorIds.length; i++) {
        Author a = new Author();
        a.setAuthorId(Integer.parseInt(authorIds[i]));
        authors.add(a);
      }
      book.setAuthors(authors);
      Publisher publisher = new Publisher();
      publisher.setPublisherId(pubId);
      book.setPublisher(publisher);
      List<Genre> genres = new ArrayList<Genre>();
      for (int i = 0; genre_ids != null && i < genre_ids.length; i++) {
        Genre g = new Genre();
        g.setGenreId(Integer.parseInt(genre_ids[i]));
        genres.add(g);
      }
      book.setGenres(genres);

      boolean success = service.updateBook(book);
      model.addAttribute("message", success ? "Book Edited Sucessfully" : "Failed to Edit Book");
      return "admin/viewbooks";
    }
  }

  @RequestMapping(value = "/deleteBook", method = RequestMethod.GET)
  public String deleteBook(Locale locale, Model model, @RequestParam(value = "bookId") int id) {
    model.addAttribute("service", service);
    Book Book = new Book();
    Book.setBookId(id);

    service.deleteBook(Book);
    return "admin/viewbooks";
  }

  @RequestMapping(value = "/searchBooks", method = RequestMethod.GET)
  @ResponseBody
  public String searchBook(Locale locale, Model model, @RequestParam(value = "searchString") String searchString) {
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

  @RequestMapping(value = "/pageBooks", method = RequestMethod.GET)
  @ResponseBody
  public String pageBook(Locale locale, Model model, @RequestParam(value = "searchString") String searchString,
                         @RequestParam(value = "pageNo") int pageNo) {
    int maxBook = 0;
    int maxGenre = 0;
    searchString = "%" + searchString + "%";

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
      sb.append("<td><button type='button' class='btn btn btn-default' data-toggle='modal' data-target='#myModal1' "
          + "href='editBook?bookId=" + b.getBookId() + "'>EDIT</button></td>"
          + "<td><button type='button' class='btn btn btn-danger' "
          + "onclick=\"javascript:location.href='deleteBook?bookId=" + b.getBookId() + "'\">DELETE</button></td></tr>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/addPublisher", method = RequestMethod.POST)
  public String addPublisher(Locale locale, Model model, @RequestParam(value = "name") String name,
                             @RequestParam(value = "address") String address, @RequestParam(value = "phone") String phone) {
    Publisher publisher = new Publisher();
    publisher.setPublisherName(name);
    publisher.setPublisherAddress(address);
    publisher.setPublisherPhone(phone);

    boolean success = service.createPublisher(publisher);
    model.addAttribute("message", success ? "Publisher Added Sucessfully" : "Failed to Add Publisher");
    return "removed/publishers";
  }

  @RequestMapping(value = "/editPublisher", method = {RequestMethod.GET, RequestMethod.POST})
  public String editPublisher(Locale locale, Model model, @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "id") Integer id, @RequestParam(value = "address", required = false) String address,
                              @RequestParam(value = "phone", required = false) String phone) {
    if (name == null) {
      model.addAttribute("publisher", service.getPublisherById(id));
      return "admin/editpublisher";
    }
    else {
      model.addAttribute("service", service);
      Publisher publisher = new Publisher();
      publisher.setPublisherId(id);
      publisher.setPublisherName(name);
      publisher.setPublisherAddress(address);
      publisher.setPublisherPhone(phone);

      boolean success = service.updatePublisher(publisher);
      model.addAttribute("message", success ? "Publisher Edited Sucessfully" : "Failed to Edit Publisher");
      return "admin/viewpublishers";
    }
  }

  @RequestMapping(value = "/deletePublisher", method = RequestMethod.GET)
  public String deletePublisher(Locale locale, Model model, @RequestParam(value = "id") int id) {
    model.addAttribute("service", service);
    Publisher publisher = new Publisher();
    publisher.setPublisherId(id);

    service.deletePublisher(publisher);
    return "admin/viewpublishers";
  }

  @RequestMapping(value = "/searchPublishers", method = RequestMethod.GET)
  @ResponseBody
  public String searchPublisher(Locale locale, Model model, @RequestParam(value = "searchString") String searchString) {
    searchString = "%" + searchString + "%";
    List<Publisher> lst = service.getPublishersByName(searchString, -1, 5);
    int count = lst.size();
    int pages = count / 5;
    if (count % 5 != 0) pages++;
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= pages; i++) {
      sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>" + i + "</a></li>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/pagePublishers", method = RequestMethod.GET)
  @ResponseBody
  public String pagePublisher(Locale locale, Model model, @RequestParam(value = "searchString") String searchString,
                              @RequestParam(value = "pageNo") int pageNo) {
    searchString = "%" + searchString + "%";
    List<Publisher> lst = service.getPublishersByName(searchString, pageNo, 5);
    StringBuilder sb = new StringBuilder();
    sb.append("<tr><th>Name</th><th>Address</th><th>Phone</th></tr>");
    for (Publisher p : lst) {
      sb.append("<tr><td>" + p.getPublisherName() + "</td>"
          + "<td>" + p.getPublisherAddress() + "</td>"
          + "<td>" + (p.getPublisherPhone() != null ? p.getPublisherPhone() : "") + "</td>"
          + "<td><button type='button' class='btn btn btn-default' data-toggle='modal' data-target='#myModal1' "
          + "href='editPublisher?id=" + p.getPublisherId() + "'>EDIT</button></td>"
          + "<td><button type='button' class='btn btn btn-danger' "
          + "onclick=\"javascript:location.href='deletePublisher?id=" + p.getPublisherId() + "'\">DELETE</button></td></tr>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/addBranch", method = RequestMethod.POST)
  public String addBranch(Locale locale, Model model, @RequestParam(value = "name") String name,
                          @RequestParam(value = "address") String address) {
    LibraryBranch libraryBranch = new LibraryBranch();
    libraryBranch.setBranchName(name);
    libraryBranch.setBranchAddress(address);

    boolean success = service.createBranch(libraryBranch);
    model.addAttribute("message", success ? "LibraryBranch Added Sucessfully" : "Failed to Add LibraryBranch");
    return "removed/branches";
  }

  @RequestMapping(value = "/editBranch", method = {RequestMethod.GET, RequestMethod.POST})
  public String editBranch(Locale locale, Model model, @RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "id") Integer id, @RequestParam(value = "address", required = false) String address,
                           @RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "forward", required = false) String forward) {
    if (name == null) {
      model.addAttribute("branch", service.getBranchById(id));
      return "admin/editbranch";
    }
    else {
      model.addAttribute("service", service);
      LibraryBranch libraryBranch = new LibraryBranch();
      libraryBranch.setBranchId(id);
      libraryBranch.setBranchName(name);
      libraryBranch.setBranchAddress(address);

      boolean success = service.updateBranch(libraryBranch);
      model.addAttribute("message", success ? "LibraryBranch Edited Sucessfully" : "Failed to Edit LibraryBranch");
      if (forward.equals(""))
        return "admin/viewbranches";
      else
        return "librarian/choosebranch";
    }
  }

  @RequestMapping(value = "/deleteBranch", method = RequestMethod.GET)
  public String deleteBranch(Locale locale, Model model, @RequestParam(value = "id") int id) {
    model.addAttribute("service", service);
    LibraryBranch libraryBranch = new LibraryBranch();
    libraryBranch.setBranchId(id);

    service.deleteBranch(libraryBranch);
    return "admin/viewbranches";
  }

  @RequestMapping(value = "/searchBranches", method = RequestMethod.GET)
  @ResponseBody
  public String searchBranch(Locale locale, Model model, @RequestParam(value = "searchString") String searchString) {
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

  @RequestMapping(value = "/pageBranches", method = RequestMethod.GET)
  @ResponseBody
  public String pageBranch(Locale locale, Model model, @RequestParam(value = "searchString") String searchString,
                           @RequestParam(value = "pageNo") int pageNo) {
    searchString = "%" + searchString + "%";
    List<LibraryBranch> lst = service.getBranchesByName(searchString, pageNo, 5);
    StringBuilder sb = new StringBuilder();
    sb.append("<tr><th>Name</th><th>Address</th></tr>");
    for (LibraryBranch b : lst) {
      sb.append("<tr><td>" + b.getBranchName() + "</td>"
          + "<td>" + b.getBranchAddress() + "</td>"
          + "<td><button type='button' class='btn btn btn-default' data-toggle='modal' data-target='#myModal1' "
          + "href='editBranch?id=" + b.getBranchId() + "'>EDIT</button></td>"
          + "<td><button type='button' class='btn btn btn-danger' "
          + "onclick=\"javascript:location.href='deleteBranch?id=" + b.getBranchId() + "'\">DELETE</button></td></tr>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/addBorrower", method = RequestMethod.POST)
  public String addBorrower(Locale locale, Model model, @RequestParam(value = "name") String name,
                            @RequestParam(value = "address") String address, @RequestParam(value = "phone") String phone) {
    Borrower borrower = new Borrower();
    borrower.setName(name);
    borrower.setAddress(address);
    borrower.setPhone(phone);

    boolean success = service.createBorrower(borrower);
    model.addAttribute("message", success ? "Borrower Added Sucessfully" : "Failed to Add Borrower");
    return "removed/borrowers";
  }

  @RequestMapping(value = "/editBorrower", method = {RequestMethod.GET, RequestMethod.POST})
  public String editBorrower(Locale locale, Model model, @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "id") Integer id, @RequestParam(value = "address", required = false) String address,
                             @RequestParam(value = "phone", required = false) String phone) {
    if (name == null) {
      model.addAttribute("borrower", service.getBorrowerById(id));
      return "admin/editborrower";
    }
    else {
      model.addAttribute("service", service);
      Borrower borrower = new Borrower();
      borrower.setCardNo(id);
      borrower.setName(name);
      borrower.setAddress(address);
      borrower.setPhone(phone);

      boolean success = service.updateBorrower(borrower);
      model.addAttribute("message", success ? "Borrower Edited Sucessfully" : "Failed to Edit Borrower");
      return "admin/viewborrowers";
    }
  }

  @RequestMapping(value = "/deleteBorrower", method = RequestMethod.GET)
  public String deleteBorrower(Locale locale, Model model, @RequestParam(value = "id") int id) {
    model.addAttribute("service", service);
    Borrower borrower = new Borrower();
    borrower.setCardNo(id);

    service.deleteBorrower(borrower);
    return "admin/viewborrowers";
  }

  @RequestMapping(value = "/searchBorrowers", method = RequestMethod.GET)
  @ResponseBody
  public String searchBorrower(Locale locale, Model model, @RequestParam(value = "searchString") String searchString) {
    searchString = "%" + searchString + "%";
    List<Borrower> lst = service.getBorrowersByName(searchString, -1, 5);
    int count = lst.size();
    int pages = count / 5;
    if (count % 5 != 0) pages++;
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= pages; i++) {
      sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>" + i + "</a></li>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/pageBorrowers", method = RequestMethod.GET)
  @ResponseBody
  public String pageBorrower(Locale locale, Model model, @RequestParam(value = "searchString") String searchString,
                             @RequestParam(value = "pageNo") int pageNo) {
    searchString = "%" + searchString + "%";
    List<Borrower> lst = service.getBorrowersByName(searchString, pageNo, 5);
    StringBuilder sb = new StringBuilder();
    sb.append("<tr><th>Name</th><th>Address</th><th>Phone</th></tr>");
    for (Borrower b : lst) {
      sb.append("<tr><td>" + b.getName() + "</td>"
          + "<td>" + b.getAddress() + "</td>"
          + "<td>" + (b.getPhone() != null ? b.getPhone() : "") + "</td>"
          + "<td><button type='button' class='btn btn btn-default' data-toggle='modal' data-target='#myModal1' "
          + "href='editBorrower?id=" + b.getCardNo() + "'>EDIT</button></td>"
          + "<td><button type='button' class='btn btn btn-danger' "
          + "onclick=\"javascript:location.href='deleteBorrower?id=" + b.getCardNo() + "'\">DELETE</button></td></tr>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/overrideloan", method = {RequestMethod.GET, RequestMethod.POST})
  public String overrideLoan(Locale locale, Model model, @RequestParam(value = "bookId") Integer bookId,
                             @RequestParam(value = "branchId") Integer branchId, @RequestParam(value = "cardNo") Integer cardNo,
                             @RequestParam(value = "dateOut") java.sql.Date dateOut, @RequestParam(value = "dueDate") java.sql.Date dueDate) {
    BookLoan bookLoan = new BookLoan();
    Book book = new Book();
    book.setBookId(bookId);
    bookLoan.setBook(book);
    LibraryBranch libraryBranch = new LibraryBranch();
    libraryBranch.setBranchId(branchId);
    bookLoan.setLibraryBranch(libraryBranch);
    Borrower borrower = new Borrower();
    borrower.setCardNo(cardNo);
    bookLoan.setBorrower(borrower);
    bookLoan.setDateOut(dateOut);
    bookLoan.setDueDate(dueDate);
    model.addAttribute("loan", bookLoan);
    return "admin/overrideloan";
  }

  @RequestMapping(value = "/override", method = {RequestMethod.GET, RequestMethod.POST})
  public String override(Locale locale, Model model, @RequestParam(value = "bookId") Integer bookId,
                         @RequestParam(value = "branchId") Integer branchId, @RequestParam(value = "cardNo") Integer cardNo,
                         @RequestParam(value = "dateOut") java.sql.Date dateOut, @RequestParam(value = "dueDate") java.sql.Date dueDate) {
    BookLoan bookLoan = new BookLoan();
    Borrower borrower = new Borrower();
    borrower.setCardNo(cardNo);
    bookLoan.setBorrower(borrower);

    Book book = new Book();
    book.setBookId(bookId);
    bookLoan.setBook(book);

    LibraryBranch libraryBranch = new LibraryBranch();
    libraryBranch.setBranchId(branchId);
    bookLoan.setLibraryBranch(libraryBranch);

    bookLoan.setDateOut(dateOut);
    bookLoan.setDueDate(dueDate);

    boolean success = service.updateLoan(bookLoan);
    model.addAttribute("message", success ? "Overridden BookLoan Sucessfully" : "Failed to Override BookLoan");
    model.addAttribute("service", service);
    return "admin/loans";
  }

  @RequestMapping(value = "/searchLoans", method = RequestMethod.GET)
  @ResponseBody
  public String searchLoan(Locale locale, Model model, @RequestParam(value = "searchString") String searchString) {
    searchString = "%" + searchString + "%";
    List<BookLoan> lst = service.getLoansByDueDate(searchString, -1, 5);
    int count = lst.size();
    int pages = count / 5;
    if (count % 5 != 0) pages++;
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= pages; i++) {
      sb.append("<li><a id='page' href='#' onclick='paging(" + i + ");'>" + i + "</a></li>");
    }
    return sb.toString();
  }

  @RequestMapping(value = "/pageLoans", method = RequestMethod.GET)
  @ResponseBody
  public String pageLoan(Locale locale, Model model, @RequestParam(value = "searchString") String searchString,
                         @RequestParam(value = "pageNo") int pageNo) {
    searchString = "%" + searchString + "%";
    List<BookLoan> lst = service.getLoansByDueDate(searchString, pageNo, 5);
    StringBuilder sb = new StringBuilder();
    sb.append("<tr><th>Borrower Name</th><th>Book Title</th><th>LibraryBranch Name</th>"
        + "<th>LibraryBranch Address</th><th>Date Out</th><th>Due Date</th></tr>");
    for (BookLoan l : lst) {
      sb.append("<tr><td>" + l.getBorrower().getName() + "</td><td>" + l.getBook().getTitle() + "</td>"
          + "<td>" + l.getLibraryBranch().getBranchName() + "</td><td>" + l.getLibraryBranch().getBranchAddress() + "</td>"
          + "<td>" + l.getDateOut().toString() + "</td><td>" + l.getDueDate().toString() + "</td>"
          + "<td><button type='button' class='btn btn btn-default' data-toggle='modal' "
          + "data-target='#myModal1' href='overrideloan?bookId=" + l.getBook().getBookId()
          + "&branchId=" + l.getLibraryBranch().getBranchId() + "&cardNo=" + l.getBorrower().getCardNo()
          + "&dateOut=" + l.getDateOut().toString() + "&dueDate=" + l.getDueDate().toString() + "'>OVERRIDE</button></td></tr>");
    }
    return sb.toString();
  }
}
