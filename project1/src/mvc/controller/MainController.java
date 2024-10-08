package mvc.controller;

import mvc.model.CANBus;
import mvc.model.CANMessage;
import mvc.model.CANNode;
import mvc.model.FrameType;
import mvc.view.MainFrame;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class MainController {

    private MainFrame mainFrame;
    private CANNode canNode;
    private CANMessage canMessage;
    private CANBus canBus;

    public MainController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.canBus = new CANBus();
        mainFrame.setVisible(true);
        mainFrame.addBtnBtnStartListener(new BtnStartListener());
        mainFrame.addBtnBtnPauseListener(new BtnPauseListener());
        mainFrame.addBtnBtnStopListener(new BtnStopListener());
        mainFrame.addBtnBtnResetListener(new BtnResetListener());
        mainFrame.addBtnBtnAddNodeListener(new BtnAddNodeListener());
        mainFrame.addBtnBtnDeleteNodeListener(new BtnDeleteNodeListener());
        mainFrame.addBtnBtnDeletemessageListener(new BtnDeletemessageListener());
    }

    class BtnStartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.appendText("Start the simulation");
            simulateCANBusOperation();
        }

        private void simulateCANBusOperation() {
            int time = 0;

            Queue<CANMessage> globalMessageQueue = new LinkedList<>();
            Map<CANMessage, Integer> messageSourceMap = new HashMap<>();

            int nodeId = 1;
            for (CANNode node : canBus.getNodes()) {
                for (CANMessage message : node.getMessageQueue()) {
                    globalMessageQueue.add(message);
                    messageSourceMap.put(message, nodeId);
                }
                nodeId++;
            }

            while (!globalMessageQueue.isEmpty()) {
                mainFrame.appendText1("Time Stamp: " + time + "\n");

                mainFrame.appendText1("Waiting Queue: ");
                for (CANMessage message : globalMessageQueue) {
                    mainFrame.appendText1(message.getMessageID() + " ");
                }
                mainFrame.appendText1("\n");

                CANMessage messageToSend = canBus.performArbitration();
                if (messageToSend != null) {
                    int sourceNodeId = messageSourceMap.get(messageToSend);

                    mainFrame.appendText1("Bus: Node " + sourceNodeId + " sends " + messageToSend.getMessageID() + "\n");

                    globalMessageQueue.remove(messageToSend);
                } else {
                    mainFrame.appendText1("No message to send\n");
                }

                time++;
            }

            mainFrame.repaint();
            mainFrame.revalidate();
        }

    }

    class BtnPauseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.appendText("Pause the simulation.");
        }
    }

    class BtnStopListener implements ActionListener {
        private volatile boolean stopRequested = false;

        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.appendText("Stop the simulation.");
            stopRequested = true;
        }
        public boolean isStopRequested() {
            return stopRequested;
        }
    }

    class BtnResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.setTextFieldNodeId("");
            mainFrame.setTextFieldMessageRate("");
            mainFrame.setTextFieldMessageId("");
            mainFrame.setTextFieldDataContent("");
            mainFrame.getComboBoxFrameType().setSelectedIndex(0);
            DefaultTableModel model = (DefaultTableModel) mainFrame.getNodeTable().getModel();
            model.setRowCount(0);
            canBus.getNodes().clear();
            mainFrame.clearText1();
            mainFrame.clearText();
        }
    }

    class BtnAddNodeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int nodeID = validatePositiveInteger(mainFrame.getTextFieldNodeId().getText(), "Node ID");
                int messageRate = validatePositiveInteger(mainFrame.getTextFieldMessageRate().getText(), "Message Rate");
                String messageID = validateMessageId(mainFrame.getTextFieldMessageId().getText());
                String dataContent = validateBinaryString(mainFrame.getTextFieldDataContent().getText(), "Data Content");
                FrameType frameType = (FrameType) mainFrame.getComboBoxFrameType().getSelectedItem();
                CANNode existingNode = findNodeById(nodeID);
                if (existingNode != null) {
                    canNode = existingNode;
                } else {
                    canNode = new CANNode(nodeID, messageRate);
                    canBus.addNode(canNode);
                }
                Object[] rowData = {nodeID, messageID};
                mainFrame.getNodeTableModel().addRow(rowData);
                mainFrame.setTextFieldNodeId("");
                mainFrame.setTextFieldMessageRate("");
                mainFrame.setTextFieldMessageId("");
                mainFrame.setTextFieldDataContent("");
                mainFrame.getComboBoxFrameType().setSelectedIndex(0);
                canMessage = new CANMessage(messageID, dataContent, frameType);
                canNode.addMessageToQueue(canMessage);
                printNodeList();
                mainFrame.appendText("Added a new node.");
            } catch (NumberFormatException | NoSuchElementException nfe) {
                mainFrame.showError("Wrong input");
            } catch (InputValidationException ex) {
                throw new RuntimeException(ex);
            }
        }

        private String validateMessageId(String messageId) throws InputValidationException {
            validateBinaryString(messageId, "Message ID");
            if (messageId.length() != 11) {
                throw new InputValidationException("Message ID must have exactly 11 digits.");
            }

            return messageId;
        }

        private int validatePositiveInteger(String input, String fieldName) throws InputValidationException {
            try {
                int value = Integer.parseInt(input);
                if (value <= 0) {
                    throw new InputValidationException(fieldName + " should be a positive integer.");
                }
                return value;
            } catch (NumberFormatException e) {
                throw new InputValidationException(fieldName + " should be a positive integer.");
            }
        }

        private String validateBinaryString(String input, String fieldName) throws InputValidationException {
            if (!input.matches("[01]+")) {
                throw new InputValidationException(fieldName + " should be a binary string (0s and 1s).");
            }
            return input;
        }

        private static class InputValidationException extends Exception {
            public InputValidationException(String message) {
                super(message);
            }
        }

        private CANNode findNodeById(int nodeId) {
            for (CANNode node : canBus.getNodes()) {
                if (node.getNodeID() == nodeId) {
                    return node;
                }
            }
            return null;
        }

        private void printNodeList() {
            System.out.println("List of Nodes:");
            for (CANNode node : canBus.getNodes()) {
                System.out.println("Node ID: " + node.getNodeID());
                System.out.println("Messages in Node:");
                for (CANMessage message : node.getMessageQueue()) {
                    System.out.println("- Message ID: " + message.getMessageID());
                }
                System.out.println("---------------");
            }
            System.out.println("===========================");
        }
    }

    class BtnDeleteNodeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int selectedRow = mainFrame.getNodeTable().getSelectedRow();
                if (selectedRow != -1) {
                    int nodeIdToDelete = (int) mainFrame.getNodeTableModel().getValueAt(selectedRow, 0);
                    removeRowsByNodeId(nodeIdToDelete);
                    removeNodeById(nodeIdToDelete);
                    mainFrame.setTextFieldNodeId("");
                    mainFrame.setTextFieldMessageRate("");
                    mainFrame.setTextFieldMessageId("");
                    mainFrame.setTextFieldDataContent("");
                    mainFrame.getComboBoxFrameType().setSelectedIndex(0);
                    printNodeList();
                    mainFrame.appendText("Deleted a node.");
                } else {
                    mainFrame.showError("Select a row to delete");
                }
            } catch (Exception ex) {
                mainFrame.showError("Error deleting node: " + ex.getMessage());
            }
        }

        private void removeRowsByNodeId(int nodeId) {
            DefaultTableModel model = mainFrame.getNodeTableModel();
            for (int i = model.getRowCount() - 1; i >= 0; i--) {
                if ((int) model.getValueAt(i, 0) == nodeId) {
                    model.removeRow(i);
                }
            }
        }

        private void removeNodeById(int nodeId) {
            List<CANNode> nodeList = new ArrayList<>(canBus.getNodes());
            CANNode nodeToRemove = null;
            for (CANNode node : nodeList) {
                if (node.getNodeID() == nodeId) {
                    nodeToRemove = node;
                    break;
                }
            }
            if (nodeToRemove != null) {
                canBus.removeNode(nodeToRemove);
            }
        }

        private void printNodeList() {
            System.out.println("List of Nodes:");
            for (CANNode node : canBus.getNodes()) {
                System.out.println("Node ID: " + node.getNodeID());
                System.out.println("Messages in Node:");
                for (CANMessage message : node.getMessageQueue()) {
                    System.out.println("- Message ID: " + message.getMessageID());
                }
                System.out.println("---------------");
            }
            System.out.println("===========================");
        }
    }

    class BtnDeletemessageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int selectedRow = mainFrame.getNodeTable().getSelectedRow();
                if (selectedRow != -1) {
                    String messageIdToDelete = (String) mainFrame.getNodeTableModel().getValueAt(selectedRow, 1);
                    removeMessageFromNodes(messageIdToDelete);
                } else {
                    mainFrame.showError("Select a row to delete");
                }
                printNodeList();
                mainFrame.appendText("Deleted a message.");
            } catch (Exception ex) {
                mainFrame.showError("Error deleting message: " + ex.getMessage());
            }
        }

        private void removeMessageFromNodes(String messageId) {
            List<CANNode> copyOfListCAN = new ArrayList<>(canBus.getNodes());
            for (CANNode node : copyOfListCAN) {
                List<CANMessage> messages = new ArrayList<>(node.getMessageQueue());
                for (CANMessage message : messages) {
                    if (message.getMessageID().equals(messageId)) {
                        node.removeMessageFromQueue(message);
                        if (node.getMessageQueue().isEmpty()) {
                            canBus.removeNode(node);
                        }
                        removeRowByNodeIdAndMessageId(node.getNodeID(), messageId);
                        break;
                    }
                }
            }
        }

        private void removeRowByNodeIdAndMessageId(int nodeId, String messageId) {
            DefaultTableModel model = mainFrame.getNodeTableModel();
            for (int i = model.getRowCount() - 1; i >= 0; i--) {
                int tableNodeId = (int) model.getValueAt(i, 0);
                String tableMessageId = (String) model.getValueAt(i, 1);
                if (tableNodeId == nodeId && tableMessageId.equals(messageId)) {
                    model.removeRow(i);
                    break;
                }
            }
        }

        private void printNodeList() {
            System.out.println("List of Nodes:");
            for (CANNode node : canBus.getNodes()) {
                System.out.println("Node ID: " + node.getNodeID());
                System.out.println("Messages in Node:");
                for (CANMessage message : node.getMessageQueue()) {
                    System.out.println("- Message ID: " + message.getMessageID());
                }
                System.out.println("---------------");
            }
            System.out.println("===========================");
        }
    }
}
