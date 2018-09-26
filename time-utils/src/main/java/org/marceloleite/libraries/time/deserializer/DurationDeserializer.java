package org.marceloleite.libraries.time.deserializer;

import java.io.IOException;
import java.time.Duration;

import org.marceloleite.libraries.time.util.DurationUtil;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DurationDeserializer extends StdDeserializer<Duration> {

	private static final long serialVersionUID = 1L;

	public DurationDeserializer() {
		super(Duration.class);
	}

	@Override
	public Duration deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
		return DurationUtil.parseFromSeconds(jsonParser.getLongValue());
	}

}
