package com.figtreelake.util.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import com.figtreelake.util.file.FileUtil;
import com.figtreelake.util.file.FileUtilRuntimeException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.agent.PowerMockAgent;
import org.powermock.modules.junit4.rule.PowerMockRule;

@PrepareForTest({FileUtil.class, FileUtils.class, BufferedWriter.class, FileOutputStream.class})
public class FileUtilTest {

  private static final String TESTING_DIRECTORY =
      ("src/test/resources/" + FileUtilTest.class.getSimpleName()).replace("/", File.separator);

  private static final String READ_TEXT_FILE_PATH =
      TESTING_DIRECTORY + File.separator + "read-text-file.txt";

  private static final String READ_TEXT_FILE_CONTENT = "This is a text file for reading tests.";

  private static final String INEXISTENT_FILE_PATH =
      TESTING_DIRECTORY + File.separator + "inexistent-file.txt";

  private static final String CLASSPATH_TESTING_DIRECTORY = FileUtilTest.class.getSimpleName();

  private static final String CLASSPATH_READ_TEXT_FILE_PATH =
      CLASSPATH_TESTING_DIRECTORY + File.separator + "read-text-file.txt";

  private static final String READ_BINARY_FILE_PATH =
      TESTING_DIRECTORY + File.separator + "read-binary-file.bin";

  private static final byte[] READ_BINARY_FILE_CONTENT = {(byte) 0x4e, (byte) 0x6e, (byte) 0x98,
      (byte) 0x26, (byte) 0x57, (byte) 0xc0, (byte) 0x29, (byte) 0x1d, (byte) 0x8c, (byte) 0xe9,
      (byte) 0x52, (byte) 0xbc, (byte) 0x53, (byte) 0xb7, (byte) 0xd9, (byte) 0x0d};

  private static final String WRITE_TEXT_FILE_PATH =
      TESTING_DIRECTORY + File.separator + "write-text-file.txt";

  private static final String WRITE_TEXT_FILE_CONTENT = "This is a text file for writing test.";

  private static final String WRITE_BINARY_FILE_PATH =
      TESTING_DIRECTORY + File.separator + "write-binary-file.bin";

  private static final byte[] WRITE_BINARY_FILE_CONTENT =
      {(byte) 0x63, (byte) 0x14, (byte) 0xf2, (byte) 0x2e, (byte) 0xac, (byte) 0x01};

  private static final String[] SIZED_FILES_PATHS =
      {TESTING_DIRECTORY + File.separator + "12-bytes-file.bin",
          TESTING_DIRECTORY + File.separator + "5-kilobytes-file.bin",
          TESTING_DIRECTORY + File.separator + "12-point-34-kilobytes-file.bin"};

  private static final String[] SIZED_FILES_SIZES_STRING = {"12.0 B", "5.0 kB", "12.3 kB"};

  private static final long[] SIZED_FILES_SIZES_LONG = {12L, 5120, 12634};

  private static final String EXISTENT_DIRECTORY_PATH =
      TESTING_DIRECTORY + File.separator + "existent-directory";

  private static final String INEXISTENT_DIRECTORY_PATH =
      TESTING_DIRECTORY + File.separator + "inexistent-directory";

  private static final String EXISTENT_DIRECTORY_PATH_WITH_SEPARATOR =
      EXISTENT_DIRECTORY_PATH + File.separator;

  private static final Locale DEFAULT_LOCALE = Locale.getDefault();

  private static final Locale TESTING_LOCALE = Locale.ENGLISH;

  @Rule
  public PowerMockRule rule = new PowerMockRule();

  static {
    PowerMockAgent.initializeIfNeeded();
  }

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

  /**
   * Removes any remaining file if, by any chance, a file still exists after test executions.
   */
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
    Path filePath = Paths.get(READ_TEXT_FILE_PATH);

    // Act
    String content = fileUtil.retrieveTextContentFromFile(filePath);

    // Assert
    assertThat(content).isEqualTo(READ_TEXT_FILE_CONTENT);
  }

  @Test
  public void testRetrieveTextContentFromFilePathShouldRetrieveFilesFromClasspath()
      throws Exception {
    // Arrange
    File mockFile = mock(File.class);
    Path mockPath = mock(Path.class);

    when(mockPath.toFile()).thenReturn(mockFile);
    when(mockFile.exists()).thenReturn(false);
    when(mockPath.toString()).thenReturn(CLASSPATH_READ_TEXT_FILE_PATH);

    // Act
    String content = fileUtil.retrieveTextContentFromFile(mockPath);

    // Assert
    assertThat(content).isEqualTo(READ_TEXT_FILE_CONTENT);
  }

  @Test(expected = FileUtilRuntimeException.class)
  public void testRetrieveTextContentFromFilePathShouldThrowFileUtilRuntimeExceptionWhenFileIsNotFound()
      throws Exception {
    // Arrange
    Path filePath = Paths.get(INEXISTENT_FILE_PATH);

    // Act
    fileUtil.retrieveTextContentFromFile(filePath);

    // Assert
    fail("Should have thrown an exception.");
  }

  @Test(expected = FileUtilRuntimeException.class)
  public void testRetrieveTextContentFromFilePathShouldThrowFileUtilRuntimeExceptionWhenIoExceptionIsCaught()
      throws Exception {

    // Arrange
    Path filePath = Paths.get(FileUtilTest.READ_TEXT_FILE_PATH);

    FileUtil spiedFileUtil = PowerMockito.spy(fileUtil);
    PowerMockito.doThrow(new IOException()).when(spiedFileUtil, "createBufferedReader",
        ArgumentMatchers.any(Path.class));

    // Act
    spiedFileUtil.retrieveTextContentFromFile(filePath);

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
  public void testRetrieveBinaryContentFromFilePath() throws Exception {
    // Act
    byte[] content = fileUtil.retrieveBinaryContentFromFile(Paths.get(READ_BINARY_FILE_PATH));

    // Assert
    assertThat(content).isEqualTo(READ_BINARY_FILE_CONTENT);
  }

  @Test(expected = FileUtilRuntimeException.class)
  public void testRetrieveBinaryContentFromFilePathShouldThrownFileUtilRuntimeExceptionWhenIoExceptionIsCaught()
      throws Exception {
    // Arrange
    FileUtil spiedFileUtil = PowerMockito.spy(fileUtil);
    PowerMockito.doThrow(new IOException()).when(spiedFileUtil, "readAllBytesFromInputStream",
        ArgumentMatchers.any(InputStream.class));

    // Act
    spiedFileUtil.retrieveBinaryContentFromFile(Paths.get(READ_BINARY_FILE_PATH));

    // Assert
    fail("Should have thrown an exception.");
  }

  @Test(expected = FileUtilRuntimeException.class)
  public void testRetrieveBinaryContentFromFilePathShouldThrowFileUtilExceptionWhenFileDoesNotExist()
      throws Exception {
    // Act
    fileUtil.retrieveBinaryContentFromFile(Paths.get(INEXISTENT_DIRECTORY_PATH));

    // Assert
    fail("Should have thrown an exception.");
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
    fileUtil.writeContentOnFile(Paths.get(WRITE_TEXT_FILE_PATH), WRITE_TEXT_FILE_CONTENT);
    String content = fileUtil.retrieveTextContentFromFile(WRITE_TEXT_FILE_PATH);

    // Assert
    assertThat(content).isEqualTo(WRITE_TEXT_FILE_CONTENT);
  }

  @Test(expected = FileUtilRuntimeException.class)
  public void testWriteContentOnFilePathStringShouldThrowFileUtilRuntimeExceptionWhenIoExceptionIsCaught()
      throws Exception {
    // Arrange
    BufferedWriter mockBufferedWriter = PowerMockito.mock(BufferedWriter.class);

    PowerMockito.whenNew(BufferedWriter.class).withAnyArguments().thenReturn(mockBufferedWriter);

    PowerMockito.doThrow(new IOException()).when(mockBufferedWriter)
        .write(ArgumentMatchers.anyString());

    // Act
    fileUtil.writeContentOnFile(Paths.get(WRITE_TEXT_FILE_PATH), WRITE_TEXT_FILE_CONTENT);

    // Assert
    fail("Should have thrown an exception.");
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
    fileUtil.writeContentOnFile(Paths.get(WRITE_BINARY_FILE_PATH), WRITE_BINARY_FILE_CONTENT);
    byte[] content = fileUtil.retrieveBinaryContentFromFile(WRITE_BINARY_FILE_PATH);

    // Assert
    assertThat(content).isEqualTo(WRITE_BINARY_FILE_CONTENT);
  }

  @Test(expected = FileUtilRuntimeException.class)
  public void testWriteContentOnFilePathByteArrayShouldThrowFileUtilRuntimeExceptionWhenIoExceptionIsCaught()
      throws Exception {
    // Arrange
    FileOutputStream mockFileOutputStream = PowerMockito.mock(FileOutputStream.class);

    PowerMockito.whenNew(FileOutputStream.class).withAnyArguments()
        .thenReturn(mockFileOutputStream);

    PowerMockito.doThrow(new IOException()).when(mockFileOutputStream)
        .write(ArgumentMatchers.any());

    // Act
    fileUtil.writeContentOnFile(Paths.get(WRITE_BINARY_FILE_PATH), WRITE_BINARY_FILE_CONTENT);

    // Assert
    fail("Should have thrown an exception.");
  }

  @Test
  public void testRetrieveFileSizeString() throws Exception {

    long[] actualFilesSizes = new long[SIZED_FILES_PATHS.length];

    for (int index = 0; index < actualFilesSizes.length; index++) {

      // Arrange
      String filePath = SIZED_FILES_PATHS[index];

      // Act
      actualFilesSizes[index] = fileUtil.retrieveFileSize(filePath);
    }

    // Assert
    assertThat(actualFilesSizes).isEqualTo(SIZED_FILES_SIZES_LONG);
  }

  @Test
  public void testRetrieveFileSizePath() throws Exception {

    long[] actualFilesSizes = new long[SIZED_FILES_PATHS.length];

    for (int index = 0; index < actualFilesSizes.length; index++) {

      // Arrange
      Path filePath = Paths.get(SIZED_FILES_PATHS[index]);

      // Act
      actualFilesSizes[index] = fileUtil.retrieveFileSize(filePath);
    }

    // Assert
    assertThat(actualFilesSizes).isEqualTo(SIZED_FILES_SIZES_LONG);
  }

  @Test
  public void testRetrieveFileSizeFormattedAsTextString() throws Exception {
    String[] actualFilesSizes = new String[SIZED_FILES_PATHS.length];

    for (int index = 0; index < actualFilesSizes.length; index++) {

      // Arrange
      String filePath = SIZED_FILES_PATHS[index];

      // Act
      actualFilesSizes[index] = fileUtil.retrieveFileSizeFormattedAsText(filePath);
    }

    // Assert
    assertThat(actualFilesSizes).isEqualTo(SIZED_FILES_SIZES_STRING);
  }

  @Test
  public void testRetrieveFileSizeFormattedAsTextPath() throws Exception {
    String[] actualFilesSizes = new String[SIZED_FILES_PATHS.length];

    for (int index = 0; index < actualFilesSizes.length; index++) {

      // Arrange
      Path filePath = Paths.get(SIZED_FILES_PATHS[index]);

      // Act
      actualFilesSizes[index] = fileUtil.retrieveFileSizeFormattedAsText(filePath);
    }

    // Assert
    assertThat(actualFilesSizes).isEqualTo(SIZED_FILES_SIZES_STRING);
  }

  @Test
  public void testCreateDirectoryIfDoesNotExistString() throws Exception {
    // Arrange
    File inexistentDirectory = new File(INEXISTENT_DIRECTORY_PATH);
    assertThat(inexistentDirectory.exists()).isFalse();

    // Act
    fileUtil.createDirectoryIfDoesNotExist(INEXISTENT_DIRECTORY_PATH);

    // Assert
    assertThat(inexistentDirectory.exists()).isTrue();
  }

  @Test
  public void testCreateDirectoryIfDoesNotExistPath() throws Exception {
    // Arrange
    File inexistentDirectory = new File(INEXISTENT_DIRECTORY_PATH);
    assertThat(inexistentDirectory.exists()).isFalse();

    // Act
    fileUtil.createDirectoryIfDoesNotExist(Paths.get(INEXISTENT_DIRECTORY_PATH));

    // Assert
    assertThat(inexistentDirectory.exists()).isTrue();
  }

  @Test(expected = FileUtilRuntimeException.class)
  public void testCreateDirectoryIfDoesNotExistShouldThrowFileUtilRuntimeExceptionWhenIoExceptionIsCaught()
      throws Exception {
    // Arrange
    Path directoryPath = Paths.get(INEXISTENT_DIRECTORY_PATH);
    PowerMockito.spy(FileUtils.class);
    PowerMockito.doThrow(new IOException()).when(FileUtils.class);
    FileUtils.forceMkdir(directoryPath.toFile());

    // Act
    fileUtil.createDirectoryIfDoesNotExist(directoryPath);

    // Assert
    fail("Should have thrown an exception.");
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
    boolean actualResult = fileUtil.fileExists(Paths.get(EXISTENT_DIRECTORY_PATH));

    // Assert
    assertThat(actualResult).isEqualTo(expectedResult);
  }

  @Test
  public void testFileExistsPathShouldReturnFalseWhenFileDoesNotExist() throws Exception {
    // Arrange
    boolean expectedResult = false;

    // Act
    boolean actualResult = fileUtil.fileExists(Paths.get(INEXISTENT_DIRECTORY_PATH));

    // Assert
    assertThat(actualResult).isEqualTo(expectedResult);
  }

  @Test(expected = FileUtilRuntimeException.class)
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

    // Assert
    assertTrue(true);
  }

  @Test(expected = FileUtilRuntimeException.class)
  public void testThrowExceptionIfFileDoesNotExistPathShouldThrowFileUtilExceptionWhenFileDoesNotExist()
      throws Exception {
    // Act
    fileUtil.throwExceptionIfFileDoesNotExist(Paths.get(INEXISTENT_DIRECTORY_PATH));

    // Assert
    fail("Should have thrown an exception.");
  }

  @Test
  public void testThrowExceptionIfFileDoesNotExistPathShouldContinueWhenFileExists()
      throws Exception {
    // Act
    fileUtil.throwExceptionIfFileDoesNotExist(Paths.get(EXISTENT_DIRECTORY_PATH));
    
    // Assert
    assertTrue(true);
  }

  @Test(expected = FileUtilRuntimeException.class)
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
    
    // Assert
    assertTrue(true);
  }

  @Test(expected = FileUtilRuntimeException.class)
  public void testThrowExceptionIfDirectoryDoesNotExistPathShouldThrowFileUtilExceptionWhenDirectoryDoesNotExist()
      throws Exception {
    // Act
    fileUtil.throwExceptionIfDirectoryDoesNotExist(Paths.get(INEXISTENT_DIRECTORY_PATH));

    // Assert
    fail("Should have thrown an exception.");
  }

  @Test
  public void testThrowExceptionIfDirecttoryDoesNotExistPathShouldContinueWhenDirectoryExists()
      throws Exception {
    // Act
    fileUtil.throwExceptionIfDirectoryDoesNotExist(Paths.get(EXISTENT_DIRECTORY_PATH));

    // Assert
    assertTrue(true);
  }

  @Test(expected = FileUtilRuntimeException.class)
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
    
    // Assert
    assertTrue(true);
  }

  @Test(expected = FileUtilRuntimeException.class)
  public void testThrowExceptionIfFileIsNotDirectoryPathShouldThrowFileUtilExceptionWhenFileIsNotDirectory()
      throws Exception {
    // Act
    fileUtil.throwExceptionIfFileIsNotDirectory(Paths.get(READ_TEXT_FILE_PATH));

    // Assert
    fail("Should have thrown an exception.");
  }

  @Test
  public void testThrowExceptionIfFileIsNotDirectoryPathShouldContinueWhenFileIsDirectory()
      throws Exception {
    // Act
    fileUtil.throwExceptionIfFileIsNotDirectory(Paths.get(EXISTENT_DIRECTORY_PATH));

    // Assert
    assertTrue(true);
  }

  @Test(expected = FileUtilRuntimeException.class)
  public void testThrowExceptionIfFileIsDirectoryStringShouldThrowFileUtilExceptionWhenFileIsDirectory()
      throws Exception {
    // Act
    fileUtil.throwExceptionIfFileIsDirectory(EXISTENT_DIRECTORY_PATH);

    // Assert
    fail("Should have thrown an exception.");
  }

  @Test
  public void testThrowExceptionIfFileIsDirectoryStringShouldContinueWhenFileIsNotDirectory()
      throws Exception {
    // Act
    fileUtil.throwExceptionIfFileIsDirectory(READ_TEXT_FILE_PATH);

    // Assert
    assertTrue(true);
  }

  @Test(expected = FileUtilRuntimeException.class)
  public void testThrowExceptionIfFileIsDirectoryPathShouldThrowFileUtilExceptionWhenFileIsDirectory()
      throws Exception {
    // Act
    fileUtil.throwExceptionIfFileIsDirectory(Paths.get(EXISTENT_DIRECTORY_PATH));

    // Assert
    fail("Should have thrown an exception.");
  }

  @Test
  public void testThrowExceptionIfFileIsDirectoryPathShouldContinueWhenFileIsNotDirectory()
      throws Exception {
    // Act
    fileUtil.throwExceptionIfFileIsDirectory(Paths.get(READ_TEXT_FILE_PATH));

    // Assert
    assertTrue(true);
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
    String actualPath = fileUtil.appendSeparatorIfNecessary(EXISTENT_DIRECTORY_PATH_WITH_SEPARATOR);

    // Assert
    assertThat(actualPath).isEqualTo(expectedPath);
  }

}
