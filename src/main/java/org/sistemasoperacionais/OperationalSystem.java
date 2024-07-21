package org.sistemasoperacionais;

public class OperationalSystem {
    private static final int VIRTUAL_MEMORY_SIZE = 8;
    private static final int SWAP_MEMORY_SIZE = 8;
    private static final int PHYSICAL_MEMORY_SIZE = 4;
    private static final int THREAD_SLEEP_SECONDS = 3;

    private VirtualMemory virtualMemory;
    private PhysicalAndSwapMemory physicalAndSwapMemory;
    private SecondChanceAlgorithm secondChanceAlgorithm;

    public OperationalSystem() {
        this.virtualMemory = new VirtualMemory(VIRTUAL_MEMORY_SIZE);
        this.physicalAndSwapMemory = new PhysicalAndSwapMemory(PHYSICAL_MEMORY_SIZE, SWAP_MEMORY_SIZE);
        this.secondChanceAlgorithm = new SecondChanceAlgorithm(physicalAndSwapMemory, THREAD_SLEEP_SECONDS, virtualMemory);

        // Iniciar a thread do algoritmo de Segunda Chance
    }

    public void startSecondChanceAlgorithm(){
        secondChanceAlgorithm.start();
    }

    public void read(int threadNumber, int virtualIndex){
         VirtualPage virtualPage = virtualMemory.getVirtualMemory().get(virtualIndex);
         // Checa se o índice existe no Array que representa a memória virtual
         if (virtualMemory.getVirtualMemory().get(virtualIndex) != null){
             System.out.print("A thread " + threadNumber + " ");
             System.out.print(" acessou o endereço " + virtualPage.getPageTable());
             if (virtualPage.isPresent()){
                 // Se o valor estiver salvo na memória física
                 System.out.println(" que possui o valor " + physicalAndSwapMemory.getPhysicalMemory().get(virtualPage.getPageTable()) + " na memória física.");
             } else {
                 // Se não estiver, temos que mover o valor da swap para a memória física.
                 int physicalIndex = physicalAndSwapMemory.moveFromSwapToPhysical(virtualPage.getPageTable());
                 System.out.print(" que possui o valor " + physicalAndSwapMemory.getPhysicalMemory().get(physicalIndex) + " na memória física.");
                 // Após movermos o valor, altera-se a página virtual para que aponte para o endereço na memória física
                 virtualPage.setPresent(true);
                 virtualPage.setReferenced(true);
                 virtualPage.setPageTable(physicalIndex);
             }
         } else if (virtualMemory.getVirtualMemory().get(virtualIndex) == null) {
             System.out.println("A thread " + threadNumber + " acessou o endereço " + virtualIndex + " da memória virtual, porém este está vazio.");
         }
    }

    public void write(int threadNumber, int value){
        boolean wrote = false;
        for (int i = 0; i < virtualMemory.getVirtualMemory().size(); i++) {
            VirtualPage virtualPage = virtualMemory.getVirtualMemory().get(i);
            if (virtualPage == null) {
                virtualPage = new VirtualPage(true, true, physicalAndSwapMemory.addToPhysical(value));
                virtualMemory.getVirtualMemory().set(i, virtualPage);
                wrote = true;
                System.out.println("A thread " + threadNumber + " escreveu o valor " + value + " no endereço " + i);
                break;
            }
        }
        if (!wrote) {
            System.out.println("A thread " + threadNumber + " não conseguiu escrever o valor " + value + " porque a memória virtual está cheia.");
        }
    }
}

