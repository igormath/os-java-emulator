package org.sistemasoperacionais;

public class Process extends Thread{

    private OperationalSystem os;
    //{"4-R", "5-R", "0-R", "4-W-2"};
    private String[] instructions;
    private int threadNumber;

    public Process(String[] instructions, OperationalSystem os, int threadNumber) {
        this.instructions = instructions;
        this.os = os;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run(){
        for (String instruction : instructions) {
            String[] splitedInstructions = instruction.split("-");
            if (splitedInstructions[1] == "R"){
                os.read(threadNumber, Integer.parseInt(splitedInstructions[0]));
            } else if (splitedInstructions[1] == "W") {
                os.write(threadNumber, Integer.parseInt(splitedInstructions[2]));
            }
        }
    }
}
