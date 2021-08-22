----------------------------------------------------------------
-- convertir todos los nombres que deban ser unicos a MINUCULA--
----------------------------------------------------------------
CREATE TRIGGER IF NOT EXISTS "update_names_to_lower_prof_tg"
    AFTER UPDATE OF "name" ON "profesor"
BEGIN
    UPDATE "profesor"
    SET "name" = lower(new.name)
    WHERE lower("name") = lower(new.name);
end;

CREATE TRIGGER IF NOT EXISTS "insert_names_to_lower_prof_tg"
    AFTER INSERT ON "profesor"
BEGIN
    UPDATE "profesor"
    SET "name" = lower(new.name)
    WHERE lower("name") = lower(new.name);
end;

CREATE TRIGGER IF NOT EXISTS "update_names_to_lower_carr_tg"
    AFTER UPDATE OF "name" ON "carrera"
BEGIN
    UPDATE "carrera"
    SET "name" = lower(new.name)
    WHERE lower("name") = lower(new.name);
end;

CREATE TRIGGER IF NOT EXISTS "insert_names_to_lower_carr_tg"
    AFTER INSERT ON "carrera"
BEGIN
    UPDATE "carrera"
    SET "name" = lower(new.name)
    WHERE lower("name") = lower(new.name);
end;

CREATE TRIGGER IF NOT EXISTS "update_names_to_lower_aula_tg"
    AFTER UPDATE OF "room" ON "aula"
BEGIN
    UPDATE "aula"
    SET "room" = lower(new.room)
    WHERE lower("room") = lower(new."room");
end;

CREATE TRIGGER IF NOT EXISTS "insert_names_to_lower_aula_tg"
    AFTER INSERT ON "aula"
BEGIN
    UPDATE "aula"
    SET "room" = lower(new.room)
    WHERE lower("room") = lower(new."room");
end;

CREATE TRIGGER IF NOT EXISTS "update_names_to_lower_pabellon_tg"
    AFTER UPDATE OF "pab_name" ON "pabellon"
BEGIN
    UPDATE "pabellon"
    SET "pab_name" = lower(new.pab_name)
    WHERE lower("pab_name") = lower(new."pab_name");
end;

CREATE TRIGGER IF NOT EXISTS "insert_names_to_lower_pabellon_tg"
    AFTER INSERT ON "pabellon"
BEGIN
    UPDATE "pabellon"
    SET "pab_name" = lower(new.pab_name)
    WHERE lower("pab_name") = lower(new."pab_name");
end;

-----------------------------------------------------
-- Verificar comp_carrera.year <= carrera.duration---
-----------------------------------------------------
CREATE TRIGGER IF NOT EXISTS "valid_years_comp_carrera_updt_tg"
    BEFORE UPDATE OF "year" ON "comp_carrera"
BEGIN
    SELECT
        CASE
            WHEN "carrera".duration < NEW."year" THEN
            RAISE (ABORT,'Year is over the career duration')
        END
    FROM "carrera"
    WHERE "carrera"."id" = NEW."id_carrera";

end;

CREATE TRIGGER IF NOT EXISTS "valid_years_comp_carrera_insrt_tg"
    BEFORE INSERT ON "comp_carrera"
BEGIN
    SELECT
        CASE
            WHEN "carrera".duration < NEW."year" THEN
            RAISE (ABORT,'Year is over the career duration')
        END
    FROM "carrera"
    WHERE "carrera"."id" = NEW."id_carrera";
end;