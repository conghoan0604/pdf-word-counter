import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Main {
    public static void main(String[] args) throws IOException{
        String originalString = loadPdf();
        HashSet<String> stopWords = loadStopWords();
//        System.out.println(stopWords.toString());
//        System.out.println(stopWords.size());
        String cleanedString = cleanString(originalString);
        ArrayList<String> wordList = convertStringToWordList(cleanedString);

//        System.out.println(wordList.toString());

        System.out.println(countWord(wordList, stopWords)[0]);
        System.out.println(countWord(wordList, stopWords)[1]);

    }

    private static String loadPdf() throws IOException {
        String filename;
        Scanner scan;
        scan = new Scanner(System.in);

        //Retrieving filename from keyboard
        System.out.print("Enter PDF Filename: ");
        filename = scan.nextLine();

        //Load PDF document
        File file = new File("resources/"+ filename +".pdf");
        File fileWithExtension = new File("resources/"+ filename);

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

        //Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();

        //Retrieving text from PDF document
        String text = pdfStripper.getText(document);

        //Closing the document
        document.close();

        return text;
    }

    private static HashSet<String> loadStopWords() {
        HashSet<String> stopWords = new HashSet<>();
        BufferedReader bufferedReader = null;
        try {
            String strCurrentLine;
            bufferedReader = new BufferedReader(new FileReader("resources/StopWords.txt"));
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

    private static String cleanString(String string) {
         return string.replaceAll("[0-9$&+,:;=?@#|'<>.^*()%!-/\\\\_{}\\[\\]`~]", " ").replaceAll("\\s\\s+", " ").toLowerCase();
    }

    private static ArrayList<String> convertStringToWordList(String string) {
        String[] wordArray = string.split(" ");
        ArrayList<String> wordList = new ArrayList<>();
        Collections.addAll(wordList, wordArray);
        return wordList;
    }

//    private static boolean checkStopWord(String word1, String word2) {
//        boolean isStopWord = false;
//        if (word1.equals(word2))
//            isStopWord = true;
//        return isStopWord;
//    }
//
//    private static boolean checkExistedWord(String word1, String word2) {
//        boolean isExisted = false;
//        if (word1.equals(word2))
//            isExisted = true;
//        return isExisted;
//    }
//
//    private static void countWord(ArrayList<String> wordList, ArrayList<String> stopWords) {
//        ArrayList<String> countedWords = new ArrayList<>();
//        ArrayList<Integer> count = new ArrayList<>();
//        for (String word : wordList) {
//            for (String stopWord : stopWords)
//                if (!checkStopWord(word, stopWord))
//                    if (!checkExistedWord(word, stopWord)) {
//                        countedWords.add(word);
//                        count.add(1);
//                    } else {
//                        for (int i = 0; i < countedWords.size(); i++)
//                            if (word.equals(countedWords.get(i)))
//                                count.set(i, count.get(i) + 1);
//                    }
//        }
//    }

    private static int[] countWord(ArrayList<String> wordList, HashSet<String> stopWords) {
        int count = 0;
        int countStopWord = 0;
//        for (String word : wordList)
//            for (String stopWord : stopWords)
//                if (!checkStopWord(word, stopWord)) {
//                    count++;
//                    break;
//                } else {
//                    countStopWord++;
//                    break;
//                }
        for (String word : wordList) {
            if (!stopWords.contains(word))
                count++;
            else countStopWord++;
        }
        return new int[]{count, countStopWord};
    }
}

