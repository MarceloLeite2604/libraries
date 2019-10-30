package com.github.marceloleite2604.util.time.local.serializer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocalTimeDeserializerTest {

  @Mock
  private JsonParser jsonParser;

  @Mock
  private DeserializationContext deserializationContext;

  private LocalTimeDeserializer localTimeDeserializer;

  @Before
  public void setUp() {
    this.localTimeDeserializer = new LocalTimeDeserializer();
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
