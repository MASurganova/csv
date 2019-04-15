package com.test.csv.Util;


import com.test.csv.Application;
import com.test.csv.service.UserFormService;
import com.test.csv.to.CsvUserForm;
import com.test.csv.model.UserForm;
import com.test.csv.repository.UserFormRepository;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CsvDataLoaderTest {

  @Autowired
  UserFormRepository repository;

  @Test
  public void loadUserForms() {
    List<CsvUserForm> list = CsvDataLoader.loadCsvUserForms(UserFormService.FILE_NAME);
    Assert.assertNotNull(list);
    Assert.assertFalse(list.isEmpty());
    Assert.assertNotNull(list.get(0).getSsoid());
    Assert.assertNotNull(list.get(0).getTs());
  }

  @Test
  public void csvUserFormToUserForm() {
    CsvUserForm csvUserForm = new CsvUserForm();
    csvUserForm.setSsoid("650ae77a-ffce-475d-a930-c7e345e0658c");
    csvUserForm.setTs("1499763601");
    csvUserForm.setGrp("guis_-47");
    csvUserForm.setType("formcontroller");
    csvUserForm.setSubtype("send");
    csvUserForm.setUrl("https://www.mos.ru/pgu/ru/application/guis/-47/#step_1");
    csvUserForm.setOrgid("guis");
    csvUserForm.setFormid("-47");
    csvUserForm.setCode("MPGU");
    csvUserForm.setYmdh("2017-07-11-09");
    UserForm userForm = CsvDataLoader.csvUserFormToUserForm(csvUserForm);
    Assert.assertNotNull(userForm);
    Assert.assertNotNull(userForm.getDate());
    Assert.assertNotNull(userForm.getEventTime());
    Assert.assertFalse(userForm.getCode().isEmpty());
    Assert.assertFalse(userForm.getEventGroup().isEmpty());
    Assert.assertFalse(userForm.getEventType().isEmpty());
    Assert.assertFalse(userForm.getEventSubtype().isEmpty());
    Assert.assertFalse(userForm.getOrganizationId().isEmpty());
    Assert.assertFalse(userForm.getFormId().isEmpty());
    Assert.assertFalse(userForm.getUrl().isEmpty());
  }
}
