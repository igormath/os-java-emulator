package org.sistemasoperacionais;

import java.util.ArrayList;
import java.util.List;

public class SecondChanceAlgorithm {
    private List<Integer> fifoOrder;
    private VirtualMemory virtualMemory;
    private int virtualMemorySize;

    public SecondChanceAlgorithm(VirtualMemory virtualMemory) {
        this.virtualMemory = virtualMemory;
        this.virtualMemorySize = virtualMemory.getVirtualMemorySize();
        this.fifoOrder = new ArrayList<>(virtualMemorySize);
    }

    public void addFifoOrder(int virtualIndex) {
        if (!fifoOrder.contains(virtualIndex)){
            fifoOrder.add(virtualIndex);
            System.out.println("Índice adicionado à lista FIFO: " + virtualIndex);
        }
    }

    public int getFifoOrderSize(){
        return fifoOrder.size();
    }

    public void runSecondChance() {
        System.out.println("Estado atual da lista FIFO: " + fifoOrder);
        if (fifoOrder.isEmpty()) {
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
                page.setReferenced(false);
                fifoOrder.add(fifoOrder.remove(i)); // Move a página para o fim da fila FIFO
                i--; // Para ajustar o índice após a movimentação
                System.out.println("A página de pageTable " + page.getPageTable() + " foi enviada para o fim da fila FIFO.");
            }
        }
    }
}
