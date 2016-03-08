package fr.insa.lyon.tc.elp.streams;

import java.io.*;

/**
 * Created by pvienne on 07/03/2016.
 */
public class Copy {

    private static void copy(InputStream is, OutputStream os)
            throws IOException {
        int b = -1;
        while ((b=is.read())>0)
            os.write(b);
    }

    public static void main(String... args){
        try {
            copy(new FileInputStream(args[1]), new FileOutputStream(args[2]));
        } catch (IOException e) {
            System.err.println("Can not achieve copy due to error :");
            e.printStackTrace(System.err);
        }
    }

}
