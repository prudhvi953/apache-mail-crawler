package com.imaginea.mailcrawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupDocument {
	private int maxPageCrawlTimeout = 1*1000;
	private boolean ignoreContentType = true;
	private boolean ignoreHttpErrors = true;

	public Document getDocument(String url) throws IOException {
		return Jsoup.connect(url).ignoreContentType(ignoreContentType)
				.timeout(maxPageCrawlTimeout)
				.ignoreHttpErrors(ignoreHttpErrors).get();
	}
}
