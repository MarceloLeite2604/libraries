package com.github.marceloleite2604.flask;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class FlaskTest {
	
	private static final String SCRIPTS_DIRECTORY = "scripts";
	
	private static final String SCRIPT_INSIDE_DIRECTORY_NAME = "script-inside-directory.sql";
	
	private static final String SCRIPT_INSIDE_DIRECTORY_CONTENT = "SELECT * FROM dual;";
	
	private static final String SCRIPT_OUTSIDE_DIRECTORY_NAME = "script-outside-directory.sql";
	
	private static final String SCRIPT_OUTSIDE_DIRECTORY_CONTENT = "DROP TABLE users CASCADE;";
	
	@Test
	public void testLoadContentInformingDirectory() throws Exception {
		// Arrange
		Flask flask = new Flask(SCRIPTS_DIRECTORY);
		String expectedContent = SCRIPT_INSIDE_DIRECTORY_CONTENT;
		
		// Act
		String actualContent = flask.loadContent(SCRIPT_INSIDE_DIRECTORY_NAME);

		// Assert
		assertThat(actualContent).isEqualTo(expectedContent);
	}
	
	@Test
	public void testLoadContent() throws Exception {
		// Arrange
		Flask flask = new Flask();
		String expectedContent = SCRIPT_OUTSIDE_DIRECTORY_CONTENT;
		
		// Act
		String actualContent = flask.loadContent(SCRIPT_OUTSIDE_DIRECTORY_NAME);

		// Assert
		assertThat(actualContent).isEqualTo(expectedContent);
	}
}
