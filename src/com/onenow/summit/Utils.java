package com.onenow.summit;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	public Utils() {
		// TODO Auto-generated constructor stub
	}

	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static final YakConfig readConfig(String file)
			throws JsonParseException, JsonMappingException, IOException {
		return OBJECT_MAPPER.readValue(new File(file), YakConfig.class);
	}

	public static final YakConfig readConfig() throws JsonParseException,
			JsonMappingException, IOException {
		return readConfig("./config/yak.json");
	}
}
