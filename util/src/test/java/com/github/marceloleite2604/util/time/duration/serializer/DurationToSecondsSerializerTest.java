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
public class DurationToSecondsSerializerTest {

  @Mock
  private JsonGenerator jsonGenerator;

  @Mock
  private SerializerProvider serializerProvider;

  @Captor
  private ArgumentCaptor<Double> argumentCaptor;

  private DurationToSecondsSerializer durationSerializer;

  @Before
  public void setUp() {
    this.durationSerializer = new DurationToSecondsSerializer();
  }

  @Test
  public void testSerialize() throws Exception {
    // Arrange
    double expectedSeconds = 3600.0;
    Duration duration = Duration.ofSeconds(3600L);

    // Act
    durationSerializer.serialize(duration, jsonGenerator, serializerProvider);

    // Assert
    verify(jsonGenerator, times(1)).writeNumber(argumentCaptor.capture());
    assertEquals(expectedSeconds, argumentCaptor.getValue().doubleValue(), 0.1);
  }

}
