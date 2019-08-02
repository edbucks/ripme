package com.rarchives.ripme.ripper.rippers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.rarchives.ripme.ripper.AbstractHTMLRipper;
import com.rarchives.ripme.utils.Http;

public class SuperheroinecomixxxRipper extends AbstractHTMLRipper {

    public SuperheroinecomixxxRipper(URL url) throws IOException {
        super(url);
    }

    @Override
    public String getHost() {
        return "superheroinecomixxx";
    }

    @Override
    public String getDomain() {
        return "superheroinecomixxx.com";
    }

    @Override
    public String getGID(URL url) throws MalformedURLException {
	Pattern p = Pattern.compile("https?://superheroinecomixxx.com/([a-zA-Z0-9_-]+)/([a-zA-Z0-9_-]+)/?");                                                            
	Matcher m = p.matcher(url.toExternalForm());
        if (m.matches()) {
            return m.group(1);
        }
        throw new MalformedURLException("Expected superheroinecomixxx URL format: " +
                "superheroinecomixxx.com/Public/Album - got " + url + " instead");
    }

    @Override
    public Document getFirstPage() throws IOException {
        // "url" is an instance field of the superclass
        return Http.url(url).get();
    }

    @Override
    public List<String> getURLsFromPage(Document doc) {
        List<String> result = new ArrayList<>();
	for (Element el : doc.select("img")) {
	    result.add("https://" + getDomain() + el.attr("src"));
	}
	for (Element el : doc.select("[href*=.avi]")) {
	    result.add("https://" + getDomain() + el.attr("href"));
	}
	return result;
    }

    @Override
    public void downloadURL(URL url, int index) {

        addURLToDownload(url, getPrefix(index));
    }
}
