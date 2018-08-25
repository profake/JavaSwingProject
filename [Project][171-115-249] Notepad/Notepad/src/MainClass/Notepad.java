package MainClass;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class Notepad extends JFrame {

    //data
    JTextArea textBox;
    JTextField fileNameOpen, fileNameSave;
    JLabel jFileNameOpen, jFileNameSave;
    JPanel mainPanel;
    JPanel textPanel, buttonPanel;
    JButton newButton, saveButton, openButton;

    public Notepad() {
        //making everything
        this.setTitle("Notepad");

        jFileNameOpen = new JLabel("Name: ");
        jFileNameSave = new JLabel("Name: ");
        fileNameOpen = new JTextField(10);
        fileNameSave = new JTextField(10);
        textBox = new JTextArea();
        mainPanel = new JPanel();
        textPanel = new JPanel();
        buttonPanel = new JPanel();
        newButton = new JButton("New");
        saveButton = new JButton("Save");
        openButton = new JButton("Open");

        //layouts
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        textPanel.setLayout(new BorderLayout());

        //adding to textPanel
        textPanel.add(textBox);
        textPanel.add(new JScrollPane(textBox));

        //adding to top button panel
        buttonPanel.add(newButton);

        buttonPanel.add(jFileNameOpen);
        buttonPanel.add(fileNameOpen);
        buttonPanel.add(openButton);

        buttonPanel.add(jFileNameSave);
        buttonPanel.add(fileNameSave);
        buttonPanel.add(saveButton);

        //adding panels to main
        mainPanel.add(buttonPanel);
        mainPanel.add(textPanel);

        //button actions
        saveButton.addActionListener((ActionEvent ae) -> {
            try {
                save();
            } catch (IOException ex) {
                Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        openButton.addActionListener((ActionEvent ae) -> {
            try {
                open();
            } catch (IOException ex) {
                Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        newButton.addActionListener((ActionEvent ae) -> {
            newFile();
        });

        //final
        add(mainPanel);
        setSize(640, 480);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    //functions
    private boolean TextBoxIsEmpty() {
        String string = textBox.getText();
        String empty = new String();
        if (string.equals(empty)) {
            return true;
        }
        return false;
    }

    private void newFile() {
        String string = textBox.getText();
        String empty = new String();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        if (!TextBoxIsEmpty()) {
            int dialogResult = JOptionPane.showConfirmDialog(null, "File not empty. Discard anyway?", "Warning", dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
                textBox.setText("");
                fileNameOpen.setText("");
                setTitle("Notepad");
            }
        } else {
            fileNameOpen.setText("");
            setTitle("Notepad");
        }
    }

    private void save() throws IOException {
        String string = textBox.getText();
        String fileN = fileNameSave.getText();
        String empty = new String();
        if (fileN.equals(empty)) {
            JOptionPane.showMessageDialog(null, "Enter file name to save!", "Error ", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        File file = new File(fileN + ".txt");
        try (
                BufferedReader reader = new BufferedReader(new StringReader(string));
                PrintWriter writer = new PrintWriter(new FileWriter(file));) {
            reader.lines().forEach(line -> writer.println(line));
        }
        setTitle("Notepad - " + fileN);
    }

    private void open() throws IOException {
        String fileN = fileNameOpen.getText();
        String empty = new String();
        if (fileN.equals(empty)) {
            JOptionPane.showMessageDialog(null, "Enter file name to open!", "Error ", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int dialogButton = JOptionPane.YES_NO_OPTION;
        if (!TextBoxIsEmpty()) {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Current field not empty. Open file anyway?", "Warning", dialogButton);
            if (dialogResult != JOptionPane.YES_OPTION) {
                return;
            }
        }
        File file = new File(fileN + ".txt");
        try {
            FileReader reader = new FileReader(fileN + ".txt");
            BufferedReader br = new BufferedReader(reader);
            textBox.read(br, null);
            br.close();
        } catch (java.io.FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Specified file not found!", "Error ", JOptionPane.INFORMATION_MESSAGE);
        }
        setTitle("Notepad - " + fileN);

    }
}
