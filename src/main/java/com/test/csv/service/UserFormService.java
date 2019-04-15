package com.test.csv.service;

import com.test.csv.Util.CsvDataLoader;
import com.test.csv.model.UserForm;
import com.test.csv.repository.UserFormRepository;
import com.test.csv.to.TopForm;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFormService {

  public static final String FILE_NAME = "test_case.csv";

  private UserFormRepository repository;

  @Autowired
  public UserFormService(UserFormRepository repository) {
    this.repository = repository;
  }

  @PostConstruct
  private void init() {
    fillDatabase();
  }

  public Iterable<UserForm> users() {
    return repository.findAll();
  }

  public List<TopForm> top5Forms() {
    return repository.getTopForm().stream()
        .sorted((f1, f2) -> (int)(f2.getCount() - f1.getCount())).limit(5).collect(Collectors.toList());
  }

  public List<UserForm> userFormByLastHour() {
    return repository.getUserFormByTime(LocalTime.now().minusHours(1), LocalTime.now());
  }

  public boolean add(UserForm userForm) {
    return repository.save(userForm) != null;
  }

  private void fillDatabase() {
    CsvDataLoader.loadCsvUserForms(FILE_NAME).parallelStream().map(CsvDataLoader::csvUserFormToUserForm)
        .forEach(repository::save);
  }

}
