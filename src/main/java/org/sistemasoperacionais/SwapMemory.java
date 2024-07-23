package org.sistemasoperacionais;

public class SwapMemory {
    private Integer[] swapValues;
    private int swapMemorySize;

    public SwapMemory(int swapMemorySize) {
        this.swapMemorySize = swapMemorySize;
        swapValues = new Integer[swapMemorySize];
    }

    public Integer[] getSwapMemory() {
        return swapValues;
    }

    public Integer getValueSwap(int indexSwap){
        return swapValues[indexSwap];
    }

    public void setValueSwap(int indexSwap, Integer value){
        swapValues[indexSwap] = value;
    }

    public Integer getFreeIndexSwap(){
        for (int i = 0; i < swapMemorySize; i++){
            if (swapValues[i] == null){
                return i;
            }
        }
        return null;
    }
}
