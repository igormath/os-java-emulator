package org.sistemasoperacionais;

public class OperationalSystem implements IOperationalSystem{

    private static final int VIRTUAL_MEMORY_SIZE = 8;
    private static final int SWAP_MEMORY_SIZE = 8;
    private static final int PHYSICAL_MEMORY_SIZE = 4;
    private static final int THREAD_SLEEP_SECONDS = 3;


    SecondChanceAlgorithm algorithm;

    private VirtualMemory virtualMemory;
    private PhysicalMemory physicalMemory;
    private SwapMemory swapMemory;
    private IMMU mmu;

    public OperationalSystem() {
        virtualMemory = new VirtualMemory(VIRTUAL_MEMORY_SIZE);
        physicalMemory = new PhysicalMemory(PHYSICAL_MEMORY_SIZE);
        swapMemory = new SwapMemory(SWAP_MEMORY_SIZE);
        algorithm = new SecondChanceAlgorithm(virtualMemory, physicalMemory, swapMemory);
        mmu = new MMU(physicalMemory, swapMemory, virtualMemory);
        Clock clock = new Clock(THREAD_SLEEP_SECONDS, algorithm);
        clock.start(); // Inicia a Thread Clock assim que o Sistema Operacional é instanciado.
        Process process1 = new Process(new String[]{"4-W-8", "3-W-41", "4-R", "3-R", "7-W-9", "1-W-63", "1-R", "7-R"}, mmu, 1);
        process1.start();
    }

    @Override
    public void read(int virtualIndex, int threadNumber) {
        System.out.print("A thread " + threadNumber);
        // virtualMemory.readFromVirtualMemory(virtualIndex);
    }

    @Override
    public void write(int virtualIndex, int value, int threadNumber) {
        System.out.print("A thread " + threadNumber);
        // virtualMemory.writeToVirtualMemory(virtualIndex, value);
        algorithm.getFifoOrder().add(virtualIndex);
    }
}
