package com.test.csv.repository;

import com.test.csv.model.TopForm;
import com.test.csv.model.UserForm;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserFormRepository extends CrudRepository<UserForm, Integer> {

//  SELECT form_id, COUNT(*) as c FROM user_form WHERE form_id != '' GROUP BY form_id ORDER BY c DESC LIMIT 5
  @Query("SELECT new com.test.csv.model.TopForm(u.formId, COUNT(u.userUid)) FROM UserForm u WHERE u.formId NOT in ('') GROUP BY u.formId")
  List<TopForm> getTopForm();

  @Query("SELECT u FROM UserForm u WHERE u.eventTime BETWEEN :startTime AND :endTime ORDER BY u.eventTime DESC")
  List<UserForm> getUserFormByTime(@Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);

//  @Query(value = "SELECT u FROM User u ORDER BY id")
//  Page<UserForm> findAllUsersWithPagination(Pageable pageable);

}