package com.test.csv.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CsvUserForm {
  String ssoid;
  String ts;
  String grp;
  String type;
  String subtype;
  String url;
  String orgid;
  String formid;
  String code;
  String ltpa;
  String sudirresponse;
  String ymdh;
}
