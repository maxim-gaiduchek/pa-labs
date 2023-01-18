package lab.controllers;

import javax.swing.*;

public class WindowController {

    private JPanel panel;

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public void showPanel() {
        if (panel != null) {
            panel.setVisible(false);
        }
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(panel.getSize());
        panel.setVisible(true);
        frame.setVisible(true);
    }
}
