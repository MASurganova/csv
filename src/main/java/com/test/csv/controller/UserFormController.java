package com.test.csv.controller;

import com.test.csv.model.UserForm;
import com.test.csv.service.UserFormService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserFormController {

  private UserFormService service;

  @Autowired
  public UserFormController(UserFormService service) {
    this.service = service;
  }

  /**
   * Returns start page
   */
  @GetMapping()
  public String greeting(
      @RequestParam(name = "name", required = false, defaultValue = "World") String name,
      Model model) {
    model.addAttribute("name", name);
    return "greeting";
  }

  /**
   * Returns page with top five of forms
   */
  @GetMapping("/topForms")
  public String topForms(Model model) {
    model.addAttribute("forms", service.top5Forms());
    return "topForms";
  }

  /**
   * Returns page with list of user forms by last hour
   */
  @GetMapping("/lastHour")
  public String userFormByLastHour(Model model, @RequestParam("page") Optional<Integer> page) {
    pagination(model, service.userFormByLastHour(getPageRequest(page, 40)));
    return "lastHour";
  }

  /**
   * Returns page with list of unfinished user forms by last hour
   */
  @GetMapping("/unfinished")
  public String unfinishedUserForm(Model model, @RequestParam("page") Optional<Integer> page) {
    pagination(model, service.unfinishedUserForm(getPageRequest(page, 50)));
    return "unfinished";
  }

  @PostMapping
  public String add(@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime time,
      @RequestParam String group,
      @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime date, Model model) {
    UserForm userForm = new UserForm();
    userForm.setEventTime(time);
    userForm.setEventGroup(group);
    userForm.setDate(date);
    service.add(userForm);
    return "/users";
  }

  @GetMapping(value = "/users")
  public String users(Model model, @RequestParam("page") Optional<Integer> page) {
    pagination(model, service.userForms(getPageRequest(page, 100)));
    return "users";
  }

  /**
   * Adds pagination functionality to the page
   */
  private void pagination(Model model, Page<UserForm> userFormPage) {
    model.addAttribute("formsPage", userFormPage);
    int totalPages = userFormPage.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed()
          .collect(Collectors.toList());
      model.addAttribute("pageNumbers", pageNumbers);
    }
  }

  /**
   * Returns the current PageRequest for pagination
   */
  private PageRequest getPageRequest(Optional<Integer> page, int count) {
    int currentPage = page.orElse(1);
    return PageRequest.of(currentPage - 1, count);
  }
}