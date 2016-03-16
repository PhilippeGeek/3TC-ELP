package fr.insa.lyon.tc.elp.calculatrice;

/**
 * Unité de Calcule. Elle a la possibilité d'effectuer des opérations et d'en produire un résultat.
 */
public interface Computer {

    void revertLastAction();
    void redoLastAction();
    void act(ComputeOperation operation);

}
