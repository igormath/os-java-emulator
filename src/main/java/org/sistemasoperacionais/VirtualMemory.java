package org.sistemasoperacionais;

import java.util.ArrayList;
import java.util.List;

public class VirtualMemory {
    private List<Page> virtualMemory;
    private int virtualMemorySize;
    private IAccess access;

    public VirtualMemory(int virtualMemorySize) {
        this.virtualMemorySize = virtualMemorySize;
        virtualMemory = new ArrayList<>(virtualMemorySize);
        for (int i = 0; i < virtualMemorySize; i++){
            virtualMemory.add(null);
        }
    }

    public void writeToVirtualMemory(int index, int value){
        if (virtualMemory.get(index) == null){
            if (index <= virtualMemorySize - 1){
                virtualMemory.set(index, new Page(true, true, index));
                System.out.println(" fez uma operação de escrita do valor " + value + " na página virtual do índice " + index);
            }
        } else {
            System.out.println(" fez uma operação de escrita no índice " + index + " da memória virtual, porém este está ocupado.");
        }
    }

    public void readFromVirtualMemory(int index){
        Page page = virtualMemory.get(index);
        if (page != null){
            if (page.isPresent()){
                access.readFromPhysical(index);
            }else{
                access.readFromSwap(index);
            }
        } else {
            System.out.println(" fez uma operação de leitura na página virtual de índice " + index + ", porém este está vazio.");
        }
    }

    public List<Page> getVirtualMemory() {
        return virtualMemory;
    }

    public void setVirtualMemory(List<Page> virtualMemory) {
        this.virtualMemory = virtualMemory;
    }

    public void setAccess(IAccess access) {
        this.access = access;
    }
}
