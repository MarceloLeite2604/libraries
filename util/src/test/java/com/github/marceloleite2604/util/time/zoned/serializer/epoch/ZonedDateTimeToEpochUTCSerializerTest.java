package com.github.marceloleite2604.util.time.zoned.serializer.epoch;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ZonedDateTimeToEpochUTCSerializerTest {

  @Mock
  private JsonGenerator jsonGenerator;

  @Mock
  private SerializerProvider serializerProvider;

  @Captor
  private ArgumentCaptor<Long> argumentCaptor;

  private ZonedDateTimeToEpochUTCSerializer zonedDateTimeToEpochUTCSerializer;

  @Before
  public void setUp() {
    this.zonedDateTimeToEpochUTCSerializer = new ZonedDateTimeToEpochUTCSerializer();
  }

  @Test
  public void testSerialize() throws Exception {
    // Arrange
    long expectedValue = 1537933206L;
    ZonedDateTime zonedDateTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 8, 40, 6, 0), ZoneId.of("Etc/GMT-5"));

    // Act
    zonedDateTimeToEpochUTCSerializer.serialize(zonedDateTime, jsonGenerator, serializerProvider);

    // Assert
    verify(jsonGenerator, times(1)).writeNumber(argumentCaptor.capture());
    assertEquals(expectedValue, argumentCaptor.getValue().longValue());
  }

}
