package com.example.demo.dao;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Board;

@Repository
@RequiredArgsConstructor
public class BoardDao {
	private final SqlSession session;

	public int boardInsert(Board board) {		
		return session.insert("insert", board);
	}
	
	public List<Board> boardlist() {
		return session.selectList("list");
	}
}
