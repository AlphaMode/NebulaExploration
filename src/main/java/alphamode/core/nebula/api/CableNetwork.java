package alphamode.core.nebula.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for managing a network of cables
 */
public class CableNetwork {

    private Set<Node> nodes = new HashSet<>();

    public Set<Node> getNodes() {
        return nodes;
    }

    /**
     * @param node Adds a new node to the network
     */
    public void addNode(Node node) {
        nodes.add(node);
    }

    /**
     * @param node Removes a node from the network
     */
    public void removeNode(Node node) {
        nodes.remove(node);
    }

}
