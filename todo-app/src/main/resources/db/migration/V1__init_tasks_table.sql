drop table if exists tasks;
create table tasks(
                      id int PRIMARY KEY auto_increment,
                      description varchar(100) not null,
                      done bit
)