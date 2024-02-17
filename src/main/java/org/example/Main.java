package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;


public class Main {

    public static final String API_URL = "https://api.nasa.gov/planetary/apod";
    public static final String API_KEY = "3CbZKc0hub8ZasoiN4qzaDSOI6YPRqSdf0jhJfHO";
    public static final String URL = API_URL + "?api_key=" + API_KEY;

    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        // запрос и ответ по API NASA
        HttpGet request = new HttpGet(URL);
        CloseableHttpResponse response = httpClient.execute(request);
        NasaAnswer answer = mapper.readValue(response.getEntity().getContent(), NasaAnswer.class);

        // скачивание картинки
        HttpGet imageGet = new HttpGet(answer.url);
        CloseableHttpResponse image = httpClient.execute(imageGet);

        // сохранение картинки в файл
        String[] urlSplited = answer.url.split("/");
        String filename = urlSplited[urlSplited.length - 1];
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        image.getEntity().writeTo(fileOutputStream);
    }
}