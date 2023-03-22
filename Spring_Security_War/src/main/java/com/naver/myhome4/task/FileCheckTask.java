package com.naver.myhome4.task;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.naver.myhome4.domain.MySaveFolder;
import com.naver.myhome4.service.BoardService;

@Service
public class FileCheckTask {

		private static final Logger logger = LoggerFactory.getLogger(FileCheckTask.class);
		
		@Autowired
		private MySaveFolder mysavefolder;
		
		@Autowired
		private BoardService boardService;
		
		//cron 사용법
				//seconds(초:0~59) minutes(분:0~59) hours(시:0~23) day(일:1~31)
				//months(달:1~12) day of week(요일:0~6) year(optional)
				//		   초  분 시  일  달 요일
		@Scheduled(cron = "0  40  *  *  *  *")  //*는 모든 값을 의미함. 요일이 *이면 요일에 구애받지 않겠다. 여기서는 년도 생략. 
		public void checkFiles() throws Exception{
			String saveFolder = mysavefolder.getSavefolder();
			
			logger.info("checkFiles");
			
			List<String> deleteFileList = boardService.getDeleteFileList();   //파일에 있는 목록을 가져와. 셀렉트에서 목록 가져와. 
			
			//for(String filename : deleteFileList) {
			for(int i=0; i<deleteFileList.size(); i++) {  
				String filename = deleteFileList.get(i);
				File file = new File(mysavefolder + filename);
				if(file.exists()) {   //������ �����ϴ��� Ȯ���ϰ� ������. 
					if(file.delete()) {
						logger.info(file.getPath() + " 삭제되었습니다.");
						boardService.deleteFileList(filename);
					}
				}else {
					logger.info(file.getPath() + " 파일이 존재하지 않습니다.");
				}
			}
		}

}
