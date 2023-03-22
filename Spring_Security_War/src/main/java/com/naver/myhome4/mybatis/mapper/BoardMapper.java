package com.naver.myhome4.mybatis.mapper;
//	<mybatis-spring:scan base-package="com.naver.myhome4.mapper"/>

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.naver.myhome4.domain.Board;

/*
	Mapper 인터페이스란 매퍼 파일에 기재된 SQL을 호출하기 위한 인터페이스입니다.
	MyBatis-Spring은 Mapper 인터페이스를 이용해서 실제 sql 처리가 되는 클래스를 자동으로 생성합니다.
* */

@Mapper
public interface BoardMapper {
		
		//���� ���� ���ϱ�
		public int getListCount();
		
		//�� ��� ����
		public List<Board> getBoardList(HashMap<String, Integer> map);
		
		//�� ���� ����
		public Board getDetail(int num);
		
		//�� �亯
		public int boardReply(Board board);
		
		//�� ����
		public int boardModify(Board modifyboard);
		
		//�� ����
		public int boardDelete(Board board);
		
		//��ȸ�� ������Ʈ
		public int setReadCountUpdate(int num);
		
		//�۾������� Ȯ��
		public Board isBoardWriter(HashMap<String, Object> map);
		
		//�� ����ϱ�
		public void insertBoard(Board board);
		
		//BOARD_RE_SEQ�� ����
		public int boardReplyUpdate(Board board);
		
		public List<String> getDeleteFileList();
		
		public void deleteFileList(String filename);

	}


