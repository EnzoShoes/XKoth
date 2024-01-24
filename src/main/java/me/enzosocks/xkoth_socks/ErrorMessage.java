package me.enzosocks.xkoth_socks;

public enum ErrorMessage {
	SUCCESS("§aSuccess!"),
	NO_PERMISSION("§cYou don't have permission to do that!"),
	NOT_PLAYER("§cYou must be a player to do that!"),
	KOTH_NOT_FOUND("§cKoth not found!"),
	KOTH_ALREADY_EXISTS("§cA koth with that name already exists!"),
	INVALID_REGION("§cRegion does not exist ! Please select a region using world edit before creating a koth !");

	private String message;

	ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
