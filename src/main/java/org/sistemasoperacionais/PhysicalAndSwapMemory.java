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
        for (int i = 0; i < swapMemory.size(); i++) {
            if (swapMemory.get(i) == null) {
                swapMemory.set(i, value);
                System.out.println("O valor " + value + " foi movido para o índice " + i + " da memória SWAP");
                return i;
            }
        }
        return -1; // Indica que a memória SWAP está cheia
    }

    // pageTable == valor que está salvo
    public int addToPhysical(int value){
        for (int i = 0; i < physicalMemory.size(); i++) {
            if (physicalMemory.get(i) == null) {
                physicalMemory.set(i, value);
                return i;
            }
        }
        return -1; // Indica que a memória física está cheia
    }

    public int moveFromSwapToPhysical(int swapIndex){
        int value = swapMemory.get(swapIndex);
        for (int i = 0; i < physicalMemory.size(); i++) {
            if (physicalMemory.get(i) == null) {
                physicalMemory.set(i, value);
                swapMemory.set(swapIndex, null);
                System.out.println("O valor " + value + " que estava no SWAP foi movido para a memória física.");
                return i;
            }
        }
        return -1; // Indica que a memória física está cheia
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

