create table paciente ( id serial primary key, nome varchar(200) not null, cpf varchar(14) not null unique );
create table medicamento ( id serial primary key, nome varchar(200) not null unique );
create table receita ( id serial primary key, paciente_id int references paciente(id) );
create table medicamento_receitado ( id serial primary key, receita_id int references receita(id), medicamento_id int references medicamento(id) );
