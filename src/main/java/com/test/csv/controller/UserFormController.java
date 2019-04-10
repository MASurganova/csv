package com.test.csv.controller;

import com.test.csv.model.UserForm;
import com.test.csv.repisitory.UserFormRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

  private UserFormRepository repository;

  @Autowired
  public UserFormController(UserFormRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/greeting")
  public String greeting(
      @RequestParam(name = "name", required = false, defaultValue = "World") String name,
      Model model) {
    model.addAttribute("name", name);
    return "greeting";
  }

  @GetMapping
  public String users(Model model) {
    Iterable<UserForm> userForms = repository.findAll();
    model.addAttribute("userForms", userForms);
    return "users";
  }

  @PostMapping
  public String add(@RequestParam LocalTime time, @RequestParam String group,
      @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime date, Model model) {
    UserForm userForm = new UserForm();
    userForm.setEventTime(time);
    userForm.setEventGroup(group);
    userForm.setDate(date);

    repository.save(userForm);

    Iterable<UserForm> userForms = repository.findAll();

    model.addAttribute("userForms", userForms);

    return "users";
  }

}