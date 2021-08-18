package data_access;

import java.io.*;
import java.util.ArrayList;

public class FileCntlr {
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

    public static boolean save(){
        /*
        PersistenceFile writeFile = new PersistenceFile();
        try {
            //save in root directory to test later with .jar deployment
            FileOutputStream f = new FileOutputStream(new File("Persistence.objects"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(writeFile);

            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return false;
        } catch (IOException e) {
            System.out.println("Error initializing stream");
            return false;
        }

         */
        return true;
    }

    public static boolean load(){
        try {
            FileInputStream fi = new FileInputStream(new File("Persistence.objects"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            //PersistenceFile readFile = (PersistenceFile)
            oi.readObject();

            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return false;
        } catch (IOException e) {
            System.out.println("Error initializing stream");
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
