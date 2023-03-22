/*
 * MVC ���Ͽ��� ����Ͻ� ���� ������ Controller, Service, Dao�� ������ �л���� ����.
 * MVC ���Ͽ��� Service �� ����
 * ����Ͻ� ������ ó��.
 * */
package com.naver.myhome4.service;

import java.util.List;

import com.naver.myhome4.domain.Member;

public interface MemberService {	//��� �����ؼ� �ʿ��� �޼���� ������� 

	public int isId(String id, String pass);
	
	public int insert(Member member);
	
	public int isId(String id);
	
	public Member member_info(String id);
	
	public void delete(String id);
	
	public int update(Member m);
	
	public List<Member> getSearchList(int index, String search_word,	
									  int page, int limit);
	
	public int getSearchListCount(int index, String search_word);
}
