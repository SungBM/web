drop table delete_file purge;

create table delete_file(
	board_file varchar2(50),
	reg_date date default sysdate
);


* update�������� ���� ��
upload���Ͽ� �ִ� �׸� ������ ������ ��
1. ������ ������� ��
2. ������ �־��µ� �����ϴ� ���
3. �Խù��� �������� ��


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