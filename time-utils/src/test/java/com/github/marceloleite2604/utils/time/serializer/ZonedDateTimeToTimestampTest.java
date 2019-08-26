package com.github.marceloleite2604.utils.time.serializer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.marceloleite2604.utils.time.serializer.ZonedDateTimeToTimestampSerializer;

@RunWith(MockitoJUnitRunner.class)
public class ZonedDateTimeToTimestampTest {

	@Mock
	private JsonGenerator jsonGenerator;

	@Mock
	private SerializerProvider serializerProvider;

	@Captor
	private ArgumentCaptor<String> argumentCaptor;

	private ZonedDateTimeToTimestampSerializer zonedDateTimeToTimestampSerializer;

	@Before
	public void setUp() {
		this.zonedDateTimeToTimestampSerializer = new ZonedDateTimeToTimestampSerializer();
	}

	@Test
	public void testSerialize() throws Exception {
		// Arrange
		String expectedValue = "2018-09-26T08:40:06.738+0500";
		ZonedDateTime zonedTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 8, 40, 6, 738000000), ZoneId.of("Etc/GMT-5"));

		// Act
		zonedDateTimeToTimestampSerializer.serialize(zonedTime, jsonGenerator, serializerProvider);

		// Assert
		verify(jsonGenerator, times(1)).writeString(argumentCaptor.capture());
		assertEquals(expectedValue, argumentCaptor.getValue());
	}

}
