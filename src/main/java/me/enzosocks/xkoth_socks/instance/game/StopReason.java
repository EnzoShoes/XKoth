package me.enzosocks.xkoth_socks.instance.game;

public enum StopReason {
	TIMEOUT("Game has ended due to timeout ! Winner is: %winner%", true),
	TIMEOUT_NO_WINNER("Game has ended due to timeout ! No winner.", false),
	FORCE_STOP("Game has been ended by an administrator.", false),
	CAPTURE("%winner% has captured the KOTH %name%!", true);

	String message;
	boolean hasWinner;

	StopReason(String message, boolean hasWinner) {
		this.message = message;
		this.hasWinner = hasWinner;
	}

	public String getMessage() {
		return message;
	}
}
