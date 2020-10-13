package com.company;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestThread {
    private static final SimpleReadWriteLock lock = new SimpleReadWriteLock();
    private static String message = "a";

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new WriterA());
        t1.setName("Writer A");

        Thread t2 = new Thread(new WriterB());
        t2.setName("Writer B");

        Thread t3 = new Thread(new Reader());
        t3.setName("Reader");

        Thread t4 = new Thread(new Reader());
        t4.setName("Reader");

        Thread t5 = new Thread(new Reader());
        t5.setName("Reader");

        Thread t6 = new Thread(new Reader());
        t6.setName("Reader");

        Thread t7 = new Thread(new Reader());
        t7.setName("Reader");

        Thread t8 = new Thread(new WriterA());
        t8.setName("Writer A");

        Thread t9 = new Thread(new WriterB());
        t9.setName("Writer B");

        Thread t10 = new Thread(new WriterA());
        t10.setName("Writer A");

        Thread t11 = new Thread(new WriterB());
        t11.setName("Writer B");


        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
        t11.start();


        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();
        t7.join();
        t8.join();
        t9.join();
        t10.join();
        t11.join();
    }

    static class Reader implements Runnable {

        public void run() {

            if(lock.isWriteLock()) {
                System.out.println("Write Lock Present.");
            }
            lock.readLock().lock();

            System.out.println("Start reading ...");

            try {
                Long duration = (long) (Math.random() * 10000);
                System.out.println(Thread.currentThread().getName()
                        + "  Time Taken " + (duration / 1000) + " seconds.");
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() +": "+ message );
                lock.readLock().unlock();
            }
        }
    }

    static class WriterA implements Runnable {

        public void run() {
            lock.writeLock().lock();

            System.out.println("Start writing A ...");
            try {
                Long duration = (long) (Math.random() * 10000);
                System.out.println(Thread.currentThread().getName()
                        + "  Time Taken " + (duration / 1000) + " seconds.");
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                message = message.concat("a");
                lock.writeLock().unlock();
            }
        }
    }

    static class WriterB implements Runnable {

        public void run() {
            lock.writeLock().lock();

            System.out.println("Start writing B ...");
            try {
                Long duration = (long) (Math.random() * 10000);
                System.out.println(Thread.currentThread().getName()
                        + "  Time Taken " + (duration / 1000) + " seconds.");
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                message = message.concat("b");
                lock.writeLock().unlock();
            }
        }
    }
}
