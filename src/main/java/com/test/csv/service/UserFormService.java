package com.test.csv.service;

import com.test.csv.Util.CsvDataLoader;
import com.test.csv.model.UserForm;
import com.test.csv.repository.UserFormRepository;
import com.test.csv.to.TopForm;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
  public Page<UserForm> userForms(Pageable pageable) {
    return repository.findAll(pageable);
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
  public Page<UserForm> userFormByLastHour(Pageable pageable) {
    return  userFormByTime(LocalDateTime.now().minusHours(1), LocalDateTime.now(), pageable);
  }

   /**
   * Returns list of user forms by time
   */
  public Page<UserForm> userFormByTime(LocalDateTime start, LocalDateTime end, Pageable pageable) {
    return repository.findAllByEventTimeBetweenOrderByEventTimeDesc(start, end, pageable);
  }

  /**
   * Returns list of unfinished user forms
   */
  public Page<UserForm> unfinishedUserForm(Pageable pageable) {
    return repository.getUnfinishedUserForm(pageable);
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
