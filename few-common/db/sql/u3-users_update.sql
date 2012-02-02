CREATE TABLE users.user_profile (
  user_id INTEGER NOT NULL, 
  field_id VARCHAR(20) NOT NULL, 
  value VARCHAR, 
  CONSTRAINT user_profile_fk FOREIGN KEY (user_id)
    REFERENCES users.users(id)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
    NOT DEFERRABLE
) WITHOUT OIDS;

CREATE UNIQUE INDEX user_profile_idx ON users.user_profile
  USING btree (user_id, field_id);
