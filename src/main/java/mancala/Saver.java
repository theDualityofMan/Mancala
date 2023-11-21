import java.io.*;

public class Saver implements Serializable{

    public static void saveObject(Serializable toSave, String filename){
        try{
            FileOutputStream file = new FileOutputStream("assets/" + filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(toSave);
            out.close();
            file.close();
            System.out.println("Object has been serialized");
        } catch (IOException e){
            System.out.println(e);
        }
    }

    public static Serializable loadObject(String filename){
        Serializable object = null;
        try{
            FileInputStream file = new FileInputStream("assets/" + filename);
            ObjectInputStream in = new ObjectInputStream(file);
            object = (Serializable)in.readObject();
            in.close();
            file.close();
            System.out.println("Object has been deserialized");
        } catch (IOException e){
            System.out.println(e);
        } catch (ClassNotFoundException e){
            System.out.println(e);
        }

        return object;
    }
}