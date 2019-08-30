package com.github.marceloleite2604.util.time.deserializer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.Duration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

@RunWith(MockitoJUnitRunner.class)
public class DurationDeserializerTest {

	@Mock
	private JsonParser jsonParser;

	@Mock
	private DeserializationContext deserializationContext;

	private DurationDeserializer durationDeserializer;

	@Before
	public void setUp() {
		this.durationDeserializer = new DurationDeserializer();
	}

	@Test
	public void testDeserialize() throws Exception {
		// Arrange
		long seconds = 900L;
		Duration expectedDuration = Duration.ofSeconds(seconds);
		when(jsonParser.getLongValue()).thenReturn(seconds);

		// Act
		Duration actualDuration = durationDeserializer.deserialize(jsonParser, deserializationContext);

		// Assert
		assertEquals(expectedDuration, actualDuration);
	}

}
