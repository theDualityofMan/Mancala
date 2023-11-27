package mancala;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Saver implements Serializable{
    public static final long serialVersionUID = 4398743;


    /**
     * Takes in an object to save and a filename, and saves a serialized version of that object at that file location
     *
     * @param toSave the object to save
     * @param filename the filename to save at
     */
    public static void saveObject(Serializable toSave, String filename) throws IOException {
        try{
            if(!Files.exists(Paths.get("./assets"))){
                Files.createDirectory(Paths.get("./assets"));
            }
            FileOutputStream file = new FileOutputStream("assets/" + filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(toSave);
            out.close();
            file.close();
        } catch (IOException e){
            throw e;
        }
    }

    /**
     * Takes in a filename and tries to return the object found at that file location
     *
     * @param filename the filename to load from
     * @return the object found at given location
     */
    public static Serializable loadObject(String filename) throws IOException, ClassNotFoundException{
        Serializable object;
        try{
            FileInputStream file = new FileInputStream("assets/" + filename);
            ObjectInputStream in = new ObjectInputStream(file);
            object = (Serializable)in.readObject();
            in.close();
            file.close();
        } catch (Exception e){
            throw e;
        } 

        return object;
    }
}