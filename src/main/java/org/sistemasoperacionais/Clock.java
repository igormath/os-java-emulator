package org.sistemasoperacionais;

public class Clock extends Thread {
    private int sleepTime;
    private ClockListener cl;

    public Clock(int sleepTime, ClockListener cl) {
        this.sleepTime = sleepTime;
        this.cl = cl;
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(sleepTime * 1000L);
                System.out.println("Clock executando runSecondChance...");
                cl.tick();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
