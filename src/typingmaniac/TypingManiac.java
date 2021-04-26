/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typingmaniac;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import javax.imageio.ImageIO;

/**
 *
 * @author roblescw
 */
class TypingManiac {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new HomeScreen();
    }

}

class HomeScreen extends JFrame implements ActionListener {

    JButton btnStart2 = new JButton("Start");
    JButton btnExit = new JButton("Exit");
    JLabel lblTypM = new JLabel("  -Typing Maniac for Babies-");
    JPanel jpBg = new JPanel();
    String name;
    int score = 0, numberOfWordsLeft, lives = 3;

    HomeScreen() {
        super("");
        setContentPane(new JLabel(new ImageIcon("C:\\Users\\HP\\Desktop"
                + "\\Files(Programming)\\JavaProgramming\\TypingManiac"
                + "\\src\\typingmaniac\\with-font.png")));
        setSize(705, 525);
        jpBg.setLayout(new GridLayout(0, 2));
        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        lblTypM.setFont(new Font("Verdana", Font.BOLD, 30));
        btnStart2.addActionListener(this);
        btnExit.addActionListener(this);

        btnStart2.setBackground(Color.blue);
        btnStart2.setForeground(Color.white);
        btnExit.setBackground(Color.blue);
        btnExit.setForeground(Color.white);
        add(jpBg, BorderLayout.SOUTH);
        jpBg.add(btnStart2);
        jpBg.add(btnExit);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent click) {
        if (click.getSource() == btnStart2) {
            dispose();
            new GameScreen();
        } else if (click.getSource() == btnExit) {
            int reply = JOptionPane.showConfirmDialog(null, "Do you want to exit the game", "", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
            }
        }

    }

    class GameScreen extends JFrame {

        ArrayList<String> wordList;
        JButton btnStart = new JButton("Start");
        JButton btnQuit = new JButton("Quit");
        JPanel dp = new DrawPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JTextField tfTypingField = new JTextField(15);

        JLabel lblScore = new JLabel("Score: 0");

        JLabel lblNumOfWordsAndLives = new JLabel("#OfWords Left: " + " Lives: 3");

        WordRun word1 = null;
        WordRun word2 = null;
        WordRun word3 = null;

        GameScreen() {
            super("Typing Maniac for Babies");
            setSize(700, 500);
            setDefaultCloseOperation(3);
            setLocationRelativeTo(null);
            setResizable(true);
            tfTypingField.getDocument().addDocumentListener(new ListenText());
            dp.setBackground(Color.DARK_GRAY);
            p2.setLayout(new GridLayout(0, 2));
            p2.setBackground(Color.blue);
            p3.setLayout(new BorderLayout(150, 0));
            p3.setBackground(Color.blue);
            btnStart = new JButton("Start");
            btnStart.addActionListener(new ButtonHandler());
            btnQuit.addActionListener(new ButtonHandler());
            btnStart.setBackground(Color.BLUE);
            btnStart.setForeground(Color.white);
            btnQuit.setBackground(Color.BLUE);
            btnQuit.setForeground(Color.white);
            tfTypingField.setBackground(Color.darkGray);
            tfTypingField.setForeground(Color.white);
            setLayout(new BorderLayout());
            wordList = new ArrayList<String>();
            //Words
            String line;
            String filepath = "C:\\Users"
                    + "\\HP\\Desktop\\Files(Programming)"
                    + "\\JavaProgramming\\TypingManiac\\src"
                    + "\\typingmaniac\\words.txt";
            try {
                BufferedReader br = new BufferedReader(new FileReader(filepath));
                while ((line = br.readLine()) != null) {
                    wordList.add(line);
                }
                br.close();

            } catch (Exception e) {

            }

            //Adding Compinents(Default)
            add(dp, BorderLayout.CENTER);
            btnQuit.setEnabled(false);
            // Panel 2 north
            add(p2, BorderLayout.NORTH);
            p2.add(btnStart);
            p2.add(btnQuit);

            add(p3, BorderLayout.SOUTH);

            //Panel 2 South
            lblNumOfWordsAndLives.setForeground(Color.white);
            lblScore.setForeground(Color.white);
            p3.add(lblScore, BorderLayout.WEST);
            p3.add(tfTypingField, BorderLayout.CENTER);
            p3.add(lblNumOfWordsAndLives, BorderLayout.EAST);

            //Adding words
            word1 = new WordRun();
            word2 = new WordRun();
            word3 = new WordRun();
            //Setting every word into String
            word1.st = null;
            word2.st = null;
            word3.st = null;
            setVisible(true);

        }

        class DrawPanel extends JPanel {

            private BufferedImage image;

            public DrawPanel() {
                try {
                    image = ImageIO.read(new File("C:\\Users\\HP"
                            + "\\Desktop\\Files(Programming)"
                            + "\\JavaProgramming\\TypingManiac"
                            + "\\src\\typingmaniac\\VIRAY-BACKGROUND.png"));
                } catch (IOException ex) {
                }
            }

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g.drawImage(image, 0, 0, this);
                Font f = new Font("Bebas Neue", Font.BOLD, 14);

                FontMetrics fm = this.getFontMetrics(f);
                g2.setColor(Color.WHITE);
                g2.setFont(f);

                if (word1.st != null) {
                    g2.drawString(word1.st, word1.posx, word1.posy);
                }
                if (word2.st != null) {
                    g2.drawString(word2.st, word2.posx, word2.posy);
                }
                if (word3.st != null) {
                    g2.drawString(word3.st, word3.posx, word3.posy);
                }
            }

        }

        class WordRun implements Runnable {

            String st = null;
            int posx;
            int posy;

            @Override
            public void run() {
                try {
                    Random rand = null;
                    while (true) {
                        if ((st == null) || (posy == dp.getHeight())) {
                            rand = new Random();
                            Thread.currentThread().sleep(rand.nextInt(1000));

                            posy = 0;
                            do {
                                st = wordList.get(rand.nextInt(wordList.size()));
                                st = wordList.get(rand.nextInt(wordList.size()));
                                posx = rand.nextInt(dp.getWidth());
                            } while (posx > (dp.getWidth() - 100));

                            tfTypingField.setText("");
                            numberOfWordsLeft++;
                            lblNumOfWordsAndLives.setText("#OfWords Left: " + numberOfWordsLeft + " Lives: " + lives);
                        } else {
                            posy++;
                            if (score < 10) {
                                Thread.currentThread().sleep(20);
                            } else if (score >= 10) {
                                Thread.currentThread().sleep(15);
                            } else if (score >= 20) {
                                Thread.currentThread().sleep(10);
                            }
                            if (posy == dp.getHeight()) {
                                --lives;
                            }

                            clean();
                        }
                        repaint();

                    }
                } catch (Exception e) {
                }
            }

            private void clean() {
                if (lives == 0 && posy != dp.getHeight()) {
                    wordList = null;

                    word1.posy = posy;
                    word2.posy = posy;
                    word3.posy = posy;

                    word1.posy = posy;
                    word2.posy = posy;
                    word3.posy = posy;

                    lblNumOfWordsAndLives.setText("#OfWords Left: " + numberOfWordsLeft + " Lives: " + lives);
                    lives = 3;

                    JOptionPane.showMessageDialog(null, "Game over!!", "Game over!!", JOptionPane.WARNING_MESSAGE);

                    dispose();

                    int reply = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        new GameScreen();
                    } else if (reply == JOptionPane.NO_OPTION) {
                        new HomeScreen();
                    } else {
                        System.exit(0);
                    }

                }
                repaint();
            }

        }

        class ButtonHandler implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand() == "Start") {
                    Thread t1 = new Thread(word1);
                    Thread t2 = new Thread(word2);
                    Thread t3 = new Thread(word3);
                    t1.start();
                    t2.start();
                    t3.start();
                    btnQuit.setEnabled(true);
                    btnStart.setEnabled(false);
                }
                if (e.getActionCommand() == "Quit") {
                    btnQuit.setEnabled(false);
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        dispose();
                        new HomeScreen();
                    } else if (reply == JOptionPane.NO_OPTION) {
                        btnQuit.setEnabled(true);
                    }else {
                        System.exit(0);
                    }
                }
            }
        }

        class ListenText implements DocumentListener {

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                if (tfTypingField.getText().equals(word1.st)) {
                    --numberOfWordsLeft;
                    word1.st = null;
                    word1.posy = 0;
                    score += tfTypingField.getText().length();
                    lblScore.setText("Score: " + score);

                }
                if (tfTypingField.getText().equals(word2.st)) {
                    --numberOfWordsLeft;
                    word2.st = null;
                    word2.posy = 0;
                    score += tfTypingField.getText().length();
                    lblScore.setText("Score: " + score);

                }
                if (tfTypingField.getText().equals(word3.st)) {
                    --numberOfWordsLeft;
                    word3.st = null;
                    word3.posy = 0;
                    score += tfTypingField.getText().length();
                    lblScore.setText("Score: " + score);

                }
            }
        }
    }
}
