package com.company;

public class SimpleReadWriteLock {
    int readers;
    boolean writer;
    Lock readLock, writeLock;

    public SimpleReadWriteLock(){
        writer = false;
        readers = 0;
        readLock = new ReadLock();
        writeLock = new WriteLock();
    }

    public Lock readLock(){
        return readLock;
    }

    public Lock writeLock(){
        return writeLock;
    }

    public boolean isWriteLock(){
        return writer;
    }

    class ReadLock implements Lock {
        @Override
        public void lock() {
            synchronized (SimpleReadWriteLock.class) {
                while (writer) {
                    try {
                        SimpleReadWriteLock.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                readers++;
            }
        }
        @Override
        public void unlock() {
            synchronized (SimpleReadWriteLock.class) {
                readers--;
                if (readers == 0)
                    SimpleReadWriteLock.class.notifyAll();
            }
        }
    }

    class WriteLock implements Lock {
        @Override
        public void lock() {
            synchronized (SimpleReadWriteLock.class) {
                while (readers > 0 || writer){
                    try {
                        SimpleReadWriteLock.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                writer = true;
            }
        }
        @Override
        public void unlock() {
            synchronized (SimpleReadWriteLock.class) {
                writer = false;
                SimpleReadWriteLock.class.notifyAll();
            }
        }
    }
}
