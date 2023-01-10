package com.example.demo.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.bean.FileBoardVO;



@Mapper
public interface FileBoardMapper {
	
	
	
	
	  List<FileBoardVO> getFileBoardList(); 
	  
	  FileBoardVO fileBoardDetail(int b_no); 
	  
	  int fileBoardInsert(FileBoardVO fileBoard); 
	  
	  int fileBoardUpdate(FileBoardVO fileBoard); 
	  
	  int fileBoardDelete(int bno); 
	

//	@Select("select * from file_board order by b-no")
//	public List<FileBoardVO> getFileBoardList();
//
//	@Select("select * from file_board where b_no=#{b_no}")
//	public FileBoardVO fileBoardDetail(int b_no);
//
//	@Insert("insert into file_board(title, content, writer) values (#{title}, #{content}, #{writer})")
//	public int fileBoardInsert(FileBoardVO fileBoard);
//
//	@Update({ "<script> ",
//
//			"update file_board set ",
//
//			"<if test=\"title != null\">title=#{title}</if>",
//
//			"	<if test=\"title != null and content != null\">,</if>, ",
//
//			"	 <if test=\"content != null\">content=#{content}</if>, ",
//
//			"	MEM_EMAIL = #{vo.mem_email}, ",
//
//			"	MEM_IMG = #{vo.mem_img}, ",
//
//			"	MEM_DETAIL = #{vo.mem_detail} ",
//
//			"  WHERE b_no=#{b_no} ",		
//
//			"</script>" })
//	public int fileBoardUpdate(FileBoardVO fileBoard);
//
//	@Delete("delete from file_board where b_no=#{b_no}")
//	public int fileBoardDelete(int bno);

}
