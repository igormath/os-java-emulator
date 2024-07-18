package org.sistemasoperacionais;

import java.util.ArrayList;
import java.util.List;

public class PhysicalAndSwapMemory {
    int physicalSize;
    int swapSize;
    private List<Integer> physicalMemory;
    private List<Integer> swapMemory;

    public PhysicalAndSwapMemory(int physicalSize, int swapSize) {
        this.physicalSize = physicalSize;
        this.swapSize = swapSize;
        physicalMemory = new ArrayList<>(physicalSize);
        swapMemory = new ArrayList<>(swapSize);

        // Inicializando valores das listas que representam a Memória Virtual e o SWAP
        for (int i = 0; i < swapSize; i++){
            swapMemory.add(null);
        }

        // Inicializando valores das listas que representam a Memória Física
        for (int i = 0; i < physicalSize; i++){
            physicalMemory.add(null);
        }
    }

    // pageTable == valor que está salvo
    public int moveToSwap(int value){
        int index = 0;
        for (int i = 0; i < swapMemory.size(); i++) {
            if (swapMemory.get(i) == null){
                swapMemory.add(i, value);
                index = i;
            }
        }
        return index;
    }

    // pageTable == valor que está salvo
    public int addToPhysical(int value){
        int index = -1, pointer = 0;
        do{
            // Loop que varre a memória física de forma circular até achar espaço.
            if (physicalMemory.get(pointer) == null){
                physicalMemory.add(pointer, value);
                index = pointer;
            }
            pointer = (pointer + 1) % physicalMemory.size();
        } while (index == - 1);
        return index;
    }

    public int moveFromSwapToPhysical(int swapIndex){
        int value = swapMemory.get(swapIndex);
        int index = -1, pointer = 0;
        do{
            if (physicalMemory.get(pointer) == null){
                physicalMemory.add(pointer, value);
                swapMemory.add(swapIndex, null);
                index = pointer;
            }
            pointer = (pointer + 1) % physicalMemory.size();
        } while (index == - 1);
        return index;
    }

    public List<Integer> getPhysicalMemory() {
        return physicalMemory;
    }

    public void setPhysicalMemory(List<Integer> physicalMemory) {
        this.physicalMemory = physicalMemory;
    }

    public List<Integer> getSwapMemory() {
        return swapMemory;
    }

    public void setSwapMemory(List<Integer> swapMemory) {
        this.swapMemory = swapMemory;
    }
}

