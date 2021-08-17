package data_access;

import data_transfer.*;
import org.sqlite.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SubjectSQLiteDAO implements SubjectDAO{
    private String urlToDB = "jdbc:sqlite:.data.dt";
    private static final SQLiteConfig connConfig = new SQLiteConfig();
    private static Connection conn = null;

    private static final String createSubjStr = "insert into asignatura (name) values (?)";
    private static final String readSubjByNameStr = "select id from asignatura where name=?";
    private static final String createLectStr = "insert into clase (id_asignatura,p_asigned) " +
                                                "values (?,?)";

    private static final String fixedLectTblStr = "insert into ocupation (id_aula,id_clase,day,hour,id_profesor) values (?,?,?,?,?)";
    private static final String parcFixexLectTblStr = "insert into clase_fija_parcial (id_aula,id_clase,day,hour,id_profesor) values (?,?,?,?,?)";

    private static final String readAllLecturesStr = "SELECT c.id_clase idLect, o.room idRoom, o.day day, o.hour hour, o.prof teacher\n" +
            "FROM clase c LEFT JOIN (\n" +
            "SELECT ocu.id_aula as room, ocu.id_clase as class, ocu.day as day, ocu.hour as hour, ocu.id_profesor as prof FROM ocupation as ocu\n" +
            "UNION\n" +
            "SELECT cfp.id_aula as room, cfp.id_clase as class, cfp.day as day, cfp.hour as hour, cfp.id_profesor as prof FROM clase_fija_parcial as cfp\n" +
            ") as o on c.id_clase=o.class\n" +
            "WHERE c.id_asignatura=?\n" +
            "ORDER by c.id_clase;";


    public SubjectSQLiteDAO(){
        connConfig.enforceForeignKeys(true);

        establishConnection();
    }

    public SubjectSQLiteDAO(String databaseURL){
        urlToDB = databaseURL;
    }

    ///////SUBJECTS
    @Override
    public SubjectDTO createSubject(SubjectDTO subject) {
        establishConnection();

        SubjectDTO subjectToReturn = null;

        //(name)
        try (PreparedStatement createSt = conn.prepareStatement(createSubjStr)) {
            conn.setAutoCommit(false);

            createSt.setString(1, subject.getName());

            createSt.executeUpdate();
            conn.commit();

            subjectToReturn = this.getSubjectByName(subject.getName());
        }catch (SQLException e) {
            try {
                System.err.println("::::::::::::::::::::::::::::::::::::::::::::::::::::");
                System.err.print("ERROR AL CREAR EL REGISTRO: ");
                if (conn != null) {
                    System.err.println(e.getMessage());
                    System.err.println("INTENTADO HACER ROLLBACK");
                    System.err.println("::::::::::::::::::::::::::::::::::::::::::::::::::::");
                    conn.rollback();
                }
            } catch (SQLException excep) {
                System.err.print("ERROR AL INTENTAR HACER ROLLBACK");
                excep.printStackTrace();
            }

        }finally {
            closeConnection();
        }

        return subjectToReturn;
    }

    @Override
    public List<SubjectDTO> getAllSubjects() {
        final String readAllStr = "select id,name from asignatura order by id";
        List<SubjectDTO> allSubjectsList = new LinkedList<>();

        establishConnection();

        try (PreparedStatement readSt = conn.prepareStatement(readAllStr)) {
            ResultSet res = readSt.executeQuery();

            if (res != null)
                while (res.next())
                    allSubjectsList.add(new SubjectDTO(res.getInt("id"), res.getString("name")));

        } catch (SQLException exception) {
            System.err.println("ERROR AL EJECUTAR LA CONSULTA A LA BASE DE DATOS");
            exception.printStackTrace();
            allSubjectsList = null;
        } finally {
            closeConnection();
        }

        return allSubjectsList;
    }

    @Override
    public SubjectDTO getSubjectByName(String subjectName) {
        establishConnection();

        SubjectDTO subjectToReturn = null;

        try (PreparedStatement readSt = conn.prepareStatement(readSubjByNameStr)) {
            readSt.setString(1, subjectName);
            ResultSet res = readSt.executeQuery();

            if (res != null && res.next()){
                subjectToReturn = new SubjectDTO(subjectName);
                subjectToReturn.setIdSubject(res.getInt("id"));
            }

        } catch (SQLException exception) {
            System.err.println("ERROR AL EJECUTAR LA CONSULTA A LA BASE DE DATOS");
            exception.printStackTrace();
        } finally {
            closeConnection();
        }

        return subjectToReturn;
    }

    ///////LECTURES
    @Override
    public LectureDTO createLecture(LectureDTO lectureToAdd) {
        establishConnection();

        LectureDTO lectureToReturn = null;

        //clase (id_asignatura,p_asigned)
        try{
            int idLecture = -1;
            PreparedStatement createClaseSt = conn.prepareStatement(createLectStr, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement createOcupationSt;
            conn.setAutoCommit(false);
            createClaseSt.setInt(1,lectureToAdd.getIdSubject());
            createClaseSt.setInt(2, 0);


            createClaseSt.executeUpdate();
            ResultSet res = createClaseSt.getGeneratedKeys();
            if (res != null && res.next())
                idLecture =  res.getInt(1);

            ////Llenar los registros de ocupacion segun corresponda
            ////(id_aula,id_clase,day,hour,id_profesor)
            if(lectureToAdd.getRoomId() > 0 && lectureToAdd.getDayOfWeek() != null && lectureToAdd.getStartTime() > 0){
                createOcupationSt = conn.prepareStatement(fixedLectTblStr);
            }else{
                createOcupationSt = conn.prepareStatement(parcFixexLectTblStr);
            }

            createOcupationSt.setInt(2, idLecture);

            if(lectureToAdd.getRoomId() > 0) createOcupationSt.setInt(1, lectureToAdd.getRoomId());
            else                             createOcupationSt.setNull(1, Types.INTEGER);

            if(lectureToAdd.getDayOfWeek() != null) createOcupationSt.setInt(3, lectureToAdd.getDayOfWeek().ordinal()+1);
            else                                    createOcupationSt.setNull(3, Types.INTEGER);

            if(lectureToAdd.getStartTime() > 0) createOcupationSt.setInt(4, lectureToAdd.getStartTime());
            else                                createOcupationSt.setNull(4, Types.INTEGER);

            if(lectureToAdd.getTeacher() != null)   createOcupationSt.setString(5,lectureToAdd.getTeacher());
            else                                    createOcupationSt.setNull(5,Types.VARCHAR);

            createOcupationSt.executeUpdate();

            conn.commit();

            lectureToReturn = lectureToAdd;
            lectureToReturn.setIdLecture(idLecture);
        }catch (SQLException e) {
            try {
                System.err.println("::::::::::::::::::::::::::::::::::::::::::::::::::::");
                System.err.print("ERROR AL CREAR EL REGISTRO: ");
                if (conn != null) {
                    System.err.println(e.getMessage());
                    System.err.println("INTENTADO HACER ROLLBACK");
                    System.err.println("::::::::::::::::::::::::::::::::::::::::::::::::::::");
                    conn.rollback();
                }
            } catch (SQLException excep) {
                System.err.print("ERROR AL INTENTAR HACER ROLLBACK");
                excep.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        } finally{
            closeConnection();
        }

        return lectureToReturn;
    }

    @Override
    public List<LectureDTO> getLectures(int idRequiredSubject) {
        List<LectureDTO> allLecturesList = new LinkedList<>();

        establishConnection();

        try (PreparedStatement readSt = conn.prepareStatement(readAllLecturesStr)) {

            readSt.setInt(1, idRequiredSubject);

            ResultSet res = readSt.executeQuery();

            if (res != null)
                //(c.id_clase idLect, o.room idRoom, o.day day, o.hour hour, o.prof teacher)
                while (res.next()){
                    int idLecture = res.getInt("idLect");
                    int idRoom = res.getInt("idRoom");
                    DayOfWeek day = toDayOfWeek(res.getInt("day"));
                    int hour = res.getInt("hour");
                    String teacher = res.getString("teacher");

                    idRoom = (idRoom < 1)? idRoom - 1 : idRoom;
                    hour = (hour < 1)? hour - 1 : hour;

                    allLecturesList.add(new LectureDTO(idRequiredSubject, idLecture, hour, day, idRoom, teacher));
                }

        } catch (SQLException exception) {
            System.err.println("ERROR AL EJECUTAR LA CONSULTA A LA BASE DE DATOS");
            exception.printStackTrace();
            allLecturesList = null;
        } finally {
            closeConnection();
        }

        return allLecturesList;
    }

    private DayOfWeek toDayOfWeek(int day) {
        switch (day){
            case 1:
                return DayOfWeek.Lunes;
            case 2:
                return DayOfWeek.Martes;
            case 3:
                return DayOfWeek.Miercoles;
            case 4:
                return DayOfWeek.Jueves;
            case 5:
                return DayOfWeek.Viernes;
            case 6:
                return DayOfWeek.Sabado;
            default:
                return null;
        }
    }

    @Override
    public int removeLecture(int lectureId) {
        return 0;
    }

    ///////CAREERS
    @Override
    public CareerInstance createCInstance(int subjectId, CareerInstance careerToAdd) {
        return null;
    }

    @Override
    public List<CareerInstance> getCareers(int idSubject) {
        List<CareerInstance> careers = new LinkedList<>();

        return careers;
    }

    @Override
    public int removeCareer(int idSubject, CareerInstance careerInstance) {
        return 0;
    }

    private void establishConnection(){
        try {
            if(conn == null || conn.isClosed()){
                try {
                    conn = DriverManager.getConnection(urlToDB, connConfig.toProperties());

                } catch (SQLException exception) {
                    System.err.println("ERROR AL CONECTAR CON LA BASE DE DATOS");
                    exception.printStackTrace();
                }
            }
        } catch (SQLException exception) {
            System.err.println("ERROR AL VERIFICAR SI LA CONEXION ESTA CERRADA.");
        }
    }

    private void closeConnection(){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException exception) {
                System.err.println("ERROR AL INTENTAR DESCONECTAR DE LA BASE DE DATOS.");
                System.err.println(exception.getMessage());
            }
        }
    }
}
