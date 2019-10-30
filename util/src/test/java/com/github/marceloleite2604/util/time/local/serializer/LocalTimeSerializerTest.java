package com.github.marceloleite2604.util.time.local.serializer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocalTimeSerializerTest {

  @Mock
  private JsonGenerator jsonGenerator;

  @Mock
  private SerializerProvider serializerProvider;

  @Captor
  private ArgumentCaptor<String> argumentCaptor;

  private LocalTimeSerializer localTimeSerializer;

  @Before
  public void setUp() {
    this.localTimeSerializer = new LocalTimeSerializer();
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
