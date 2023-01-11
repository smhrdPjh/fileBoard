package com.example.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileVO {
  
  private int f_no; 
  private int b_no; 
  private String filename; 
  private String fileoriginname;  
  private String fileurl;   
  
}