package net.gabor7d2.pcbuilder.gui;


import net.gabor7d2.pcbuilder.gui.general.ScrollPane2D;
import net.gabor7d2.pcbuilder.gui.general.SmartScrollPane;

import javax.swing.*;
import java.awt.*;

public class TestRow extends ScrollPane2D.ScrollPane2DRow {

    private final SmartScrollPane scrollPane = new SmartScrollPane();

    public TestRow() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JTextArea("Twtweretetej34jcit34ojt34jctj34jtö3söt9sö4et jeötj5 tjo5 jtoi5jtt iojts 5jt 4ejt5os4jeto4jtojs5tjos45kto54t"));
        panel.add(new JTextArea("Twtweretetej34jcit34ojt34jctj34jtö3söt9sö4et jeötj5 tjo5 jtoi5jtt iojts 5jt 4ejt5os4jeto4jtojs5tjos45kto54t"));
        panel.add(new JTextArea("Twtweretetej34jcit34ojt34jctj34jtö3söt9sö4et jeötj5 tjo5 jtoi5jtt iojts 5jt 4ejt5os4jeto4jtojs5tjos45kto54t"));
        panel.add(new JTextArea("Twtweretetej34jcit34ojt34jctj34jtö3söt9sö4et jeötj5 tjo5 jtoi5jtt iojts 5jt 4ejt5os4jeto4jtojs5tjos45kto54t"));
        panel.add(new JTextArea("Twtweretetej34jcit34ojt34jctj34jtö3söt9sö4et jeötj5 tjo5 jtoi5jtt iojts 5jt 4ejt5os4jeto4jtojs5tjos45kto54t"));
        panel.add(new JTextArea("Twtweretetej34jcit34ojt34jctj34jtö3söt9sö4et jeötj5 tjo5 jtoi5jtt iojts 5jt 4ejt5os4jeto4jtojs5tjos45kto54t"));
        panel.add(new JTextArea("Twtweretetej34jcit34ojt34jctj34jtö3söt9sö4et jeötj5 tjo5 jtoi5jtt iojts 5jt 4ejt5os4jeto4jtojs5tjos45kto54t"));
        panel.add(new JTextArea("Twtweretetej34jcit34ojt34jctj34jtö3söt9sö4et jeötj5 tjo5 jtoi5jtt iojts 5jt 4ejt5os4jeto4jtojs5tjos45kto54t"));
        panel.add(new JTextArea("Twtweretetej34jcit34ojt34jctj34jtö3söt9sö4et jeötj5 tjo5 jtoi5jtt iojts 5jt 4ejt5os4jeto4jtojs5tjos45kto54t"));
        panel.add(new JTextArea("Twtweretetej34jcit34ojt34jctj34jtö3söt9sö4et jeötj5 tjo5 jtoi5jtt iojts 5jt 4ejt5os4jeto4jtojs5tjos45kto54t"));
        panel.add(new JTextArea("Twtweretetej34jcit34ojt34jctj34jtö3söt9sö4et jeötj5 tjo5 jtoi5jtt iojts 5jt 4ejt5os4jeto4jtojs5tjos45kto54t"));
        panel.add(new JTextArea("Twtweretetej34jcit34ojt34jctj34jtö3söt9sö4et jeötj5 tjo5 jtoi5jtt iojts 5jt 4ejt5os4jeto4jtojs5tjos45kto54t"));
        panel.add(new JTextArea("Twtweretetej34jcit34ojt34jctj34jtö3söt9sö4et jeötj5 tjo5 jtoi5jtt iojts 5jt 4ejt5os4jeto4jtojs5tjos45kto54t"));
        panel.add(new JTextArea("Twtweretetej34jcit34ojt34jctj34jtö3söt9sö4et jeötj5 tjo5 jtoi5jtt iojts 5jt 4ejt5os4jeto4jtojs5tjos45kto54t"));
        panel.add(new JTextArea("Twtweretetej34jcit34ojt34jctj34jtö3söt9sö4et jeötj5 tjo5 jtoi5jtt iojts 5jt 4ejt5os4jeto4jtojs5tjos45kto54t"));
        panel.add(new JTextArea("Twtweretetej34jcit34ojt34jctj34jtö3söt9sö4et jeötj5 tjo5 jtoi5jtt iojts 5jt 4ejt5os4jeto4jtojs5tjos45kto54t"));

        scrollPane.setViewportView(panel);
        add(scrollPane);
    }

    @Override
    public void placedInsideScrollPane(JScrollPane outerScrollPane) {
        scrollPane.setWheelScrollingEnabled(false);
        scrollPane.addMouseWheelListener(e -> {
            int wheelRotation = e.getWheelRotation() * 16;

            if ((e.isShiftDown() || !outerScrollPane.getVerticalScrollBar().isVisible()) && scrollPane.getHorizontalScrollBar().isVisible()) {
                scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getValue() + wheelRotation);
            } else {
                outerScrollPane.getVerticalScrollBar().setValue(outerScrollPane.getVerticalScrollBar().getValue() + wheelRotation);
            }
        });
    }
}
