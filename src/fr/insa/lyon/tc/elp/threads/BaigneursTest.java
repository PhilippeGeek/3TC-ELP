package fr.insa.lyon.tc.elp.threads;

/**
 * Created by pvienne on 14/03/16.
 */
public class BaigneursTest {

    public static void main(String... args){
        Piscine piscine = new Piscine();    //la piscine
        int n = 150;
        Thread[] baigneurs = new Thread[n];
        for (int i = 0; i < n; i++)         //les baigneurs
        {
            baigneurs[i] = new Thread(new Baigneur(piscine, 5));
            baigneurs[i].start();
        }
    }

}
