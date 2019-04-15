package com.test.csv.to;

import java.util.Comparator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Transfer object to get data from database for request top five forms
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TopForm {
  private String formId;
  private long count;

  public TopForm(String formId, long count) {
    this.formId = formId;
    this.count = count;
  }

}
