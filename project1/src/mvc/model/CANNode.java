package mvc.model;

import java.util.LinkedList;
import java.util.Queue;

public class CANNode {
    private int nodeID;
    private int messageRate;
    private int errorCount;
    private Queue<CANMessage> messageQueue;

    public CANNode(int nodeID, int messageRate) {
        this.nodeID = nodeID;
        this.messageRate = messageRate;
        this.errorCount = 0;
        this.messageQueue = new LinkedList<>();
    }

    public void addMessageToQueue(CANMessage message)
    {
        messageQueue.add(message);
    }

    public void removeMessageFromQueue(CANMessage message){
        messageQueue.remove(message);
    }

    public CANMessage queueArbitration(){
        if(messageQueue.isEmpty())
            return null;

        CANMessage highestPriorityMessage = messageQueue.peek();

        for(CANMessage message : messageQueue){
            if(message.getMessageID().compareTo(highestPriorityMessage.getMessageID()) < 0)
                highestPriorityMessage = message;
        }

        return highestPriorityMessage;
    }

    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    public int getMessageRate() {
        return messageRate;
    }

    public void setMessageRate(int messageRate) {
        this.messageRate = messageRate;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public boolean hasMessage(CANMessage message) {
        return messageQueue.contains(message);
    }

    public void removeMessage(CANMessage message) {
        messageQueue.remove(message);
    }

    public Queue<CANMessage> getMessageQueue() {
        return messageQueue;
    }

    public void setMessageQueue(Queue<CANMessage> messageQueue) {
        this.messageQueue = messageQueue;
    }
}
