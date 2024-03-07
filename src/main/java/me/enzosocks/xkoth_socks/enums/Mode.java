package me.enzosocks.xkoth_socks.enums;

public enum Mode {
	SCORE("score"),
	CAPTURE("capture");

	private String mode;

	Mode(String mode) {
		this.mode = mode;
	}

	public static Mode fromString(String mode) {
		for (Mode m : Mode.values()) {
			if (m.mode.equalsIgnoreCase(mode)) {
				return m;
			}
		}
		throw new IllegalArgumentException("Invalid mode: " + mode);
	}
}
