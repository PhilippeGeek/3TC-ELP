package fr.insa.lyon.tc.elp;

/**
 * Mise en évidence des changements d'affectations entre types natifs et objets.
 */
@SuppressWarnings({"ConstantConditions", "UnnecessaryLocalVariable"}) // Parce qu'on fait des chose qui pourrait être simplifiés
public class Affectation {

    public static void main(String... args){
        boolean b1 = false;
        boolean b2 = b1;
        b2 = !b2;
        System.out.println("Résultat de réaffectation sur un type natif : ");
        System.out.println( b1 );
        Interrupteur i1 = new Interrupteur();
        Interrupteur i2 = i1;
        i2.basculer();
        System.out.println("Résultat de réaffectation sur des objets : ");
        System.out.println( i1.estEnMarche );
    }

}
