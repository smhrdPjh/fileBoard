package com.example.demo.bean;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileBoardVO {
  
  private int b_no;
  private String title;
  private String content;
  private String writer;
  private Date reg_date;
}