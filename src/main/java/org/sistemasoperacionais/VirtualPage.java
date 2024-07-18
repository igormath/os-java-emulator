package org.sistemasoperacionais;

public class VirtualPage {
    // Se ocorreu acesso recente
    private boolean isReferenced;
    // Se está alocada na memória física
    private boolean isPresent;
    // Endereço em que está salva
    private int pageTable;

    public VirtualPage(boolean isReferenced, boolean isPresent, int pageTable) {
        this.isReferenced = isReferenced;
        this.isPresent = isPresent;
        this.pageTable = pageTable;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public boolean isReferenced() {
        return isReferenced;
    }

    public void setReferenced(boolean referenced) {
        isReferenced = referenced;
    }

    public int getPageTable() {
        return pageTable;
    }

    public void setPageTable(int pageTable) {
        this.pageTable = pageTable;
    }
}
