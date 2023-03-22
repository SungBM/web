package com.naver.myhome4.service;

import java.util.List;

import com.naver.myhome4.domain.Comment;

public interface CommentService {
	
	//���� ���� ���ϱ�
	public int getListCount(int board_num);
	
	//��� ��� ��������
	public List<Comment> getCommentList(int board_num, int page);
	
	//��� ����ϱ�
	public int commentsInsert(Comment c);
	
	//��� ����
	public int commentsDelete(int num);
	
	//��� ����
	public int commentsUpdate(Comment co);
}
