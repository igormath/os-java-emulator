package org.sistemasoperacionais;

import java.util.ArrayList;
import java.util.List;

public class Access implements IAccess {

    private List<Integer> physicalMemory;
    private List<Integer> swapMemory;
    private VirtualMemory virtualMemory;
    private int physicalSize;
    private int swapSize;

    public Access(int physicalSize, int swapSize, VirtualMemory virtualMemory) {
        this.physicalSize = physicalSize;
        this.swapSize = swapSize;
        this.virtualMemory = virtualMemory;
        physicalMemory = new ArrayList<>(physicalSize);
        swapMemory = new ArrayList<>(swapSize);

        // Inicializando as listas com valores nulos.
        for (int i = 0; i < swapSize; i++){
            swapMemory.add(null);
        }
        for (int i = 0; i < physicalSize; i++){
            physicalMemory.add(null);
        }
    }

    @Override
    public void readFromPhysical(int virtualIndex) {
        Page page = virtualMemory.getVirtualMemory().get(virtualIndex);
        // A referência da página é alterado para true, indicando que houve acesso recente.
        page.setReferenced(true);
        System.out.println(" valor armazenado na memória virtual de índice " + virtualIndex + ": " + physicalMemory.get(page.getPageTable()));
    }

    @Override
    public void readFromSwap(int virtualIndex) {
        // Recupera a página salva no índice informado da memória virtual
        Page page = virtualMemory.getVirtualMemory().get(virtualIndex);
        System.out.println("  fez uma operação de leitura na página virtual de índice " + virtualIndex + " do valor " + page.getPageTable() + " que estava no SWAP.");
        // Utiliza a função seguinte para mover o valor para a memória física.
        moveToPhysical(swapMemory.get(page.getPageTable()), virtualIndex);
    }

    @Override
    public void moveToSwap(int virtualIndex) {
        Page page = virtualMemory.getVirtualMemory().get(virtualIndex);
        for (int i = 0; i < swapSize; i++){
            if (swapMemory.get(i) == null){
                // Adiciona valor na memória swap
                swapMemory.set(i, physicalMemory.get(page.getPageTable()));
                System.out.println("Salvando o valor " + physicalMemory.get(page.getPageTable())  + " no índice " + i + " do SWAP.");
                // Remove valor na memória física, atribuindo o valor nulo no lugar
                physicalMemory.set(page.getPageTable(), null);
                // Atualiza valor da pageTable na página da memória virtual
                page.setPageTable(i);
                // Atualiza isPresent para falso, indicando que o valor está armazenado na memória swap.
                page.setPresent(false);
                break;
            }
        }
    }

    @Override
    public void moveToPhysical(int value, int virtualIndex) {
        while(true){
            for (int i = 0; i < physicalSize; i++){
                if (physicalMemory.get(i) == null){
                    // Quando achar um espaço na memória física, armazena o valor neste índice
                    physicalMemory.set(i, value);
                    System.out.println(" valor " + value + " foi armazenado no índice " + i);
                    // Atualiza o índice em que o valor foi salvo no PageTable
                    virtualMemory.getVirtualMemory().get(virtualIndex).setPageTable(i);
                    // Atualiza o status isPresent, informando que o valor está salvo na memória física
                    virtualMemory.getVirtualMemory().get(virtualIndex).setPresent(true);
                    // Atualiza o status isReferenced, informando que o valor foi acessado recentemente
                    virtualMemory.getVirtualMemory().get(virtualIndex).setReferenced(true);
                    return;
                }
            }
        }
    }
}
