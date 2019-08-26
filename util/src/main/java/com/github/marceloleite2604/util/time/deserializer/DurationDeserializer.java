package com.github.marceloleite2604.util.time.deserializer;

import java.io.IOException;
import java.time.Duration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.marceloleite2604.util.time.DurationUtil;

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
