package com.github.marceloleite2604.util.time.zoned.serializer.epoch;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.github.marceloleite2604.util.time.zoned.serializer.epoch.ZonedDateTimeFromEpochDeserializer;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ZonedDateTimeFromEpochDeserializerTest {

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
    ZonedDateTime expectedLocalTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 8, 12, 14), ZoneOffset.UTC);
    when(jsonParser.getValueAsLong()).thenReturn(1537949534L);

    // Act
    ZonedDateTime actualLocalTime =
        zonedDateTimeFromEpochDeserializer.deserialize(jsonParser, deserializationContext);

    // Assert
    assertEquals(expectedLocalTime, actualLocalTime);
  }

}