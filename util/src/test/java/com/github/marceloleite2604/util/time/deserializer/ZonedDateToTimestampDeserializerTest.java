package com.github.marceloleite2604.util.time.deserializer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.github.marceloleite2604.util.time.zoned.ZonedDateTimeUtil;
import com.github.marceloleite2604.util.time.zoned.serializer.timestamp.ZonedDateTimeFromTimestampDeserializer;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ZonedDateToTimestampDeserializerTest {

  @Mock
  private JsonParser jsonParser;

  @Mock
  private DeserializationContext deserializationContext;

  private ZonedDateTimeFromTimestampDeserializer zonedDateTimeFromTimestampDeserializer;

  @Before
  public void setUp() {
    this.zonedDateTimeFromTimestampDeserializer = new ZonedDateTimeFromTimestampDeserializer();
  }

  @Test
  public void testDeserialize() throws Exception {
    // Arrange
    ZonedDateTime expectedZonedDateTime = ZonedDateTime
        .of(LocalDateTime.of(2018, 9, 26, 8, 12, 14, 295000000), ZonedDateTimeUtil.DEFAULT_ZONE_ID);
    when(jsonParser.getValueAsString()).thenReturn("2018-09-26T08:12:14.295+0000");

    // Act
    ZonedDateTime actualZonedDateTime =
        zonedDateTimeFromTimestampDeserializer.deserialize(jsonParser, deserializationContext);

    // Assert
    assertEquals(expectedZonedDateTime, actualZonedDateTime);
  }

}
