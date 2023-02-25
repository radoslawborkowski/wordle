package org.example;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class Word {

    String word;
    int chances;

    public Word() throws IOException, InterruptedException {
        this.word = generateWord("en");
        this.chances = (int)(word.length() * 1.5);
    }
    public Word(String language) throws IOException, InterruptedException {
        this.word = generateWord(language);
        this.chances = (int)(word.length() * 1.5);
    }

    public String getWord() {
        return word;
    }

    public int getChances() {
        return chances;
    }

    private String generateWord(String language) throws IOException, InterruptedException {
        HttpRequest randomWordRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://random-words5.p.rapidapi.com/getRandom"))
                .header("X-RapidAPI-Key", "a2fc2c60bemsh14da4e9211b92cdp1546aejsnfb9470a69bca")  // API key from the test account
                .header("X-RapidAPI-Host", "random-words5.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> randomWordResponse = HttpClient.newHttpClient().send(randomWordRequest, HttpResponse.BodyHandlers.ofString());

        String randomWord = randomWordResponse.body();

        if (!Objects.equals(language, "en")){
            randomWord = translateWord(language, randomWord);
        }

        return randomWord;
    }

    private String translateWord(String target, String word) throws IOException, InterruptedException {
        HttpRequest translateRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "application/gzip")
                .header("X-RapidAPI-Key", "a2fc2c60bemsh14da4e9211b92cdp1546aejsnfb9470a69bca")
                .header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("source=en&target="+target+"&format=text&q=" + word))
                .build();
        HttpResponse<String> translateResponse = HttpClient.newHttpClient().send(translateRequest, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonTranslatedWord = new Gson().fromJson(translateResponse.body(), JsonObject.class);
        return jsonTranslatedWord
                .getAsJsonObject("data")
                .getAsJsonArray("translations")
                .get(0)
                .getAsJsonObject()
                .get("translatedText")
                .getAsString();
    }
}
