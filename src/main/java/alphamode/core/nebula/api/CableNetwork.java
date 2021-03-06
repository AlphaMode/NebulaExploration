package alphamode.core.nebula.api;

import alphamode.core.nebula.gases.GasVolume;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for managing a network of cables
 */
public class CableNetwork {

    private int transferRate = 10;

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
        if(node.isParent()) {
            boolean parents = false;
            for(Node node1 : nodes) {
                if(node1.isParent()) {
                    parents = true;
                    break;
                }
            }
            if (!parents) {
                for(Node node1 : nodes) {
                    node1.setNetwork(null);
                }
            }
        }
        nodes.remove(node);
    }

    public List<GasVolume> attemptTransfer(List<GasVolume> gasVolumes) {
        for(GasVolume gas : gasVolumes) {
            //gas.getAmount() / gasVolumes.size();
        }


        return null;
    }

}
