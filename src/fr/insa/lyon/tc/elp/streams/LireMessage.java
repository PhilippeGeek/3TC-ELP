package fr.insa.lyon.tc.elp.streams;

import java.io.*;
import java.util.Date;

/**
 * Created by pvienne on 07/03/2016.
 */
public class LireMessage {
    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("usage: java LireMessage <dest>");
        }
        else {

            try {

                File dest = new File(args[0]);
                FileInputStream fout = new FileInputStream(dest);
                ObjectInputStream oout = new ObjectInputStream(fout);

                Message m = (Message) oout.readObject();

                oout.close();
                fout.close();

                System.out.println(m);
            } catch (FileNotFoundException e) {
                System.err.println("ERR: Le fichier source n'existe pas ou le fichier de destination ne peut être ouvert.");
            } catch (IOException e) {
                System.err.println("ERR: entrée/sortie.");
            } catch (ClassNotFoundException e) {
                e.printStackTrace(System.err);
            } finally {
                System.out.println("END");
            }


        }
    }

}
