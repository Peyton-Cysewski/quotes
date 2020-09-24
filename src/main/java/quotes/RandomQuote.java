package quotes;

import com.google.gson.stream.JsonReader;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class RandomQuote {
    public static void main(String[] args) {
        try {
            System.out.println(FindQuote());
        } catch (Exception e) {
            System.out.println("There was an error with this method.");
            System.out.println(e.getMessage());
        }
    }

    public static String FindQuote() throws Exception {
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

        return quotes.get(index).toString();
    }
}
