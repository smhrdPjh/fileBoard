package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bean.FileBoardVO;
import com.example.demo.mapper.FileBoardMapper;

@Service
public class FileBoardService {

  @Autowired
  FileBoardMapper fileboardmapper;
  

  public List<FileBoardVO> getFileBoardList() {
    return fileboardmapper.getFileBoardList();
  }


  public FileBoardVO fileBoardDetail(int b_no) {
    return fileboardmapper.fileBoardDetail(b_no);
  }


  public int fileBoardInsert(FileBoardVO fileBoard) {
    return fileboardmapper.fileBoardInsert(fileBoard);
  }


  public int fileBoardUpdate(FileBoardVO fileBoard) {
    return fileboardmapper.fileBoardUpdate(fileBoard);
  }


  public int fileBoardDelete(int bno) {
    return fileboardmapper.fileBoardDelete(bno);
  }

}
