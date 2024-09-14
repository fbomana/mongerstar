-- do nothing
create table singers (
    name varchar(30) not null primary key
  );

  create table songs (
      title varchar2(512) not null,
      author varchar2(512) not null,
      language varchar2(20) not null
  );
  create index title_index on songs(title);
  create index author_index on songs(author);
  create index language_index on songs(language);