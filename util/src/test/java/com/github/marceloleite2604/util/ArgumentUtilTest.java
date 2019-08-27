package com.github.marceloleite2604.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.github.marceloleite2604.util.ArgumentUtil;
import com.github.marceloleite2604.util.fixture.ArgumentsFixture;

public class ArgumentUtilTest {

	private ArgumentUtil argumentsUtils;

	@Before
	public void setUp() {
		argumentsUtils = new ArgumentUtil();
	}

	@Test
	public void checkArgumentsExistantArgument() {
		// Act
		boolean actualResult = argumentsUtils.checkArgument(ArgumentsFixture.create(), ArgumentsFixture.ARGUMENT_B);

		// Assert
		assertTrue(actualResult);
	}

	@Test
	public void checkArgumentsInexistantArgument() {
		// Act
		boolean actualResult = argumentsUtils.checkArgument(ArgumentsFixture.create(),
				ArgumentsFixture.INEXISTANT_ARGUMENT);

		// Assert
		assertFalse(actualResult);
	}

	@Test
	public void retrieveArgumentExistantArgumentValue() {
		// Assert
		Optional<String> expectedResult = Optional.of(ArgumentsFixture.VALUE_ARGUMENT_A);

		// Act
		Optional<String> actualResult = argumentsUtils.retrieveArgument(ArgumentsFixture.create(),
				ArgumentsFixture.ARGUMENT_A);

		// Assert
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void retrieveArgumentInexistantArgumentValue() {
		// Assert
		Optional<String> expectedResult = Optional.empty();

		// Act
		Optional<String> actualResult = argumentsUtils.retrieveArgument(ArgumentsFixture.create(),
				ArgumentsFixture.INEXISTANT_ARGUMENT);

		// Assert
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void retrieveArgumentBooleanArgument() {
		// Assert
		Optional<String> expectedResult = Optional.empty();

		// Act
		Optional<String> actualResult = argumentsUtils.retrieveArgument(ArgumentsFixture.create(),
				ArgumentsFixture.ARGUMENT_B);

		// Assert
		assertEquals(expectedResult, actualResult);
	}
}
