package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Utils {
    public static String getUrl(String nasaUrl) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        ObjectMapper mapper = new ObjectMapper();
        HttpGet request = new HttpGet(nasaUrl);
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            NasaAnswer answer = mapper.readValue(response.getEntity().getContent(), NasaAnswer.class);

            // скачивание картинки
//        HttpGet imageGet = new HttpGet(answer.url);
//        CloseableHttpResponse image = httpClient.execute(imageGet);
//
//        // сохранение картинки в файл
//        String[] urlSplited = answer.url.split("/");
//        String filename = urlSplited[urlSplited.length - 1];
//        FileOutputStream fileOutputStream = new FileOutputStream(filename);
//        image.getEntity().writeTo(fileOutputStream);

            return answer.url;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}