package fr.insa.lyon.tc.elp.calculatrice;

import javax.swing.*;

/**
 * Operation
 */
public abstract class Operation implements ComputeOperation {

    private Float originValue;
    protected Float resultValue;

    public Operation(float left, float right){
        originValue = left;
        resultValue = compute(left, right);
    }

    @Override
    public float getResult(float right) {
        if(resultValue == null)
            resultValue = compute(originValue, right);
        return resultValue;
    }

    @Override
    public float getSource() {
        return originValue;
    }

    @Override
    public float setSource(float i) {
        resultValue = null;
        Float previousValue = originValue;
        originValue = i;
        return previousValue;
    }

    public abstract float compute(float left, float right);
}
