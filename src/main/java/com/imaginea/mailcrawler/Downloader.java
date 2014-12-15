package com.imaginea.mailcrawler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;

public class Downloader {

	public void download(URL url, DownloadDestination destination)
			throws IOException {
		// TODO Use autoclosable for inputstream
		InputStream inputStream = url.openConnection().getInputStream();
		try {
			destination.put(url.getPath(), inputStream);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	public void download(Collection<URL> urls, DownloadDestination destination)
			throws IOException {
		for (URL url : urls) {
			this.download(url, destination);
		}
	}
}
