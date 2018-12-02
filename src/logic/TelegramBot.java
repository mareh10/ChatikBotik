package logic;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TelegramBot extends TelegramLongPollingBot implements InputOutput{
    private final String BOT_NAME = "Capricorn";
    private final String BOT_TOKEN = "658563566:AAECohdHHTskGM3StIVCtOYLVzvXHOOM7QM";
    private ConcurrentLinkedQueue<Request> requestQueue = new ConcurrentLinkedQueue<>();

    @Override
    public Request input() {
        return requestQueue.poll();
    }

    @Override
    public void output(String userId, String output) {
        sendMsg(userId, output);
    }

    /**
     * Отправка сообщения нужному пользователю.
     * @param userId ID пользователя
     * @param text Текст сообщения
     */
    private void sendMsg(String userId, String text) {
        SendMessage s = new SendMessage()
                .setChatId(userId)
                .setText(text);
        try {
            execute(s);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message curMessage = update.getMessage();
        requestQueue.add(new Request(curMessage.getChatId().toString(), curMessage.getText()));
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
