
package com.github.marceloleite2604.util.time.duration.serializer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.time.Duration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DurationFromISO8601FormatDeserializerTest {

  @Mock
  private JsonParser jsonParser;

  @Mock
  private DeserializationContext deserializationContext;

  private DurationFromISO8601FormatDeserializer durationFromISO8601FormatDeserializer;

  @Before
  public void setUp() {
    this.durationFromISO8601FormatDeserializer = new DurationFromISO8601FormatDeserializer();
  }

  @Test
  public void testDeserialize() throws Exception {
    // Arrange
    String text = "PT8H5M13S";
    Duration expectedDuration = Duration.ofHours(8).plusMinutes(5).plusSeconds(13);
    when(jsonParser.getText()).thenReturn(text);

    // Act
    Duration actualDuration =
        durationFromISO8601FormatDeserializer.deserialize(jsonParser, deserializationContext);

    // Assert
    assertEquals(expectedDuration, actualDuration);
  }
}
