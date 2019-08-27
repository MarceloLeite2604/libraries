package com.github.marceloleite2604.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.github.marceloleite2604.util.exception.FileUtilException;

public class FileUtil {

	private static final int BUFFER_SIZE = 1024;

	private static final String[] UNITS = { "B", "kB", "MB", "GB", "TB", "PB", "EB" };

	private static final int BYTE_BLOCK_SIZE = 1024;

	public String retrieveTextContentFromFile(String filePath) {
		return retrieveTextContentFromFile(Paths.get(filePath));
	}

	public String retrieveTextContentFromFile(Path path) {
		return readTextContentFromFile(path);
	}

	private String readTextContentFromFile(Path path) {
		try (BufferedReader bufferedReader = createBufferedReader(path)) {
			return bufferedReader.lines()
					.collect(Collectors.joining(System.lineSeparator()));
		} catch (IOException exception) {
			String message = String.format(FileUtilMessageTemplates.ERROR_READING_FILE_CONTENT,
					path);
			throw new FileUtilException(exception, message);
		}
	}

	public byte[] retrieveBinaryContentFromFile(String filePath) {
		return retrieveBinaryContentFromFile(Paths.get(filePath));
	}

	public byte[] retrieveBinaryContentFromFile(Path path) {
		throwExceptionIfFileDoesNotExist(path);
		throwExceptionIfFileIsDirectory(path);
		return readBinaryContentFromFile(path);
	}

	private byte[] readBinaryContentFromFile(Path path) {
		try (FileInputStream fileInputStream = new FileInputStream(path.toFile())) {

			return readAllBytesFromInputStream(fileInputStream);

		} catch (IOException exception) {
			String message = String.format(FileUtilMessageTemplates.ERROR_READING_FILE_CONTENT,
					path);
			throw new FileUtilException(exception, message);
		}
	}

	private byte[] readAllBytesFromInputStream(InputStream fileInputStream) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		int length;
		byte[] result = new byte[0];

		while ((length = fileInputStream.read(buffer)) != -1) {
			result = ArrayUtils.addAll(result, ArrayUtils.subarray(buffer, 0, length));
		}

		return result;
	}

	private BufferedReader createBufferedReader(Path path) throws IOException {
		if (path.toFile()
				.exists()) {
			return Files.newBufferedReader(path, StandardCharsets.UTF_8);
		} else {
			InputStream inputStream = retrieveMandatoryInputStreamFromClasspath(path);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			return new BufferedReader(inputStreamReader);
		}

	}

	private InputStream retrieveMandatoryInputStreamFromClasspath(Path path) {
		InputStream inputStream = retrieveInputStreamFromClasspath(path.toString());
		if (inputStream == null) {
			String message = String.format(FileUtilMessageTemplates.FILE_DOES_NOT_EXIST, path);
			throw new FileUtilException(message);
		}
		return inputStream;
	}

	private InputStream retrieveInputStreamFromClasspath(String path) {
		return Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream(path);
	}

	public void writeContentOnFile(String path, String content) {
		writeContentOnFile(Paths.get(path), content);
	}

	public void writeContentOnFile(Path path, String content) {
		try (FileOutputStream fileOutputStream = new FileOutputStream(path.toFile());
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
		writeContentOnFile(Paths.get(path), bytes);
	}

	public void writeContentOnFile(Path path, byte[] bytes) {
		try (FileOutputStream fileOutputStream = new FileOutputStream(path.toFile())) {
			fileOutputStream.write(bytes);
		} catch (IOException exception) {
			String message = String.format(FileUtilMessageTemplates.ERROR_WRITING_CONTENT_ON_FILE,
					path);
			throw new FileUtilException(exception, message);
		}
	}

	public String retrieveFileSize(Path path) {
		long fileSizeBytes = path.toFile()
				.length();

		return formatAsHumanReadableSize(fileSizeBytes);
	}

	public String retrieveFileSize(String path) {
		return retrieveFileSize(Paths.get(path));
	}

	public String formatAsHumanReadableSize(long size) {
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

	public boolean fileExists(Path path) {
		return path.toFile()
				.exists();
	}

	public boolean fileExists(String filePath) {
		return fileExists(Paths.get(filePath));
	}

	public void throwExceptionIfDirectoryDoesNotExist(String path) {
		throwExceptionIfDirectoryDoesNotExist(Paths.get(path));
	}

	public void throwExceptionIfDirectoryDoesNotExist(Path path) {
		throwExceptionIfFileIsNotDirectory(path);

		File directory = path.toFile();

		if (!directory.exists()) {
			String message = String.format(FileUtilMessageTemplates.DIRECTORY_DOES_NOT_EXIST, path);
			throw new FileUtilException(message);
		}
	}

	public void throwExceptionIfFileIsNotDirectory(String path) {
		throwExceptionIfFileIsNotDirectory(Paths.get(path));
	}

	public void throwExceptionIfFileIsNotDirectory(Path path) {
		File directory = path.toFile();

		if (directory.isFile()) {
			String message = String.format(FileUtilMessageTemplates.FILE_IS_NOT_DIRECTORY, path);
			throw new FileUtilException(message);
		}
	}

	public void throwExceptionIfFileIsDirectory(String path) {
		throwExceptionIfFileIsDirectory(Paths.get(path));
	}

	public void throwExceptionIfFileIsDirectory(Path path) {
		File directory = path.toFile();

		if (!directory.isFile()) {
			String message = String.format(FileUtilMessageTemplates.FILE_IS_A_DIRECTORY, path);
			throw new FileUtilException(message);
		}
	}

	public String appendSeparatorIfNecessary(String text) {
		if (!text.endsWith(File.separator)) {
			return text.concat(File.separator);
		}

		return text;
	}

	public void throwExceptionIfFileDoesNotExist(String path) {
		throwExceptionIfFileDoesNotExist(Paths.get(path));
	}

	public void throwExceptionIfFileDoesNotExist(Path path) {
		if (!fileExists(path)) {
			String message = String.format(FileUtilMessageTemplates.FILE_DOES_NOT_EXIST, path);
			throw new FileUtilException(message);
		}
	}
}