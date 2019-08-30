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

/**
 * A series of handy methods which helps working with files.
 * 
 * @see <a href="http://www.github.com/MarceloLeite2604/libraries" target=
 *      "_top">GitHub project</a>
 * 
 * @author MarceloLeite2604
 *
 */
public class FileUtil {

	private static final int BUFFER_SIZE = 1024;

	private static final String[] UNITS = { "B", "kB", "MB", "GB", "TB", "PB", "EB" };

	private static final int BYTE_BLOCK_SIZE = 1024;

	/**
	 * Retrieves text content from a file.
	 * 
	 * @param filePath
	 *            Path to the file to be read.
	 * @return The file text content.
	 */
	public String retrieveTextContentFromFile(String filePath) {
		return retrieveTextContentFromFile(Paths.get(filePath));
	}

	/**
	 * Retrieves text content from a file.
	 * 
	 * @param filePath
	 *            Path to the file to be read.
	 * @return The file text content.
	 */
	public String retrieveTextContentFromFile(Path filePath) {
		return readTextContentFromFile(filePath);
	}

	private String readTextContentFromFile(Path path) {
		try (BufferedReader bufferedReader = createBufferedReader(path)) {
			return bufferedReader.lines()
					.collect(Collectors.joining(System.lineSeparator()));
		} catch (IOException exception) {
			String message = String.format(FileUtilMessageTemplates.ERROR_READING_FILE_CONTENT,
					path);
			throw new FileUtilRuntimeException(exception, message);
		}
	}

	/**
	 * Retrieves the binary content from a file.
	 * 
	 * @param filePath
	 *            Path to the file to be read.
	 * @return The file binary content.
	 */
	public byte[] retrieveBinaryContentFromFile(String filePath) {
		return retrieveBinaryContentFromFile(Paths.get(filePath));
	}

	/**
	 * Retrieves the binary content from a file.
	 * 
	 * @param filePath
	 *            Path to the file to be read.
	 * @return The file binary content.
	 */
	public byte[] retrieveBinaryContentFromFile(Path filePath) {
		throwExceptionIfFileDoesNotExist(filePath);
		throwExceptionIfFileIsDirectory(filePath);
		return readBinaryContentFromFile(filePath);
	}

	private byte[] readBinaryContentFromFile(Path path) {
		try (FileInputStream fileInputStream = new FileInputStream(path.toFile())) {

			return readAllBytesFromInputStream(fileInputStream);

		} catch (IOException exception) {
			String message = String.format(FileUtilMessageTemplates.ERROR_READING_FILE_CONTENT,
					path);
			throw new FileUtilRuntimeException(exception, message);
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
			throw new FileUtilRuntimeException(message);
		}
		return inputStream;
	}

	private InputStream retrieveInputStreamFromClasspath(String path) {
		return Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream(path);
	}

	/**
	 * Writes a text content on a file.
	 * 
	 * @param filePath
	 *            Path to the file to write text.
	 * @param content
	 *            The text to be written.
	 */
	public void writeContentOnFile(String filePath, String content) {
		writeContentOnFile(Paths.get(filePath), content);
	}

	/**
	 * Writes a text content on a file.
	 * 
	 * @param filePath
	 *            Path to the file to write text.
	 * @param content
	 *            The text to be written.
	 */
	public void writeContentOnFile(Path filePath, String content) {
		try (FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile());
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,
						StandardCharsets.UTF_8);
				BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
			bufferedWriter.write(content);
		} catch (IOException exception) {
			String message = String.format(FileUtilMessageTemplates.ERROR_WRITING_CONTENT_ON_FILE,
					filePath);
			throw new FileUtilRuntimeException(exception, message);
		}
	}

	/**
	 * Writes a binary content on a file.
	 * 
	 * @param filePath
	 *            Path to the file to write the content.
	 * @param bytes
	 *            Bytes to be written.
	 */
	public void writeContentOnFile(String filePath, byte[] bytes) {
		writeContentOnFile(Paths.get(filePath), bytes);
	}

	/**
	 * Writes a binary content on a file.
	 * 
	 * @param filePath
	 *            Path to the file to write the content.
	 * @param bytes
	 *            Bytes to be written.
	 */
	public void writeContentOnFile(Path filePath, byte[] bytes) {
		try (FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile())) {
			fileOutputStream.write(bytes);
		} catch (IOException exception) {
			String message = String.format(FileUtilMessageTemplates.ERROR_WRITING_CONTENT_ON_FILE,
					filePath);
			throw new FileUtilRuntimeException(exception, message);
		}
	}

	/**
	 * Retrieves the size of a file (in bytes).
	 * 
	 * @param filePath
	 *            Path to the file which the size must be retrieved.
	 * @return The file size in bytes.
	 */
	public long retrieveFileSize(String filePath) {
		return retrieveFileSize(Paths.get(filePath));
	}

	/**
	 * Retrieves the size of a file (in bytes).
	 * 
	 * @param filePath
	 *            Path to the file which the size must be retrieved.
	 * @return The file size in bytes.
	 */
	public long retrieveFileSize(Path filePath) {
		return filePath.toFile()
				.length();
	}

	/**
	 * Retrieved the size of a file formatted as text.
	 * 
	 * @param filePath
	 *            Path to the file which the size must be retrieved.
	 * @return A text informing the file size using the most considerable size unit
	 *         (kB, MB, GB, etc.).
	 */
	public String retrieveFileSizeFormattedAsText(String filePath) {
		return retrieveFileSizeFormattedAsText(Paths.get(filePath));
	}

	/**
	 * Retrieved the size of a file formatted as text.
	 * 
	 * @param path
	 *            Path to the file which the size must be retrieved.
	 * @return A text informing the file size using the most considerable size unit
	 *         (kB, MB, GB, etc.).
	 */
	public String retrieveFileSizeFormattedAsText(Path path) {
		long fileSizeBytes = retrieveFileSize(path);
		return formatAsHumanReadableSize(fileSizeBytes);
	}

	private String formatAsHumanReadableSize(long size) {
		int logSizeBaseBlock = (int) log(size, BYTE_BLOCK_SIZE);

		return String.format("%.1f %s", size / Math.pow(BYTE_BLOCK_SIZE, logSizeBaseBlock),
				UNITS[logSizeBaseBlock]);
	}

	private double log(double value, double base) {
		return Math.log(value) / Math.log(base);
	}

	/**
	 * Creates a directory.
	 * 
	 * @param directoryPath
	 *            Path of the directory to be created.
	 */
	public void createDirectoryIfDoesNotExist(String directoryPath) {
		createDirectoryIfDoesNotExist(Paths.get(directoryPath));
	}

	/**
	 * Creates a directory.
	 * 
	 * @param directoryPath
	 *            Path of the directory to be created.
	 */
	public void createDirectoryIfDoesNotExist(Path directoryPath) {
		File directory = directoryPath.toFile();
		if (!directory.exists()) {
			try {
				FileUtils.forceMkdir(directory);
			} catch (IOException exception) {
				String message = String.format(FileUtilMessageTemplates.ERROR_CREATING_DIRECTORY,
						directoryPath);
				throw new FileUtilRuntimeException(message);
			}
		}
	}

	/**
	 * Checks if a file exists.
	 * 
	 * @param filePath
	 *            Path to the file.
	 * @return {@code true} if file exists, {@code false} otherwise.
	 */
	public boolean fileExists(String filePath) {
		return fileExists(Paths.get(filePath));
	}

	/**
	 * Checks if a file exists.
	 * 
	 * @param filePath
	 *            Path to the file.
	 * @return {@code true} if file exists, {@code false} otherwise.
	 */
	public boolean fileExists(Path filePath) {
		return filePath.toFile()
				.exists();
	}

	/**
	 * Throws a {@link FileUtilRuntimeException} if the specified file does not
	 * exist.
	 * 
	 * @param filePath
	 *            Path to the directory which should be checked.
	 */
	public void throwExceptionIfFileDoesNotExist(String filePath) {
		throwExceptionIfFileDoesNotExist(Paths.get(filePath));
	}

	/**
	 * Throws a {@link FileUtilRuntimeException} if the specified file does not
	 * exist.
	 * 
	 * @param filePath
	 *            Path to the directory which should be checked.
	 */
	public void throwExceptionIfFileDoesNotExist(Path filePath) {
		if (!fileExists(filePath)) {
			String message = String.format(FileUtilMessageTemplates.FILE_DOES_NOT_EXIST, filePath);
			throw new FileUtilRuntimeException(message);
		}
	}

	/**
	 * Throws a {@link FileUtilRuntimeException} if the specified directory does not
	 * exist.
	 * 
	 * @param directoryPath
	 *            Path to the directory which should be checked.
	 */
	public void throwExceptionIfDirectoryDoesNotExist(String directoryPath) {
		throwExceptionIfDirectoryDoesNotExist(Paths.get(directoryPath));
	}

	/**
	 * Throws a {@link FileUtilRuntimeException} if the specified directory does not
	 * exist.
	 * 
	 * @param directoryPath
	 *            Path to the directory which should be checked.
	 */
	public void throwExceptionIfDirectoryDoesNotExist(Path directoryPath) {
		throwExceptionIfFileIsNotDirectory(directoryPath);

		File directory = directoryPath.toFile();

		if (!directory.exists()) {
			String message = String.format(FileUtilMessageTemplates.DIRECTORY_DOES_NOT_EXIST,
					directoryPath);
			throw new FileUtilRuntimeException(message);
		}
	}

	/**
	 * Throws a {@link FileUtilRuntimeException} if the specified file is not a
	 * directory.
	 * 
	 * @param filePath
	 *            Path to the file which should be checked.
	 */
	public void throwExceptionIfFileIsNotDirectory(String filePath) {
		throwExceptionIfFileIsNotDirectory(Paths.get(filePath));
	}

	/**
	 * Throws a {@link FileUtilRuntimeException} if the specified file is not a
	 * directory.
	 * 
	 * @param filePath
	 *            Path to the file which should be checked.
	 */
	public void throwExceptionIfFileIsNotDirectory(Path filePath) {
		File directory = filePath.toFile();

		if (directory.isFile()) {
			String message = String.format(FileUtilMessageTemplates.FILE_IS_NOT_DIRECTORY,
					filePath);
			throw new FileUtilRuntimeException(message);
		}
	}

	/**
	 * Throws a {@link FileUtilRuntimeException} if the specified file is a
	 * directory.
	 * 
	 * @param filePath
	 *            Path to the file which should be checked.
	 */
	public void throwExceptionIfFileIsDirectory(String filePath) {
		throwExceptionIfFileIsDirectory(Paths.get(filePath));
	}

	/**
	 * Throws a {@link FileUtilRuntimeException} if the specified file is a
	 * directory.
	 * 
	 * @param filePath
	 *            Path to the file which should be checked.
	 */
	public void throwExceptionIfFileIsDirectory(Path filePath) {
		File directory = filePath.toFile();

		if (!directory.isFile()) {
			String message = String.format(FileUtilMessageTemplates.FILE_IS_A_DIRECTORY, filePath);
			throw new FileUtilRuntimeException(message);
		}
	}

	/**
	 * Appends the {@link File#separator} a the text if it does not have it already.
	 * 
	 * @param text
	 *            Text to append the {@link File#separator}
	 * @return The content of the {@code text} parameter, plus the
	 *         {@link File#separator} character appended if it does not have it
	 *         already.
	 */
	public String appendSeparatorIfNecessary(String text) {
		if (!text.endsWith(File.separator)) {
			return text.concat(File.separator);
		}

		return text;
	}
}