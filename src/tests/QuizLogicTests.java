package tests;

import iomanager.Question;
import junit.framework.TestCase;
import logic.*;

public class QuizLogicTests extends TestCase {

    Quiz quiz;
    User user;

    public void setUp(){
        quiz = new Quiz();
        user = new User("1", State.Playing);
        quiz.handleGameRequest("blablabla", user);
    }

    public void testCorrectAnswer(){
        assertEquals(quiz.handleGameRequest(quiz.curQuestion.CorrectAnswer, user),
                "WELL PLAYED!\n\n" + Question.formatQuestion(quiz.curQuestion));
    }

    public void testIncorrectAnswer(){
        assertEquals(quiz.handleGameRequest("abracadabra", user),
                "Неправильный ответ, давай еще раз\n");
    }
}
