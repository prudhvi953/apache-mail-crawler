package com.imaginea.mailcrawler;

import java.io.IOException;
import java.io.InputStream;

public interface DownloadDestination {

	public void put(String name, InputStream inputStream) throws IOException;

}
