package ru.otus.hw2;

import java.util.*;

public class DIYarrayList<T> implements List<T> {
    private Object[] resultArray;
    private int currentIndex;

    public DIYarrayList() {
        resultArray = new Object[5];
    }

    public DIYarrayList(int capacity) {
        resultArray = new Object[capacity];
    }

    @Override
    public int size() {
        return resultArray.length;
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        return listIterator();
    }

    @Override
    public Object[] toArray() {
        return resultArray;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T item) {
        if(currentIndex >= this.size()) {
            resultArray = Arrays.copyOf(resultArray, size() + 1);
        }

        resultArray[currentIndex++] = item;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(int index) {
        if(index >= this.size()) {
            throw new IndexOutOfBoundsException();
        }

        return (T)this.resultArray[index];
    }

    @Override
    public T set(int index, T element) {
        resultArray[index] = element;
        return (T)resultArray[index];
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIterator<T>() {
            int index = -1;
            @Override
            public boolean hasNext() {
                if(index + 1 < size()) {
                    return true;
                }

                return false;
            }

            @Override
            public T next() {
                return (T) resultArray[nextIndex()];
            }

            @Override
            public boolean hasPrevious() {
                if(index - 1 > 0 && index - 1 < size()) {
                    return true;
                }

                return false;
            }

            @Override
            public T previous() {
                return (T) resultArray[previousIndex()];
            }

            @Override
            public int nextIndex() {
                if(index + 1 < size()) {
                    index = index + 1;
                    return index;
                }
                return index;
            }

            @Override
            public int previousIndex() {
                if(index - 1 >= 0 && index - 1 < size()) {
                    index = index - 1;
                    return index;
                }

                return index;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(T item) {
                resultArray[index] = item;
            }

            @Override
            public void add(T t) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
