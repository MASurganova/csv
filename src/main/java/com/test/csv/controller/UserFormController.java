package com.test.csv.controller;

import com.test.csv.Util.CsvDataLoader;
import com.test.csv.model.TopForm;
import com.test.csv.model.UserForm;
import com.test.csv.repository.UserFormRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
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
  private boolean filled;

  @Autowired
  public UserFormController(UserFormRepository repository) {
    this.repository = repository;
  }

  @GetMapping()
  public String greeting(
      @RequestParam(name = "name", required = false, defaultValue = "World") String name,
      Model model) {
    if (!filled) {
      new Thread(this::fillDatabase).run();
    }
    model.addAttribute("name", name);
    return "greeting";
  }

  @GetMapping("/users")
  public String users(Model model) {
    Iterable<UserForm> userForms = repository.findAll();
    model.addAttribute("userForms", userForms);
    return "users";
  }

  @GetMapping("/topForms")
  public String topForms(Model model) {
    List<TopForm> forms = repository.getTopForm().stream()
        .sorted((f1, f2) -> (int)(f2.getCount() - f1.getCount())).limit(5).collect(Collectors.toList());
    model.addAttribute("forms", forms);
    return "topForms";
  }

  @GetMapping("/lastHour")
  public String userFormByLastHour(Model model) {
    List<UserForm> forms = repository.getUserFormByTime(LocalTime.now().minusHours(1), LocalTime.now());
    model.addAttribute("forms", forms);
    return "lastHourUserForm";
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

  public void fillDatabase() {
    CsvDataLoader.loadCsvUserForms().parallelStream().map(CsvDataLoader::csvUserFormToUserForm)
        .forEach(repository::save);
    filled = true;
  }

}