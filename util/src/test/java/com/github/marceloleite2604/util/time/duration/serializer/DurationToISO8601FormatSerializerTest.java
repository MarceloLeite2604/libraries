
package com.github.marceloleite2604.util.time.duration.serializer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.time.Duration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DurationToISO8601FormatSerializerTest {

  @Mock
  private JsonGenerator jsonGenerator;

  @Mock
  private SerializerProvider serializerProvider;

  @Captor
  private ArgumentCaptor<String> argumentCaptor;

  private DurationToISO8601FormatSerializer durationToISO8601FormatSerializer;

  @Before
  public void setUp() {
    this.durationToISO8601FormatSerializer = new DurationToISO8601FormatSerializer();
  }

  @Test
  public void testSerialize() throws Exception {
    // Arrange
    String expectedText = "PT1H";
    Duration duration = Duration.ofSeconds(3600L);

    // Act
    durationToISO8601FormatSerializer.serialize(duration, jsonGenerator, serializerProvider);

    // Assert
    verify(jsonGenerator, times(1)).writeString(argumentCaptor.capture());
    assertEquals(expectedText, argumentCaptor.getValue());
  }

}
