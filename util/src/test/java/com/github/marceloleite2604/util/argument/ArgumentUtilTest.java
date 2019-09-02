package com.github.marceloleite2604.util.argument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.github.marceloleite2604.util.fixture.ArgumentsFixture;

public class ArgumentUtilTest {

	private ArgumentUtil argumentsUtils;

	@Before
	public void setUp() {
		argumentsUtils = new ArgumentUtil();
	}

	@Test
	public void testRetrieveArgumentStringArrayString() {
		// Assert
		Optional<String> expectedResult = Optional.of(ArgumentsFixture.VALUE_ARGUMENT_1);

		// Act
		Optional<String> actualResult = argumentsUtils.retrieveArgument(ArgumentsFixture.create(),
				ArgumentsFixture.ARGUMENT_1);

		// Assert
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void retrieveArgumentStringArrayStringShouldReturnEmptyOptionalWhenArgumentDoesNotExist() {
		// Assert
		Optional<String> expectedResult = Optional.empty();

		// Act
		Optional<String> actualResult = argumentsUtils.retrieveArgument(ArgumentsFixture.create(),
				ArgumentsFixture.INEXISTENT_ARGUMENT);

		// Assert
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void retrieveArgumentStringArrayStringShouldReturnArgumentItselfWhenItDoesNotHaveValue() {
		// Assert
		String[] arguments = ArgumentsFixture.create();
		String argumentName = ArgumentsFixture.ARGUMENT_2;
		Optional<String> expectedResult = Optional.of(ArgumentsFixture.ARGUMENT_2);

		// Act
		Optional<String> actualResult = argumentsUtils.retrieveArgument(arguments, argumentName);

		// Assert
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void retrieveArgumentStringArrayStringShouldReturnArgumentItselfWhenItIsTheLastArgument() {
		// Assert
		String[] arguments = ArgumentsFixture.create();
		String argumentName = ArgumentsFixture.ARGUMENT_4;
		Optional<String> expectedResult = Optional.of(ArgumentsFixture.ARGUMENT_4);

		// Act
		Optional<String> actualResult = argumentsUtils.retrieveArgument(arguments, argumentName);

		// Assert
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testRetrieveArgumentStringArrayStringString() throws Exception {

		// Arrange
		String[] arguments = ArgumentsFixture.create();
		String argumentName = ArgumentsFixture.ARGUMENT_1;
		String defaultArgumentValue = "defaultArgumentValue";
		String expectedResult = ArgumentsFixture.VALUE_ARGUMENT_1;

		// Act
		String actualResult = argumentsUtils.retrieveArgument(arguments, argumentName,
				defaultArgumentValue);

		// Assert
		assertThat(actualResult).isEqualTo(expectedResult);
	}

	@Test
	public void testRetrieveArgumentStringArrayStringStringShouldReturnDefaultValueWhenArgumentDoesNotExist()
			throws Exception {

		// Arrange
		String[] arguments = ArgumentsFixture.create();
		String argumentName = ArgumentsFixture.INEXISTENT_ARGUMENT;
		String defaultArgumentValue = "defaultArgumentValue";
		String expectedResult = defaultArgumentValue;

		// Act
		String actualResult = argumentsUtils.retrieveArgument(arguments, argumentName,
				defaultArgumentValue);

		// Assert
		assertThat(actualResult).isEqualTo(expectedResult);
	}

	@Test(expected = ArgumentUtilRuntimeException.class)
	public void testRetrieveArgumentStringArrayStringStringShouldThrowArgumentUtilRuntimeExceptionWhenNameIsNull()
			throws Exception {

		// Arrange
		String[] arguments = ArgumentsFixture.create();
		String argumentName = null;
		String defaultArgumentValue = "defaultArgumentValue";

		// Act
		argumentsUtils.retrieveArgument(arguments, argumentName, defaultArgumentValue);

		// Assert
		fail("Should have thrown an exception.");
	}

	@Test
	public void testRetrieveArgumentStringArrayStringStringShouldReturnDefaultValueWhenArgumentsIsNull()
			throws Exception {

		// Arrange
		String[] arguments = null;
		String argumentName = ArgumentsFixture.ARGUMENT_1;
		String defaultArgumentValue = "defaultArgumentValue";
		String expectedResult = defaultArgumentValue;

		// Act
		String actualResult = argumentsUtils.retrieveArgument(arguments, argumentName,
				defaultArgumentValue);

		// Assert
		assertThat(actualResult).isEqualTo(expectedResult);
	}

	@Test
	public void testCheckArgumentShouldReturnTrueWhenArgumentExists() {
		// Arrange
		boolean expectedResult = true;
		String[] arguments = ArgumentsFixture.create();
		String argumentName = ArgumentsFixture.ARGUMENT_2;

		// Act
		boolean actualResult = argumentsUtils.checkArgument(arguments, argumentName);

		// Assert
		assertThat(actualResult).isEqualTo(expectedResult);
	}

	@Test
	public void testCheckArgumentShouldReturnFalseWhenArgumentDoesNotExist() {
		// Arrange
		boolean expectedResult = false;
		String[] arguments = ArgumentsFixture.create();
		String argumentName = ArgumentsFixture.INEXISTENT_ARGUMENT;

		// Act
		boolean actualResult = argumentsUtils.checkArgument(arguments, argumentName);

		// Assert
		assertThat(actualResult).isEqualTo(expectedResult);
	}

}
