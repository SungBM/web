package com.naver.myhome4.task;

import java.io.File;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.naver.myhome4.domain.MailVO;
import com.naver.myhome4.domain.MySaveFolder;

@Component //servlet-context에서 context 설정해줘야 nullpoint안남. <context:component-scan base-package="com.naver.myhome4" />
public class SendMail {
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@Autowired
	private MySaveFolder mysavefolder;
	
	private static final Logger logger = LoggerFactory.getLogger(SendMail.class);
	
	public void sendMail(MailVO vo) {
		
		String sendfile = mysavefolder.getSendfile();
		
		MimeMessagePreparator mp = new MimeMessagePreparator() {  //�������̽��� �̿��ؼ� ���� �ۼ��� ������ ����ִ� ��� = �͸�Ŭ����

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				/*
				 * MimeMessage : 이 클래스는 MIME 스타일 이메일 메시지를 나타냅니다.
				 * MIME(영어: Multipurpose Internet Mail Extensions)는 전자 우편을 위한 인터넷 표준 포맷입니다.
				 */
				/* MimeMessageHelper를 이용하면 첨부파일이나 특수 문자 인코딩으로 작업할 때 전달된  MimeMessage를 채우는데
				 * 편리합니다.
				 */
				//두번째 인자 true는 멀티 파트 메시지를 사용하겠다는 의미입니다.
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
				helper.setFrom(vo.getFrom());
				helper.setTo(vo.getTo());
				helper.setSubject(vo.getSubject());
				
				//1. 문자로만 전송하는 경우
				//두번째 인자는 html을 사용하겠다는 뜻입니다.
				//helper.setText(vo.getContent(), true);
				
				//2. 이미지를 내장해서 보내는 경우
				//cid(content id)
				String content = "<img src='cid:Home'>" + vo.getContent();
				helper.setText(content, true);
				
				//sendfile = savefolder에 있는 아이스크림 그림. 이미지를 보내는 것은 하나의 데이터로 보내는거라서 FileSystemResource로 보냄.
				FileSystemResource file = new FileSystemResource(new File(sendfile));  //
				//addInline메서드의 첫 번째 메서드에는 cid(content id)=Home를 지정합니다.
				helper.addInline("Home", file);   //
				
				//3. 파일을 첨부해서 보내는 경우
				//첫번째 인자 : 첨부될 파일의 이름입니다.
				//두번째 인자 : 첨부파일
				helper.addAttachment("딸기.jpg", file);
				
			}
			
		}; //new MimeMessagePreparator() 
		
		mailSender.send(mp); //메일 전송합니다.
		logger.info("메일 전송했습니다.");
	}//sendMail(MailVO vo)
	
}//class SendMail
