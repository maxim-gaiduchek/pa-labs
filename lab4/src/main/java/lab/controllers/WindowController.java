package lab.controllers;

import javax.swing.*;

public class WindowController {

    private JFrame lastFrame;
    private JPanel panel;

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public void showPanel() {
        if (lastFrame != null) {
            lastFrame.setVisible(false);
        }
        lastFrame = new JFrame();
        lastFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lastFrame.add(panel);
        lastFrame.setSize(panel.getSize());
        panel.setVisible(true);
        lastFrame.setVisible(true);
    }
}
