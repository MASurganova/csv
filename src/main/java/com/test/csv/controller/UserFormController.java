package com.test.csv.controller;

import com.test.csv.model.UserForm;
import com.test.csv.service.UserFormService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
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

  @GetMapping("/userForms")
  public String users(Model model) {
    model.addAttribute("forms", service.userForms());
    return "users";
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
  public String userFormByLastHour(Model model) {
    model.addAttribute("forms", service.userFormByLastHour());
    return "lastHourUserForm";
  }

  /**
   * Returns page with list of unfinished user forms by last hour
   */
  @GetMapping("/unfinished")
  public String unfinishedUserForm(Model model) {
    model.addAttribute("forms", service.unfinishedUserForm());
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

    model.addAttribute("forms", service.userForms());

    return "users";
  }
}