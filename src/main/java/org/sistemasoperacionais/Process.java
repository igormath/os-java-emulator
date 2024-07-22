package org.sistemasoperacionais;

public class Process extends Thread{
    private String[] inputArray;
    private int threadNumber;
    private IMMU mmu;

    public Process(String[] inputArray, IMMU mmu, int threadNumber) {
        this.inputArray = inputArray;
        this.mmu = mmu;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        for (String s : inputArray) {
            try {
                Thread.sleep(100);
                String[] operation = s.split("-");
                if (operation[1].equals("R")){
                    mmu.read(Integer.parseInt(operation[0]), threadNumber);
                } else if (operation[1].equals("W")) {
                    mmu.write(Integer.parseInt(operation[0]), Integer.parseInt(operation[2]) ,threadNumber);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
