package com.imaginea.mailcrawler;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

	private static final Logger LOGGER = Logger.getLogger(Crawler.class);
	private final String anchor_tag = "a";
	private final String abs_href = "abs:href";

	public Collection<URL> crawl(URL url, Pattern pattern) throws IOException {
		Collection<URL> urlCollection = new HashSet<URL>();
		Queue<URL> urlQueue = new LinkedList<URL>();
		JsoupDocument jsoupDocument = new JsoupDocument();
		// Hard code validUrl for faster output for now
		URL validURL = new URL(
				"http://mail-archives.apache.org/mod_mbox/maven-users/201412");
		
		int count = 0;

		do {
			Document doc = jsoupDocument.getDocument(url.toString());
			Elements links = doc.select(anchor_tag);
			for (Element link : links) {
				URL absURL = new URL(link.attr(abs_href));
				if (absURL.toString().contains(validURL.toString())
						&& !urlCollection.contains(absURL)) {
					urlCollection.add(absURL);
					urlQueue.add(absURL);
					LOGGER.info("Crawled:" + absURL);
					count++;
				}
			}
			url = urlQueue.isEmpty() ? null : urlQueue.remove();
			/*if (count > 20) {
				return urlSet;
			}*/
		} while (url != null);

		Collection<URL> filteredURLCollection = new HashSet<URL>();
		for (URL filteredUrl : urlCollection) {
			Matcher matcher = pattern.matcher(filteredUrl.toString());
			if (matcher.matches()) {
				filteredURLCollection.add(filteredUrl);
			}
		}
		return filteredURLCollection;
	}
}
