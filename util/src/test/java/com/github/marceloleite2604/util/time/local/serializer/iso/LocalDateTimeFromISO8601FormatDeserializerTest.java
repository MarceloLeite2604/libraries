
package com.github.marceloleite2604.util.time.local.serializer.iso;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocalDateTimeFromISO8601FormatDeserializerTest {

  @Mock
  private JsonParser jsonParser;

  @Mock
  private DeserializationContext deserializationContext;

  private LocalDateTimeFromISO8601FormatDeserializer localDateTimeFromISO8601FormatDeserializer;

  @Before
  public void setUp() {
    this.localDateTimeFromISO8601FormatDeserializer =
        new LocalDateTimeFromISO8601FormatDeserializer();
  }

  @Test
  public void testDeserialize() throws Exception {
    // Arrange
    LocalDateTime expectedLocalDateTime = LocalDateTime.of(2018, 9, 26, 10, 12, 31);
    when(jsonParser.getText()).thenReturn("2018-09-26T10:12:31");

    // Act
    LocalDateTime actualLocalDateTime =
        localDateTimeFromISO8601FormatDeserializer.deserialize(jsonParser, deserializationContext);

    // Assert
    assertEquals(expectedLocalDateTime, actualLocalDateTime);
  }

}
