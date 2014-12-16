package com.imaginea.mailcrawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

public class FileBasedDownloadDesitnation implements DownloadDestination {

	private File workDir;

	public FileBasedDownloadDesitnation(String workDir) {
		this.workDir = new File(workDir);
	}

	public void put(String name, InputStream inputStream) throws IOException {
		File outputFile = new File(this.workDir, name);

		if (!outputFile.exists()) {
			if (outputFile.getParentFile() != null) {
				outputFile.getParentFile().mkdirs();
			}
			outputFile.createNewFile();
		}
		
		//TODO Use Autoclosable
		try (OutputStream outputStream = new FileOutputStream(outputFile)) {
		IOUtils.copy(inputStream, outputStream);
		}
	}
}
