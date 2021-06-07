package alphamode.core.nebula.api;

public interface Node {
    Node getNodeProvider();
    void setNodeProvider(Node provider);
    boolean isParent();
    void setParent(boolean bool);
    CableNetwork getNetwork();
    void setNetwork(CableNetwork network);
}
