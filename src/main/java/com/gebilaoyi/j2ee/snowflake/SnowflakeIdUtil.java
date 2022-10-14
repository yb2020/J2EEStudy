package com.gebilaoyi.j2ee.snowflake;

public class SnowflakeIdUtil {
	private static final SnowflakeIdWorker idWorker;
	static {
		idWorker = new SnowflakeIdWorker();
	}

	public static Long nextId() {
		return idWorker.nextId();
	}
	/**
	 * 获取long类型雪花ID
	 */
	public static long uniqueLong() {
		return nextId();
	}

	/**
	 * 获取String类型雪花ID
	 */
	public static String uniqueLongHex() {
		return String.format("%016x", uniqueLong());
	}

}
