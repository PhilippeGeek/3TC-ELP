package fr.insa.lyon.tc.elp.calculatrice;

import javax.swing.*;
import java.awt.*;

/**
 * Représente une calculatrice sur un écran
 */
public class CalculatriceFrame extends JFrame{

    public static final String CALCULATRICE_TITLE = "Calculatrice";

    public CalculatriceFrame(){
        super(CALCULATRICE_TITLE);
        setMinimumSize(new Dimension(300, 200));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
