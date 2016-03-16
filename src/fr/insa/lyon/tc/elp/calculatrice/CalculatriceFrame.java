package fr.insa.lyon.tc.elp.calculatrice;

import fr.insa.lyon.tc.elp.calculatrice.operations.Addition;
import fr.insa.lyon.tc.elp.calculatrice.operations.Division;
import fr.insa.lyon.tc.elp.calculatrice.operations.Multiply;
import fr.insa.lyon.tc.elp.calculatrice.operations.Substraction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * Représente une calculatrice sur un écran
 */
public class CalculatriceFrame extends JFrame implements Computer{

    public static final String CALCULATRICE_TITLE = "Calculatrice";
    private final JTextField input;

    private Stack<ComputeOperation> operations = new Stack<>();
    private Float currentValue = null;
    private Float memorySource = null;
    private Float currentInputValue = 0.0f;
    private boolean shouldEraseInput = true;
    private ComputeOperation currentOperation;

    public CalculatriceFrame() {
        super(CALCULATRICE_TITLE);
        setMinimumSize(new Dimension(500, 300));
        setLayout(new BorderLayout());

        this.input = new JTextField();
        add(input, BorderLayout.NORTH);

        {
            JPanel buttonsPanel = new JPanel(new GridLayout(4, 3));
            for (int i = 7; i > 0; i = i - 3)
                for (int j = 0; j < 3; j++)
                    buttonsPanel.add(new NumberButton(this, i + j));
            buttonsPanel.add(new JPanel());
            buttonsPanel.add(new NumberButton(this, 0));
            buttonsPanel.add(new JPanel());

            //buttonsPanel.add(new OperationButton(this, "sqrt", Multiply.class));
            //buttonsPanel.add(new OperationButton(this, "^2", Multiply.class));
            add(buttonsPanel, BorderLayout.CENTER);
        }

        {
            JPanel buttonsPanel = new JPanel(new GridLayout(5,1));
            buttonsPanel.add(new OperationButton(this, "+", Addition.class));
            buttonsPanel.add(new OperationButton(this, "-", Substraction.class));
            buttonsPanel.add(new OperationButton(this, "/", Division.class));
            buttonsPanel.add(new OperationButton(this, "*", Multiply.class));
            JButton computeButton = new JButton("=");
            computeButton.addActionListener((e) -> {
                if (currentOperation != null)
                    computeCurrentOperation();
                setCurrentInputValue(getCurrentValue());
                setShouldEraseInput(true);
            });
            buttonsPanel.add(computeButton);
            add(buttonsPanel, BorderLayout.EAST);
        }

        {

        }

        {
            JButton clear = new JButton("C");
            clear.addActionListener((e) -> {
                currentOperation = null;
                setCurrentValue(0.0f);
                setMemorySource(0.0f);
                setCurrentInputValue(0.0f);
            });
            add(clear, BorderLayout.WEST);
        }


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
        if(memorySource!=null && currentOperation!=null){
            computeCurrentOperation();
            setCurrentInputValue(currentValue);
            setShouldEraseInput(true);
            this.currentOperation = operation;
            this.memorySource = this.currentInputValue;
        } else {
            this.currentOperation = operation;
            this.memorySource = this.currentInputValue;
            setShouldEraseInput(true);
        }
    }

    private void computeCurrentOperation() {
        currentOperation.setSource(memorySource);
        currentValue = currentOperation.getResult(currentInputValue);
        this.operations.push(currentOperation);
        currentOperation = null;
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
    }

    public float getCurrentValue() {
        if(currentValue == null)
            return 0.0f;
        return currentValue;
    }

    public Float getCurrentInputValue() {
        return currentInputValue;
    }

    public void setCurrentInputValue(Float currentInputValue) {
        if(((float)(int)(currentInputValue/1))==currentInputValue){
            this.input.setText(Integer.toString((int)(currentInputValue/1)));
        } else {
            this.input.setText(Float.toString(currentInputValue));
        }
        this.currentInputValue = currentInputValue;
    }

    public void setMemorySource(float memorySource) {
        this.memorySource = memorySource;
    }

    public boolean isShouldEraseInput() {
        boolean val = shouldEraseInput;
        setShouldEraseInput(!val);
        return val;
    }

    public void setShouldEraseInput(boolean shouldEraseInput) {
        this.shouldEraseInput = shouldEraseInput;
    }
}
