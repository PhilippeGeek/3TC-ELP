package fr.insa.lyon.tc.elp.calculatrice;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * Représente une calculatrice sur un écran
 */
public class CalculatriceFrame extends JFrame implements Computer{

    public static final String CALCULATRICE_TITLE = "Calculatrice";

    private Stack<ComputeOperation> operations = new Stack<>();
    private float currentValue;
    private Object currentInputValue;

    public CalculatriceFrame(){
        super(CALCULATRICE_TITLE);
        setMinimumSize(new Dimension(300, 200));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void revertLastAction() {
        final ComputeOperation pop = operations.pop();
        if (pop == null) return;
        setCurrentValue(pop.getSource());
    }

    @Override
    public void redoLastAction() {
        throw new NotImplementedException();
    }

    @Override
    public void act(ComputeOperation operation) {

    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
    }

    public float getCurrentValue() {
        return currentValue;
    }

    public Object getCurrentInputValue() {
        return currentInputValue;
    }

    public void setCurrentInputValue(Object currentInputValue) {
        this.currentInputValue = currentInputValue;
    }
}
