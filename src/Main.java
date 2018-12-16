import logic.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.HashMap;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
//        var botapi = new TelegramBotsApi();
//        TelegramBot bot = new TelegramBot();
//        try {
//            botapi.registerBot(bot);
//        } catch (TelegramApiRequestException e) {
//            e.printStackTrace();
//        }
//        MainLoop.runLoop(bot);

        var bot = new Console();
        MainLoop.runLoop(bot);
    }
}
