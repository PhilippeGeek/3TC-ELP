package fr.insa.lyon.tc.elp.calculatrice;

/**
 * An operatable action on number
 */
public interface ComputeOperation {

    float getResult(float left);
    float getSource();
    float setSource(float i);

}
