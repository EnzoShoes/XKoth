package me.enzosocks.xkoth_socks.managers.leaderboards;

import java.util.Date;

public class Win {
	private String playerOrTeam;
	private Date winDate;

	public Win(String playerOrTeam, Date winDate) {
		this.playerOrTeam = playerOrTeam;
		this.winDate = winDate;
	}

	public String getPlayerOrTeam() {
		return playerOrTeam;
	}

	public Date getWinDate() {
		return winDate;
	}
}
