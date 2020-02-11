
package com.github.marceloleite2604.util.time.local.serializer.iso;

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
public class LocalDateTimeToISO8601FormatSerializerTest {

  @Mock
  private JsonGenerator jsonGenerator;

  @Mock
  private SerializerProvider serializerProvider;

  @Captor
  private ArgumentCaptor<String> argumentCaptor;

  private LocalDateTimeToISO8601FormatSerializer localDateTimeToISO8601FormatSerializer;

  @Before
  public void setUp() {
    this.localDateTimeToISO8601FormatSerializer = new LocalDateTimeToISO8601FormatSerializer();
  }

  @Test
  public void testSerialize() throws Exception {
    // Arrange
    String exptectedText = "2018-09-26T08:40:06";
    LocalDateTime zonedTime = LocalDateTime.of(2018, 9, 26, 8, 40, 6, 0);

    // Act
    localDateTimeToISO8601FormatSerializer.serialize(zonedTime, jsonGenerator, serializerProvider);

    // Assert
    verify(jsonGenerator, times(1)).writeString(argumentCaptor.capture());
    assertEquals(exptectedText, argumentCaptor.getValue());
  }

}
