package alphamode.core.nebula.api;

public interface Node {
    void setParentReference(Node child);
    Node getParentReference();
    boolean isParent();
    void setParent(boolean bool);
    CableNetwork getNetwork();
    void setNetwork(CableNetwork network);
}
