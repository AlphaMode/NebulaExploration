package alphamode.core.nebula.blocks;

import java.util.Map;

public interface ICable<T> {
    Map<String,T> get();
    void add(T value);
    void add(String id,T value);
    void setParent(ICable parent);
    boolean isParent();
}
