package com.naver.myhome4.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.naver.myhome4.domain.Comment;
import com.naver.myhome4.service.CommentService;

@RestController   //@Controller + @@ResponseBody�� ������ ��. �Ʒ�ó�� @ResponseBody �ߺ��ؼ� ���� �ʾƵ���.
@RequestMapping(value="/comment")
public class CommentController {
	
	private CommentService commentService;
	
	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService=commentService;
	}
	
	// *@ResponseBody�� jackson�� �̿��Ͽ� JSON ����ϱ�
	//@ResponseBody
	@PostMapping(value="/list")
	public Map<String, Object> CommentList(int board_num, int page){
		List<Comment> list = commentService.getCommentList(board_num, page);
		int listcount = commentService.getListCount(board_num);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("listcount", listcount);
		return map;
	}
	
	
	//@ResponseBody	//body�� �Ǿ ������
	@PostMapping(value="/add")
	public int add(Comment comment){	//view.js�� data�ȿ� �ִ� content, id, board_num 3���� ������ ��ƿ´�
		return commentService.commentsInsert(comment);
	}
	
	
	
	//@ResponseBody	//body�� �Ǿ ������
	@PostMapping(value="/update")
	public int update(Comment co){	
		return commentService.commentsUpdate(co);
	}
	
	
	//@ResponseBody	//body�� �Ǿ ������
	@PostMapping(value="/delete")
	public int CommentDelete(int num){	
		return commentService.commentsDelete(num);
	}

	
}
