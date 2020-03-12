package controller.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileCtrlr {
    public static short importCSV(String path){
        try{
            FileReader iFile = new FileReader(path);


            return 1;
        }catch (IOException e){
            return -1;
        }

        
    }
    
    public static short exportCSV(String path, ArrayList<Object> contenedores){
        try{
            FileWriter oFile = new FileWriter(path);


            return 1;
        }catch (IOException e){
            return -1;
        }
    }
    
    public static short exportXLS(String path, ArrayList<Object> contenedores){
        try{
            FileWriter oFile = new FileWriter(path);


            return 1;
        }catch (IOException e){
            return -1;
        }
    }
}
