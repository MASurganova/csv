package com.test.csv;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class Main {

  public static void main(String[] args) {
    long ts = 1499763598;
    Timestamp timestamp = new Timestamp(ts * 1000);
    System.out.println(timestamp);
    System.out.println( new Date(ts*1000));
    System.out.println(
        LocalDateTime.ofInstant(Instant.ofEpochSecond(ts), ZoneId.of("UTC")));
    System.out.println();
    System.out.println(LocalDateTime.parse("2017-07-11 17:59:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
  }
}
