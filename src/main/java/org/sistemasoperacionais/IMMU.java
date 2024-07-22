package org.sistemasoperacionais;

public interface IMMU {
    public void read(int virtualIndex, int threadId);
    public void write(int virtualIndex, int value, int threadId);
}
