package com;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class TestController implements HttpServletResponse {


    private HashSet<String> links;

    public TestController() {
        links = new HashSet<String>();
    }

    private String header;

    @ResponseBody
    @RequestMapping("/")
    public Object page(@RequestParam String url) throws IOException {

        Connection.Response res = Jsoup.connect(url).timeout(10*1000).execute();
        header = res.contentType();

        addHeader("text/*","application/xml");
        setHeader("text/*","application/xml");

        return getPageLinks(url);
    }

    public String getPageLinks(String URL) {

        if (!links.contains(URL)) {
            try {

                if (links.add(URL)) {
                   System.out.println(URL);
                }

                Connection.Response res = Jsoup.connect(URL).method(Connection.Method.GET).timeout(10*1000).execute();
                String contentType=res.contentType();
               // Map<String, String> cookies = res.cookies();
                Map<String, String> cookies = new HashMap<String, String>();

                Connection connection1 = Jsoup.connect(URL);
                for (Map.Entry<String, String> cookie : cookies.entrySet()) {
                    connection1.cookie(cookie.getKey(), cookie.getValue());
                }


                //   System.out.println(contentType);
              //  header = res.contentType();
                Document document = Jsoup.connect(URL).cookies(cookies)
                        .header("Accept-Encoding", "gzip")
                        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
                        .cookies(res.cookies())
                        .get();

/// ref src, przepisac, cookies,
                Elements linksOnPage = document.select("a[href]");
                linksOnPage.forEach( l -> {
                    String origialLik = l.attr("href");
                    l.attr("href", "http://localhost:8090/?url="+origialLik);

                });

               Elements linksOnPage1 = document.select("link[href]");
                linksOnPage1.forEach( l -> {
                    String origialLik = l.attr("href");
                    l.attr("href", "http://localhost:8090/?url="+origialLik);

                });

               Elements linksOnPage2 = document.select("img[src]");
                linksOnPage2.forEach( l -> {
                    String origialLik = l.attr("src");
                    l.attr("src", "http://localhost:8090/?url="+origialLik);

                });

                Elements linksOnPage3 = document.select("style");
                linksOnPage3.forEach( l -> {
                    String origialLik = l.attr("style");
                    l.attr("style", "http://localhost:8090/?url="+origialLik);

                });


             /*   Matcher cssMatcher = Pattern.compile("[.](\\w+)\\s*[{]([^}]+)[}]").matcher(linksOnPage3.html());
                while (cssMatcher.find()) {
                    System.out.println("Style `" + cssMatcher.group(1) + "`: " + cssMatcher.group(2));
                }*/
                /*linksOnPage3.forEach( l -> {
                    String origialLik = l.attr("src");
                    l.attr("src", "http://localhost:8090/?url="+origialLik);

                });
*/
                return document.html();
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
        return "";
    }

    @Override
    public void addCookie(Cookie cookie) {

    }

    @Override
    public boolean containsHeader(String s) {
        return false;
    }

    @Override
    public String encodeURL(String s) {
        return null;
    }

    @Override
    public String encodeRedirectURL(String s) {
        return null;
    }

    @Override
    public String encodeUrl(String s) {
        return null;
    }

    @Override
    public String encodeRedirectUrl(String s) {
        return null;
    }

    @Override
    public void sendError(int i, String s) throws IOException {

    }

    @Override
    public void sendError(int i) throws IOException {

    }

    @Override
    public void sendRedirect(String s) throws IOException {

    }

    @Override
    public void setDateHeader(String s, long l) {

    }

    @Override
    public void addDateHeader(String s, long l) {

    }

    @Override
    public void setHeader(String s, String s1) {

    }

    @Override
    public void addHeader(String s, String s1) {

    }

    @Override
    public void setIntHeader(String s, int i) {

    }

    @Override
    public void addIntHeader(String s, int i) {

    }

    @Override
    public void setStatus(int i) {

    }

    @Override
    public void setStatus(int i, String s) {

    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public String getHeader(String s) {
        return null;
    }

    @Override
    public Collection<String> getHeaders(String s) {
        return null;
    }

    @Override
    public Collection<String> getHeaderNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentLengthLong(long l) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
