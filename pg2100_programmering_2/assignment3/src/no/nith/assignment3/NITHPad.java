package no.nith.assignment3;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This application was developed as an assignment at NITH.
 * The application is an implementation of a text editor
 * with some simple capabilities like reading from a file
 * and saving to a file.
 *
 * I believe that the scope and complexity of this assignment
 * does not require any extensive commenting of methods as
 * most of the method-names should be fairly self-explanatory.
 *
 * @author Espen Rønning <ronesp13@nith.no>
 * @version 1.8
 * @since 08.05.2014
 */
public class NITHPad extends JFrame implements ActionListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    private static boolean isChanged;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu formatMenu;
    private JMenu editMenu;
    private JMenu helpMenu;
    private JMenuItem openItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;
    private JMenuItem textWrapItem;
    private JMenuItem noTextWrapItem;
    private JMenu fontSizeMenu;
    private JMenuItem[] fontSizeItems;
    private JMenuItem clearItem;
    private JMenuItem aboutItem;

    private JScrollPane scrollPane;
    private JTextArea textArea;

    public NITHPad() {
        super("NITHPad");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createComponents();
        createShortcuts();
        createListeners();
    }

    private void createComponents() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("<html><u>F</u>ile</html>");
        formatMenu = new JMenu("<html>F<u>o</u>rmat</html>");
        editMenu = new JMenu("<html><u>E</u>dit</html>");
        helpMenu = new JMenu("<html><u>H</u>elp</html>");
        openItem = new JMenuItem("<html><u>O</u>pen</html>");
        saveItem = new JMenuItem("<html><u>S</u>ave</html>");
        exitItem = new JMenuItem("<html><u>E</u>xit</html>");
        textWrapItem = new JMenuItem("<html>Text <u>w</u>rap");
        noTextWrapItem = new JMenuItem("<html><u>N</u>o Text wrap</html>");
        fontSizeMenu = new JMenu("<html><u>F</u>ont size</html>");
        fontSizeItems = new JMenuItem[6];
        for (int i = 10, count = 0; i <= 20; i += 2) { // create font size elements
            fontSizeItems[count] = new JMenuItem(String.valueOf(i));
            fontSizeMenu.add(fontSizeItems[count++]);
        }
        clearItem = new JMenuItem("<html><u>C</u>lear</html>");
        aboutItem = new JMenuItem("<html><u>A</u>bout NITHPad</html>");
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);

        setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        menuBar.add(formatMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        formatMenu.add(textWrapItem);
        formatMenu.add(noTextWrapItem);
        formatMenu.add(new JSeparator());
        formatMenu.add(fontSizeMenu);

        editMenu.add(clearItem);
        helpMenu.add(aboutItem);

        add(scrollPane);
    }

    private void createShortcuts() {
        fileMenu.setMnemonic(KeyEvent.VK_F);
        formatMenu.setMnemonic(KeyEvent.VK_O);
        editMenu.setMnemonic(KeyEvent.VK_E);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        openItem.setMnemonic(KeyEvent.VK_O);
        saveItem.setMnemonic(KeyEvent.VK_S);
        exitItem.setMnemonic(KeyEvent.VK_E);
        textWrapItem.setMnemonic(KeyEvent.VK_W);
        noTextWrapItem.setMnemonic(KeyEvent.VK_N);
        fontSizeMenu.setMnemonic(KeyEvent.VK_F);
        clearItem.setMnemonic(KeyEvent.VK_C);
        aboutItem.setMnemonic(KeyEvent.VK_A);
    }

    private void createListeners() {
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        textWrapItem.addActionListener(this);
        noTextWrapItem.addActionListener(this);
        for (JMenuItem item : fontSizeItems) { // add listener to font size menu items
            item.addActionListener(this);
        }
        clearItem.addActionListener(this);
        aboutItem.addActionListener(this);

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isChanged = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isChanged = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isChanged = true;
            }
        });
    }

    private void writeToFile(File file) {
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            writer.println(textArea.getText());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            textArea.setText("");
            while (scanner.hasNext()) {
                textArea.append(scanner.nextLine() + "\n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(openItem)) {
            openFileTask();
        } else if (e.getSource().equals(saveItem)) {
            saveFileTask();
        } else if (e.getSource().equals(exitItem)) {
            exitProgramTask();
        } else if (e.getSource().equals(textWrapItem)) {
            setTextWrap(true);
        } else if (e.getSource().equals(noTextWrapItem)) {
            setTextWrap(false);
        } else if (e.getSource().equals(clearItem)) {
            clearTask();
        } else if (e.getSource().equals(aboutItem)) {
            JOptionPane.showMessageDialog(null, "<html>This application is developed by Espen R&oslash;nning.</html>");
        }
        for (JMenuItem item : fontSizeItems) {
            if (e.getSource().equals(item)) {
                changeFontSizeTask(e);
                break;
            }
        }
    }

    private void changeFontSizeTask(ActionEvent e) {
        JMenuItem fontItem = (JMenuItem) e.getSource();
        int fontSize = Integer.parseInt(fontItem.getText());
        textArea.setFont(textArea.getFont().deriveFont((float) fontSize));
    }

    private void clearTask() {
        if (promptSave() == JOptionPane.CANCEL_OPTION) {
            return;
        }
        textArea.setText("");
        isChanged = false;
    }

    private void exitProgramTask() {
        if (promptSave() == JOptionPane.CANCEL_OPTION) {
            return;
        }
        System.exit(0);
    }

    private int promptSave() {
        if (isChanged) {
            int choice = JOptionPane.showConfirmDialog(null, "Do you want to save before proceeding?", "Save", JOptionPane.YES_NO_CANCEL_OPTION);
            switch (choice) {
                case JOptionPane.YES_OPTION:
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        writeToFile(file);
                        isChanged = false;
                    }
                    break;
                case JOptionPane.NO_OPTION:return JOptionPane.NO_OPTION;
                case JOptionPane.CANCEL_OPTION:return JOptionPane.CANCEL_OPTION;
            }
        }
        return JOptionPane.YES_OPTION;
    }

    private void saveFileTask() {
        promptSave();
        isChanged = false;
    }

    private void openFileTask() {
        if (promptSave() == JOptionPane.CANCEL_OPTION) {
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (file.exists()) {
                readFromFile(file);
                isChanged = false;
            } else {
                JOptionPane.showMessageDialog(null, "The selected file does not exist", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void setTextWrap(boolean doWrap) {
        textArea.setLineWrap(doWrap);
        textArea.setWrapStyleWord(doWrap);
    }

    public static void main(String[] args) {
        NITHPad nith = new NITHPad();
        nith.setVisible(true);
    }
}