package org.sistemasoperacionais;

public interface IAccess {
    public void readFromPhysical(int index);
    public void readFromSwap(int virtualIndex);
    public void moveToSwap(int virtualIndex);
    public void moveToPhysical(int value, int virtualIndex);
}
