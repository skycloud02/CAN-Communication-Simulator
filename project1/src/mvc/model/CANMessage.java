package mvc.model;

public class CANMessage {

    private String messageID;
    private String dataContent;
    private FrameType frameType;

    public CANMessage(String messageID, String dataContent, FrameType frameType) {
        this.messageID = messageID;
        this.dataContent = dataContent;
        this.frameType = frameType;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public FrameType getFrameType() {
        return frameType;
    }

    public void setFrameType(FrameType frameType) {
        this.frameType = frameType;
    }
}
