package fr.insa.lyon.tc.elp.calculatrice;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Number button
 */
public class NumberButton extends JButton implements ActionListener {

    private final int value;
    private final CalculatriceFrame frame;

    public NumberButton(CalculatriceFrame frame, int i){
        super(Integer.toString(i));
        this.value = i;
        this.frame = frame;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(frame.isShouldEraseInput())
            frame.setCurrentInputValue((float)value);
        else
            frame.setCurrentInputValue(frame.getCurrentInputValue()*10+value);
    }
}
