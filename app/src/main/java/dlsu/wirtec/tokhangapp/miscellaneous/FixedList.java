package dlsu.wirtec.tokhangapp.miscellaneous;


import java.util.AbstractSet;
import java.util.Iterator;

/**
 * Stores a reference of the array
 * Created by lyssa on 30/03/2017.
 */

public class FixedList<E> extends AbstractSet<E> {

    private E[] elements;

    public FixedList(E[] elements){
        this.elements = elements;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<E>(this);
    }

    @Override
    public int size() {
        return elements.length;
    }

    private class ArrayIterator<E> implements Iterator<E> {

        private final FixedList<E> fixedList;
        private int currentIndex = 0;

        public ArrayIterator(FixedList fixedList){
            this.fixedList = fixedList;
        }

        @Override
        public boolean hasNext() {
            return currentIndex >= fixedList.elements.length;
        }

        @Override
        public E next() {
            return fixedList.elements[++currentIndex];
        }
    }
}
