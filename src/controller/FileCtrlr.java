package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileCtrlr {
    public static boolean importCSV(String path, ArrayList<String> data){ //cada String contiene elementos separados por comas
        try{
            FileReader iFile = new FileReader(path);
            BufferedReader bLine = new BufferedReader(iFile);
            String line;

            while ((line = bLine.readLine()) != null){
                data.add(line);
            }

            iFile.close();
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }

        
    }
    
    public static boolean exportCSV(String path, ArrayList<String> content){
        try{
            FileWriter oFile = new FileWriter(path);

            //PROCESO DE ESCRITURA EN CSV

            oFile.close();
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean exportXLS(String path, ArrayList<String> content){
        try{
            FileWriter oFile = new FileWriter(path);

            //PROCESO DE ESCRITURA EN EXCEL EMBELLECIDO

            oFile.close();
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
