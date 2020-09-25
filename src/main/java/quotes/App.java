package quotes;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class App {
    public static void main(String[] args) {
        try {
            Quote quote = GetSwansonQuote();
            AddQuoteToLocal(quote);
            System.out.println(quote);
        }
        catch (Exception e) {
            System.out.println("The internet quote wasn't working, so we pulled a locally stored quote instead.");
        System.out.println(GetLocalQuote().toString());
        }
    }

    public static Quote GetLocalQuote() {
        try {
            File file = new File("src/main/resources/recentquotes.json");
            FileInputStream stream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            JsonReader jsr = new JsonReader(reader);

            ArrayList<Quote> quotes = new ArrayList<Quote>();

            jsr.beginArray();
            while (jsr.hasNext()) {
                String author = null;
                String text = null;

                jsr.beginObject();
                while (jsr.hasNext()) {
                    String name = jsr.nextName();
                    if (name.equals("author"))
                        author = jsr.nextString();
                    else if (name.equals("text"))
                        text = jsr.nextString();
                    else
                        jsr.skipValue();
                }
                jsr.endObject();

                Quote quote = new Quote(text, author);
                quotes.add(quote);
            }
            jsr.endArray();
            jsr.close();

            Random rand = new Random();
            int index = rand.nextInt(quotes.size());

            return quotes.get(index);
        } catch (Exception e) {
            System.out.println("There was an error with this method.");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Quote GetSwansonQuote() throws IOException {
        URL url = new URL("https://ron-swanson-quotes.herokuapp.com/v2/quotes");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer swansonResponse = new StringBuffer();
        String inputLine = reader.readLine();
        while(inputLine != null){
            swansonResponse.append(inputLine);
            inputLine = reader.readLine();
        }
        reader.close();
        connection.disconnect();

        String jsonQuote = swansonResponse.toString();
        String text = new Gson().fromJson(jsonQuote, String[].class)[0];
        String author = "Ron Swanson";
        Quote result = new Quote(text, author);
        return result;
    }

    public static void AddQuoteToLocal(Quote quote) throws IOException {
        Gson gson = new Gson();
        FileWriter wr = new FileWriter("src/main/resources/test.json", true);
        JsonWriter jwr = new JsonWriter(wr);
        gson.toJson(quote, Quote.class, jwr);
        wr.close();
    }
}
