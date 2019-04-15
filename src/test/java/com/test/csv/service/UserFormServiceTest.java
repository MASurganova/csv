package com.test.csv.service;

import com.test.csv.Application;
import com.test.csv.model.UserForm;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@NoArgsConstructor
public class UserFormServiceTest {

  @Autowired
  UserFormService service;

  @Test
  public void users() {
    Iterable<UserForm> userForms = service.users();
    Assert.assertNotNull(userForms);
    Assert.assertTrue(userForms.iterator().hasNext());
  }

  @Test
  public void top5Forms() {
  }

  @Test
  public void userFormByLastHour() {
  }
}
