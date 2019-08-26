package com.github.marceloleite2604.util.time.deserializer;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.marceloleite2604.util.time.ZonedDateTimeUtil;

public class ZonedDateTimeFromTimestampDeserializer extends StdDeserializer<ZonedDateTime> {

	private static final long serialVersionUID = 1L;

	public ZonedDateTimeFromTimestampDeserializer() {
		super(ZonedDateTime.class);
	}

	@Override
	public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
		return ZonedDateTimeUtil.convertFromWrittenTimestamp(jsonParser.getValueAsString());
	}

}
