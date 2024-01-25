package me.enzosocks.xkoth_socks.instance.game;

public class GameRules {
	private long maxTime;
	private int pointsToWin;
	private boolean winnerIfTimeRunsOut;

	public GameRules(long maxTime, int pointsToWin, boolean winnerIfTimeRunsOut) {
		this.maxTime = maxTime;
		this.pointsToWin = pointsToWin;
		this.winnerIfTimeRunsOut = winnerIfTimeRunsOut;
	}

	public GameRules() {
		this(600, 100, true);
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
