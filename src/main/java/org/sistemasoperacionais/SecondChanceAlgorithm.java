package org.sistemasoperacionais;

import java.util.List;

public class SecondChanceAlgorithm extends Thread{

    private int sleepTime;
    private VirtualMemory virtualMemory;
    private PhysicalAndSwapMemory physicalAndSwapMemory;

    public SecondChanceAlgorithm(PhysicalAndSwapMemory physicalAndSwapMemory, int sleepTime, VirtualMemory virtualMemory) {
        this.physicalAndSwapMemory = physicalAndSwapMemory;
        this.sleepTime = sleepTime;
        this.virtualMemory = virtualMemory;
    }

    private List<VirtualPage> virtualMemoryList;
    private List<Integer> swapMemory;

    public void run(){
        virtualMemoryList = virtualMemory.getVirtualMemory();
        System.out.println("Algoritmo de segunda chance iniciado!");
        while (true){
            try {
                Thread.sleep(sleepTime * 1000L);
                synchronized (virtualMemoryList) {
                    for (int i = 0; i < virtualMemoryList.size(); i++) {
                        if (virtualMemoryList.get(i) != null) {
                            if (virtualMemoryList.get(i).isReferenced()) {
                                virtualMemoryList.get(i).setReferenced(false);
                            }
                            if (!virtualMemoryList.get(i).isReferenced() && virtualMemoryList.get(i).isPresent()) {
                                // Remove o valor do Array List que representa a memória física
                                int value = physicalAndSwapMemory.getPhysicalMemory().get(virtualMemoryList.get(i).getPageTable());
                                // Adiciona esse valor a memória SWAP
                                int swapIndex = physicalAndSwapMemory.moveToSwap(value);
                                // Limpa a posição na memória física
                                physicalAndSwapMemory.getPhysicalMemory().add(virtualMemoryList.get(i).getPageTable(), null);
                                // Muda a presença para falso (indica que o valor está salvo na memória SWAP)
                                virtualMemoryList.get(i).setPresent(false);
                                // Atualiza o mapeamento com a nova posição na memória virtual
                                virtualMemoryList.get(i).setPageTable(swapIndex);
                            }
                        }
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
