package com.github.marceloleite2604.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import com.github.marceloleite2604.util.exception.FileUtilException;

public class FileUtil {

	private static final String[] UNITS = { "B", "kB", "MB", "GB", "TB", "PB", "EB" };

	private static final int BYTE_BLOCK_SIZE = 1024;

	public String retrieveContentFromFile(String filePath) {
		return retrieveContentFromFile(Paths.get(filePath));
	}

	public String retrieveContentFromFile(Path path) {

		try (BufferedReader bufferedReader = createBufferedReader(path, StandardCharsets.UTF_8)) {
			return bufferedReader.lines()
					.collect(Collectors.joining(System.lineSeparator()));
		} catch (IOException exception) {
			String message = String.format(FileUtilMessageTemplates.ERROR_READING_FILE_CONTENT,
					path);
			throw new FileUtilException(exception, message);
		}
	}

	private BufferedReader createBufferedReader(Path path, Charset charset) throws IOException {
		if (path.toFile()
				.exists()) {
			return Files.newBufferedReader(path, charset);
		} else {
			InputStream inputStream = createInputStreamFromClasspath(path.toString());
			if (inputStream == null) {
				String message = String.format(FileUtilMessageTemplates.FILE_DOES_NOT_EXIST, path);
				throw new FileUtilException(message);
			}
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			return new BufferedReader(inputStreamReader);
		}

	}

	private InputStream createInputStreamFromClasspath(String caminhoArquivo) {
		return Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream(caminhoArquivo);
	}

	public void writeContentOnFile(String path, String content) {
		try (FileOutputStream fileOutputStream = new FileOutputStream(path);
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,
						StandardCharsets.UTF_8);
				BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
			bufferedWriter.write(content);
		} catch (IOException exception) {
			String message = String.format(FileUtilMessageTemplates.ERROR_WRITING_CONTENT_ON_FILE,
					path);
			throw new FileUtilException(exception, message);
		}
	}

	public void writeContentOnFile(String path, byte[] bytes) {
		try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
			fileOutputStream.write(bytes);
		} catch (IOException exception) {
			String message = String.format(FileUtilMessageTemplates.ERROR_WRITING_CONTENT_ON_FILE,
					path);
			throw new FileUtilException(exception, message);
		}
	}

	public String formatAsHumanReadableSize(int size) {
		int logSizeBaseBlock = (int) log(size, BYTE_BLOCK_SIZE);

		return String.format("%.1f %s", size / Math.pow(BYTE_BLOCK_SIZE, logSizeBaseBlock),
				UNITS[logSizeBaseBlock]);
	}

	private double log(double value, double base) {
		return Math.log(value) / Math.log(base);
	}

	public void createDirectoryIfDoesNotExist(String directoryPath) {
		File directory = Paths.get(directoryPath)
				.toFile();

		if (!directory.exists()) {
			try {
				FileUtils.forceMkdir(directory);
			} catch (IOException exception) {
				String message = String.format(FileUtilMessageTemplates.ERROR_CREATING_DIRECTORY,
						directoryPath);
				throw new FileUtilException(message);
			}
		}
	}

	public boolean fileExists(String filePath) {
		return Paths.get(filePath)
				.toFile()
				.exists();
	}

	public void throwErrorIfDirectoryDoesNotExist(String directoryPath) {
		throwErrorIfFileIsNotDirectory(directoryPath);

		File directory = Paths.get(directoryPath)
				.toFile();

		if (!directory.exists()) {
			String message = String.format(FileUtilMessageTemplates.DIRECTORY_DOES_NOT_EXIST,
					directoryPath);
			throw new FileUtilException(message);
		}
	}

	public void throwErrorIfFileIsNotDirectory(String directoryPath) {
		File directory = Paths.get(directoryPath)
				.toFile();

		if (directory.isFile()) {
			String message = String.format(FileUtilMessageTemplates.FILE_IS_NOT_DIRECTORY,
					directoryPath);
			throw new FileUtilException(message);
		}
	}

	public String appendSeparatorIfNecessary(String text) {
		if (!text.endsWith(File.separator)) {
			return text.concat(File.separator);
		}

		return text;
	}
}