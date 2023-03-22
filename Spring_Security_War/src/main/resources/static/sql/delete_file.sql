drop table delete_file purge;

create table delete_file(
	board_file varchar2(50),
	reg_date date default sysdate
);


* update관점에서 봤을 때
upload파일에 있는 그림 사진이 없어질 때
1. 파일이 변경됐을 때
2. 기존에 있었는데 제거하는 경우
3. 게시물이 지워졌을 때


===============================================================================

CREATE OR REPLACE TRIGGER save_delete_file
AFTER UPDATE OR DELETE
ON BOARD2
FOR EACH ROW
BEGIN
	IF(:OLD.BOARD_FILE IS NOT NULL) THEN
	 IF(:OLD.BOARD_FILE != :NEW.BOARD_FILE OR :NEW.BOARD_FILE IS NULL) THEN
		  INSERT INTO DELETE_FILE
			(BOARD_FILE)
		  VALUES(:OLD.BOARD_FILE);
	 END IF;
	END IF;
END;
/

===============================================================================