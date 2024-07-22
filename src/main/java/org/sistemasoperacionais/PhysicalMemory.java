package org.sistemasoperacionais;

public class PhysicalMemory {
    private Integer[] physicalValues;
    private int physicalMemorySize;

    public PhysicalMemory(int physicalMemorySize) {
        this.physicalMemorySize = physicalMemorySize;
        physicalValues = new Integer[physicalMemorySize];
    }

    public Integer[] getPhysicalMemory() {
        return physicalValues;
    }

    public Integer getValuePhysical(int indexPhysical){
        return physicalValues[indexPhysical];
    }

    public void setValuePhysical(int indexPhysical, Integer value){
        physicalValues[indexPhysical] = value;
    }

    public Integer getFreeIndexPhysical(){
        for (int i = 0; i < physicalMemorySize; i++){
            if (physicalValues[i] == null){
                return i;
            }
        }
        return null;
    }
}
