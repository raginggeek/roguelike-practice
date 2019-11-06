package com.raginggeek.games.roguelikepractice.app;

import asciiPanel.AsciiPanel;
import com.raginggeek.games.roguelikepractice.screens.Screen;
import com.raginggeek.games.roguelikepractice.screens.StartScreen;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Component
public class AppFrame extends JFrame implements KeyListener {
    private static final long serialVersionUID = 1060623638149583738L;

    private AsciiPanel terminal;
    private Screen screen;

    public AppFrame() {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        terminal = new AsciiPanel();
        add(terminal);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AppFrame().setVisible(true);
            }
        });
    }


    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}
