package org.marceloleite.libraries.time.deserializer;

import java.io.IOException;
import java.time.ZonedDateTime;

import org.marceloleite.libraries.time.util.ZonedDateTimeUtil;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

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
