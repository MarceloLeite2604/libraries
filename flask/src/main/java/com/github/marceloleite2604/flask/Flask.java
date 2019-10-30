package com.github.marceloleite2604.flask;

import com.github.marceloleite2604.util.file.FileUtil;
import java.io.File;


/**
 * Flask is a File Loader ASsistant Kit. It helps load content from text files.
 *
 * @see <a href="http://www.github.com/MarceloLeite2604/libraries" target= "_top">GitHub project</a>
 * @author MarceloLeite2604
 *
 */
public class Flask {

  private String directory;

  private FileUtil fileUtil;

  /**
   * Constructs a Flask to load content from text files.
   * 
   * @param directory Directory which text file paths informed on load methods will start.
   */
  public Flask(String directory) {
    this.directory = directory;
    this.fileUtil = new FileUtil();
  }

  /**
   * Constructs a Flask to load content from text files.
   */
  public Flask() {
    this(null);
  }

  /**
   * Load content from a file.
   * 
   * @param filePath Path to the text file which the content should be loaded. If a directory was
   *        defined on Flask instantiation, the path must start from this directory.
   * @return The text file content.
   */
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
