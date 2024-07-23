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
                    if (threadNumber == 1){
                        Thread.sleep(1500);
                    } else if (threadNumber == 2){
                        Thread.sleep(2000);
                    }
                    Integer value = mmu.read(Integer.parseInt(operation[0]), threadNumber);
                    if (value != null){
                        System.out.println("A thread " + threadNumber + " leu o valor " + value);
                    } else {
                        System.out.println("A thread " + threadNumber + " não conseguiu realizar operação de leitura.");
                    }
                } else if (operation[1].equals("W")) {
                    if (threadNumber == 1){
                        Thread.sleep(1500);
                    } else if (threadNumber == 2){
                        Thread.sleep(2000);
                    }
                    Integer index = mmu.write(Integer.parseInt(operation[0]), Integer.parseInt(operation[2]) ,threadNumber);
                    if (index != null){
                        System.out.println("A thread " + threadNumber + " escreveu o valor " + operation[2] + " no índice " + index + ".");
                    } else {
                        System.out.println("A thread " + threadNumber + " não conseguiu realizar operação de escrita.");
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
