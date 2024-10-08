package mvc;

import mvc.controller.MainController;
import mvc.view.MainFrame;

public class CANSimulatorApp {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        MainController mainController = new MainController(mainFrame);
    }
}
