package com.imaginea.mailcrawler;

import java.io.IOException;
import java.net.URL;
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

	public Set<URL> crawl(URL url, Pattern pattern) throws IOException {
		Set<URL> urlSet = new HashSet<URL>();
		Queue<URL> urlQueue = new LinkedList<URL>();
		JsoupDocument jsoupDocument = new JsoupDocument();
		// Hard code validUrl for faster output for 
		URL validUrl = new URL(
				"http://mail-archives.apache.org/mod_mbox/maven-users/201412");

		do {
			Document doc = jsoupDocument.getDocument(url.toString());
			Elements links = doc.select(anchor_tag);
			for (Element link : links) {
				URL absUrl = new URL(link.attr(abs_href));
				if (absUrl.toString().contains(validUrl.toString())
						&& !urlSet.contains(absUrl)) {
					urlSet.add(absUrl);
					urlQueue.add(absUrl);
					Crawler.LOGGER.info("Crawled:" + absUrl);
				}
			}
			url = urlQueue.isEmpty() ? null : urlQueue.remove();
		} while (url != null);

		Set<URL> filteredUrlSet = new HashSet<URL>();
		for (URL filteredUrl : urlSet) {
			Matcher matcher = pattern.matcher(filteredUrl.toString());
			if (matcher.matches()) {
				filteredUrlSet.add(filteredUrl);
			}
		}
		return filteredUrlSet;
	}
}
