package logic;

import iomanager.Question;
import iomanager.QuestionsParser;
import iomanager.RequestToURL;

import java.util.ArrayList;
import java.util.Random;

public class Quiz implements Game{
    Random random = new Random();
    public Question curQuestion;
    public ArrayList<Question> questions;
    private boolean isWaitingUserAnswer = false;

    public Quiz(){
        var data = RequestToURL.openUrl(
                "https://baza-otvetov.ru/categories/view/1/" + random.nextInt(300) * 10);
        questions = QuestionsParser.getQuestions(data);

        curQuestion = questions.get(random.nextInt(questions.size()));
    }

    @Override
    public String handleGameRequest(String request, User user) {
        if (!isWaitingUserAnswer){
            isWaitingUserAnswer = true;
            return Question.formatQuestion(curQuestion);
        }

        try {
            if (curQuestion.Variants.get(Integer.parseInt(request)).toLowerCase()
                    .equals(curQuestion.CorrectAnswer.toLowerCase())) {
                questions.remove(curQuestion);

                if (questions.size() == 0) {
                    user.state = State.Choosing;
                    return "Спасибо за игру! Что дальше? \n \t 1: Викторина";
                }
                var last_answer = curQuestion.CorrectAnswer;

                curQuestion = questions.get(random.nextInt(questions.size()));
                return "Верно, " + last_answer + "!\n\n" + Question.formatQuestion(curQuestion);
            } else
                return "Неправильный ответ, давай еще раз\n";
        } catch (NumberFormatException e) {
            return "Неправильный ответ, давай еще раз\n";
        }

    }
}
