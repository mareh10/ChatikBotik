package iomanager;

import java.util.ArrayList;
import java.util.HashMap;

public class Question
{
    public String Question;
    public HashMap<Integer, String> Variants;
    public String CorrectAnswer;

    public Question(String question, HashMap<Integer, String> variants, String correctAnswer)
    {
        this.Question = question;
        this.Variants = variants;
        this.CorrectAnswer = correctAnswer;
    }

    public static String formatQuestion(Question q) {
        String s = q.Question;
        var i = 1;
        for (var variant : q.Variants.values()) {
            s += "\n" + i +": " + variant;
            i = i + 1;
        }

//        System.out.println(s);

        return s;
    }
}
