INSERT INTO "asignatura"("name")
VALUES ();

INSERT INTO "profesor"("name")
VALUES ();

INSERT INTO "clase"("id_asignatura", "id_clase", "id_profesor")
VALUES ();

INSERT INTO "aula"("pab", "room")
VALUES ();

INSERT INTO "ocupacion"("id_aula", "id_asignatura", "id_clase", "day", "hour")
VALUES ();

-------------------------------------------------------
---PRUEBAS DE COMP_CARRERA.YEAR NO SUPERA DURACION-----
-- DE LA CARRERA---------------------------------------
-------------------------------------------------------
select * from asignatura left join carrera c on 1=1;
insert into asignatura("name")
values ('Matematica 1'),
       ('Literatura'),
       ('Informatica');

insert into carrera(name, duration, h_inic, h_fin)
values ('Relaciones', 5, 8, 15),
       ('Sociales', 3, 12, 16);

insert into comp_carrera (id_asignatura, id_carrera, year)
values (3, 2, 5);

update comp_carrera
set year = 6
where id_asignatura = 2;

delete
from comp_carrera
where id_asignatura = 3;

select comp_carrera.year as "year",a.name, c.name, c.duration
from comp_carrera
    join asignatura a on comp_carrera.id_asignatura = a.id
    join carrera c on comp_carrera.id_carrera = c.id;