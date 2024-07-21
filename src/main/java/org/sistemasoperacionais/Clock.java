package org.sistemasoperacionais;

public class Clock extends Thread{
    private int sleepTime;
    private SecondChanceAlgorithm algorithm;

    public Clock(int sleepTime, SecondChanceAlgorithm algorithm) {
        this.sleepTime = sleepTime;
        this.algorithm = algorithm;
    }

    public void run(){
        try {
            while (true){
                Thread.sleep(sleepTime * 1000L);
                algorithm.runSecondChance();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
