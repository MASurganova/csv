package com.test.csv.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Entity for database
 */
@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "userForm")
@Entity
public class UserForm {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private int id;

  private String userUid;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime eventTime;

  private String eventGroup;
  private String eventType;
  private String eventSubtype;
  private String url;
  private String organizationId;
  private String formId;
  private String code;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime date;

  public UserForm(String userUid, LocalDateTime eventTime, String formId) {
    this.userUid = userUid;
    this.eventTime = eventTime;
    this.eventSubtype = eventSubtype;
    this.formId = formId;
  }
}
