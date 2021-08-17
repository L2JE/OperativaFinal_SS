delete from carrera where 1=1;
delete from asignatura where 1=1;
delete from comp_carrera where 1=1;
delete from clase where 1=1;
delete from profesor where 1=1;

insert into carrera (id, name, duration, h_inic, h_fin)
values (256, 'Introduccion a la Joda', 5, 8, 12);

insert into carrera (id, name, duration, h_inic, h_fin)
values (2, 'Carrera 1', 5, 8, 13),
       (4, 'Carrera 2', 5, 8, 13),
       (6, 'Carrera 3', 5, 8, 13),
       (8, 'Carrera 4', 5, 8, 13),
       (10, 'Carrera 5', 5, 8, 13),
       (12, 'Carrera 6', 5, 8, 13),
       (14, 'Carrera 7', 5, 8, 13),
       (16, 'Carrera 8', 5, 8, 13);

insert into asignatura (id, name)
values (200, 'Asignatura Test Subj 1'),
       (300, 'Asignatura Test Subj 2'),
       (400, 'Asignatura Test Subj 3');

----------------------------------------------
----------------------------------------------
----------------------------------------------

insert into carrera (id, name, duration, h_inic, h_fin)
values (200, 'Carrera Test Subj 1', 2, 8, 13),
       (400, 'Carrera Test Subj 2', 4, 8, 13),
       (600, 'Carrera Test Subj 3', 6, 8, 13),
       (800, 'Carrera Test Subj 4', 8, 8, 13);

insert into comp_materia (id_asignatura, id_carrera, year)
values (200,400, 3),
       (300,600, 5),
       (200,800, 5),
       (200,200, 2),
       (200,600, 3);

insert into profesor (name)
values ('Juan'),
       ('Pedro'),
       ('Jose'),
       ('Luis');

insert into clase (id_asignatura, id_profesor)
values (300, 'jose'),
       (400, 'Luis');
