CREATE TABLE common.access_log (
  uri VARCHAR(256) NOT NULL, 
  method VARCHAR(10) NOT NULL, 
  response_code INTEGER NOT NULL, 
  processing_time INTEGER NOT NULL, 
  "timestamp" BIGINT NOT NULL, 
  remote_address VARCHAR(40) NOT NULL, 
  referer VARCHAR(256), 
  session_id VARCHAR(40), 
  user_id VARCHAR(20)
) WITHOUT OIDS;

CREATE TABLE common.activity_log (
  user_id INTEGER, 
  level INTEGER NOT NULL, 
  type VARCHAR(40) NOT NULL, 
  text VARCHAR(100) NOT NULL, 
  "timestamp" BIGINT NOT NULL
) WITHOUT OIDS;
