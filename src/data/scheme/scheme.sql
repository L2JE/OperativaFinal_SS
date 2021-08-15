PRAGMA foreign_keys = ON;

CREATE TABLE "carrera"(
    "id" INTEGER NOT NULL,
    "name" TEXT NOT NULL UNIQUE,
    "duration" INTEGER NOT NULL,
    "h_inic" INTEGER NOT NULL,
    "h_fin" INTEGER NOT NULL,
    CHECK (
        duration > 0 AND
        duration < 10 AND
        h_inic > 0 AND
        h_inic < 21 AND
        h_fin < 22 AND
        h_fin > (carrera.h_inic + 1)
    ),

    PRIMARY KEY ("id" AUTOINCREMENT)
);

CREATE TABLE "asignatura" (
	"id"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);

CREATE TABLE "comp_carrera"(
    "id_asignatura" INTEGER NOT NULL,
    "id_carrera" INTEGER NOT NULL,
    "year" INTEGER NOT NULL,

    PRIMARY KEY ("id_asignatura", "id_carrera"),
    FOREIGN KEY ("id_asignatura")
        REFERENCES "asignatura"("id")
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    FOREIGN KEY ("id_carrera")
        REFERENCES "asignatura"("id")
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

CREATE TABLE "profesor" (
	"name"	TEXT NOT NULL,
	PRIMARY KEY("name")
);

CREATE TABLE "clase" (
	"id_asignatura"	INTEGER NOT NULL,
	"id_clase"	INTEGER NOT NULL,
	"id_profesor"	TEXT NOT NULL,
	PRIMARY KEY("id_asignatura","id_clase"),
	FOREIGN KEY("id_asignatura") 
        REFERENCES "asignatura"("id") 
            ON DELETE RESTRICT 
            ON UPDATE RESTRICT,
	FOREIGN KEY("id_profesor") 
        REFERENCES "profesor"("name")
            ON DELETE RESTRICT 
            ON UPDATE CASCADE
);

CREATE TABLE "aula" (
	"id"	INTEGER NOT NULL,
	"pab"	TEXT NOT NULL,
	"room"	TEXT NOT NULL,
	UNIQUE ("pab","room"),
	PRIMARY KEY("id" AUTOINCREMENT)
);

CREATE TABLE "ocupation" (
	"id_aula"	INTEGER NOT NULL,
	"id_asignatura"	INTEGER NOT NULL,
	"id_clase"	INTEGER NOT NULL,
	"day"	INTEGER,
	"hour"	INTEGER,

	CHECK (
	    day > 0 AND
	    day < 7 AND
	    hour > 0 AND
	    hour < 22
    ),

	PRIMARY KEY("id_aula","day","hour"),
	FOREIGN KEY("id_aula") 
        REFERENCES "aula"("id") 
            ON DELETE RESTRICT 
            ON UPDATE CASCADE,
	FOREIGN KEY("id_asignatura","id_clase") 
        REFERENCES "clase"("id_asignatura","id_clase") 
            ON DELETE CASCADE 
            ON UPDATE RESTRICT
);

CREATE UNIQUE INDEX "idx_pab_room"
ON "aula"("pab","room");

--------------------------------------------------------------
--------INDICES UTILES DE CREACION AUTOMATICA:----------------
-- INDICE EN OCUPACION POR ID_AULA----------------------------
-- INDICE EN COMP_CARRERA POR ASIGNATURA----------------------
-- INDICE EN CLASE POR ASIGNATURA-----------------------------
--------------------------------------------------------------