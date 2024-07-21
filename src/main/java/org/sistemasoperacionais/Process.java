package org.sistemasoperacionais;

public class Process extends Thread{
    private String[] inputArray;
    private int threadNumber;
    private IOperationalSystem os;

    public Process(String[] inputArray, IOperationalSystem os, int threadNumber) {
        this.inputArray = inputArray;
        this.os = os;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        for (String s : inputArray) {
            try {
                Thread.sleep(100);
                String[] operation = s.split("-");
                if (operation[1].equals("R")){
                    os.read(Integer.parseInt(operation[0]), threadNumber);
                } else if (operation[1].equals("W")) {
                    os.write(Integer.parseInt(operation[0]), Integer.parseInt(operation[2]) ,threadNumber);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
