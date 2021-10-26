package com.sevensignal.infocollector.models;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class InformationElementA {
	public static final int EID_SSID = 0;
	public static final int EID_SUPPORTED_RATES = 1;
	public static final int EID_TIM = 5;
	public static final int EID_BSS_LOAD = 11;
	public static final int EID_ERP = 42;
	public static final int EID_HT_CAPABILITIES = 45;
	public static final int EID_RSN = 48;
	public static final int EID_EXTENDED_SUPPORTED_RATES = 50;
	public static final int EID_HT_OPERATION = 61;
	public static final int EID_INTERWORKING = 107;
	public static final int EID_ROAMING_CONSORTIUM = 111;
	public static final int EID_EXTENDED_CAPS = 127;
	public static final int EID_VHT_CAPABILITIES = 191;
	public static final int EID_VHT_OPERATION = 192;
	public static final int EID_VSA = 221;
	public static final int EID_EXTENSION_PRESENT = 255;

	// Extension IDs
	public static final int EID_EXT_HE_CAPABILITIES = 35;
	public static final int EID_EXT_HE_OPERATION = 36;

	public int id;
	public int idExt;

	public byte[] bytes;

	public InformationElementA() {
	}

	public InformationElementA(int id, byte[] bytes) {
		this.id = id;
		this.bytes = bytes.clone();
	}

	public InformationElementA(InformationElementA rhs) {
		this.id = rhs.id;
		this.idExt = rhs.idExt;
		this.bytes = rhs.bytes.clone();
	}

	/**
	 * The element ID of the information element. Defined in the IEEE 802.11-2016 spec
	 * Table 9-77.
	 */
	public int getId() {
		return id;
	}

	/**
	 * The element ID Extension of the information element. Defined in the IEEE 802.11-2016 spec
	 * Table 9-77.
	 */
	public int getIdExt() {
		return idExt;
	}

	/**
	 * Get the specific content of the information element.
	 */
	public ByteBuffer getBytes() {
		return ByteBuffer.wrap(bytes).asReadOnlyBuffer();
	}

	public String toString() {
		return "IE: " + this.id + ", " + Arrays.toString(this.bytes);
	}
}
