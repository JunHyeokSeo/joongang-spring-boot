package com.example.demo.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.dao.BoardDao;
import com.example.demo.model.Board;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardDao dao;

	public int boardInsert(Board board) {		
		return dao.boardInsert(board);
	}
	
	public List<Board> boardlist() {
		return dao.boardlist();
	}
}
