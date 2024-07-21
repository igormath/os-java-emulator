package org.sistemasoperacionais;

import java.util.ArrayList;
import java.util.List;

public class SecondChanceAlgorithm{
    private List<Integer> fifoOrder;
    private VirtualMemory virtualMemory;
    private IAccess acesso;

    public SecondChanceAlgorithm(int virtualMemorySize, VirtualMemory virtualMemory, IAccess acesso) {
        fifoOrder = new ArrayList<>(virtualMemorySize);
        this.virtualMemory = virtualMemory;
        this.acesso = acesso;
    }

    public void addFifoOrder(int virtualIndex){
        fifoOrder.add(virtualIndex);
    }

    public List<Integer> getFifoOrder() {
        return fifoOrder;
    }

    public void runSecondChance(){
        if (fifoOrder.isEmpty()){
            return;
        }

        for (int i = 0; i < fifoOrder.size(); i++){
            int index = fifoOrder.get(i);
            Page page = virtualMemory.getVirtualMemory().get(index);

            if (page == null){
                fifoOrder.remove(i);
            }

            for (Integer integer : fifoOrder) {
                System.out.println("Índice: " + integer);
            }

            if (page.isReferenced()){
                // Segunda chance. O booleano que guarda se a página foi acessada recentemente é alterada para falso
                // e a página é jogada para o fim da fila FIFO.
                page.setReferenced(false);
                fifoOrder.add(fifoOrder.remove(i));
                System.out.println("A página de pageTable " + page.getPageTable() + " foi enviada para o fim da fila FIFO.");
            } else {
                // Enviar para swap e "desreferenciar".
                acesso.moveToSwap(page.getPageTable());
                fifoOrder.remove(i);
                // executar método que envia para SWAP.
                break;
            }
        }
    }
}

