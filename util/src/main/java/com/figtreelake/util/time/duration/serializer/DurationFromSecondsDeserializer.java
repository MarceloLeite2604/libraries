package com.figtreelake.util.time.duration.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.figtreelake.util.time.duration.DurationUtil;
import java.io.IOException;
import java.time.Duration;

/**
 * <p>
 * A {@link StdDeserializer} extension to deserialize {@link Duration} objects from an amount of
 * seconds.
 * </p>
 * <p>
 * Its serialization equivalent can be found on {@link DurationToSecondsSerializer} class.
 * </p>
 *
 * @author MarceloLeite2604
 *
 */
public class DurationFromSecondsDeserializer extends StdDeserializer<Duration> {

  private static final long serialVersionUID = 1L;

  private final transient DurationUtil durationUtil;

  public DurationFromSecondsDeserializer() {
    super(Duration.class);
    this.durationUtil = new DurationUtil();
  }

  @Override
  public Duration deserialize(JsonParser jsonParser, DeserializationContext context)
      throws IOException {
    return durationUtil.parseFromSeconds(jsonParser.getLongValue());
  }

}
