package com.github.marceloleite2604.flask;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

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

		URL url = this.getClass()
				.getClassLoader()
				.getResource(completeFilePath);

		Path path = null;
		try {
			path = Paths.get(url.toURI());
		} catch (URISyntaxException exception) {
			String message = String.format(FlaskMessageTemplates.ERROR_RETRIEVING_FILE_CONTENT,
					filePath);
			throw new FlaskRuntimeException(message);
		}

		return fileUtil.retrieveContentFromFile(path);
	}

	private String appendDirectory(String filePath) {
		if (directory != null) {
			return directory + File.separator + filePath;
		}
		return filePath;
	}
}
