import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

class Controller {
    private Model theModel;
    private View theView;

    Controller(View theView, Model theModel) {
        this.theView = theView;
        this.theModel = theModel;
        this.theView.addActionListener(new  btnChooseActionListener(), new btnCountActionListener(), new btnAboutActionListener(), new btnStopWordsActionListener(), new btnTokenizedTextActionListener(), new btnPdfStopWordsActionListener());
    }

    public class btnCountActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            theView.setResult(theModel.getResult());
        }
    }

    public class btnChooseActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter extFilter = new FileNameExtensionFilter("PDF File", "pdf");
                chooser.setFileFilter(extFilter);

                int returnVal = chooser.showOpenDialog((JButton) e.getSource());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    theView.setFile(chooser.getSelectedFile());
                    theView.setFilePathField(chooser.getSelectedFile().getPath());
                }

                theModel.runCountWord(theView.getFile());
                theView.setText(theModel.getOriginalText());

            } catch (IOException e1) {
                e1.printStackTrace();
                theView.displayError("Loi trong qua trinh doc file");
            }
        }

    }

    public static class btnAboutActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Danh sách thành viên \n - Ngô Công Hoan - 16001788 \n - Nguyễn Sơn Tùng - 16001888 \n - Đào Hồng Hà - 16001775", "Thông tin nhóm", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public class btnStopWordsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            theView.setTextResult(theModel.getStopWords());
        }
    }

    public class btnPdfStopWordsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            theView.setTextResult(theModel.getPdfStopWords());
        }
    }

    public class btnTokenizedTextActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            theView.setTextResult(theModel.getTokenizedText());
        }
    }
}