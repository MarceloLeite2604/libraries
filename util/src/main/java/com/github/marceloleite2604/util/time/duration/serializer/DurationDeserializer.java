package com.github.marceloleite2604.util.time.duration.serializer;

import java.io.IOException;
import java.time.Duration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.marceloleite2604.util.time.duration.DurationUtil;

public class DurationDeserializer extends StdDeserializer<Duration> {

	private static final long serialVersionUID = 1L;

	private final transient DurationUtil durationUtil;

	public DurationDeserializer() {
		super(Duration.class);
		this.durationUtil = new DurationUtil();
	}

	@Override
	public Duration deserialize(JsonParser jsonParser, DeserializationContext context)
			throws IOException {
		return durationUtil.parseFromSeconds(jsonParser.getLongValue());
	}

}
