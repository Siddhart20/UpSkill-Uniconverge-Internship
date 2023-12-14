import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MPlayer extends JFrame implements ActionListener {

    private JTextField filePathField;
    private JButton playButton;
    private JButton pauseButton;
    private JButton chooseButton;
    private JButton loopButton;
    private boolean isPaused;
    private boolean isLooping = false;
    private JFileChooser fileChooser;
    private Clip clip;

    public MPlayer() {
        super("Music Player"); // Set the title of the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        filePathField = new JTextField(20);
        playButton = new JButton("Play"); // Enclose button text in quotes
        pauseButton = new JButton("Pause"); // Enclose button text in quotes
        chooseButton = new JButton("Choose File"); // Enclose button text in quotes
        loopButton = new JButton("Loop"); // Enclose button text in quotes
        isPaused = false;
        isLooping = false;

        playButton.addActionListener(this);
        pauseButton.addActionListener(this);
        chooseButton.addActionListener(this);
        loopButton.addActionListener(this);

        add(filePathField);
        add(chooseButton);
        add(playButton);
        add(pauseButton);
        add(loopButton);

        fileChooser = new JFileChooser(".");
        fileChooser.setFileFilter(new FileNameExtensionFilter("WAV Files", "wav"));

        setSize(500, 100);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == playButton) {
            playMusic();
        } else if (event.getSource() == pauseButton) {
            pauseMusic();
        } else if (event.getSource() == chooseButton) {
            chooseFile();
        } else if (event.getSource() == loopButton) {
            toggleLoop();
        }
    }

    private void playMusic() {

        if (clip != null && clip.isRunning()) {
            clip.stop();
        }

        try {
            File file = new File(filePathField.getText());
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);

            clip = AudioSystem.getClip();
            clip.open(audioIn);

            if (isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            clip.start();

        } catch (Exception e) {
            e.printStackTrace(); // Print the exception stack trace for debugging
        }

    }

    private void pauseMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPaused = true;
            pauseButton.setText("Resume"); // Enclose button text in quotes
        } else if (clip != null && isPaused) {
            clip.start();

            if (isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            isPaused = false;
            pauseButton.setText("Pause"); // Enclose button text in quotes
        }
    }

    private void chooseFile() {
        fileChooser.setCurrentDirectory(new File("."));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void toggleLoop() {
        isLooping = !isLooping;
        if (isLooping) {
            loopButton.setText("Stop Loop"); // Enclose button text in quotes

            if (clip.isRunning()) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } else {
            loopButton.setText("Loop"); // Enclose button text in quotes

            if (clip.isRunning()) {
                clip.loop(0);
            }
        }
    }

    public static void main(String[] args) {
        new MPlayer();
    }
}
