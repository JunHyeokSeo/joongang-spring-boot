package com.example.demo.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("board")
public class BoardBean {

	private int board_num;
	private String board_name;
	private String board_pass;
	private String board_subject; 	//글제목
	private String board_content; 	//글내용
	private int board_re_ref;     	//글그룹번호
	private int board_re_lev;     	//댓글 깊이
	private int board_re_seq;     	//댓글 출력 순서
	private int board_readcount;  	//조회수
	private Date board_date;    	//등록날짜
	
}
