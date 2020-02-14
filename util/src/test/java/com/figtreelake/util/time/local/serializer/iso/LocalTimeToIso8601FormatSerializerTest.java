package com.figtreelake.util.time.local.serializer.iso;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.figtreelake.util.time.local.serializer.iso.LocalTimeToIso8601FormatSerializer;
import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocalTimeToIso8601FormatSerializerTest {

  @Mock
  private JsonGenerator jsonGenerator;

  @Mock
  private SerializerProvider serializerProvider;

  @Captor
  private ArgumentCaptor<String> argumentCaptor;

  private LocalTimeToIso8601FormatSerializer localTimeSerializer;

  @Before
  public void setUp() {
    this.localTimeSerializer = new LocalTimeToIso8601FormatSerializer();
  }

  @Test
  public void testSerialize() throws Exception {
    // Arrange
    String exptectedText = "23:17:39";
    LocalTime localTime = LocalTime.of(23, 17, 39);

    // Act
    localTimeSerializer.serialize(localTime, jsonGenerator, serializerProvider);

    // Assert
    verify(jsonGenerator, times(1)).writeString(argumentCaptor.capture());
    assertEquals(exptectedText, argumentCaptor.getValue());
  }

}
