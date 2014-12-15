package com.imaginea.mailcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class ApacheMailDownloader {

	private static final Logger LOGGER = Logger
			.getLogger(ApacheMailDownloader.class);

	private Configuration configuration;

	private Crawler crawler;

	private Downloader downloader;

	public ApacheMailDownloader(Configuration configuration) {
		this.configuration = configuration;
	}

	public Crawler getCrawler() {
		return crawler;
	}

	public void setCrawler(Crawler crawler) {
		this.crawler = crawler;
	}

	public Downloader getDownloader() {
		return downloader;
	}

	public void setDownloader(Downloader downloader) {
		this.downloader = downloader;
	}

	public void download(String year, DownloadDestination destination)
			throws MalformedURLException, IOException {

		Set<URL> urlSet = this.crawler
				.crawl(new URL(configuration.getBaseURL()),
						configuration.getPattern());
		this.downloader.download(urlSet, destination);
	}

	public static class Configuration {

		private String baseURL;

		private Pattern pattern;

		public Configuration(String baseURL, Pattern pattern) {
			this.setBaseURL(baseURL);
			this.setPattern(pattern);
		}

		public String getBaseURL() {
			return baseURL;
		}

		public void setBaseURL(String baseURL) {
			this.baseURL = baseURL;
		}

		public Pattern getPattern() {
			return pattern;
		}

		public void setPattern(Pattern pattern) {
			this.pattern = pattern;
		}
	}

	public static void main(String args[]) throws MalformedURLException,
			IOException {
		final int args_num = 1;
		if (args.length == args_num) {
			String outDirPath = args[0];

			Configuration configuration = new Configuration(
					"http://mail-archives.apache.org/mod_mbox/maven-users/",
					Pattern.compile(".*/raw/.*/.*"));

			ApacheMailDownloader apacheMailDownloader = new ApacheMailDownloader(
					configuration);
			apacheMailDownloader.setCrawler(new Crawler());
			apacheMailDownloader.setDownloader(new Downloader());

			FileBasedDownloadDesitnation destination = new FileBasedDownloadDesitnation(
					outDirPath);
			apacheMailDownloader.download("2014", destination);
		} else {
			throw new IllegalArgumentException("One argument must be provided");
		}
	}
}