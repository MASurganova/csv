package com.test.csv.Util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.test.csv.to.CsvUserForm;
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

/**
 * Data uploader from csv file
 */
public class CsvDataLoader {
  private static final Logger logger = LoggerFactory.getLogger(CsvDataLoader.class);

  /**
   * Returns list of transfer objects by file name
   */
  public static List<CsvUserForm> loadCsvUserForms(String fileName) {
    try {
      CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';');
      CsvMapper mapper = new CsvMapper();
      File file = new ClassPathResource(fileName).getFile();
      MappingIterator<CsvUserForm> readValues =
          mapper.reader(CsvUserForm.class).with(bootstrapSchema).readValues(file);
      return readValues.readAll();
    } catch (Exception e) {
      logger.error("Error occurred while loading object list from file " + fileName, e);
      return Collections.emptyList();
    }
  }

  /**
   * Adapter between UserForm and CsvUserForm
   */
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
