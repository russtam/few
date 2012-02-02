CREATE SCHEMA common ;

CREATE TABLE common.news (
  feed_id VARCHAR(20) NOT NULL, 
  news_id INTEGER NOT NULL, 
  title VARCHAR(300) NOT NULL, 
  text VARCHAR NOT NULL, 
  author_user_id INTEGER NOT NULL, 
  creation_time TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL, 
  modification_time TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL, 
  CONSTRAINT news_pkey PRIMARY KEY(news_id)
) WITHOUT OIDS;


CREATE INDEX news_idx ON common.news
  USING btree (feed_id);


CREATE TABLE common.simple_texts (
  cat_id VARCHAR(20) NOT NULL, 
  text_id VARCHAR(40) NOT NULL, 
  text VARCHAR NOT NULL, 
  CONSTRAINT simple_texts_idx PRIMARY KEY(cat_id, text_id)
) WITHOUT OIDS;

