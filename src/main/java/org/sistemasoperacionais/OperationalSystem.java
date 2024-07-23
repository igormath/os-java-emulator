package org.sistemasoperacionais;

public class OperationalSystem {
    private static final int VIRTUAL_MEMORY_SIZE = 8;
    private static final int SWAP_MEMORY_SIZE = 8;
    private static final int PHYSICAL_MEMORY_SIZE = 4;
    private static final int CLOCK_THREAD_SLEEP_SECONDS = 1;

    private VirtualMemory virtualMemory;
    private PhysicalMemory physicalMemory;
    private SwapMemory swapMemory;
    private IMMU mmu;

    public OperationalSystem() {
        virtualMemory = new VirtualMemory(VIRTUAL_MEMORY_SIZE);
        physicalMemory = new PhysicalMemory(PHYSICAL_MEMORY_SIZE);
        swapMemory = new SwapMemory(SWAP_MEMORY_SIZE);
        mmu = new MMU(physicalMemory, swapMemory, virtualMemory);
        Clock clock = new Clock(CLOCK_THREAD_SLEEP_SECONDS, (ClockListener) mmu);
        clock.start(); // Inicia a Thread Clock assim que o Sistema Operacional é instanciado.
        Process process1 = new Process(new String[]{"0-W-8", "3-W-41", "0-R", "3-R", "2-W-9", "1-W-63", "1-R", "2-R"}, mmu, 1);
        Process process2 = new Process(new String[]{"0-W-35", "3-W-11", "0-R", "3-R", "2-W-48", "1-W-22", "1-R", "2-R"}, mmu, 2);
        process1.start();
        process2.start();
    }
}
