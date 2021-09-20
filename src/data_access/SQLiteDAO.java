package data_access;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDAO {
    protected enum ResultCode {
        FK_FAILED (404),
        YEAR_OVER_MAX (400),
        SUCCESS (200),
        UNKNOWN_ERR (-1);

        protected final int resultCode;

        ResultCode(int resultCode) {
            this.resultCode = resultCode;
        }
    }

    protected String urlToDB = "jdbc:sqlite:.data.dt";
    protected static final SQLiteConfig connConfig = new SQLiteConfig();
    protected static Connection conn = null;

    protected SQLiteDAO(){
        connConfig.enforceForeignKeys(true);
    }

    protected SQLiteDAO(String urlToDB){
        connConfig.enforceForeignKeys(true);
        this.urlToDB = urlToDB;
    }

    ///////SUPPORT METHODS
    protected void establishConnection(){
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

    protected void closeConnection(){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException exception) {
                System.err.println("ERROR AL INTENTAR DESCONECTAR DE LA BASE DE DATOS.");
                System.err.println(exception.getMessage());
            }
        }
    }

    protected String capitalizeWords(String string) {
        if(string != null){
            char[] charArray = string.toCharArray();
            boolean foundSpace = true;

            for(int i = 0; i < charArray.length; i++) {
                if(Character.isLetter(charArray[i])) {
                    // check space is present before the letter
                    if(foundSpace) {
                        charArray[i] = Character.toUpperCase(charArray[i]);
                        foundSpace = false;
                    }
                }
                else
                    foundSpace = true;
            }
            return String.valueOf(charArray);
        }
        return null;
    }

    protected SubjectSQLiteDAO.ResultCode extractErrorCode(String message) {
        if(message.contains("TRIGGER"))
            return SubjectSQLiteDAO.ResultCode.YEAR_OVER_MAX;

        if (message.contains("FOREIGN"))
            return SubjectSQLiteDAO.ResultCode.FK_FAILED;

        return SubjectSQLiteDAO.ResultCode.UNKNOWN_ERR;
    }
}
