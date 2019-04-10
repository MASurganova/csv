package com.test.csv.controller;

import com.test.csv.model.UserForm;
import com.test.csv.repisitory.UserFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserFormController {

  UserFormRepository repository;

  @Autowired
  public UserFormController(UserFormRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/greeting")
  public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
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
  public String add(@RequestParam String text, @RequestParam String tag, Model model) {
    UserForm userForm = new UserForm();
    userForm.setText(text);
    userForm.setTag(tag);

   repository.save(userForm);

    Iterable<UserForm> userForms = repository.findAll();

    model.addAttribute("userForms", userForms);

    return "users";
  }

}