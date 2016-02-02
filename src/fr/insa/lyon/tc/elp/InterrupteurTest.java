package fr.insa.lyon.tc.elp;

public class InterrupteurTest {

    public static void main(String... args){
        Interrupteur interrupteur = new Interrupteur();
        interrupteur.estEnMarche = true;
        //noinspection ConstantConditions
        System.out.println("L'interrupteur est en marche ? "+interrupteur.estEnMarche);
        interrupteur.basculer();
        System.out.println("L'interrupteur est en marche ? "+interrupteur.estEnMarche);
    }

}
