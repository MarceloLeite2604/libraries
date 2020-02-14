
package com.figtreelake.util.time.local.serializer.epoch;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.figtreelake.util.time.local.serializer.epoch.LocalDateTimeFromEpochDeserializer;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocalDateTimeFromEpochDeserializerTest {

  @Mock
  private JsonParser jsonParser;

  @Mock
  private DeserializationContext deserializationContext;

  private LocalDateTimeFromEpochDeserializer localDateTimeFromEpochDeserializer;

  @Before
  public void setUp() {
    this.localDateTimeFromEpochDeserializer = new LocalDateTimeFromEpochDeserializer();
  }

  @Test
  public void testDeserialize() throws Exception {
    // Arrange
    LocalDateTime expectedLocalDateTime = LocalDateTime.of(2018, 9, 26, 8, 12, 14);
    when(jsonParser.getValueAsLong()).thenReturn(1537949534L);

    // Act
    LocalDateTime actualLocalDateTime =
        localDateTimeFromEpochDeserializer.deserialize(jsonParser, deserializationContext);

    // Assert
    assertEquals(expectedLocalDateTime, actualLocalDateTime);
  }

}
