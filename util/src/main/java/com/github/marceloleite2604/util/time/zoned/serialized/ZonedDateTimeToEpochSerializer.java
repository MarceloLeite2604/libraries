package com.github.marceloleite2604.util.time.zoned.serialized;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.marceloleite2604.util.time.zoned.ZonedDateTimeUtil;

public class ZonedDateTimeToEpochSerializer extends StdSerializer<ZonedDateTime> {

	private static final long serialVersionUID = 1L;

	private final transient ZonedDateTimeUtil zonedDateTimeUtil;

	public ZonedDateTimeToEpochSerializer() {
		super(ZonedDateTime.class);
		this.zonedDateTimeUtil = new ZonedDateTimeUtil();
	}

	@Override
	public void serialize(ZonedDateTime zonedDateTime, JsonGenerator generator,
			SerializerProvider provider) throws IOException {
		generator.writeNumber(zonedDateTimeUtil.convertAsEpochTime(zonedDateTime));
	}

}
