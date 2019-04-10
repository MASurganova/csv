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
  private LocalTime eventTime;
  private String eventGroup;
  private String eventType;
  private String eventSubtype;
  private String url;
  private String organizationId;
  private String formId;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime date;

}
