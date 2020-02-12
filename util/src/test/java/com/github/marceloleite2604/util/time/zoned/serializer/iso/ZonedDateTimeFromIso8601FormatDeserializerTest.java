package com.github.marceloleite2604.util.time.zoned.serializer.iso;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.github.marceloleite2604.util.time.zoned.serializer.iso.ZonedDateTimeFromIso8601FormatDeserializer;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ZonedDateTimeFromIso8601FormatDeserializerTest {

  @Mock
  private JsonParser jsonParser;

  @Mock
  private DeserializationContext deserializationContext;

  private ZonedDateTimeFromIso8601FormatDeserializer zonedDateTimeFromISO8601FormatDeserializer;

  @Before
  public void setUp() {
    this.zonedDateTimeFromISO8601FormatDeserializer =
        new ZonedDateTimeFromIso8601FormatDeserializer();
  }

  @Test
  public void testDeserialize() throws Exception {
    // Arrange
    ZonedDateTime expectedLocalTime = ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 10, 12, 31),
        ZoneId.of("Etc/GMT+12").normalized());
    when(jsonParser.getText()).thenReturn("2018-09-26T10:12:31-12:00");

    // Act
    ZonedDateTime actualLocalTime =
        zonedDateTimeFromISO8601FormatDeserializer.deserialize(jsonParser, deserializationContext);

    // Assert
    assertEquals(expectedLocalTime, actualLocalTime);
  }

}
