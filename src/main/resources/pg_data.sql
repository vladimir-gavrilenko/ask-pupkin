INSERT INTO users(id, email, name, password_hash) VALUES (1, 'foo@foo.com', 'foo', '12345678901234567890123456789012');
INSERT INTO users(id, email, name, password_hash) VALUES (2, 'bar@bar.com', 'bar', '12345678901234567890123456789012');
INSERT INTO users(id, email, name, password_hash) VALUES (3, 'baz@baz.com', 'baz', '12345678901234567890123456789012');

INSERT INTO questions(id, header, content, user_id) VALUES (1, 'Foo`s question 1', 'Question 1...', 1);
INSERT INTO questions(id, header, content, user_id) VALUES (2, 'Bar`s question', 'Another one question...', 2);
INSERT INTO questions(id, header, content, user_id) VALUES (3, 'Baz`s question 1', 'Question from Baz...', 3);

INSERT INTO answers(id, content, question_id, user_id) VALUES (1, 'Answer from Bar', 1, 2);
INSERT INTO answers(id, content, question_id, user_id) VALUES (2, 'Answer from Baz', 2, 3);
INSERT INTO answers(id, content, question_id, user_id) VALUES (3, 'Answer from Foo', 3, 1);
INSERT INTO answers(id, content, question_id, user_id) VALUES (4, 'Another one answer from Bar', 3, 2);
INSERT INTO answers(id, content, question_id, user_id) VALUES (5, 'Another one answer from Foo', 2, 1);
INSERT INTO answers(id, content, question_id, user_id) VALUES (6, 'Another one answer from Baz', 2, 3);

INSERT INTO likes(question_id, user_id) VALUES(1, 2);
INSERT INTO likes(question_id, user_id) VALUES(1, 3);
INSERT INTO likes(question_id, user_id) VALUES(2, 1);
INSERT INTO likes(question_id, user_id) VALUES(3, 2);

UPDATE answers SET is_correct = TRUE WHERE id = 3;
UPDATE answers SET is_correct = TRUE WHERE id = 5;
