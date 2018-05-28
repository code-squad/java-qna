INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) VALUES ('pobi', '9229', 'parkjaesung', 'pobi@codesquad.ac.kr');
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) VALUES ('jk', '9229', 'kimjung', 'jk@codesquad.ac.kr');

INSERT INTO QUESTION (TITLE, CONTENTS, WRITER_ID, ANSWERS_COUNT) VALUES ('사람은 무엇으로 사는가?', '톨스토이가 씀', '1', '0');
INSERT INTO QUESTION (TITLE, CONTENTS, WRITER_ID, ANSWERS_COUNT) VALUES ('사람은 무엇으로 성장하는가?', '존 맥스웰이 씀', '2', '1');

INSERT INTO ANSWER (WRITER_ID, QUESTION_ID, COMMENT) VALUES ('1', '2', '무엇으로 성장하나요?');