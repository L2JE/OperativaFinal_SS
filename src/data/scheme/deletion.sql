drop table if exists "carrera";
drop table if exists "comp_carrera";
drop table if exists "asignatura";
drop table if exists "aula";
drop table if exists "ocupation";
drop table if exists "clase_fija_parcial"
drop table if exists "profesor";
drop table if exists "clase";
drop trigger if exists "valid_years_comp_carrera_updt_tg";
drop trigger if exists "valid_years_comp_carrera_insrt_tg";

--delete from "sqlite_master" where 1=1;
delete from  "sqlite_sequence" where 1=1;
