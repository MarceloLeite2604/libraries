package com.github.marceloleite2604.flask;

import java.io.File;

import com.github.marceloleite2604.util.file.FileUtil;

public class Flask {

	private String directory;

	private FileUtil fileUtil;

	public Flask(String directory) {
		this.directory = directory;
		this.fileUtil = new FileUtil();
	}

	public Flask() {
		this(null);
	}

	public String loadContent(String filePath) {

		String completeFilePath = appendDirectory(filePath);

		return fileUtil.retrieveTextContentFromFile(completeFilePath);
	}

	private String appendDirectory(String filePath) {
		if (directory != null) {
			return directory + File.separator + filePath;
		}
		return filePath;
	}
}
