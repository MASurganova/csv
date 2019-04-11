package com.test.csv.Util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.test.csv.model.CsvUserForm;
import com.test.csv.model.UserForm;
import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class CsvDataLoader {
  private static final Logger logger = LoggerFactory.getLogger(CsvDataLoader.class);

  public static List<CsvUserForm> loadCsvUserForms() {
    try {
      CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';');
      CsvMapper mapper = new CsvMapper();
      File file = new ClassPathResource("test_case.csv").getFile();
      MappingIterator<CsvUserForm> readValues =
          mapper.reader(CsvUserForm.class).with(bootstrapSchema).readValues(file);
      return readValues.readAll();
    } catch (Exception e) {
      logger.error("Error occurred while loading object list from file " + "test_case.csv", e);
      return Collections.emptyList();
    }
  }

  public static UserForm csvUserFormToUserForm(CsvUserForm csvUserForm) {
    UserForm userForm = new UserForm();
    userForm.setUserUid(csvUserForm.getSsoid());
    userForm.setEventTime(
        new Timestamp(Long.parseLong(csvUserForm.getTs())).toLocalDateTime().toLocalTime());
    userForm.setUrl(csvUserForm.getUrl());
    userForm.setEventGroup(csvUserForm.getGrp());
    userForm.setEventType(csvUserForm.getType());
    userForm.setEventSubtype(csvUserForm.getSubtype());
    userForm.setDate(LocalDateTime.parse(csvUserForm.getYmdh(),
        DateTimeFormatter.ofPattern("yyyy-MM-dd-HH")));
    userForm.setOrganizationId(csvUserForm.getOrgid());
    userForm.setCode(csvUserForm.getCode());
    userForm.setFormId(csvUserForm.getFormid());
    return userForm;
  }
}
