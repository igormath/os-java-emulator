package org.sistemasoperacionais;

public interface IOperationalSystem {
    public void read(int virtualIndex, int threadNumber);
    public void write(int virtualIndex, int value, int threadNumber);
}
