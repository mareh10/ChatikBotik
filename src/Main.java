import iomanager.Question;
import iomanager.QuestionsParser;
import iomanager.RequestToURL;
import logic.*;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    public static void main(String[] args) {
        InputOutput ui = new VkBot();

//        ApiContextInitializer.init();
//        TelegramBotsApi botapi = new TelegramBotsApi();
//        try {
//            botapi.registerBot((TelegramBot)ui);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }

        MainLoop.runLoop(ui);

    }
}
