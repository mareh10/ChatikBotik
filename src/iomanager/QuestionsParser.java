package iomanager;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionsParser {
    public static ArrayList<Question> getQuestions(ArrayList<String> data) {
        ArrayList<Question> questions = new ArrayList<>();
        Question question = new Question(null, null, null);

        Pattern questionFinder = Pattern.compile(">.*?</a>");
        Pattern answerFinder = Pattern.compile(":\\s((.+?),?)+?\t\\s"); //":\\s.+?, .+?, .+?\t\\s"
        Pattern correctAnswerFinder = Pattern.compile("<td>.+?</td>");
        Matcher matcher;

        ArrayList<String> answers = new ArrayList<>();
        HashMap<Integer, String> num_to_answer_dict = new HashMap<>();

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
                    for (var e : vars)
                        e = e.trim();
                    var temp = vars.get(0).substring(2);
                    vars.remove(0);
                    vars.add(temp);
                    for (var variant : vars)
                        answers.add(variant.trim());
                }
            }
            if (line.matches(".*?<td>.+?</td>.*?"))
            {
                matcher = correctAnswerFinder.matcher(line);
                if (matcher.find())
                {
                    question.CorrectAnswer = matcher.group().substring(4, matcher.group().length() - 5);

                    if (!answers.contains(question.CorrectAnswer))
                        answers.add(question.CorrectAnswer);

                    Collections.shuffle(answers);
                    var i = 1;
                    for (var answer : answers){
                        num_to_answer_dict.put(i, answer);
                        i = i + 1;
                    }
                    question.Variants = num_to_answer_dict;
                    answers = new ArrayList<String>();
                    num_to_answer_dict = new HashMap<>();

                    if (question.Question != null)
                        questions.add(question);
                    question = new Question(null, null, null);
                }
            }
        }
        return questions;
    }
}
