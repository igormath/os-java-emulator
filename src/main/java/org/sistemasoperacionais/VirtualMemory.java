package org.sistemasoperacionais;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VirtualMemory {
    private List<VirtualPage> virtualMemory;

    public VirtualMemory(int virtualSize) {
        virtualMemory = Collections.synchronizedList(new ArrayList<>(virtualSize));

        for (int i = 0; i < virtualSize; i++){
            virtualMemory.add(null);
        }
    }

    public List<VirtualPage> getVirtualMemory() {
        return virtualMemory;
    }
}
