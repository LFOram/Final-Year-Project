package Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {
    public static void main(String[] args){

        ArrayList<String> wordList = new ArrayList<>();
        try {
            Scanner test = new Scanner(new FileInputStream("D:\\Uni Project\\Final Year Project\\res\\test.txt"));
            while (test.hasNext()){
                while (test.hasNext()){
                    String word = test.next();
                    if(!wordList.contains(word)){
                        wordList.add(word);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(wordList);

    }
}
