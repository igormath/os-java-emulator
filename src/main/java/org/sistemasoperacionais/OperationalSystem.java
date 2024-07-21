package org.sistemasoperacionais;

public class OperationalSystem implements IOperationalSystem{

    private static final int VIRTUAL_MEMORY_SIZE = 8;
    private static final int SWAP_MEMORY_SIZE = 8;
    private static final int PHYSICAL_MEMORY_SIZE = 4;
    private static final int THREAD_SLEEP_SECONDS = 3;


    SecondChanceAlgorithm algorithm;

    private VirtualMemory virtualMemory;
    private IAccess access;

    public OperationalSystem() {
        virtualMemory = new VirtualMemory(VIRTUAL_MEMORY_SIZE);
        access = new Access(PHYSICAL_MEMORY_SIZE, SWAP_MEMORY_SIZE, virtualMemory);
        virtualMemory.setAccess(access);
        algorithm = new SecondChanceAlgorithm(VIRTUAL_MEMORY_SIZE, virtualMemory, access);
        Clock clock = new Clock(2, algorithm);
        clock.start(); // Inicia a Thread Clock assim que o Sistema Operacional é instanciado.
    }

    @Override
    public void read(int virtualIndex, int threadNumber) {
        System.out.print("A thread " + threadNumber);
        virtualMemory.readFromVirtualMemory(virtualIndex);
    }

    @Override
    public void write(int virtualIndex, int value, int threadNumber) {
        System.out.print("A thread " + threadNumber);
        virtualMemory.writeToVirtualMemory(virtualIndex, value);
        algorithm.getFifoOrder().add(virtualIndex);
    }
}
