package core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AddonTagesschau extends Addon {

    boolean isRunning = false;

    public AddonTagesschau() {
        super("Tagesschau", new String[]{"play", "Tagesschau", "the", "news"});
    }

    @Override
    public void init() {

    }

    @Override
    public boolean reactTo(String userInput) {
        if (userInput.contains("news")) {
            try {
                Document document = Jsoup.connect("https://www.tagesschau.de/100sekunden/").get();

                Elements links = document.select("a[href]");

                String url = "https:" + links.stream().map(element -> element.attr("href")).filter(href -> href.contains("webxl")).collect(Collectors.joining());

                ArrayList<String> list = new ArrayList<>();
                list.add(url);
                System.out.println(url);

                this.isRunning = true;

                return true;

            } catch (IOException e) {
                IO.out("Something went wrong, sorry.");
            }
        }
        return false;
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return this.isRunning;
    }

}
