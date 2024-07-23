package org.sistemasoperacionais;

public interface IMMU {
    public Integer read(int virtualIndex, int threadId);
    public Integer write(int virtualIndex, int value, int threadId);
}
