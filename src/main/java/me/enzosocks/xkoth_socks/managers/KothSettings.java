package me.enzosocks.xkoth_socks.managers;

public class KothSettings {
	private long maxTime;
	private int pointsToWin;
	private boolean winnerIfTimeRunsOut;

	public KothSettings() {
		this(600, 100, true);
	}

	public KothSettings(long maxTime, int pointsToWin, boolean winnerIfTimeRunsOut) {
		this.maxTime = maxTime;
		this.pointsToWin = pointsToWin;
		this.winnerIfTimeRunsOut = winnerIfTimeRunsOut;
	}

	public long getMaxTime() {
		return maxTime;
	}

	public int getPointsToWin() {
		return pointsToWin;
	}

	public boolean isWinnerIfTimeRunsOut() {
		return winnerIfTimeRunsOut;
	}

}
