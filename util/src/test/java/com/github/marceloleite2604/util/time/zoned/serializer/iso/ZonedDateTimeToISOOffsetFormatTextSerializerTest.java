package com.github.marceloleite2604.util.time.zoned.serializer.iso;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.marceloleite2604.util.time.zoned.serializer.iso.ZonedDateTimeToISOOffsetFormatTextSerializer;
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
public class ZonedDateTimeToISOOffsetFormatTextSerializerTest {

  @Mock
  private JsonGenerator jsonGenerator;

  @Mock
  private SerializerProvider serializerProvider;

  @Captor
  private ArgumentCaptor<String> argumentCaptor;

  private ZonedDateTimeToISOOffsetFormatTextSerializer zonedDateTimeSerializer;

  @Before
  public void setUp() {
    this.zonedDateTimeSerializer = new ZonedDateTimeToISOOffsetFormatTextSerializer();
  }

  @Test
  public void testSerialize() throws Exception {
    // Arrange
    String exptectedText = "2018-09-26T08:40:06+05:00";
    ZonedDateTime zonedTime =
        ZonedDateTime.of(LocalDateTime.of(2018, 9, 26, 8, 40, 6, 0), ZoneId.of("Etc/GMT-5"));

    // Act
    zonedDateTimeSerializer.serialize(zonedTime, jsonGenerator, serializerProvider);

    // Assert
    verify(jsonGenerator, times(1)).writeString(argumentCaptor.capture());
    assertEquals(exptectedText, argumentCaptor.getValue());
  }

}
