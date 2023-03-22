package com.naver.myhome4.service;

import java.util.List;

import com.naver.myhome4.domain.Board;

public interface BoardService {
	
	//���� ���� ���ϱ�
	public int getListCount();
	
	//�� ��� ����
	public List<Board> getBoardList(int page, int limit);
	
	//�� ���� ����
	public Board getDetail(int num);
	
	//�� �亯
	public int boardReply(Board board);
	
	//�� ����
	public int boardModify(Board modifyboard);
	
	//�� ����
	public int boardDelete(int num);
	
	//��ȸ�� ������Ʈ
	public int setReadCountUpdate(int num);
	
	//�۾������� Ȯ��
	public boolean isBoardWriter(int num, String pass);
	
	//�� ����ϱ�
	public void insertBoard(Board board);
	
	//BOARD_RE_SEQ�� ����
	public int boardReplyUpdate(Board board);
	
	
	public List<String> getDeleteFileList();
	
	public void deleteFileList(String filename);

}
