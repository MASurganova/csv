package com.test.csv.service;

import com.test.csv.Application;
import com.test.csv.model.UserForm;
import com.test.csv.to.TopForm;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@NoArgsConstructor
public class UserFormServiceTest {

  @Autowired
  UserFormService service;

  @Test
  public void users() {
    Iterable<UserForm> userForms = service.userForms(PageRequest.of(0, 10));
    Assert.assertNotNull(userForms);
    Assert.assertTrue(userForms.iterator().hasNext());
  }

  @Test
  public void top5Forms() {
    List<TopForm> topForms = service.top5Forms();
    Assert.assertNotNull(topForms);
    Assert.assertEquals(topForms.size(), 5);
    Assert.assertTrue(topForms.get(0).getCount() > topForms.get(1).getCount());
  }

  @Test
  public void userFormByLastHour() {
    Iterable<UserForm> userForms = service.userFormByLastHour(PageRequest.of(0, 10));
    Assert.assertNotNull(userForms);
    Assert.assertFalse(userForms.iterator().hasNext());
  }

  @Test
  public void userFormByTime() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime start = LocalDateTime.parse("2017-07-11 08:59:00", dtf);
    LocalDateTime end = LocalDateTime.parse("2017-07-11 09:00:00", dtf);
    Page<UserForm> userForms = service.userFormByTime(start, end, PageRequest.of(0, 10));
    Assert.assertNotNull(userForms);
    Assert.assertTrue(userForms.stream().map(UserForm::getEventTime)
        .allMatch(t -> t.compareTo(start) >= 0 && t.compareTo(end) <= 0));
  }

  @Test
  public void unfinishedUserForm() {
    Page<UserForm> userForms = service.unfinishedUserForm(PageRequest.of(0, 100));
    System.out.println("\n\n\n" + userForms.getTotalElements() + "\n\n\n");
    Assert.assertNotNull(userForms);
    Assert.assertTrue(userForms.iterator().hasNext());
    Assert.assertFalse(
        userForms.stream().map(UserForm::getUserUid).anyMatch(u -> u == null || u.isEmpty()));
    Assert.assertFalse(
        userForms.stream().map(UserForm::getFormId).anyMatch(u -> u == null || u.isEmpty()));
    Assert.assertFalse(userForms.stream().map(UserForm::getEventSubtype)
        .anyMatch(u -> u.equals("send") || u.equals("success")));
  }

}
