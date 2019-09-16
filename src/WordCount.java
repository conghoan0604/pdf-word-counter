import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import vn.hus.nlp.tokenizer.VietTokenizer;

public class WordCount {
    private static String resourcePath = "resources/";
    private static String resultPath = "results/";
    private static String stopWordsFile = resourcePath + "StopWords.txt";
    private static String originalTextFile = resultPath + "1.OriginalText.txt";
    private static String tokenizedTextFile = resultPath + "2.TokenizedText.txt";
    private static String cleanedTextFile = resultPath + "3.CleanedText.txt";

    public static void main(String[] args) throws IOException{
        String originalText = loadPdf();
        writeToFile(originalTextFile, originalText);

        VietTokenizer tokenizer = new VietTokenizer();
        tokenizer.tokenize(originalTextFile, tokenizedTextFile);

        String tokenizedText = loadTxt();
        ArrayList<String> wordList = convertTextToWordList(tokenizedText);

        String cleanedText = cleanString(tokenizedText);
        writeToFile(cleanedTextFile, cleanedText);

        HashSet<String> stopWords = loadStopWords();

        System.out.println(countWord(wordList, stopWords));
    }

    private static String loadPdf() throws IOException {
        String filename;
        Scanner scan;
        scan = new Scanner(System.in);

        System.out.print("Enter PDF Filename: ");
        filename = scan.nextLine();

        File file = new File(resourcePath + filename + ".pdf");
        File fileWithExtension = new File(resourcePath + filename);

        PDDocument document;

        if (file.exists()) {
            document = PDDocument.load(file);
        } else if (fileWithExtension.exists()) {
            document = PDDocument.load(fileWithExtension);
        } else {
            document = null;
            System.out.println("File "+ filename + ".pdf not found!");
            System.exit(0);
        }

        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();

        return text;
    }

    private static void writeToFile(String filename, String text) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write (text);
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static HashSet<String> loadStopWords() {
        HashSet<String> stopWords = new HashSet<>();
        BufferedReader bufferedReader = null;

        try {
            String strCurrentLine;
            bufferedReader = new BufferedReader(new FileReader(stopWordsFile));
            while ((strCurrentLine = bufferedReader.readLine()) != null) {
//                System.out.println(strCurrentLine);
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

    private static String loadTxt() {
        String text = "";
        BufferedReader bufferedReader = null;

        try {
            String currentLine;
            bufferedReader = new BufferedReader(new FileReader(tokenizedTextFile));
            while ((currentLine = bufferedReader.readLine()) != null) {
                text = text + currentLine;
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

        return text;
    }

    private static String cleanString(String string) {
        return string.replaceAll("[0-9!-/\\[-`:-@{-~]", " ").replaceAll("\\z", " ").replaceAll("\\s\\s+", " ").toLowerCase();

//         return string.replaceAll("[\r\n|\r|\n0-9$&+,:;=?@#|'<>.^*()%!-/\\\\{}\\[\\]`~]", " ").replaceAll("\\s\\s+", " ").toLowerCase();
    }

    private static ArrayList<String> convertTextToWordList(String string) {
        String[] wordArray = string.split(" ");
        ArrayList<String> wordList = new ArrayList<>();

        Collections.addAll(wordList, wordArray);

        return wordList;
    }

    private static int countWord(ArrayList<String> wordList, HashSet<String> stopWords) {
        int count = 0;

        for (String word : wordList) {
            if (!stopWords.contains(word))
                count++;
            else {
                System.out.println(word);
            }
        }

        return count;
    }
}

