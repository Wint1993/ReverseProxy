package com;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;


@Controller
public class TestController {


    private HashSet<String> links;

    public TestController() {
        links = new HashSet<String>();
    }

    @ResponseBody
    @RequestMapping("/")
    public Object page(@RequestParam String url) throws IOException {



//        URL url1 = new URL(url);
//        URLConnection con = url1.openConnection();
//        InputStream in = con.getInputStream();        getPageLinks(url);

//        String encoding = con.getContentEncoding();
//        encoding = encoding == null ? "UTF-8" : encoding;
//        String body = IOUtils.toString(in, encoding);
     //   System.out.println(body);
        return getPageLinks(url);
    }

    public String getPageLinks(String URL) {
        //4. Check if you have already crawled the URLs
        //(we are intentionally not checking for duplicate content in this example)
        if (!links.contains(URL)) {
            try {
                //4. (i) If not add it to the index
                if (links.add(URL)) {
                 //   System.out.println(URL);
                }


                Document document = Jsoup.connect(URL).ignoreContentType(true).get();


              //  Elements linksOnPage = document.select("a[href]");
              //  String linkhref = linksOnPage.attr("href");



/// ref src, przepisac, cookies,
                Elements linksOnPage = document.select("a[href]");
                linksOnPage.forEach( l -> {
                    String origialLik = l.attr("href");
                    l.attr("href", "http://localhost:8090/?url="+origialLik);
                   // System.out.println(l);
                });

               Elements linksOnPage1 = document.select("link[href]");
                linksOnPage1.forEach( l -> {
                    String origialLik = l.attr("href");
                    l.attr("href", "http://localhost:8090/?url="+origialLik);
                  //  System.out.println(l);
                });


                Elements linksOnPage2 = document.select("img[src]");
                linksOnPage2.forEach( l -> {
                    String origialLik = l.attr("src");
                    l.attr("src", "http://localhost:8090/?url="+origialLik);

                    // System.out.println(l);
                });




                return document.html();
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
        return "";
    }
}
