import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Collections;
import java.util.HashSet;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import vn.hus.nlp.tokenizer.VietTokenizer;

class Model {
    private String originalText;
    private String tokenizedText;
    private HashSet<String> pdfStopWords = new HashSet<>();
    private String resourcePath = "resources/";
    private String resultPath = "results/";
    private String stopWordsFile = resourcePath + "StopWords.txt";
    private HashSet<String> stopWords = new HashSet<>();
    private String originalTextFile = resultPath + "1.OriginalText.txt";
    private String tokenizedTextFile = resultPath + "2.TokenizedText.txt";
    private String cleanedTextFile = resultPath + "3.CleanedText.txt";
    private int result = 0;

    private String getFileExtension(String filePath) {
        String extension="";

        int i = filePath.lastIndexOf('.');
        int p = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf('\\'));

        if (i > p) {
            extension = filePath.substring(i+1);
        }

        return extension;
    }

    private boolean isPdf(String filePath) {
        String extension = getFileExtension(filePath);
        return extension.equals("pdf");
    }

    private String loadPdf(File file) throws IOException {
        String filepath = file.getAbsolutePath();
        boolean isPdf = isPdf(filepath);

        PDDocument document;

        if (file.exists() && isPdf) {
            document = PDDocument.load(file);
        } else {
            document = null;
            System.exit(0);
        }

        PDFTextStripper pdfStripper = new PDFTextStripper();
        String documentText = pdfStripper.getText(document);
        document.close();

        return documentText;
    }

    private HashSet<String> loadStopWords() {
        BufferedReader bufferedReader = null;

        try {
            String strCurrentLine;
            bufferedReader = new BufferedReader(new FileReader(stopWordsFile));
            while ((strCurrentLine = bufferedReader.readLine()) != null) {
                stopWords.add(strCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return stopWords;
    }

    private void writeToFile(String filename, String text) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write (text);
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private String loadTxt() {
        StringBuilder text = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            String currentLine;
            bufferedReader = new BufferedReader(new FileReader(tokenizedTextFile));
            while ((currentLine = bufferedReader.readLine()) != null) {
                text.append(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return text.toString();
    }

    private static ArrayList<String> convertTextToWordList(String string) {
        String[] wordArray = string.split(" ");
        ArrayList<String> wordList = new ArrayList<>();

        Collections.addAll(wordList, wordArray);

        return wordList;
    }

    private int countWord(ArrayList<String> wordList, HashSet<String> stopWords) {
        int count = 0;

        for (String word : wordList) {
            if (!stopWords.contains(word)) {
                count++;
            } else {
                pdfStopWords.add(word);
            }
        }

        return count;
    }

    private String cleanString(String string) {
        return string.replaceAll("[\r\n|\r|\n0-9$&+,:;=?@#|'<>.^*()%!-/\\\\{}\\[\\]`~]", " ").replaceAll("\\s\\s+", " ").toLowerCase();
    }


    void runCountWord(File file) throws IOException {
        originalText = loadPdf(file);
        writeToFile(originalTextFile, originalText);

        VietTokenizer tokenizer = new VietTokenizer();
        tokenizer.tokenize(originalTextFile, tokenizedTextFile);

        tokenizedText = loadTxt();
        ArrayList<String> wordList = convertTextToWordList(tokenizedText);

        String cleanedText = cleanString(tokenizedText);
        writeToFile(cleanedTextFile, cleanedText);
        wordList = convertTextToWordList(cleanedText);

        HashSet<String> stopWords = loadStopWords();

        result = countWord(wordList, stopWords);
    }

    String getOriginalText() {
        return originalText;
    }

    String getTokenizedText() {
        return cleanString(tokenizedText);
    }

    String getPdfStopWords() {
        return pdfStopWords.toString();
    }

    String getStopWords() {
        return stopWords.toString();
    }

    int getResult() {
        return result;
    }
}
