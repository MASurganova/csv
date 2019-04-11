package com.test.csv.controller;

import com.test.csv.Application;
import com.test.csv.model.TopForm;
import com.test.csv.repository.UserFormRepository;
import java.util.List;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserFormControllerTest {

  @Autowired
  UserFormController controller;

  @Autowired
  UserFormRepository repository;

  @Test
  public void fillDatabase() {
    controller.fillDatabase();
  }
}
