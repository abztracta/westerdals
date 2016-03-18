INSERT INTO User (id, email, password, usertype) VALUES (1, 'mari@test.com', '123ABc', 'STUDENT');
INSERT INTO User (id, email, password, usertype) VALUES (2, 'ole@test.com', 'abC123', 'TEACHER');
INSERT INTO User (id, email, password, usertype) VALUES (3, 'kari@test.com', '1234Abc', 'STUDENT');
INSERT INTO User (id, email, password, usertype) VALUES (4, 'marianne@test.com', '1234Abc', 'STUDENT');

INSERT INTO Location (id, building, room) VALUES (1, 'Galleriet', '82');
INSERT INTO Location (id, building, room) VALUES (2, 'Galleriet', '81');

INSERT INTO Subject (id, name, FK_LOCATION) VALUES (1, 'PG5100', 1);
INSERT INTO Subject (id, name, FK_LOCATION) VALUES (2, 'PG6100', 2);

INSERT INTO USR_SUB (SUBJECTS_ID, USERS_ID) VALUES (1, 1);
INSERT INTO USR_SUB (SUBJECTS_ID, USERS_ID) VALUES (2, 1);
INSERT INTO USR_SUB (SUBJECTS_ID, USERS_ID) VALUES (1, 2);
INSERT INTO USR_SUB (SUBJECTS_ID, USERS_ID) VALUES (2, 3);

INSERT INTO Event (id, title, description, type, FK_SUBJECT) VALUES (1, 'Lecture #1', NULL, 'LECTURE', 1);
INSERT INTO Event (id, title, description, type, FK_SUBJECT) VALUES (2, 'Practice #1', 'Gjennomgang av JSF', 'PRACTICE', 1);

INSERT INTO event_details (id, startDate, endDate) VALUES (1, '2016-01-04 08:00:00', '2016-01-04 10:00:00');
INSERT INTO event_details (id, startDate, endDate) VALUES (2, '2016-01-04 10:00:00', '2016-01-04 12:00:00');