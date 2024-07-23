package org.sistemasoperacionais;

public class MMU implements IMMU, ClockListener {

    private SecondChanceAlgorithm algorithm;
    private VirtualMemory virtualMemory;
    private PhysicalMemory physicalMemory;
    private SwapMemory swapMemory;

    public MMU(PhysicalMemory physicalMemory, SwapMemory swapMemory, VirtualMemory virtualMemory) {
        this.physicalMemory = physicalMemory;
        this.swapMemory = swapMemory;
        this.virtualMemory = virtualMemory;
        algorithm = new SecondChanceAlgorithm(virtualMemory);
    }

    public Integer write(int virtualIndex, int value, int threadId) {
        Integer freeIndexPhysical = physicalMemory.getFreeIndexPhysical();
        int virtualMemorySize = virtualMemory.getVirtualMemorySize();
        // Lógica de "conversão" de índices entre as threads.
        if (threadId == 2){
            virtualIndex += (virtualMemorySize / 2);
        }

        algorithm.addFifoOrder(virtualIndex);

        if (freeIndexPhysical != null) {
            virtualMemory.setVirtualMemoryPage(new Page(true, true, freeIndexPhysical), virtualIndex);
            physicalMemory.setValuePhysical(freeIndexPhysical, value);
            return freeIndexPhysical;
        } else {
            algorithm.runSecondChance();
            for (int i = 0; i < algorithm.getFifoOrderSize(); i++) {
                Page page = virtualMemory.getVirtualMemoryPage(i);
                // Acha uma página que não foi referenciada para movê-la para o swap.
                if (!page.isReferenced()){
                    // -- MOVENDO VALOR PARA A SWAP --
                    // Busca um índice livre na SWAP
                    int freeIndexSwap = swapMemory.getFreeIndexSwap();
                    // Antes de mover para a swap, obtêm a antiga referência para a memória física, que ficará livre após a movimentação para a swap.
                    freeIndexPhysical = page.getPageTable();
                    // Adiciona o valor antigo ao swap
                    swapMemory.setValueSwap(freeIndexSwap, physicalMemory.getValuePhysical(page.getPageTable()));
                    // -- MOVENDO VALOR PARA A MEMÓRIA FÍSICA --
                    // Adiciona novo valor a memória física
                    physicalMemory.setValuePhysical(freeIndexPhysical, value);
                    // Adiciona página à memória virtual com novas referências.
                    virtualMemory.setVirtualMemoryPage(new Page(true, true, freeIndexPhysical), virtualIndex);
                    return freeIndexPhysical;
                }
            }
        }
        return null;
    }

    public Integer read(int virtualIndex, int threadId) {
        Page page = virtualMemory.getVirtualMemoryPage(virtualIndex);

        algorithm.addFifoOrder(virtualIndex);

        if (page.isPresent()) {
            Integer value = physicalMemory.getValuePhysical(page.getPageTable());
            page.setReferenced(true);
            return value;
        } else {
            Integer freeIndexPhysical = physicalMemory.getFreeIndexPhysical();
            Integer value = swapMemory.getValueSwap(page.getPageTable());
            if (freeIndexPhysical != null) {
                // Se a página não estiver presente mas existir espaço na memória física
                swapMemory.setValueSwap(page.getPageTable(), null);
                physicalMemory.setValuePhysical(freeIndexPhysical, value);
                page.setPageTable(freeIndexPhysical);
                page.setPresent(true);
                page.setReferenced(true);
                return value;
            } else {
                // Se a página não estiver presente e não existir espaço na memória física
                // Roda algoritmo segunda chance para arrumar espaço na memória física.
                algorithm.runSecondChance();
                for (int i = 0; i < algorithm.getFifoOrderSize(); i++) {
                    Page fifoPage = virtualMemory.getVirtualMemoryPage(i);
                    if (!fifoPage.isReferenced()){
                        // -- MOVENDO VALOR PARA A SWAP --
                        // Busca um índice livre na SWAP
                        int freeIndexSwap = swapMemory.getFreeIndexSwap();
                        // Antes de mover para a swap, obtêm a antiga referência para a memória física, que ficará livre após a movimentação para a swap.
                        freeIndexPhysical = page.getPageTable();
                        // Adiciona o valor antigo ao swap
                        swapMemory.setValueSwap(freeIndexSwap, physicalMemory.getValuePhysical(page.getPageTable()));
                        // -- MOVENDO VALOR PARA A MEMÓRIA FÍSICA --
                        // Adiciona novo valor a memória física
                        physicalMemory.setValuePhysical(freeIndexPhysical, value);
                        // Adiciona página à memória virtual com novas referências.
                        virtualMemory.setVirtualMemoryPage(new Page(true, true, freeIndexPhysical), virtualIndex);
                        return value;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void tick() {
        // Clock vai zerar os valores referenciados.
        algorithm.runSecondChance();
    }
}
