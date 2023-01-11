package com.example.demo.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bean.FileBoardVO;
import com.example.demo.bean.FileVO;
import com.example.demo.service.FileBoardService;


@Controller
@RequestMapping("/fileBoard")
public class FileBoardController {
  
  @Autowired
  FileBoardService fileBoardService;
  
  @RequestMapping("/list")
  private String fileBoardList(Model model, HttpServletRequest request) {

    List<FileBoardVO> testList = new ArrayList<FileBoardVO>();
    testList = fileBoardService.getFileBoardList();
    model.addAttribute("testlist", testList);
    return "/fileBoard/list";
  }

  @RequestMapping("/detail/{b_no}")
  private String fileBoardDetail(@PathVariable("b_no") int b_no, Model model) {
    model.addAttribute("detail", fileBoardService.fileBoardDetail(b_no));
    
    if(fileBoardService.fileDetail(b_no) == null) {
      return "fileBoard/detail";
    } else {
      model.addAttribute("file", fileBoardService.fileDetail(b_no));
      return "fileBoard/detail";
    }
 
  }
  
  @RequestMapping("/insert")
  private String fileBoardInsertForm(@ModelAttribute FileBoardVO board) {
    return "fileBoard/insert";
  }
  
  
  @RequestMapping("/update/{b_no}")
  private String fileBoardUpdateForm(@PathVariable("b_no") int b_no, Model model) {
    model.addAttribute("detail", fileBoardService.fileBoardDetail(b_no));
    return "fileBoard/update";
  }
  
  @RequestMapping("/updateProc")
  private String fileBoardUpdateProc(@ModelAttribute FileBoardVO board) {
    
    fileBoardService.fileBoardUpdate(board);
    int bno = board.getB_no();
    String b_no = Integer.toString(bno);
    return "redirect:/fileBoard/detail/"+b_no;
  }
  
  @RequestMapping("/delete/{b_no}")
  private String fileBoardDelete(@PathVariable("b_no") int b_no) {
    fileBoardService.fileBoardDelete(b_no);
    return "redirect:/fileBoard/list";
  }
  @RequestMapping("/insertProc")
  private String fileBoardInsertProc(@ModelAttribute FileBoardVO board, @RequestPart MultipartFile 
files, HttpServletRequest request) throws IllegalStateException, IOException, Exception {  
    
    if(files.isEmpty()) {
      fileBoardService.fileBoardInsert(board);
      
    } else {
      String fileName = files.getOriginalFilename(); // 파일명 그대로
      //확장자추출?
      String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
      File destinationFile; // 파일생성을 위한 file타입의 destinationFile이라는 변수 선언.
      String destinationFileName; //db에저장될 파일이름 선언
      
     
   
      String fileUrl = "C:/Users/Kim/Desktop/here/";

      do { //우선 실행 후
        //고유명 생성
        destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;//파일이름 만들기
        destinationFile = new File(fileUrl + destinationFileName); //url로가서 파일생성(destinationFileName이름으로 생성됨)
      } while (destinationFile.exists()); 

      destinationFile.getParentFile().mkdirs(); //디렉토리생성!!!!
      files.transferTo(destinationFile);// 업로드 처리 => 설명 : 받아온 객체를 업로드 처리하지 않으면 임시파일에 저장된 파일이 자동적으로 삭제되기 때문에 MultipartFile객체의 transferTo() 메서드를 이용해서 업로드처리를 해야 함.



      fileBoardService.fileBoardInsert(board);

      FileVO file = new FileVO();
      file.setB_no(board.getB_no());
      file.setFilename(destinationFileName);
      file.setFileoriginname(fileName);
      file.setFileurl(fileUrl);
      
      fileBoardService.fileInsert(file);
    }
    
    return "forward:/fileBoard/list"; //객체 재사용
  }
  
  @RequestMapping("/fileDown/{b_no}")
  private void fileDown(@PathVariable("b_no") int b_no, HttpServletRequest request, 
  HttpServletResponse response) throws UnsupportedEncodingException, Exception {
 
    request.setCharacterEncoding("UTF-8");
    FileVO fileVO = fileBoardService.fileDetail(b_no);
    
    //파일 업로드 경로
    try {
      String fileUrl = fileVO.getFileurl();
      System.out.println(fileUrl);
      fileUrl += "/";
      String savePath = fileUrl;
      String fileName = fileVO.getFilename();

      //실제 내보낼 파일명
       String originFileName = fileVO.getFileoriginname();
       InputStream in = null;
       OutputStream os = null;
       File file= null;
       Boolean skip = false;
       String client = "";
       
       //파일을 읽어 스트림에 담기
      try {
        file = new File(savePath, fileName);
        in = new FileInputStream(file);
      } catch (FileNotFoundException fe) {
        skip = true;
      } 

      client = request.getHeader("User-Agent");
       
      //파일 다운로드 헤더 지정
      response.reset();
      response.setContentType("application/octet-stream");
      response.setHeader("Content-Description", "HTML Generated Data");

      if(!skip) {
        //IE
        if(client.indexOf("MSIE") != -1) {
          response.setHeader("Content-Disposition", "attachment; filename=\"" 
            + java.net.URLEncoder.encode(originFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
        //IE 11 이상
        } else if (client.indexOf("Trident") != -1) {
          response.setHeader("Content-Disposition", "attachment; filename=\""
            + java.net.URLEncoder.encode(originFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
        //한글 파일명 처리
        } else {
          response.setHeader("Content-Disposition", "attachment; filename=\"" + 
new String(originFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
          response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
         }
         
        response.setHeader("Content-Length", ""+file.length());
        os = response.getOutputStream();
        byte b[] = new byte[(int) file.length()];
        int leng = 0;

        while ((leng = in.read(b)) > 0) {
          os.write(b, 0, leng);
        }
      } else {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script> alert('파일을 찾을 수 없습니다.'); history.back(); </script>");
        out.flush();
      }
       
       in.close();
       os.close();
    
    } catch (Exception e) {
      System.out.println("ERROR : " + e.getStackTrace());
    }
  }

  
}
