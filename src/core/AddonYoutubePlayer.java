package core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AddonYoutubePlayer extends Addon {

    public AddonYoutubePlayer() {
        super("YoutubePlayer", new String[]{"play", "by", "from", "youtube"});
    }

    @Override
    public void init() {

    }

    @Override
    public boolean reactTo(String userInput) {
        return false;
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    public static void main(String[] args) {
        try {
            // Works with startpage -> But only about 10 results
            Document document = Jsoup.connect("https://www.startpage.com/do/search?sc=WTdlc0xPPK1i20&query=sia&cat=video").get();
            Elements links = document.getElementsByClass("vo-sp__link");
            ArrayList<String> list = (ArrayList<String>) links.stream().map(element -> element.attr("href")).collect(Collectors.toList());

            list.forEach(System.out::println);
//            Media.mediaPlayer.start(list);

            // Works with ecosia -> More results, but less specific for music
//            Document document = Jsoup.connect("https://www.ecosia.org/videos?q=eminem").get();
//            Elements links = document.getElementsByClass("search-result-title__link link link--color-result");
//            links.stream().map(element -> element.attr("href")).forEach(System.out::println);

//            Document document = Jsoup.connect("https://www.youtube.com/results?search_query=eminem").get();
//
//            System.out.println(document.getAllElements());

//            String url = "https:" + links.stream().map(element -> element.attr("href")).filter(href -> href.contains("webxl")).collect(Collectors.joining());
//
//            list.add(url);
//            System.out.println(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
