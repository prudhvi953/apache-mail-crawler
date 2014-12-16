package com.imaginea.mailcrawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;

import org.apache.log4j.Logger;

public class Downloader {
	private static final Logger LOGGER = Logger.getLogger(Downloader.class);

	public void download(URL url, DownloadDestination destination)
			throws IOException {
		// TODO Use autoclosable for inputstream
		try (InputStream inputStream = url.openConnection().getInputStream()) {
			destination.put(url.getPath(), inputStream);
		} catch (IOException e) {
			try (FileWriter fw = new FileWriter("resume", true); 
			BufferedWriter bw = new BufferedWriter(fw)) {
			bw.write(url.toString() + "\n");
			LOGGER.info("writing to file" + url.toString());
			}
		}
	}

	public void download(Collection<URL> urls, DownloadDestination destination)
			throws IOException {		
		for (URL url : urls) {
			LOGGER.info("Downloading URLs" + url);
			this.download(url, destination);
		}
	}
}
