import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

class View extends JFrame {

    private JPanel contentPane;
    private JMenuBar menuBar;
    private JMenu mnHelp;
    private JButton mnhAbout;
    private JPanel mainPane;
    private File file;
    private JTextArea resultArea;
    private JTextArea textArea;
    private JButton btnChonFile;
    private JButton btnCountWord;
    private JScrollPane scrollPane;
    private JScrollPane scrollPane_1;
    private JTextField filePathField;

    /**
     * Create the frame.
     */
    View() {
        setTitle("WordCountPDF");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 380);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        menuBar = new JMenuBar();
        mnHelp = new JMenu("Trợ giúp");
        mnhAbout = new JButton("Thông tin nhóm");
        mnHelp.add(mnhAbout);
        menuBar.add(mnHelp);
        getContentPane().add(menuBar, BorderLayout.PAGE_START);

        mainPane = new JPanel();
        mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.add(mainPane, BorderLayout.CENTER);

        GridBagLayout gbl_mainPane = new GridBagLayout();
        gbl_mainPane.columnWidths = new int[]{0, 0, 0, 0};
        gbl_mainPane.rowHeights = new int[]{38, 104, 0, 104, 0};
        gbl_mainPane.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_mainPane.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
        mainPane.setLayout(gbl_mainPane);

        JLabel lblFile = new JLabel("File:");
        GridBagConstraints gbc_lblFile = new GridBagConstraints();
        gbc_lblFile.insets = new Insets(0, 0, 5, 5);
        gbc_lblFile.anchor = GridBagConstraints.WEST;
        gbc_lblFile.gridx = 0;
        gbc_lblFile.gridy = 0;
        mainPane.add(lblFile, gbc_lblFile);

        filePathField = new JTextField();
        GridBagConstraints gbc_filePathField = new GridBagConstraints();
        gbc_filePathField.insets = new Insets(0, 0, 5, 5);
        gbc_filePathField.fill = GridBagConstraints.HORIZONTAL;
        gbc_filePathField.gridx = 1;
        gbc_filePathField.gridy = 0;
        mainPane.add(filePathField, gbc_filePathField);
        filePathField.setColumns(10);

        btnChonFile = new JButton("Chọn file .pdf");

        GridBagConstraints gbc_btnChonFile = new GridBagConstraints();
        gbc_btnChonFile.insets = new Insets(0, 0, 5, 0);
        gbc_btnChonFile.gridx = 2;
        gbc_btnChonFile.gridy = 0;
        mainPane.add(btnChonFile, gbc_btnChonFile);

        JLabel lblText = new JLabel("Text:");
        GridBagConstraints gbc_lblText = new GridBagConstraints();
        gbc_lblText.anchor = GridBagConstraints.WEST;
        gbc_lblText.insets = new Insets(0, 0, 5, 5);
        gbc_lblText.gridx = 0;
        gbc_lblText.gridy = 1;
        mainPane.add(lblText, gbc_lblText);

        scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.gridx = 1;
        gbc_scrollPane.gridy = 1;
        mainPane.add(scrollPane, gbc_scrollPane);

        textArea = new JTextArea();
        scrollPane.setViewportView(textArea);

        btnCountWord = new JButton("Count");
        GridBagConstraints gbc_btnCountWord = new GridBagConstraints();
        gbc_btnCountWord.anchor = GridBagConstraints.WEST;
        gbc_btnCountWord.insets = new Insets(0, 0, 5, 5);
        gbc_btnCountWord.gridx = 1;
        gbc_btnCountWord.gridy = 2;
        mainPane.add(btnCountWord, gbc_btnCountWord);

        JLabel lblResult = new JLabel("Result:");
        GridBagConstraints gbc_lblResult = new GridBagConstraints();
        gbc_lblResult.anchor = GridBagConstraints.WEST;
        gbc_lblResult.insets = new Insets(0, 0, 0, 5);
        gbc_lblResult.gridx = 0;
        gbc_lblResult.gridy = 3;
        mainPane.add(lblResult, gbc_lblResult);

        scrollPane_1 = new JScrollPane();
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
        gbc_scrollPane_1.gridx = 1;
        gbc_scrollPane_1.gridy = 3;
        mainPane.add(scrollPane_1, gbc_scrollPane_1);

        resultArea = new JTextArea();
        scrollPane_1.setViewportView(resultArea);

    }

    void setResult(int result){
        resultArea.setText(String.valueOf(result));
    }

    void setText(String text){
        textArea.setText(text);
    }

    File getFile() {
        return file;
    }

    void setFile(File file) {
        this.file = file;
    }

    void setFilePathField(String filePath) {
        this.filePathField.setText(filePath);
    }

    void addActionListener(ActionListener btnChoooseAL, ActionListener btnCountAL, ActionListener btnAbout){
        btnChonFile.addActionListener(btnChoooseAL);
        btnCountWord.addActionListener(btnCountAL);
        mnhAbout.addActionListener(btnAbout);
    }

    void displayError(String error){
        JOptionPane.showMessageDialog(this, error);
    }
}
