package iomanager;

import java.util.ArrayList;
import java.util.Collections;

public class Question
{
    public String Question;
    public ArrayList<String> Variants;
    public String CorrectAnswer;

    public Question(String question, ArrayList<String> variants, String correctAnswer)
    {
        this.Question = question;
        this.Variants = variants;
        this.CorrectAnswer = correctAnswer;
    }

    public static String formatQuestion(Question q) {
        String s = q.Question;
        Collections.shuffle(q.Variants);
        for (var variant : q.Variants) {
            s += "\n" + variant;
        }

//        System.out.println(s);

        return s;
    }
}
