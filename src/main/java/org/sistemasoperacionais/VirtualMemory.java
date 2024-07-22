package org.sistemasoperacionais;

public class VirtualMemory {
    private Page[] virtualMemory;
    private int virtualMemorySize;

    public VirtualMemory(int virtualMemorySize) {
        this.virtualMemorySize = virtualMemorySize;
        virtualMemory = new Page[virtualMemorySize];
        for (int i  = 0; i < virtualMemorySize; i++){
            virtualMemory[i] = new Page();
        }
    }

    public Page getVirtualMemoryPage(int pageIndex) {
        return virtualMemory[pageIndex];
    }

    public void setVirtualMemoryPage(Page virtualPage, int virtualPageIndex) {
        virtualMemory[virtualPageIndex] = virtualPage;
    }

    public int getVirtualMemorySize() {
        return virtualMemorySize;
    }
}
