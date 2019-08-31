package com.github.marceloleite2604.util.time.zoned.serializer.timestamp;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.marceloleite2604.util.time.zoned.ZonedDateTimeUtil;

public class ZonedDateTimeToTimestampSerializer extends StdSerializer<ZonedDateTime> {

	private static final long serialVersionUID = 1L;

	private final transient ZonedDateTimeUtil zonedDateTimeUtil;
	
	public ZonedDateTimeToTimestampSerializer() {
		super(ZonedDateTime.class);
		this.zonedDateTimeUtil = new ZonedDateTimeUtil();
	}

	@Override
	public void serialize(ZonedDateTime zonedDateTime, JsonGenerator generator, SerializerProvider provider)
			throws IOException {
		generator.writeString(zonedDateTimeUtil.toStringAsTimestamp(zonedDateTime));
	}
}