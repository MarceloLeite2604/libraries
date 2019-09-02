package com.github.marceloleite2604.util.time.deserializer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.github.marceloleite2604.util.time.zoned.ZonedDateTimeUtil;
import com.github.marceloleite2604.util.time.zoned.serializer.epoch.ZonedDateTimeFromEpochDeserializer;

@RunWith(MockitoJUnitRunner.class)
public class ZonedDateToEpochTimeDeserializerTest {

	@Mock
	private JsonParser jsonParser;

	@Mock
	private DeserializationContext deserializationContext;

	private ZonedDateTimeFromEpochDeserializer zonedDateTimeFromEpochDeserializer;

	@Before
	public void setUp() {
		this.zonedDateTimeFromEpochDeserializer = new ZonedDateTimeFromEpochDeserializer();
	}

	@Test
	public void testDeserialize() throws Exception {
		// Arrange
		ZonedDateTime expectedLocalTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 8, 12, 14),
				ZonedDateTimeUtil.DEFAULT_ZONE_ID);
		when(jsonParser.getValueAsLong()).thenReturn(1537949534L);

		// Act
		ZonedDateTime actualLocalTime = zonedDateTimeFromEpochDeserializer.deserialize(jsonParser,
				deserializationContext);

		// Assert
		assertEquals(expectedLocalTime, actualLocalTime);
	}

}
