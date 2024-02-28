package me.enzosocks.xkoth_socks.schedulers.displays;

public abstract class DisplayManager implements DisplayConfig {
	protected final boolean enabled;
	protected final int viewDistance;
	protected final boolean onlyShowWhenCapturing;

	public DisplayManager(boolean enabled, int viewDistance, boolean onlyShowWhenCapturing) {
		this.enabled = enabled;
		this.viewDistance = viewDistance;
		this.onlyShowWhenCapturing = onlyShowWhenCapturing;
	}

	public void update(DisplayData displayData) {
		if (this.enabled) {
			this.doUpdate(displayData);
		}
	}

	protected abstract void doUpdate(DisplayData displayData);

	public abstract void clear();


	@Override
	public boolean isOnlyShowWhenCapturing() {
		return onlyShowWhenCapturing;
	}

	@Override
	public int getViewDistance() {
		return viewDistance;
	}

}