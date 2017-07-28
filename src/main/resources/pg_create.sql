DROP TABLE IF EXISTS "answers" CASCADE;
DROP TABLE IF EXISTS "likes" CASCADE;
DROP TABLE IF EXISTS "questions" CASCADE;
DROP TABLE IF EXISTS "users" CASCADE;
DROP FUNCTION IF EXISTS on_new_like() CASCADE;
DROP TRIGGER IF EXISTS "new_like" ON "likes" CASCADE;

CREATE TABLE "users" (
  "id" SERIAL NOT NULL,
  "email" VARCHAR(128) NOT NULL UNIQUE,
  "name" VARCHAR(128) NOT NULL UNIQUE,
  "password_hash" VARCHAR(32) NOT NULL, -- hotfix for hibernate
  "avatar_path" VARCHAR(512) NOT NULL DEFAULT 'default.jpg', -- TODO maybe blob?
  CONSTRAINT users_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);


CREATE TABLE "questions" (
  "id" SERIAL NOT NULL,
  "header" TEXT NOT NULL,
  "content" TEXT DEFAULT NULL,
  "user_id" INTEGER NOT NULL,
  "rating" INTEGER NOT NULL DEFAULT '0',
  "ts" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT questions_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);


CREATE TABLE "answers" (
  "id" serial NOT NULL,
  "content" TEXT NOT NULL,
  "question_id" INTEGER NOT NULL,
  "user_id" INTEGER NOT NULL,
  "is_correct" BOOLEAN DEFAULT NULL,
  "ts" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE ("question_id", "is_correct"),
  CONSTRAINT answers_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);


CREATE TABLE "likes" (
  "question_id" INTEGER NOT NULL,
  "user_id" INTEGER NOT NULL,
  CONSTRAINT likes_pk PRIMARY KEY ("question_id","user_id")
) WITH (
  OIDS=FALSE
);


ALTER TABLE "questions" ADD CONSTRAINT "questions_fk0" FOREIGN KEY ("user_id") REFERENCES "users"("id");
ALTER TABLE "answers" ADD CONSTRAINT "answers_fk0" FOREIGN KEY ("question_id") REFERENCES "questions"("id");
ALTER TABLE "answers" ADD CONSTRAINT "answers_fk1" FOREIGN KEY ("user_id") REFERENCES "users"("id");
ALTER TABLE "likes" ADD CONSTRAINT "likes_fk0" FOREIGN KEY ("question_id") REFERENCES "questions"("id");
ALTER TABLE "likes" ADD CONSTRAINT "likes_fk1" FOREIGN KEY ("user_id") REFERENCES "users"("id");


CREATE OR REPLACE FUNCTION on_new_like() RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
  UPDATE questions
    SET rating =
      (
        SELECT COUNT(*) FROM likes
        WHERE question_id = NEW.question_id
      )
  WHERE id = NEW.question_id;
  RETURN NEW;
END;
$$;


CREATE TRIGGER new_like
  AFTER INSERT OR UPDATE OR DELETE
  ON likes
  FOR EACH ROW
  EXECUTE PROCEDURE on_new_like();
