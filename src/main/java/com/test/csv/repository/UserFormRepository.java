package com.test.csv.repository;

import com.test.csv.model.UserForm;
import com.test.csv.to.TopForm;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserFormRepository extends CrudRepository<UserForm, Integer> {

  @Query("SELECT new com.test.csv.to.TopForm(u.formId, COUNT(u.userUid)) FROM UserForm u "
      + "WHERE u.formId NOT in ('') GROUP BY u.formId")
  List<TopForm> getTopForm();

  @Query("SELECT u From UserForm u WHERE (u.userUid, u.formId, u.eventTime) IN "
      + "(SELECT u1.userUid, u1.formId, max(u1.eventTime) FROM UserForm u1 "
      + "GROUP BY u1.userUid, u1.formId) AND u.eventSubtype NOT IN ('send', 'success') "
      + "AND NOT u.userUid = '' AND u.formId NOT IN ('', 'null')")
  List<UserForm> getUnfinishedUserForm();

  List<UserForm> findAllByEventTimeBetweenOrderByEventTimeDesc(LocalDateTime start,
      LocalDateTime end);

}