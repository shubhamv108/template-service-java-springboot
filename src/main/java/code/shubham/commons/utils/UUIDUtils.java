package code.shubham.commons.utils;

import code.shubham.commons.generators.id.implementations.UUIDGenerator;

import java.util.UUID;

public class UUIDUtils {

	public static String uuid5(final String key) {
		return UUIDGenerator.generateType5UUID(key).toString();
	}

	public static String generate() {
		return UUID.randomUUID().toString();
	}

}
