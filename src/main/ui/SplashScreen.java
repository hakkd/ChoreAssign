package ui;

import javax.swing.*;
import java.awt.*;

// represents a splashscreen to be displayed when the GUI is opening
// code adapted from https://www.tutorialsfield.com/java-splash-screen-with-progress-bar/
public class SplashScreen {
    private JFrame splashScreen;
    private final JLabel title = new JLabel("ChoreAssign");
    private final JLabel author = new JLabel("Elmeri Hakkinen, 2023");
    private final JProgressBar progressBar = new JProgressBar();
    private final JLabel progressText = new JLabel();
    private final JLabel image = new JLabel(new ImageIcon("data/noun-mop-3446522.png")); // source: https://thenounproject.com/
    private static final int HEIGHT = 400;
    private static final int WIDTH = 600;

    public SplashScreen() {
        createFrame();
        addImage();
        addTitle();
        addAuthor();
        addProgressText();
        addProgressBar();
        runningPBar();
    }

    // MODIFIES: this, splashScreen
    // EFFECTS: initializes the JFrame on which the splash screen will be built
    private void createFrame() {
        splashScreen = new JFrame();
        splashScreen.getContentPane().setLayout(null);
        splashScreen.setUndecorated(true);
        splashScreen.setSize(WIDTH,HEIGHT);
        splashScreen.setLocationRelativeTo(null);
        splashScreen.getContentPane().setBackground(Color.white);
        splashScreen.setVisible(true);
    }

    // MODIFIES: this, image
    // EFFECTS: adds image to splashScreen
    private void addImage() {
        image.setBounds(WIDTH / 2 - 160, HEIGHT / 4 - 160, 320, 320);
        splashScreen.add(image);
    }

    // MODIFIES: this, title
    // EFFECTS: adds title to splashScreen
    public void addTitle() {
        title.setFont(new Font("monospaced",Font.BOLD,30));
        title.setBounds(WIDTH / 2 - 100,220,600,40);
        title.setForeground(Color.BLACK);
        splashScreen.add(title);
    }

    // MODIFIES: this, author
    // EFFECTS: adds author to splashScreen
    public void addAuthor() {
        author.setFont(new Font("arial",Font.PLAIN,15));
        author.setBounds(WIDTH - 170,HEIGHT - 40,200,40);
        author.setForeground(Color.BLACK);
        splashScreen.add(author);
    }

    // MODIFIES: this, progress
    // EFFECTS: adds loading percentage to progress bar
    public void addProgressText() {
        progressText.setBounds(250,320,200,50);//Setting the size and location of the label
        progressText.setForeground(Color.black);//Setting foreground Color
        progressText.setFont(new Font("monospaced",Font.BOLD,15));//Setting font properties
        splashScreen.add(progressText);//adding label to the frame
    }

    // MODIFIES: this, progressBar
    // EFFECTS:
    public void addProgressBar() {
        progressBar.setBounds(100,280,400,50);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(false);
        progressBar.setBackground(Color.WHITE);
        progressBar.setForeground(Color.BLACK);
        progressBar.setValue(0);
        splashScreen.add(progressBar);
    }

    // MODIFIES: this, progressBar
    // EFFECTS: shows loading progress
    public void runningPBar() {
        int i = 0;

        while (i <= 100) {
            try {
                Thread.sleep(50);
                progressBar.setValue(i);
                progressText.setText("Loading " + i + "%");
                i++;
                if (i == 0) {
                    splashScreen.dispose();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
