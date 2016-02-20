package com.gcit.lms.controller;

import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.LibrarianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
  private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
  @Autowired
  AdminService adminService;
  @Autowired
  LibrarianService libService;
  @Autowired
  BorrowerService borrowerService;


  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(Locale locale, Model model) {
//    logger.info("Welcome home! The client locale is {}.", locale);

    return "index";
  }

  /*Menu*/
  @RequestMapping(value = "/admin", method = RequestMethod.GET)
  public String admin(Locale locale, Model model) {
    return "admin";
  }

  @RequestMapping(value = "/librarian", method = RequestMethod.GET)
  public String librarian(Locale locale, Model model) {
    return "librarian";
  }

  @RequestMapping(value = "/checkcard", method = RequestMethod.GET)
  public String checkCard(Locale locale, Model model) {
    return "checkcard";
  }

  /*Author*/
//  @RequestMapping(value = "/authors", method = RequestMethod.GET)
//  public String authors(Locale locale, Model model) {
//    return "authors";
//  }

//  @RequestMapping(value = "/addauthor", method = RequestMethod.GET)
//  public String addAuthor(Locale locale, Model model) {
//    return "addauthor";
//  }

  @RequestMapping(value = "/viewauthors", method = RequestMethod.GET)
  public String viewAuthors(Locale locale, Model model) {
    model.addAttribute("service", adminService);
    return "viewauthors";
  }

  /*Book*/
  @RequestMapping(value = "/books", method = RequestMethod.GET)
  public String books(Locale locale, Model model) {
    return "removed/books";
  }

  @RequestMapping(value = "/addbook", method = RequestMethod.GET)
  public String addBook(Locale locale, Model model) {
    model.addAttribute("service", adminService);
    return "admin/addbook";
  }

  @RequestMapping(value = "/viewbooks", method = RequestMethod.GET)
  public String viewBooks(Locale locale, Model model) {
    model.addAttribute("service", adminService);
    return "viewbooks";
  }

  /*Publisher*/
  @RequestMapping(value = "/publishers", method = RequestMethod.GET)
  public String publishers(Locale locale, Model model) {
    return "publishers";
  }

  @RequestMapping(value = "/addpublisher", method = RequestMethod.GET)
  public String addPublisher(Locale locale, Model model) {
    model.addAttribute("service", adminService);
    return "admin/addpublisher";
  }

  @RequestMapping(value = "/viewpublishers", method = RequestMethod.GET)
  public String viewPublishers(Locale locale, Model model) {
    model.addAttribute("service", adminService);
    return "viewpublishers";
  }

  /*LibraryBranch*/
  @RequestMapping(value = "/branches", method = RequestMethod.GET)
  public String branches(Locale locale, Model model) {
    return "removed/branches";
  }

  @RequestMapping(value = "/addbranch", method = RequestMethod.GET)
  public String addBranch(Locale locale, Model model) {
    model.addAttribute("service", adminService);
    return "admin/addbranch";
  }

  @RequestMapping(value = "/viewbranches", method = RequestMethod.GET)
  public String viewBranches(Locale locale, Model model) {
    model.addAttribute("service", adminService);
    return "viewbranches";
  }

  /*Borrower*/
  @RequestMapping(value = "/borrowers", method = RequestMethod.GET)
  public String borrowers(Locale locale, Model model) {
    return "removed/borrowers";
  }

  @RequestMapping(value = "/addborrower", method = RequestMethod.GET)
  public String addBorrowers(Locale locale, Model model) {
    model.addAttribute("service", adminService);
    return "admin/addborrower";
  }

  @RequestMapping(value = "/viewborrowers", method = RequestMethod.GET)
  public String viewBorrowers(Locale locale, Model model) {
    model.addAttribute("service", adminService);
    return "viewborrowers";
  }

  /*BookLoan*/
  @RequestMapping(value = "/loans", method = RequestMethod.GET)
  public String loans(Locale locale, Model model) {
    model.addAttribute("service", adminService);
    return "loans";
  }

  /*Lib*/
  @RequestMapping(value = "/listbranches", method = RequestMethod.GET)
  public String listBranches(Locale locale, Model model) {
    model.addAttribute("service", libService);
    return "listbranches";
  }

  @RequestMapping(value = "/choosebranch", method = RequestMethod.GET)
  public String chooseBranch(Locale locale, Model model) {
    return "choosebranch";
  }

  @RequestMapping(value = "/listbooks", method = RequestMethod.GET)
  public String listBooks(Locale locale, Model model) {
    model.addAttribute("service", libService);
    return "listbooks";
  }

  /*Borrower*/
  @RequestMapping(value = "/signin", method = RequestMethod.GET)
  public String signin(Locale locale, Model model) {
    model.addAttribute("service", libService);
    return "signin";
  }

  @RequestMapping(value = "/checkoutbranch", method = RequestMethod.GET)
  public String checkoutBranch(Locale locale, Model model) {
    model.addAttribute("service", borrowerService);
    return "checkoutbranch";
  }

  @RequestMapping(value = "/checkoutbook", method = RequestMethod.GET)
  public String checkoutBook(Locale locale, Model model) {
    model.addAttribute("service", borrowerService);
    return "checkoutbook";
  }

  @RequestMapping(value = "/checkinbranch", method = RequestMethod.GET)
  public String checkinBranch(Locale locale, Model model) {
    model.addAttribute("service", borrowerService);
    return "checkinbranch";
  }

  @RequestMapping(value = "/checkinbook", method = RequestMethod.GET)
  public String checkinBook(Locale locale, Model model) {
    model.addAttribute("service", borrowerService);
    return "checkinbook";
  }
}
