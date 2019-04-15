package com.test.csv.service;

import com.test.csv.Application;
import com.test.csv.model.UserForm;
import com.test.csv.to.TopForm;
import java.util.List;
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
    Iterable<UserForm> userForms = service.userForms();
    Assert.assertNotNull(userForms);
    Assert.assertTrue(userForms.iterator().hasNext());
  }

  @Test
  public void top5Forms() {
    List<TopForm> topForms = service.top5Forms();
    Assert.assertNotNull(topForms);
    Assert.assertTrue(topForms.size() == 5);
    Assert.assertTrue(topForms.get(0).getCount() > topForms.get(1).getCount());
  }

  @Test
  public void userFormByLastHour() {
  }
}
