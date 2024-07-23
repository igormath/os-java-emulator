package org.sistemasoperacionais;

public class Page {
    // Se ocorreu acesso recente
    private boolean isReferenced;
    // Se está alocada na memória física
    private boolean isPresent;
    // Endereço em que está salva
    private Integer pageTable;

    public Page(boolean isPresent, boolean isReferenced, Integer pageTable) {
        this.isPresent = isPresent;
        this.isReferenced = isReferenced;
        this.pageTable = pageTable;
    }

    public Page() {
        this.isReferenced = false;
        this.isPresent = false;
        this.pageTable = null;
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

    public Integer getPageTable() {
        return pageTable;
    }

    public void setPageTable(int pageTable) {
        this.pageTable = pageTable;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageTable=" + pageTable +
                ", present=" + isPresent +
                ", referenced=" + isReferenced +
                '}';
    }
}
