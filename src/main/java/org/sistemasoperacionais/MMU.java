package org.sistemasoperacionais;

public class MMU implements IMMU{

    private SecondChanceAlgorithm algorithm;
    private VirtualMemory virtualMemory;
    private PhysicalMemory physicalMemory;
    private SwapMemory swapMemory;

    public MMU(PhysicalMemory physicalMemory, SwapMemory swapMemory, VirtualMemory virtualMemory) {
        this.physicalMemory = physicalMemory;
        this.swapMemory = swapMemory;
        this.virtualMemory = virtualMemory;
        algorithm = new SecondChanceAlgorithm(virtualMemory, physicalMemory, swapMemory);
    }

    // Função que irá escrever o valor pedido no índice virtual informado.
    public void write(int virtualIndex, int value, int threadId){
        Integer freeIndexPhysical = physicalMemory.getFreeIndexPhysical();
        // Checa se existe algum índice livre na memória física.
        if (freeIndexPhysical != null){
            // Se existir, cria um espaço no índice da memória virtual informado salvando a página virtual criada com os dados
            virtualMemory.setVirtualMemoryPage(new Page(true, true, freeIndexPhysical), virtualIndex);
            // Salva o valor no endereço livre na memória física.
            physicalMemory.setValuePhysical(physicalMemory.getFreeIndexPhysical(), value);
            algorithm.addFifoOrder(virtualIndex);
            System.out.println("A thread " + threadId + " salvou o valor " + value);
        } else {
            //
            //
            // Como comportar-se? Executar novamente o algoritmo de segunda chance?
            //
            //
            while (freeIndexPhysical == null) {

                try {
                    // Aguarda 1s antes de checar novamente se existe um espaço livre
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                freeIndexPhysical = physicalMemory.getFreeIndexPhysical();
            }

            // Código repetido, alterar.
            // Se existir, cria um espaço no índice da memória virtual informado salvando a página virtual criada com os dados
            virtualMemory.setVirtualMemoryPage(new Page(true, true, freeIndexPhysical), virtualIndex);
            // Salva o valor no endereço livre na memória física.
            physicalMemory.setValuePhysical(physicalMemory.getFreeIndexPhysical(), value);
            algorithm.addFifoOrder(virtualIndex);
            System.out.println("A thread " + threadId + " salvou o valor " + value);
        }
    }

    public void read(int virtualIndex, int threadId){
        Page page = virtualMemory.getVirtualMemoryPage(virtualIndex);
        // Se o valor que eu quiser ler estiver presente (salvo na memória física)
        if (page.isPresent()){
            Integer value = physicalMemory.getValuePhysical(page.getPageTable());
            page.setReferenced(true);
            System.out.println("A thread " + threadId + " leu o valor " + value + " do endereço " + page.getPageTable());
        // Caso não esteja salvo na memória física, eu precisarei retomá-lo do swap e adicioná-lo na memória física.
        } else {
            Integer freeIndexPhysical = physicalMemory.getFreeIndexPhysical();
            // Se existir espaço livre na memória física para salvar o valor vindo da swap.
            if (freeIndexPhysical != null){
                // Recupera o valor que está salvo na memória SWAP
                Integer value = swapMemory.getValueSwap(page.getPageTable());
                // Apaga o valor da SWAP
                swapMemory.setValueSwap(page.getPageTable(), null);
                // Adiciona o valor no campo vazio encontrado na memória física
                physicalMemory.setValuePhysical(freeIndexPhysical, value);
                // Atualiza a página com a nova referência do endereço na memória física
                page.setPageTable(freeIndexPhysical);
                // Atualiza o valor de present, indicando que está salvo na memória física
                page.setPresent(true);
                // Atualiza o valor de referenced, indicando que foi acessado recentemente.
                page.setReferenced(true);
            } else {
                // Espera o algoritmo de segunda chance achar um espaço na memória física
                while (freeIndexPhysical == null) {

                    try {
                        // Aguarda 1s antes de checar novamente se existe um espaço livre
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    freeIndexPhysical = physicalMemory.getFreeIndexPhysical();
                }

                //
                //
                // CÓDIGO REPETIDO REFATORAR
                //
                //

                // Assumindo que existe valor livre na memória física, repete os passos anteriores:
                // Recupera o valor que está salvo na memória SWAP
                Integer value = swapMemory.getValueSwap(page.getPageTable());
                // Apaga o valor da SWAP
                swapMemory.setValueSwap(page.getPageTable(), null);
                // Adiciona o valor no campo vazio encontrado na memória física
                physicalMemory.setValuePhysical(freeIndexPhysical, value);
                // Atualiza a página com a nova referência do endereço na memória física
                page.setPageTable(freeIndexPhysical);
                // Atualiza o valor de present, indicando que está salvo na memória física
                page.setPresent(true);
                // Atualiza o valor de referenced, indicando que foi acessado recentemente.
                page.setReferenced(true);

            }
        }
    }
}
