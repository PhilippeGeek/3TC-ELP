package fr.insa.lyon.tc.elp.producer;

import java.util.ArrayList;

/**
 * Created by pvienne on 14/03/16.
 */
public class SyncCubbyHole extends CubbyHole{

    final Thread mainThread = Thread.currentThread();

    @Override
    public synchronized Product get() {
        while (myProduct == null) {
            try {
                wait(10);
            } catch (Exception e) {
                //System.err.println(Thread.currentThread().getName()+" has been interrupted during wait");
            }
        }
        Product p = super.get();
        notify();
        return p;
    }

    @Override
    public synchronized void put(Product aProduct) {
        while (myProduct != null) {
            try {
                wait(10);
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName()+" has been interrupted during wait");
            }
        }
        super.put(aProduct);
        notify();
    }
}
