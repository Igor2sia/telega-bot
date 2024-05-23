package bot;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class TelegramBot extends TelegramLongPollingBot {

        Document document = Jsoup.connect("https://www.dotabuff.com/players/411150463").get();
        String winRate = document.selectFirst(".header-content-secondary").text().split(" ")[5];
        String lose = document.selectFirst(".losses").text();
        String lastGame = document.selectFirst(".r-body").text();

    public TelegramBot() throws IOException {
    }

    @Override
    public String getBotUsername() {
        return "gfazers_bot";
    }

    @Override
    public String getBotToken() {
        return "";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (text.equalsIgnoreCase("дотабаф внука")) {
            sendMessage.setText("https://www.dotabuff.com/players/1044422119");
        } else if (text.equalsIgnoreCase("старый бох")) {
            sendMessage.setText("Дотабаф великого\nhttps://www.dotabuff.com/players/411150463\n" + "WinRate " + winRate + "\nСтарый уже слил " + lose + " Игр." + "\nПоследняя игра на " + lastGame);

        }
        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

