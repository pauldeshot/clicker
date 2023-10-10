package ClickerBot.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class DotAlert {
    public Color color;
    public JWindow window;

    public int x = 0;

    public int y = 0;
    public int dotSize = 20;
    public DotAlert() {
        this.color = new Color(255, 0, 0, 255);

        SwingUtilities.invokeLater(() -> {
            window = new JWindow();
            window.setSize(45, 700);
            window.setAlwaysOnTop(true);
            window.setIgnoreRepaint(true);
            window.setVisible(true);
            window.createBufferStrategy(2);

            BufferStrategy bufferStrategy = window.getBufferStrategy();
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    while (true) {
                        do {
                            do {
                                Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
                                g.setColor(color);
                                g.fillRect(0, 0, 45, 700);
                            } while (bufferStrategy.contentsRestored());
                            bufferStrategy.show();
                        } while (bufferStrategy.contentsLost());
                    }
                }
            };

            worker.execute();
        });

    }

    public void red() {
        this.color = new Color(255, 0, 0, 255);
        this.dotSize = 20;

    }

    public void green() {
        this.color = new Color(0, 255, 0, 255);
        this.dotSize = 20;
    }

    public void yellow() {
        this.color = new Color(255, 255, 0, 255);
        this.dotSize = 150;
    }
}
