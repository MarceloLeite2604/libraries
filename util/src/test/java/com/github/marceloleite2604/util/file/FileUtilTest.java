package com.github.marceloleite2604.util.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.io.File;
import java.nio.file.Path;
import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.marceloleite2604.util.exception.FileUtilException;

public class FileUtilTest {

	private static final String TESTING_DIRECTORY = "src/test/resources/FileUtilTest".replace("/",
			File.separator);

	private static final String READ_TEXT_FILE_PATH = TESTING_DIRECTORY + File.separator
			+ "read-text-file.txt";

	private static final String READ_TEXT_FILE_CONTENT = "This is a text file for reading tests.";

	private static final String READ_BINARY_FILE_PATH = TESTING_DIRECTORY + File.separator
			+ "read-binary-file.bin";

	private static final byte[] READ_BINARY_FILE_CONTENT = { (byte) 0x4e, (byte) 0x6e, (byte) 0x98,
			(byte) 0x26, (byte) 0x57, (byte) 0xc0, (byte) 0x29, (byte) 0x1d, (byte) 0x8c,
			(byte) 0xe9, (byte) 0x52, (byte) 0xbc, (byte) 0x53, (byte) 0xb7, (byte) 0xd9,
			(byte) 0x0d };

	private static final String WRITE_TEXT_FILE_PATH = TESTING_DIRECTORY + File.separator
			+ "write-text-file.txt";

	private static final String WRITE_TEXT_FILE_CONTENT = "This is a text file for writing test.";

	private static final String WRITE_BINARY_FILE_PATH = TESTING_DIRECTORY + File.separator
			+ "write-binary-file.bin";

	private static final byte[] WRITE_BINARY_FILE_CONTENT = { (byte) 0x63, (byte) 0x14, (byte) 0xf2,
			(byte) 0x2e, (byte) 0xac, (byte) 0x01 };

	private static final String[] SIZED_FILES_PATHS = {
			TESTING_DIRECTORY + File.separator + "12-bytes-file.bin",
			TESTING_DIRECTORY + File.separator + "5-kilobytes-file.bin",
			TESTING_DIRECTORY + File.separator + "12-point-34-kilobytes-file.bin" };

	private static final String[] SIZED_FILES_SIZES = { "12.0 B", "5.0 kB", "12.3 kB" };

	private static final String EXISTENT_DIRECTORY_PATH = TESTING_DIRECTORY + File.separator
			+ "existent-directory";

	private static final String INEXISTENT_DIRECTORY_PATH = TESTING_DIRECTORY + File.separator
			+ "inexistent-directory";

	private static final String EXISTENT_DIRECTORY_PATH_WITH_SEPARATOR = EXISTENT_DIRECTORY_PATH
			+ File.separator;

	// private static final String INVALID_FILE_PATH = TESTING_DIRECTORY +
	// File.separator + ":";

	private static final Locale DEFAULT_LOCALE = Locale.getDefault();

	private static final Locale TESTING_LOCALE = Locale.ENGLISH;

	private FileUtil fileUtil;

	@BeforeClass
	public static void classSetUp() {
		Locale.setDefault(TESTING_LOCALE);
		createExistentDirectory();
	}

	private static void createExistentDirectory() {
		new File(EXISTENT_DIRECTORY_PATH).mkdir();
	}

	@AfterClass
	public static void classTearDown() {
		Locale.setDefault(DEFAULT_LOCALE);
	}

	@Before
	public void setUp() {
		this.fileUtil = new FileUtil();
	}

	@After
	public void tearDown() {
		deleteWriteTextFileIfExists();
		deleteWriteBinaryFileIfExists();
		deleteInexistentDirectoryIfExists();
	}

	private void deleteWriteTextFileIfExists() {
		deleteFileIfExists(new File(WRITE_TEXT_FILE_PATH));
	}

	private void deleteWriteBinaryFileIfExists() {
		deleteFileIfExists(new File(WRITE_BINARY_FILE_PATH));
	}

	private void deleteInexistentDirectoryIfExists() {
		deleteFileIfExists(new File(INEXISTENT_DIRECTORY_PATH));
	}

	private void deleteFileIfExists(File file) {
		if (file.exists()) {
			file.delete();
		}
	}

	@Test
	public void testRetrieveTextContentFromFileString() throws Exception {
		// Act
		String content = fileUtil.retrieveTextContentFromFile(READ_TEXT_FILE_PATH);

		// Assert
		assertThat(content).isEqualTo(READ_TEXT_FILE_CONTENT);
	}

	@Test
	public void testRetrieveTextContentFromFilePath() throws Exception {
		// Arrange
		Path filePath = Path.of(READ_TEXT_FILE_PATH);

		// Act
		String content = fileUtil.retrieveTextContentFromFile(filePath);

		// Assert
		assertThat(content).isEqualTo(READ_TEXT_FILE_CONTENT);
	}

	@Test
	public void testRetrieveBinaryContentFromFilePath() throws Exception {
		// Act
		byte[] content = fileUtil.retrieveBinaryContentFromFile(Path.of(READ_BINARY_FILE_PATH));

		// Assert
		assertThat(content).isEqualTo(READ_BINARY_FILE_CONTENT);
	}

	@Test(expected = FileUtilException.class)
	public void testRetrieveBinaryContentFromFilePathShouldThrowFileUtilExceptionWhenFileDoesNotExist()
			throws Exception {
		// Act
		fileUtil.retrieveBinaryContentFromFile(Path.of(INEXISTENT_DIRECTORY_PATH));

		// Assert
		fail("Should have thrown an exception.");
	}

	@Test
	public void testRetrieveBinaryContentFromFileString() throws Exception {
		// Act
		byte[] content = fileUtil.retrieveBinaryContentFromFile(READ_BINARY_FILE_PATH);

		// Assert
		assertThat(content).isEqualTo(READ_BINARY_FILE_CONTENT);
	}

	@Test
	public void testWriteContentOnFileStringString() throws Exception {
		// Act
		fileUtil.writeContentOnFile(WRITE_TEXT_FILE_PATH, WRITE_TEXT_FILE_CONTENT);
		String content = fileUtil.retrieveTextContentFromFile(WRITE_TEXT_FILE_PATH);

		// Assert
		assertThat(content).isEqualTo(WRITE_TEXT_FILE_CONTENT);
	}

	@Test
	public void testWriteContentOnFilePathString() throws Exception {
		// Act
		fileUtil.writeContentOnFile(Path.of(WRITE_TEXT_FILE_PATH), WRITE_TEXT_FILE_CONTENT);
		String content = fileUtil.retrieveTextContentFromFile(WRITE_TEXT_FILE_PATH);

		// Assert
		assertThat(content).isEqualTo(WRITE_TEXT_FILE_CONTENT);
	}

	@Test
	public void testWriteContentOnFileStringByteArray() throws Exception {
		// Act
		fileUtil.writeContentOnFile(WRITE_BINARY_FILE_PATH, WRITE_BINARY_FILE_CONTENT);
		byte[] content = fileUtil.retrieveBinaryContentFromFile(WRITE_BINARY_FILE_PATH);

		// Assert
		assertThat(content).isEqualTo(WRITE_BINARY_FILE_CONTENT);
	}

	@Test
	public void testWriteContentOnFilePathByteArray() throws Exception {
		// Act
		fileUtil.writeContentOnFile(Path.of(WRITE_BINARY_FILE_PATH), WRITE_BINARY_FILE_CONTENT);
		byte[] content = fileUtil.retrieveBinaryContentFromFile(WRITE_BINARY_FILE_PATH);

		// Assert
		assertThat(content).isEqualTo(WRITE_BINARY_FILE_CONTENT);
	}

	@Test
	public void testFormatAsHumanReadableSize() throws Exception {
		// Arrange
		long size = 46694L;
		String expectedHumanReadableSize = "45.6 kB";

		// Act
		String actualHumanReadableSize = fileUtil.formatAsHumanReadableSize(size);

		// Assert
		assertThat(actualHumanReadableSize).isEqualTo(expectedHumanReadableSize);
	}

	@Test
	public void testRetrieveFileSizePath() throws Exception {

		String[] actualFilesSizes = new String[SIZED_FILES_PATHS.length];

		for (int index = 0; index < actualFilesSizes.length; index++) {

			// Arrange
			Path filePath = Path.of(SIZED_FILES_PATHS[index]);

			// Act
			actualFilesSizes[index] = fileUtil.retrieveFileSize(filePath);
		}

		// Assert
		assertThat(actualFilesSizes).isEqualTo(SIZED_FILES_SIZES);
	}
	
	@Test
	public void testRetrieveFileSizeString() throws Exception {

		String[] actualFilesSizes = new String[SIZED_FILES_PATHS.length];

		for (int index = 0; index < actualFilesSizes.length; index++) {

			// Arrange
			String filePath = SIZED_FILES_PATHS[index];

			// Act
			actualFilesSizes[index] = fileUtil.retrieveFileSize(filePath);
		}

		// Assert
		assertThat(actualFilesSizes).isEqualTo(SIZED_FILES_SIZES);
	}

	@Test
	public void testCreateDirectoryIfDoesNotExist() throws Exception {
		// Arrange
		File inexistentDirectory = new File(INEXISTENT_DIRECTORY_PATH);
		assertThat(inexistentDirectory.exists()).isFalse();

		// Act
		fileUtil.createDirectoryIfDoesNotExist(INEXISTENT_DIRECTORY_PATH);

		// Assert
		assertThat(inexistentDirectory.exists()).isTrue();
	}

	@Test
	public void testFileExistsStringShouldReturnTrueWhenFileExists() throws Exception {
		// Arrange
		boolean expectedResult = true;

		// Act
		boolean actualResult = fileUtil.fileExists(EXISTENT_DIRECTORY_PATH);

		// Assert
		assertThat(actualResult).isEqualTo(expectedResult);
	}

	@Test
	public void testFileExistsStringShouldReturnFalseWhenFileDoesNotExist() throws Exception {
		// Arrange
		boolean expectedResult = false;

		// Act
		boolean actualResult = fileUtil.fileExists(INEXISTENT_DIRECTORY_PATH);

		// Assert
		assertThat(actualResult).isEqualTo(expectedResult);
	}

	@Test
	public void testFileExistsPathShouldReturnTrueWhenFileExists() throws Exception {
		// Arrange
		boolean expectedResult = true;

		// Act
		boolean actualResult = fileUtil.fileExists(Path.of(EXISTENT_DIRECTORY_PATH));

		// Assert
		assertThat(actualResult).isEqualTo(expectedResult);
	}

	@Test
	public void testFileExistsPathShouldReturnFalseWhenFileDoesNotExist() throws Exception {
		// Arrange
		boolean expectedResult = false;

		// Act
		boolean actualResult = fileUtil.fileExists(Path.of(INEXISTENT_DIRECTORY_PATH));

		// Assert
		assertThat(actualResult).isEqualTo(expectedResult);
	}

	@Test(expected = FileUtilException.class)
	public void testThrowExceptionIfDirectoryDoesNotExistStringShouldThrowFileUtilExceptionWhenDirectoryDoesNotExist()
			throws Exception {
		// Act
		fileUtil.throwExceptionIfDirectoryDoesNotExist(INEXISTENT_DIRECTORY_PATH);

		// Assert
		fail("Should have thrown an exception.");
	}

	@Test
	public void testThrowExceptionIfDirectoryDoesNotExistStringShouldContinueWhenDirectoryExists()
			throws Exception {
		// Act
		fileUtil.throwExceptionIfDirectoryDoesNotExist(EXISTENT_DIRECTORY_PATH);
	}

	@Test(expected = FileUtilException.class)
	public void testThrowExceptionIfDirectoryDoesNotExistPathShouldThrowFileUtilExceptionWhenDirectoryDoesNotExist()
			throws Exception {
		// Act
		fileUtil.throwExceptionIfDirectoryDoesNotExist(Path.of(INEXISTENT_DIRECTORY_PATH));

		// Assert
		fail("Should have thrown an exception.");
	}

	@Test
	public void testThrowExceptionIfDirecttoryDoesNotExistPathShouldContinueWhenDirectoryExists()
			throws Exception {
		// Act
		fileUtil.throwExceptionIfDirectoryDoesNotExist(Path.of(EXISTENT_DIRECTORY_PATH));
	}

	@Test(expected = FileUtilException.class)
	public void testThrowExceptionIfFileIsNotDirectoryStringShouldThrowFileUtilExceptionWhenFileIsNotDirectory()
			throws Exception {
		// Act
		fileUtil.throwExceptionIfFileIsNotDirectory(READ_TEXT_FILE_PATH);

		// Assert
		fail("Should have thrown an exception.");
	}

	@Test
	public void testThrowExceptionIfFileIsNotDirectoryStringShouldContinueWhenFileIsDirectory()
			throws Exception {
		// Act
		fileUtil.throwExceptionIfFileIsNotDirectory(EXISTENT_DIRECTORY_PATH);
	}

	@Test(expected = FileUtilException.class)
	public void testThrowExceptionIfFileIsNotDirectoryPathShouldThrowFileUtilExceptionWhenFileIsNotDirectory()
			throws Exception {
		// Act
		fileUtil.throwExceptionIfFileIsNotDirectory(Path.of(READ_TEXT_FILE_PATH));

		// Assert
		fail("Should have thrown an exception.");
	}

	@Test
	public void testThrowExceptionIfFileIsNotDirectoryPathShouldContinueWhenFileIsDirectory()
			throws Exception {
		// Act
		fileUtil.throwExceptionIfFileIsNotDirectory(Path.of(EXISTENT_DIRECTORY_PATH));
	}

	@Test
	public void testAppendSeparatorIfNecessaryShouldAppendSeparatorWhenPathDoesEndWithSeparator()
			throws Exception {
		// Arrange
		String expectedPath = EXISTENT_DIRECTORY_PATH_WITH_SEPARATOR;

		// Act
		String actualPath = fileUtil.appendSeparatorIfNecessary(EXISTENT_DIRECTORY_PATH);

		// Assert
		assertThat(actualPath).isEqualTo(expectedPath);
	}

	@Test
	public void testAppendSeparatorIfNecessaryShouldNotReturnSameContentWhenPathEndsWithSeparator()
			throws Exception {
		// Arrange
		String expectedPath = EXISTENT_DIRECTORY_PATH_WITH_SEPARATOR;

		// Act
		String actualPath = fileUtil
				.appendSeparatorIfNecessary(EXISTENT_DIRECTORY_PATH_WITH_SEPARATOR);

		// Assert
		assertThat(actualPath).isEqualTo(expectedPath);
	}

	@Test(expected = FileUtilException.class)
	public void testThrowExceptionIfFileDoesNotExistStringShouldThrowFileUtilExceptionWhenFileDoesNotExist()
			throws Exception {
		// Act
		fileUtil.throwExceptionIfFileDoesNotExist(INEXISTENT_DIRECTORY_PATH);

		// Assert
		fail("Should have thrown an exception.");
	}

	@Test
	public void testThrowExceptionIfFileDoesNotExistStringShouldContinueWhenFileExists()
			throws Exception {
		// Act
		fileUtil.throwExceptionIfFileDoesNotExist(EXISTENT_DIRECTORY_PATH);
	}

	@Test(expected = FileUtilException.class)
	public void testThrowExceptionIfFileDoesNotExistPathShouldThrowFileUtilExceptionWhenFileDoesNotExist()
			throws Exception {
		// Act
		fileUtil.throwExceptionIfFileDoesNotExist(Path.of(INEXISTENT_DIRECTORY_PATH));

		// Assert
		fail("Should have thrown an exception.");
	}

	@Test
	public void testThrowExceptionIfFileDoesNotExistPathShouldContinueWhenFileExists()
			throws Exception {
		// Act
		fileUtil.throwExceptionIfFileDoesNotExist(Path.of(EXISTENT_DIRECTORY_PATH));
	}

	@Test(expected = FileUtilException.class)
	public void testThrowExceptionIfFileIsDirectoryStringShouldThrowFileUtilExceptionWhenFileIsDirectory() throws Exception {
		// Act
		fileUtil.throwExceptionIfFileIsDirectory(EXISTENT_DIRECTORY_PATH);
		
		// Assert
		fail("Should have thrown an exception.");
	}
	
	@Test
	public void testThrowExceptionIfFileIsDirectoryStringShouldContinueWhenFileIsNotDirectory() throws Exception {
		// Act
		fileUtil.throwExceptionIfFileIsDirectory(READ_TEXT_FILE_PATH);
	}
	
	@Test(expected = FileUtilException.class)
	public void testThrowExceptionIfFileIsDirectoryPathShouldThrowFileUtilExceptionWhenFileIsDirectory() throws Exception {
		// Act
		fileUtil.throwExceptionIfFileIsDirectory(Path.of(EXISTENT_DIRECTORY_PATH));
		
		// Assert
		fail("Should have thrown an exception.");
	}
	
	@Test
	public void testThrowExceptionIfFileIsDirectoryPathShouldContinueWhenFileIsNotDirectory() throws Exception {
		// Act
		fileUtil.throwExceptionIfFileIsDirectory(Path.of(READ_TEXT_FILE_PATH));
	}

}
