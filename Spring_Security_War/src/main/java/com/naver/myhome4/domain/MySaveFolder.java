package com.naver.myhome4.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="my") /*application.properties에서 설정한 내용 중
										my로 시작하는 키에 대한 값을 필드에 주입합니다.
										my.savefolder=c:/upload
										my.sendfile=c:/upload/image/icecream.png
*/
public class MySaveFolder {
	private String savefolder;
	private String sendfile;
	
	public String getSavefolder() {
		return savefolder;
	}
	public void setSavefolder(String savefolder) {
		this.savefolder = savefolder;
	}
	public String getSendfile() {
		return sendfile;
	}
	public void setSendfile(String sendfile) {
		this.sendfile = sendfile;
	}
	
}
