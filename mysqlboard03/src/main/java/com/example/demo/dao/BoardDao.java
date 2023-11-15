package com.example.demo.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.example.demo.model.BoardBean;

@Mapper
public interface BoardDao {

	public void insertBoard(BoardBean b) throws Exception;

	public List<BoardBean> getBoardList(int start) throws Exception;

	public int getListCount() throws Exception;

	public BoardBean getBoardCont(int board_num) throws Exception;

	public void boardHit(int board_num) throws Exception;

	public void boardEdit(BoardBean b) throws Exception;

	public void boardDelete(int board_num) throws Exception;

	public void refEdit(BoardBean b) throws Exception;

	public void boardReplyOk(BoardBean b) throws Exception;
}
