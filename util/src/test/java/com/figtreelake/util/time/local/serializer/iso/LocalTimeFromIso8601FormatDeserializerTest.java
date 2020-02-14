package com.figtreelake.util.time.local.serializer.iso;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.figtreelake.util.time.local.serializer.iso.LocalTimeFromIso8601FormatDeserializer;
import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocalTimeFromIso8601FormatDeserializerTest {

  @Mock
  private JsonParser jsonParser;

  @Mock
  private DeserializationContext deserializationContext;

  private LocalTimeFromIso8601FormatDeserializer localTimeDeserializer;

  @Before
  public void setUp() {
    this.localTimeDeserializer = new LocalTimeFromIso8601FormatDeserializer();
  }

  @Test
  public void testDeserialize() throws Exception {
    // Arrange
    LocalTime expectedLocalTime = LocalTime.of(12, 40, 28);
    when(jsonParser.getText()).thenReturn("12:40:28");

    // Act
    LocalTime actualLocalTime =
        localTimeDeserializer.deserialize(jsonParser, deserializationContext);

    // Assert
    assertEquals(expectedLocalTime, actualLocalTime);
  }

}
