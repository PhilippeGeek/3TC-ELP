package fr.insa.lyon.tc.elp.sudoku;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by pvienne on 08/03/2016.
 */
public class Main {

    public static void main(String... args){
        if(args.length<1){
            System.out.println("Usage: Main sudoku.txt");
            return;
        }
        File file = new File(args[0]);
        if(!file.exists() || !file.canRead()){
            System.out.println("File "+file+" can not be read.");
            return;
        }
        try {
            SudokuSolver solver = new SudokuSolver(new FileInputStream(file));
            solver.displayOriginalGrid(System.out);
            solver.solve();
            solver.displayWorkingGrid(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
