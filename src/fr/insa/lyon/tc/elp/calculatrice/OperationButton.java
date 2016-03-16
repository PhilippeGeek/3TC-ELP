package fr.insa.lyon.tc.elp.calculatrice;

import fr.insa.lyon.tc.elp.calculatrice.operations.Addition;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class OperationButton extends JButton implements ActionListener {

    private final CalculatriceFrame parentCalculatrice;
    private Class<? extends Operation> operation = Addition.class;

    public OperationButton(CalculatriceFrame frame, String label, Class<? extends Operation> operation){
        super(label);
        if(operation != null)
            this.operation = operation;
        if(frame == null){
            throw new IllegalArgumentException("Can not create an Operation Button without a Calculatrice (it's a null)", new NullPointerException("frame can not be null"));
        }
        this.parentCalculatrice = frame;
        addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            parentCalculatrice.act(operation.newInstance());
        } catch (InstantiationException|IllegalAccessException e1) {
            throw new RuntimeException("Can not create the operation !", e1);
        }
    }
}
