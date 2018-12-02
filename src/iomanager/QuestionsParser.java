package iomanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionsParser {
    public static ArrayList<Question> getQuestions(ArrayList<String> data) {
        ArrayList<Question> questions = new ArrayList<>();
        Question question = new Question(null, null, null);

        Pattern questionFinder = Pattern.compile(">.*?</a>");
        Pattern answerFinder = Pattern.compile(":\\s.+?, .+?, .+?\\s");
        Pattern correctAnswerFinder = Pattern.compile("<td>\\D+?</td>");
        Matcher matcher;

        ArrayList<String> answers = new ArrayList<>();

        for (String line : data) {
            if (line.matches(".*?<a href=\"/search/view/.*"))
            {
                matcher = questionFinder.matcher(line);
                if (matcher.find())
                {
                    question.Question = matcher.group().substring(1, matcher.group().length() - 4);
                }
            }
            if (line.matches(".*?Ответы для викторин: .*?"))
            {
                matcher = answerFinder.matcher(line);
                if (matcher.find())
                {
                    ArrayList<String> vars = new ArrayList<>(Arrays.asList(matcher.group().split(", ")));
                    answers.add(vars.get(0).substring(2));
                    answers.add(vars.get(1));
                    answers.add(vars.get(2).trim());
                }
            }
            if (line.matches(".*?<td>\\D+?</td>.*?"))
            {
                matcher = correctAnswerFinder.matcher(line);
                if (matcher.find())
                {
                    question.CorrectAnswer = matcher.group().substring(4, matcher.group().length() - 5);
                    answers.add(question.CorrectAnswer);
                    question.Variants = answers;
                    answers = new ArrayList<>();

                    questions.add(question);
                    question = new Question(null, null, null);
                }
            }
        }
        return questions;
    }
}
