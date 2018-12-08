package tests;

import iomanager.Question;
import iomanager.QuestionsParser;
import iomanager.RequestToURL;
import junit.framework.TestCase;
import java.net.MalformedURLException;

import java.util.ArrayList;

public class QuestionsParserTests extends TestCase {

    String url = "https://baza-otvetov.ru/categories/view/1/2";
    ArrayList<Question> questions = QuestionsParser.getQuestions(RequestToURL.openUrl(url));

    public void testCorrectAnswerInVariants() {
        assertTrue(questions.get(1).Variants.values().contains(questions.get(1).CorrectAnswer));
    }
}
