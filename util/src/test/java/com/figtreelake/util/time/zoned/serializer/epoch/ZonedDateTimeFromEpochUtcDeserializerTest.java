package com.figtreelake.util.time.zoned.serializer.epoch;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.figtreelake.util.time.zoned.serializer.epoch.ZonedDateTimeFromEpochUtcDeserializer;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ZonedDateTimeFromEpochUtcDeserializerTest {

  @Mock
  private JsonParser jsonParser;

  @Mock
  private DeserializationContext deserializationContext;

  private ZonedDateTimeFromEpochUtcDeserializer zonedDateTimeFromEpochUTCDeserializer;

  @Before
  public void setUp() {
    this.zonedDateTimeFromEpochUTCDeserializer = new ZonedDateTimeFromEpochUtcDeserializer();
  }

  @Test
  public void testDeserialize() throws Exception {
    // Arrange
    ZonedDateTime expectedLocalTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 8, 12, 14), ZoneOffset.UTC);
    when(jsonParser.getValueAsLong()).thenReturn(1537949534L);

    // Act
    ZonedDateTime actualLocalTime =
        zonedDateTimeFromEpochUTCDeserializer.deserialize(jsonParser, deserializationContext);

    // Assert
    assertEquals(expectedLocalTime, actualLocalTime);
  }

}
