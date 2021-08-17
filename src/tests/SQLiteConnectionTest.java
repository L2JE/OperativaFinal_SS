package tests;

import data_access.CareerDAO;
import data_access.CareerSQLiteDAO;
import data_access.SubjectDAO;
import data_access.SubjectSQLiteDAO;
import data_transfer.*;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SQLiteConnectionTest {

    private static final String urlToDB = "jdbc:sqlite:.data.dt";

    public static void main(String[] args) {
        should_create_a_new_career_and_return_with_id();
        should_delete_career_by_given_id();
        should_return_careers_list_consistant_with_external_script();

        //////SUBJECT
        should_create_a_new_subject_and_return_with_id();
        should_return_subject_list_consistant_with_external_script();
        should_return_subject_given_name();

        ///////LECTURES
        should_create_a_new_lecture_and_return_with_id();
        should_return_lecture_list_consistant_with_external_script();
        should_delete_lecture_by_given_id();

        ///////CAREERS
        should_return_correct_resultCode_when_add_careerInstances();
        should_return_careerInstance_list_consistant_with_external_script();
        /*
        should_delete_careerInstance_by_given_id();
         */
    }

    private static void should_delete_careerInstance_by_given_id() {
        restartDB();
        System.out.println("TEST PASSED: SQLiteCareerSubjectDAO::should_delete_careerInstance_by_given_id");
    }

    private static void should_return_careerInstance_list_consistant_with_external_script() {
        restartDB();
        final int desiredSubjectId = 654;

        execQueryDB("insert into carrera (id, name, duration, h_inic, h_fin)\n" +
                "values (2, 'Lola', 4, 8, 10),\n" +
                "       (5, 'Lola1', 5, 8, 10),\n" +
                "       (1, 'Lola0', 4, 8, 10),\n" +
                "       (8, 'Lola2', 7, 8, 10),\n" +
                "       (9, 'Lola9', 4, 8, 10),\n" +
                "       (7, 'Lola3', 8, 8, 10);");

        execQueryDB("insert into asignatura (id,name)\n" +
                "values (666, 'Asignatura1'),\n" +
                "       (655, 'Asignatura2'),\n" +
                "       (658, 'Asignatura3'),\n" +
                "       ("+desiredSubjectId+", 'AsignaturaDeseada');");

        execQueryDB("insert into comp_carrera (id_asignatura, id_carrera, year)\n" +
                "values ("+desiredSubjectId+", 2, 4),\n" +
                "       (655, 2, 3),\n" +
                "       (655, 5, 3),\n" +
                "       ("+desiredSubjectId+", 5, 3),\n" +
                "       (658, 8, 3),\n" +
                "       ("+desiredSubjectId+", 8, 5),\n" +
                "       (666, 9, 3),\n" +
                "       ("+desiredSubjectId+", 7, 7);");

        List<CareerInstance> expectedList = new LinkedList<>();
        expectedList.add(new CareerInstance(2,"Lola", 4));
        expectedList.add(new CareerInstance(5,"Lola1", 3));
        expectedList.add(new CareerInstance(8,"Lola2", 5));
        expectedList.add(new CareerInstance(7,"Lola3", 7));

        SubjectDAO dao = new SubjectSQLiteDAO(urlToDB);
        List<CareerInstance> returned = dao.getCareers(desiredSubjectId);

        assert returned != null &&
                returned.containsAll(expectedList)
                :"\n[NO SE RECUPERARON CORRECTAMENTE - LOS DATOS NO COINCIDEN] \n" +
                "Valor de retorno: " + returned;

        System.out.println("TEST PASSED: SQLiteCareerSubjectDAO::should_return_careerInstance_list_consistant_with_external_script");
    }

    private static void should_return_correct_resultCode_when_add_careerInstances() {
        restartDB();
        final int idSubject = 958;
        final int idCareer = 321;
        final int careerDuration = 5;
        final String careerName = "Carrera de Ejemplo";

        execQueryDB("insert into carrera (id, name, duration, h_inic, h_fin) " +
                                  "values ("+idCareer+",'"+careerName+"',"+careerDuration+",10,15)");

        execQueryDB("insert into asignatura (id,name)" +
                    "values ("+idSubject+", 'NameSubject1')");

        CareerInstance shouldBeInserted = new CareerInstance(idCareer, careerName, careerDuration);
        CareerInstance shouldNOTBeInserted_NotFound = new CareerInstance(505, careerName, careerDuration);
        CareerInstance shouldNOTBeInserted_ExededYears = new CareerInstance(idCareer, careerName, careerDuration + 1);

        SubjectSQLiteDAO dao = new SubjectSQLiteDAO(urlToDB);
        int should200 = dao.createCInstance(idSubject, shouldBeInserted);
        int should404 = dao.createCInstance(idSubject, shouldNOTBeInserted_NotFound);
        int should400 = dao.createCInstance(idSubject, shouldNOTBeInserted_ExededYears);

        assert should200 == 200 && should404 == 404 && should400 == 400
                : "\n[NO SE INSERTO LA CARRERA EN LA MATERIA CORRECTAMENTE - LOS CODIGOS SON INCORRECTOS] \n" +
                "Valor de retorno del create: (should200:"+should200+
                                            ", should404:"+should404+
                                            ", should400:"+should400+")";
        System.out.println("TEST PASSED: SQLiteCareerSubjectDAO::should_return_correct_resultCode_when_add_careerInstances");
    }

    private static void should_delete_lecture_by_given_id() {
        restartDB();
        final int lectureIdToBeDeleted = 485;
        execQueryDB("insert into asignatura (id, name) values (2,'Mat2');");
        execQueryDB("insert into clase (id_asignatura, id_clase, p_asigned) values (2,"+lectureIdToBeDeleted+",0);"); //full asignada

        SubjectDAO dao = new SubjectSQLiteDAO(urlToDB);
        int removedLecture = dao.removeLecture(lectureIdToBeDeleted);


        assert removedLecture == 200
                : "\n[NO SE ELIMINO CORRECTAMENTE - LOS DATOS NO COINCIDEN] \n" +
                "Valor de retorno del DELETE: " + removedLecture;
        System.out.println("TEST PASSED: SQLiteSubjectDAO::should_delete_lecture_by_given_id");
    }

    private static void should_return_lecture_list_consistant_with_external_script() {
        restartDB();
        final int idRequiredSubject = 985;
        execQueryDB("insert into profesor (name)\n" +
                    "values ('jose')," +
                    "('juan')," +
                    "('luis');");
        execQueryDB("insert into aula (id,pab,room) " +
                    "values (55, 'pab2', 'au3')," +
                    "(56, 'pab1', 'au3')," +
                    "(57, 'pab1', 'au4');");

        execQueryDB("insert into asignatura (id, name)\n" +
                    "values (2,'Mat2')," +
                    "("+idRequiredSubject+",'MAT_REQUIRED')," +
                    "(8, 'Mat8')," +
                    "(10, 'Mat10')," +
                    "(14, 'Mat14');");

        execQueryDB("insert into clase (id_asignatura, id_clase, p_asigned)\n" +
                "values (2, 2, 0),\n" +
                "("+idRequiredSubject+", 898, 1),\n" + //Asignada Parcial
                "("+idRequiredSubject+", 145, 0),\n" +
                "(8, 888, 1),\n" +
                "(10, 125, 2),\n" + //full asignada
                "("+idRequiredSubject+", 111, 1),\n" + //Asignada Parcial
                "(14, 100, 0),\n" +
                "("+idRequiredSubject+", 101, 2);"); //full asignada

        execQueryDB("insert into ocupation (id_aula, id_clase, day, hour, id_profesor) " +
                    "values (56, 101, 5, 8, 'juan')," + //required
                    "(56, 125, 5, 15, 'luis');");

        execQueryDB("insert into clase_fija_parcial (id_aula, id_clase, day, hour, id_profesor) " +
                    "values (55, 2, null, 5, null)," +
                    "(null, 898, 4, 9, null)," + //required
                    "(55, 888, null, 5, null)," +
                    "(null, 111, null, 10, 'jose');"); //required

        List<LectureDTO> list = new LinkedList<>();
        list.add(new LectureDTO(idRequiredSubject, 101, 8, DayOfWeek.Viernes, 56, "Juan"));
        list.add(new LectureDTO(idRequiredSubject, 111, 10, null, -1, "Jose"));
        list.add(new LectureDTO(idRequiredSubject, 145, -1, null, -1, null));
        list.add(new LectureDTO(idRequiredSubject, 898, 9, DayOfWeek.Jueves, -1, null));

        SubjectDAO dao = new SubjectSQLiteDAO(urlToDB);
        List<LectureDTO> returned = dao.getLectures(idRequiredSubject);

        assert returned != null &&
                returned.equals(list)
                :"\n[NO SE RECUPERARON CORRECTAMENTE - LOS DATOS NO COINCIDEN] \n" +
                "Valor de retorno: " + returned;

        System.out.println("TEST PASSED: SQLiteSubjectDAO::should_return_lecture_list_consistant_with_external_script");
    }

    private static void should_create_a_new_lecture_and_return_with_id() {
        final int idSubject = 321;
        final String teacher = "Jose";

        restartDB();
        execQueryDB("insert into asignatura (id,name) values ("+idSubject+",'AsignaturaEjemplo')");
        execQueryDB("insert into profesor (name) values ('"+teacher+"')");
        SubjectDAO dao = new SubjectSQLiteDAO(urlToDB);

        LectureDTO beforeInsertion = new LectureDTO(idSubject, -1, 15
                                                    , DayOfWeek.Jueves, -1, teacher);
        LectureDTO afterInsertion = dao.createLecture(beforeInsertion);

        assert ((afterInsertion != null) && (
                afterInsertion.getIdSubject() == beforeInsertion.getIdSubject() &&
                afterInsertion.getIdLecture() > 0 &&
                afterInsertion.getDayOfWeek() == beforeInsertion.getDayOfWeek() &&
                afterInsertion.getRoomId() == beforeInsertion.getRoomId() &&
                afterInsertion.getStartTime() == beforeInsertion.getStartTime() &&
                afterInsertion.getTeacher().equals(beforeInsertion.getTeacher())
        )) : "\n[NO SE INSERTO LA CLASE CORRECTAMENTE - LOS DATOS NO COINCIDEN] \n" +
                "Valor de retorno del create: " + afterInsertion;


        System.out.println("TEST PASSED: SQLiteSubjectDAO::should_create_a_new_lecture_and_return_with_id");
    }

    private static void should_return_subject_given_name() {
        restartDB();
        execQueryDB("insert into asignatura (id, name)\n" +
                "values (959, 'Asignatura Te123st Subj 1');");
        SubjectDAO dao = new SubjectSQLiteDAO(urlToDB);

        String expectedName = "Asignatura Te123st Subj 1";

        SubjectDTO returned = dao.getSubjectByName(expectedName);

        assert returned.getName().equals(expectedName) &&
               returned.getIdSubject() == 959
               : "\n[NO SE RECUPERO POR NOMBRE - LOS DATOS NO COINCIDEN] \n" +
                "Valor de retorno: " + returned;

        System.out.println("TEST PASSED: SQLiteSubjectDAO::should_return_subject_given_name");
    }

    private static void should_return_subject_list_consistant_with_external_script() {
        restartDB();
        execQueryDB("insert into asignatura (id, name)\n" +
                "values (200, 'Asignatura Test Subj 1'),\n" +
                "       (300, 'Asignatura Test Subj 2'),\n" +
                "       (400, 'Asignatura Test Subj 3');");

        List<SubjectDTO> list = new LinkedList<>();
        list.add(new SubjectDTO(200, "Asignatura Test Subj 1"));
        list.add(new SubjectDTO(300, "Asignatura Test Subj 2"));
        list.add(new SubjectDTO(400, "Asignatura Test Subj 3"));

        SubjectDAO dao = new SubjectSQLiteDAO(urlToDB);
        List<SubjectDTO> returned = dao.getAllSubjects();

        assert returned != null &&
                returned.equals(list)
                :"\n[NO SE RECUPERARON CORRECTAMENTE - LOS DATOS NO COINCIDEN] \n" +
                "Valor de retorno: " + returned;


        System.out.println("TEST PASSED: SQLiteSubjectDAO::should_return_subject_list_consistant_with_external_script");
    }

    private static void should_create_a_new_subject_and_return_with_id() {
        restartDB();
        SubjectDAO dao = new SubjectSQLiteDAO(urlToDB);

        SubjectDTO beforeInsertion = new SubjectDTO("Asignatura Test Subj 1");
        SubjectDTO afterInsertion = dao.createSubject(beforeInsertion);

        assert ((afterInsertion != null) && (
                afterInsertion.getIdSubject() > 0 &&
                afterInsertion.getName().equals(beforeInsertion.getName())
        )) : "\n[NO SE INSERTO CORRECTAMENTE - LOS DATOS NO COINCIDEN] \n" +
                "Valor de retorno del create: " + afterInsertion;

        System.out.println("TEST PASSED: SQLiteSubjectDAO::should_create_a_new_subject_and_return_with_id");


    }

    private static void should_return_careers_list_consistant_with_external_script() {
        restartDB();
        execQueryDB("insert into carrera (id, name, duration, h_inic, h_fin)\n" +
                "values (2, 'Carrera 1', 5, 8, 13),\n" +
                "       (4, 'Carrera 2', 5, 8, 13),\n" +
                "       (6, 'Carrera 3', 5, 8, 13),\n" +
                "       (8, 'Carrera 4', 5, 8, 13),\n" +
                "       (10, 'Carrera 5', 5, 8, 13),\n" +
                "       (12, 'Carrera 6', 5, 8, 13),\n" +
                "       (14, 'Carrera 7', 5, 8, 13),\n" +
                "       (16, 'Carrera 8', 5, 8, 13);");

        List<CareerDTO> list = new LinkedList<>();
        list.add(new CareerDTO(2, "Carrera 1", 5, 8, 13));
        list.add(new CareerDTO(4, "Carrera 2", 5, 8, 13));
        list.add(new CareerDTO(6, "Carrera 3", 5, 8, 13));
        list.add(new CareerDTO(8, "Carrera 4", 5, 8, 13));
        list.add(new CareerDTO(10, "Carrera 5", 5, 8, 13));
        list.add(new CareerDTO(12, "Carrera 6", 5, 8, 13));
        list.add(new CareerDTO(14, "Carrera 7", 5, 8, 13));
        list.add(new CareerDTO(16, "Carrera 8", 5, 8, 13));

        CareerDAO dao = new CareerSQLiteDAO(urlToDB);
        List<CareerDTO> returned = dao.getAllCareers();

        assert returned != null &&
               returned.equals(list)
               :"\n[NO SE RECUPERARON CORRECTAMENTE - LOS DATOS NO COINCIDEN] \n" +
                "Valor de retorno: " + returned;

        System.out.println("TEST PASSED: SQLiteCareerDAO::should_return_careers_list_consistant_with_external_script");
    }

    private static void should_delete_career_by_given_id() {
        restartDB();
        execQueryDB("insert into carrera (id, name, duration, h_inic, h_fin)\n" +
                "values (256, 'Introduccion a la Joda', 5, 8, 12);");
        final int idToBeDeleted = 256;

        CareerDAO dao = new CareerSQLiteDAO(urlToDB);
        int removedCareer = dao.deleteCareer(idToBeDeleted);

        assert removedCareer == 200
               : "\n[NO SE ELIMINO CORRECTAMENTE - LOS DATOS NO COINCIDEN] \n" +
                "Valor de retorno del DELETE: " + removedCareer;
        System.out.println("TEST PASSED: SQLiteCareerDAO::should_delete_career_by_given_id");

    }

    private static void should_create_a_new_career_and_return_with_id(){
        restartDB();
        CareerDAO dao = new CareerSQLiteDAO(urlToDB);

        CareerDTO beforeInsertion = new CareerDTO("Carrera Prueba", 5, 8, 12);

        //showCareer(beforeInsertion);

        CareerDTO afterInsertion = dao.createCareer(beforeInsertion);

        //showCareer(afterInsertion);

        assert ((afterInsertion != null) && (
                afterInsertion.getIdCareer() > 0 &&
                afterInsertion.getName().equals(beforeInsertion.getName()) &&
                afterInsertion.getYears() == beforeInsertion.getYears() &&
                afterInsertion.getPreferredStart() == beforeInsertion.getPreferredStart() &&
                afterInsertion.getPreferredEnd() == beforeInsertion.getPreferredEnd()
                )) : "\n[NO SE INSERTO CORRECTAMENTE - LOS DATOS NO COINCIDEN] \n" +
                     "Valor de retorno del create: " + afterInsertion;

        System.out.println("TEST PASSED: SQLiteCareerDAO::should_create_a_new_career_and_return_with_id");

    }

    private static void showCareer(CareerDTO dto){
        if(dto != null){
            System.out.println(":::::::::MOSTRANDO DTO:::::::::::::");
            System.out.println("id = " + dto.getIdCareer());
            System.out.println("name = " + dto.getName());
            System.out.println("duration = " + dto.getYears());
            System.out.println("h_Inic = " + dto.getPreferredStart());
            System.out.println("h_Fin = " + dto.getPreferredEnd());
            System.out.println(":::::::::FIN MOSTRANDO DTO:::::::::::::");
        }
    }

    private static void restartDB(){
        Connection conn;

        try {
            conn = DriverManager.getConnection(urlToDB);

            try {
                List<PreparedStatement> statements = new LinkedList<>();
                statements.add(conn.prepareStatement("delete from ocupation where 1=1;"));
                statements.add(conn.prepareStatement("delete from carrera where 1=1;"));
                statements.add(conn.prepareStatement("delete from asignatura where 1=1;"));
                statements.add(conn.prepareStatement("delete from comp_carrera where 1=1;"));
                statements.add(conn.prepareStatement("delete from clase where 1=1;"));
                statements.add(conn.prepareStatement("delete from profesor where 1=1;"));
                statements.add(conn.prepareStatement("delete from clase_fija_parcial where 1=1;"));
                statements.add(conn.prepareStatement("delete from aula where 1=1;"));

                conn.setAutoCommit(false);

                for (PreparedStatement statement : statements)
                    statement.executeUpdate();

                conn.commit();
            }catch (SQLException e) {
                try {
                    System.err.print("ERROR AL ELIMINAR EL REGISTRO: ");
                    System.err.println(e.getMessage());
                    System.err.println("INTENTADO HACER ROLLBACK");
                    conn.rollback();
                } catch (SQLException excep) {
                    System.err.print("ERROR AL INTENTAR HACER ROLLBACK");
                    excep.printStackTrace();
                }
            } finally {
                try {
                    conn.close();
                } catch (SQLException exception) {
                    System.err.println("ERROR AL INTENTAR DESCONECTAR DE LA BASE DE DATOS.");
                    System.err.println(exception.getMessage());
                }
            }
        } catch (SQLException exception) {
            System.err.println("ERROR AL CONECTAR CON LA BASE DE DATOS");
            exception.printStackTrace();
        }
    }

    private static void execQueryDB(String query){
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);

            Connection conn = DriverManager.getConnection(urlToDB, config.toProperties());

            try (PreparedStatement querySt = conn.prepareStatement(query)) {
                conn.setAutoCommit(false);
                querySt.executeUpdate();
                conn.commit();
            }catch (SQLException e) {
                try {
                    System.err.print("ERROR AL EJECUTAR CONSULTA: ");
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                    System.err.println("INTENTADO HACER ROLLBACK");
                    conn.rollback();
                } catch (SQLException excep) {
                    System.err.print("ERROR AL INTENTAR HACER ROLLBACK");
                    excep.printStackTrace();
                }
            } finally {
                try {
                    conn.close();
                } catch (SQLException exception) {
                    System.err.println("ERROR AL INTENTAR DESCONECTAR DE LA BASE DE DATOS.");
                    System.err.println(exception.getMessage());
                }
            }
        } catch (SQLException exception) {
            System.err.println("ERROR AL CONECTAR CON LA BASE DE DATOS");
            exception.printStackTrace();
        }
    }


}
