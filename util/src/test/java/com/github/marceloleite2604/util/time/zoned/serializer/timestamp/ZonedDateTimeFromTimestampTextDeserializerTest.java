package com.github.marceloleite2604.util.time.zoned.serializer.timestamp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.github.marceloleite2604.util.time.zoned.serializer.timestamp.ZonedDateTimeFromTimestampTextDeserializer;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ZonedDateTimeFromTimestampTextDeserializerTest {

  @Mock
  private JsonParser jsonParser;

  @Mock
  private DeserializationContext deserializationContext;

  private ZonedDateTimeFromTimestampTextDeserializer zonedDateTimeFromTimestampTextDeserializer;

  @Before
  public void setUp() {
    this.zonedDateTimeFromTimestampTextDeserializer = new ZonedDateTimeFromTimestampTextDeserializer();
  }

  @Test
  public void testDeserialize() throws Exception {
    // Arrange
    ZonedDateTime expectedZonedDateTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 8, 12, 14, 295000000), ZoneOffset.UTC);
    when(jsonParser.getValueAsString()).thenReturn("2018-09-26T08:12:14.295+0000");

    // Act
    ZonedDateTime actualZonedDateTime =
        zonedDateTimeFromTimestampTextDeserializer.deserialize(jsonParser, deserializationContext);

    // Assert
    assertEquals(expectedZonedDateTime, actualZonedDateTime);
  }

}
