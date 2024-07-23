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
        System.out.println("Índice adicionado à lista FIFO: " + virtualIndex);
        System.out.println("Lista FIFO atual: " + fifoOrder);
    }

    public List<Integer> getFifoOrder() {
        return fifoOrder;
    }

    public void runSecondChance() {
        System.out.println("Executando runSecondChance...");
        System.out.println("Estado atual da lista FIFO: " + fifoOrder);
        if (fifoOrder.isEmpty()) {
            System.out.println("Lista FIFO está vazia. Nenhuma ação necessária.");
            return;
        }

        System.out.println("Conseguiu entrar no bloco de execução do algoritmo de Segunda Chance.");
        for (int i = 0; i < fifoOrder.size(); i++) {
            int index = fifoOrder.get(i);
            Page page = virtualMemory.getVirtualMemoryPage(index);

            if (page == null) {
                System.out.println("Página nula para índice: " + index);
                fifoOrder.remove(i);
                i--; // Para ajustar o índice após a remoção
                continue;
            }

            System.out.println("Página no índice " + index + ": " + page);

            if (page.isReferenced()) {
                page.setReferenced(false);
                fifoOrder.add(fifoOrder.remove(i)); // Move a página para o fim da fila FIFO
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
