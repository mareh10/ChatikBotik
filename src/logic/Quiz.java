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
        var randomPage = random.nextInt(298);
        var data = RequestToURL.openUrl("https://baza-otvetov.ru/categories/view/1/" + random.nextInt(300) * 10);
        questions = QuestionsParser.getQuestions(data);

        curQuestion = questions.get(random.nextInt(9));
    }

    @Override
    public String handleGameRequest(String request, User user) {
        if (!isWaitingUserAnswer){
            isWaitingUserAnswer = true;
            return Question.formatQuestion(curQuestion);
        }

        if (request.toLowerCase().equals(curQuestion.CorrectAnswer.toLowerCase())){
            curQuestion = questions.get(random.nextInt(9));
            return "WELL PLAYED!\n" + Question.formatQuestion(curQuestion);
        }
        else
            return "Неправильный ответ, давай еще раз\n";
    }
}
