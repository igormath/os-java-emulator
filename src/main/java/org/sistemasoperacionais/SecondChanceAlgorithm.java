package org.sistemasoperacionais;

import java.util.ArrayList;
import java.util.List;

public class SecondChanceAlgorithm {
    private List<Integer> fifoOrder;
    private VirtualMemory virtualMemory;
    private PhysicalMemory physicalMemory;
    private SwapMemory swapMemory;
    private int virtualMemorySize;

    public SecondChanceAlgorithm(VirtualMemory virtualMemory, PhysicalMemory physicalMemory, SwapMemory swapMemory) {
        this.virtualMemory = virtualMemory;
        this.physicalMemory = physicalMemory;
        this.swapMemory = swapMemory;
        this.virtualMemorySize = virtualMemory.getVirtualMemorySize();
        this.fifoOrder = new ArrayList<>(virtualMemorySize);
    }

    public void addFifoOrder(int virtualIndex) {
        fifoOrder.add(virtualIndex);
    }

    public List<Integer> getFifoOrder() {
        return fifoOrder;
    }

    public void runSecondChance() {
        if (fifoOrder.isEmpty()) {
            System.out.println("Passou por aqui!");
            return;
        }

        for (int i = 0; i < fifoOrder.size(); i++) {
            int index = fifoOrder.get(i);
            Page page = virtualMemory.getVirtualMemoryPage(index);

            if (page == null) {
                fifoOrder.remove(i);
                i--; // Para ajustar o índice após a remoção
                continue;
            }

            if (page.isReferenced()) {
                // Segunda chance. O booleano que guarda se a página foi acessada recentemente é alterada para falso
                // e a página é jogada para o fim da fila FIFO.
                page.setReferenced(false);
                fifoOrder.add(fifoOrder.remove(i));
                i--; // Para ajustar o índice após a movimentação
                System.out.println("A página de pageTable " + page.getPageTable() + " foi enviada para o fim da fila FIFO.");
            } else {
                // Implementar método que deve retirar o dado da memória física e enviar para SWAP.
                Integer physicalValue = physicalMemory.getValuePhysical(page.getPageTable());
                Integer freeSwapIndex = swapMemory.getFreeIndexSwap();

                if (freeSwapIndex != null) {
                    swapMemory.setValueSwap(freeSwapIndex, physicalValue);
                    physicalMemory.setValuePhysical(page.getPageTable(), null);
                    page.setPresent(false);
                    page.setPageTable(freeSwapIndex);
                    System.out.println("O endereço " + page.getPageTable() + " foi transferido para a SWAP");
                } else {
                    System.out.println("Memória Swap cheia!");
                }
                fifoOrder.remove(i);
                break;
            }
        }
    }
}
