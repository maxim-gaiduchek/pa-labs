package lab.views;

import javax.swing.*;
import java.awt.*;

public class ProcessPanel extends JPanel {

    public ProcessPanel() {
        super();

        setSize(300, 100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font(null, Font.PLAIN, 16));
        g2d.drawString("Йде процес розфарбовки. Чекайте...", 10, 30);
    }
}
