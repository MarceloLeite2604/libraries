package com.github.marceloleite2604.util.time.zoned.serialized;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.marceloleite2604.util.time.zoned.ZonedDateTimeUtil;

public class ZonedDateTimeFromTimestampDeserializer extends StdDeserializer<ZonedDateTime> {

	private static final long serialVersionUID = 1L;

	private final transient ZonedDateTimeUtil zonedDateTimeUtil;

	public ZonedDateTimeFromTimestampDeserializer() {
		super(ZonedDateTime.class);
		this.zonedDateTimeUtil = new ZonedDateTimeUtil();
	}

	@Override
	public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext context)
			throws IOException {
		return zonedDateTimeUtil.convertFromWrittenTimestamp(jsonParser.getValueAsString());
	}

}
