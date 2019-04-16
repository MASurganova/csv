package com.test.csv.service;

import com.test.csv.Util.CsvDataLoader;
import com.test.csv.model.UserForm;
import com.test.csv.repository.UserFormRepository;
import com.test.csv.to.TopForm;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to get data from the repository
 */
@Service
public class UserFormService {

  public static final String FILE_NAME = "test_case.csv";

  private UserFormRepository repository;

  @Autowired
  public UserFormService(UserFormRepository repository) {
    this.repository = repository;
  }

  /**
   * Fills database from csv file at the start
   */
  @PostConstruct
  private void init() {
    fillDatabase();
  }

  /**
   * Returns list of user forms
   */
  public Iterable<UserForm> userForms() {
    return repository.findAll();
  }

  /**
   * Returns top five of forms
   */
  public List<TopForm> top5Forms() {
    return repository.getTopForm().stream()
        .sorted((f1, f2) -> (int)(f2.getCount() - f1.getCount())).limit(5).collect(Collectors.toList());
  }

  /**
   * Returns list of user forms by last hour
   */
  public List<UserForm> userFormByLastHour() {
    return  userFormByTime(LocalDateTime.now().minusHours(1), LocalDateTime.now());
  }

   /**
   * Returns list of user forms by time
   */
  public List<UserForm> userFormByTime(LocalDateTime start, LocalDateTime end) {
    return repository.findAllByEventTimeBetweenOrderByEventTimeDesc(start, end);
  }

  /**
   * Returns list of unfinished user forms
   */
  public List<UserForm> unfinishedUserForm() {
    return repository.getUnfinishedUserForm();
  }

  public boolean add(UserForm userForm) {
    return repository.save(userForm) != null;
  }

  /**
   * Fills database from csv file
   */
  private void fillDatabase() {
    CsvDataLoader.loadCsvUserForms(FILE_NAME).stream().map(CsvDataLoader::csvUserFormToUserForm)
        .forEach(repository::save);
  }

}
