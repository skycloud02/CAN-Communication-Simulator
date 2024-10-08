package mvc.model;

import java.util.ArrayList;
import java.util.List;

public class CANBus {

    private List<CANNode> nodes;

    public CANBus(){
        this.nodes = new ArrayList<>();
    }

    public CANMessage performArbitration() {
        CANMessage arbitration = null;

        if (!nodes.isEmpty()) {
            for (CANNode node : nodes) {
                CANMessage queuedMessage = node.queueArbitration();

                if (queuedMessage != null &&
                        (arbitration == null ||
                                queuedMessage.getMessageID().compareTo(arbitration.getMessageID()) < 0)) {
                    arbitration = queuedMessage;
                }
            }
            if (arbitration != null) {
                for (CANNode node : nodes) {
                    if (node.hasMessage(arbitration)) {
                        node.removeMessage(arbitration);
                        break;
                    }
                }
            }
        }

        return arbitration;
    }


    public List<CANNode> getNodes() {
        return new ArrayList<>(nodes);
    }
    public void addNode(CANNode node){
        nodes.add(node);
    }

    public void removeNode(CANNode node){
        nodes.remove(node);
    }
}
