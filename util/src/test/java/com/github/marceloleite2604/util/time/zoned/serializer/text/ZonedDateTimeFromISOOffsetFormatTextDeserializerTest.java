package com.github.marceloleite2604.util.time.zoned.serializer.text;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ZonedDateTimeFromISOOffsetFormatTextDeserializerTest {

  @Mock
  private JsonParser jsonParser;

  @Mock
  private DeserializationContext deserializationContext;

  private ZonedDateTimeFromISOOffsetFormatTextDeserializer zonedDateTimeFromISOOffsetFormatTextDeserializer;

  @Before
  public void setUp() {
    this.zonedDateTimeFromISOOffsetFormatTextDeserializer =
        new ZonedDateTimeFromISOOffsetFormatTextDeserializer();
  }

  @Test
  public void testDeserialize() throws Exception {
    // Arrange
    ZonedDateTime expectedLocalTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 12, 31),
        ZoneId.of("Etc/GMT+12").normalized());
    when(jsonParser.getText()).thenReturn("2018-09-26T10:12:31-12:00");

    // Act
    ZonedDateTime actualLocalTime = zonedDateTimeFromISOOffsetFormatTextDeserializer
        .deserialize(jsonParser, deserializationContext);

    // Assert
    assertEquals(expectedLocalTime, actualLocalTime);
  }

}
