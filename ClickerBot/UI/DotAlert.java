package ClickerBot.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class DotAlert {
    public Color color;
    public JWindow window;
    public DotAlert() {
        this.color = new Color(255, 0, 0, 255);

        SwingUtilities.invokeLater(() -> {
            window = new JWindow();
            window.setSize(20, 20);
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
                                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                g.setColor(color);
                                int dotSize = 20;
                                int x = 0;
                                int y = 0;
                                g.fillOval(x, y, dotSize, dotSize);
                                g.dispose();
                            } while (bufferStrategy.contentsRestored());
                            bufferStrategy.show();
                        } while (bufferStrategy.contentsLost());
                    }
                }
            };

            worker.execute();
        });

    }

    public void hide() {
        window.setVisible(false);
    }

    public void show() {
        window.setVisible(true);
    }

    public void red() {
        this.color = new Color(255, 0, 0, 255);
    }

    public void green() {
        this.color = new Color(0, 255, 0, 255);
    }

    public void yellow() {
        this.color = new Color(255, 255, 0, 255);
    }
}
