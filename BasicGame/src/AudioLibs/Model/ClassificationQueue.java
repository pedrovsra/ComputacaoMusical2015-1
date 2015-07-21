package AudioLibs.Model;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Pedro Tiago
 */
public class ClassificationQueue {

    private ArrayBlockingQueue<Note> cq;

    public ClassificationQueue(int size) {
        this.cq = new ArrayBlockingQueue<Note>(size);
    }

    public void putOnQueue(Note e) throws InterruptedException {
        this.cq.put(e);
    }

    public Iterator getIterator() {
        return this.cq.iterator();
    }
}