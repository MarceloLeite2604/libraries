
package com.github.marceloleite2604.util.time.local.serializer.epoch;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocalDateTimeToEpochSerializerTest {

  @Mock
  private JsonGenerator jsonGenerator;

  @Mock
  private SerializerProvider serializerProvider;

  @Captor
  private ArgumentCaptor<Long> argumentCaptor;

  private LocalDateTimeToEpochSerializer localDateTimeToEpochSerializer;

  @Before
  public void setUp() {
    this.localDateTimeToEpochSerializer = new LocalDateTimeToEpochSerializer();
  }

  @Test
  public void testSerialize() throws Exception {
    // Arrange
    long expectedValue = 1537951206L;
    LocalDateTime localDateTime = LocalDateTime.of(2018, 9, 26, 8, 40, 6, 0);

    // Act
    localDateTimeToEpochSerializer.serialize(localDateTime, jsonGenerator, serializerProvider);

    // Assert
    verify(jsonGenerator, times(1)).writeNumber(argumentCaptor.capture());
    assertEquals(expectedValue, argumentCaptor.getValue().longValue());
  }

}
