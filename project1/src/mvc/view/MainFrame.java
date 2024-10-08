package mvc.view;

import mvc.model.FrameType;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Component;

public class MainFrame extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldNodeId;
    private JTextField textFieldMessageRate;
    private JTextField textFieldMessageId;
    private JTextField textFieldDataContent;
    private JTable nodeTable;
    private DefaultTableModel nodeTableModel;
    private JTextArea textAreaLogging;
    private JTextArea textAreaMonitor;
    private JLabel lblControl;
    private JButton btnStart;
    private JButton btnPause;
    private JButton btnStop;
    private JButton btnReset;
    private JLabel lblConfiguration;
    private JLabel lblNodeID;
    private JLabel lblMessageRate;
    private JButton btnAddNode;
    private JButton btnDeleteNode;
    private JLabel lblMessageID;
    private JLabel lblMessageContent;
    private JLabel lblFrameType;
    private JComboBox comboBoxFrameType;
    private JButton btnDeletemessage;
    private JScrollPane nodeScrollPane;
    private JLabel lblLogging;
    private JScrollPane scrollPane;
    private JLabel lblRealTimeMonitoring;
    private JScrollPane scrollPane1;


    public MainFrame() {
        setTitle("CAN Communication Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 956, 555);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(149, 205, 208));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblControl = new JLabel("Control");
        lblControl.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblControl.setBounds(10, 10, 73, 25);
        contentPane.add(lblControl);

        btnStart = new JButton("Start");
        btnStart.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnStart.setBounds(91, 12, 85, 21);
        contentPane.add(btnStart);

        btnPause = new JButton("Pause");
        btnPause.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnPause.setBounds(186, 12, 118, 21);
        contentPane.add(btnPause);

        btnStop = new JButton("Stop");
        btnStop.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnStop.setBounds(314, 12, 85, 21);
        contentPane.add(btnStop);

        btnReset = new JButton("Reset");
        btnReset.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnReset.setBounds(409, 12, 118, 21);
        contentPane.add(btnReset);

        lblConfiguration = new JLabel("Configuration");
        lblConfiguration.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblConfiguration.setBounds(10, 39, 129, 25);
        contentPane.add(lblConfiguration);

        lblNodeID = new JLabel("Node ID:");
        lblNodeID.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNodeID.setBounds(10, 76, 62, 19);
        contentPane.add(lblNodeID);

        textFieldNodeId = new JTextField();
        textFieldNodeId.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldNodeId.setColumns(10);
        textFieldNodeId.setBounds(82, 78, 47, 19);
        contentPane.add(textFieldNodeId);

        lblMessageRate = new JLabel("Message Rate:");
        lblMessageRate.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblMessageRate.setBounds(10, 105, 98, 19);
        contentPane.add(lblMessageRate);

        textFieldMessageRate = new JTextField();
        textFieldMessageRate.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldMessageRate.setColumns(10);
        textFieldMessageRate.setBounds(118, 105, 47, 19);
        contentPane.add(textFieldMessageRate);

        btnAddNode = new JButton("Add");
        btnAddNode.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnAddNode.setBounds(10, 132, 62, 21);
        contentPane.add(btnAddNode);

        btnDeleteNode = new JButton("Delete");
        btnDeleteNode.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnDeleteNode.setBounds(10, 161, 98, 21);
        contentPane.add(btnDeleteNode);

        lblMessageID = new JLabel("Message ID:");
        lblMessageID.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblMessageID.setBounds(169, 74, 92, 19);
        contentPane.add(lblMessageID);

        textFieldMessageId = new JTextField();
        textFieldMessageId.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldMessageId.setColumns(10);
        textFieldMessageId.setBounds(268, 76, 136, 19);
        contentPane.add(textFieldMessageId);

        lblMessageContent = new JLabel("Data Content:");
        lblMessageContent.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblMessageContent.setBounds(169, 103, 98, 19);
        contentPane.add(lblMessageContent);

        textFieldDataContent = new JTextField();
        textFieldDataContent.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textFieldDataContent.setColumns(10);
        textFieldDataContent.setBounds(278, 105, 157, 19);
        contentPane.add(textFieldDataContent);

        lblFrameType = new JLabel("Frame Type:");
        lblFrameType.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblFrameType.setBounds(169, 132, 92, 19);
        contentPane.add(lblFrameType);

        FrameType[] frameTypeValues = new FrameType[FrameType.values().length + 1];
        frameTypeValues[0] = null;
        System.arraycopy(FrameType.values(), 0, frameTypeValues, 1, FrameType.values().length);

        comboBoxFrameType = new JComboBox(frameTypeValues);
        comboBoxFrameType.setBounds(268, 134, 167, 21);
        contentPane.add(comboBoxFrameType);

        btnDeletemessage = new JButton("Delete");
        btnDeletemessage.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnDeletemessage.setBounds(169, 161, 92, 21);
        contentPane.add(btnDeletemessage);

        nodeScrollPane = new JScrollPane();
        nodeScrollPane.setBounds(10, 188, 436, 160);
        contentPane.add(nodeScrollPane);

        nodeTable = new JTable();
        nodeScrollPane.setViewportView(nodeTable);
        nodeTableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Node ID", "Message ID"}
        );

        nodeTable.setModel(nodeTableModel);

        lblLogging = new JLabel("Logging Panel");
        lblLogging.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblLogging.setBackground(new Color(149, 205, 208));
        lblLogging.setBounds(10, 359, 132, 25);
        contentPane.add(lblLogging);

        textAreaLogging = new JTextArea();
        textAreaLogging.setBounds(10, 394, 436, 98);
        contentPane.add(textAreaLogging);
        textAreaLogging.setEditable(true);

        scrollPane = new JScrollPane(textAreaLogging);
        scrollPane.setBounds(10, 394, 436, 100);
        contentPane.add(scrollPane);

        lblRealTimeMonitoring = new JLabel("Real Time Monitoring Panel");
        lblRealTimeMonitoring.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblRealTimeMonitoring.setBackground(new Color(149, 205, 208));
        lblRealTimeMonitoring.setBounds(499, 49, 244, 25);
        contentPane.add(lblRealTimeMonitoring);

        textAreaMonitor = new JTextArea();
        textAreaMonitor.setBounds(499, 84, 417, 410);
        contentPane.add(textAreaMonitor);
        textAreaMonitor.setEditable(true);

        scrollPane1 = new JScrollPane(textAreaMonitor);
        scrollPane1.setBounds(499, 84, 417, 410);
        contentPane.add(scrollPane1);
    }
    public void appendText(String text) {
        textAreaLogging.append(text);
        textAreaLogging.append("\n");  // Add a newline for better readability
    }

    // Method to clear the JTextArea
    public void clearText() {
        textAreaLogging.setText("");
    }

    public void appendText1(String text) {
        textAreaMonitor.append(text);
        textAreaMonitor.append("\n");  // Add a newline for better readability
    }

    // Method to clear the JTextArea
    public void clearText1() {
        textAreaMonitor.setText("");
    }

    public JTextField getTextFieldNodeId() {
        return textFieldNodeId;
    }

    public void setTextFieldNodeId(String textFieldNodeId) {
        this.textFieldNodeId.setText(textFieldNodeId);
    }

    public JTextField getTextFieldMessageRate() {
        return textFieldMessageRate;
    }

    public void setTextFieldMessageRate(String textFieldMessageRate) {
        this.textFieldMessageRate.setText(textFieldMessageRate);
    }

    public JTextField getTextFieldMessageId() {
        return textFieldMessageId;
    }

    public void setTextFieldMessageId(String textFieldMessageId) {
        this.textFieldMessageId.setText(textFieldMessageId);
    }

    public JTextField getTextFieldDataContent() {
        return textFieldDataContent;
    }

    public void setTextFieldDataContent(String textFieldDataContent) {
        this.textFieldDataContent.setText(textFieldDataContent);
    }

    public JTable getNodeTable() {
        return nodeTable;
    }

    public void setNodeTable(JTable nodeTable) {
        this.nodeTable = nodeTable;
    }

    public JTextArea getTextAreaLogging() {
        return textAreaLogging;
    }

    public void setTextAreaLogging(String textAreaLogging) {
        this.textAreaLogging.setText(textAreaLogging);
    }

    public JTextArea getTextAreaMonitor() {
        return textAreaMonitor;
    }

    public void setTextAreaMonitor(String textAreaMonitor) {
        this.textAreaMonitor.setText(textAreaMonitor);
    }

    public JComboBox getComboBoxFrameType() {
        return comboBoxFrameType;
    }

    public void setComboBoxFrameType(JComboBox comboBoxFrameType) {
        this.comboBoxFrameType = comboBoxFrameType;
    }

    public JScrollPane getNodeScrollPane() {
        return nodeScrollPane;
    }

    public void setNodeScrollPane(JScrollPane nodeScrollPane) {
        this.nodeScrollPane = nodeScrollPane;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public JScrollPane getScrollPane1() {
        return scrollPane1;
    }

    public void setScrollPane1(JScrollPane scrollPane1) {
        this.scrollPane1 = scrollPane1;
    }

    public JButton getBtnStart() {
        return btnStart;
    }

    public void setBtnStart(JButton btnStart) {
        this.btnStart = btnStart;
    }

    public void addBtnBtnStartListener(ActionListener action) { btnStart.addActionListener(action);}

    public JButton getBtnPause() {
        return btnPause;
    }

    public void setBtnPause(JButton btnPause) {
        this.btnPause = btnPause;
    }

    public void addBtnBtnPauseListener(ActionListener action) { btnPause.addActionListener(action);}

    public JButton getBtnStop() {
        return btnStop;
    }

    public void setBtnStop(JButton btnStop) {
        this.btnStop = btnStop;
    }

    public void addBtnBtnStopListener(ActionListener action) { btnStop.addActionListener(action);}

    public JButton getBtnReset() {
        return btnReset;
    }

    public void setBtnReset(JButton btnReset) {
        this.btnReset = btnReset;
    }

    public void addBtnBtnResetListener(ActionListener action) { btnReset.addActionListener(action);}

    public JButton getBtnAddNode() {
        return btnAddNode;
    }

    public void setBtnAddNode(JButton btnAddNode) {
        this.btnAddNode = btnAddNode;
    }

    public void addBtnBtnAddNodeListener(ActionListener action) { btnAddNode.addActionListener(action);}

    public JButton getBtnDeleteNode() {
        return btnDeleteNode;
    }

    public void setBtnDeleteNode(JButton btnDeleteNode) {
        this.btnDeleteNode = btnDeleteNode;
    }

    public void addBtnBtnDeleteNodeListener(ActionListener action) { btnDeleteNode.addActionListener(action);}

    public JButton getBtnDeletemessage() {
        return btnDeletemessage;
    }

    public void setBtnDeletemessage(JButton btnDeletemessage) {
        this.btnDeletemessage = btnDeletemessage;
    }

    public void addBtnBtnDeletemessageListener(ActionListener action) { btnDeletemessage.addActionListener(action);}

    public void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }

    public DefaultTableModel getNodeTableModel() {
        return nodeTableModel;
    }

    public void setNodeTableModel(DefaultTableModel nodeTableModel) {
        this.nodeTableModel = nodeTableModel;
    }
}
