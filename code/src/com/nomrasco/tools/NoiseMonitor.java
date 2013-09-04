package com.nomrasco.tools;

public class NoiseMonitor {
	public static final int DEFAULT_INTERVAL = 333;
	
	public static synchronized void startMonitoring() {
		
	}
	
	public static synchronized void stopMonitoring() {
		
	}
	
	public static synchronized void setInterval(final int newInterval) {
		if(newInterval != mInterval) {
			mInterval = newInterval;
			mIntervalChanged(mInterval);
		}
	}
	
	public static synchronized int interval() {
		return mInterval;
	}
	
	public static synchronized void setCallback(final NoiseMonitorCallback callback) {
		if(callback != mCallback) {
			mCallbackChanged(mCallback, callback);
			
			if(callback != null) {
				mCallback = callback;
			}
			else {
				// stop automatically monitoring
				NoiseMonitor.stopMonitoring();			
				mCallback = NoiseMonitorCallback.NULL;
			}
		}
	}
	
	public static synchronized NoiseMonitorCallback callback() {
		if(mCallback == NoiseMonitorCallback.NULL) {
			return null;
		}
		else {
			return mCallback;
		}
	}
	
	public interface NoiseMonitorCallback {
		public void noiseMonitorStarted();
		public void noiseMonitorStopped();
		public void noiseMonitorIntervalChanged(final int interval);
		public void noiseMonitorNewLevel(final int level);
		public void noiseMonitorCallbackChanged(final NoiseMonitorCallback newCallback);
		
		static public NoiseMonitorCallback NULL = new NoiseMonitorCallback() {
			public void noiseMonitorStopped() {}
			public void noiseMonitorStarted() {}
			public void noiseMonitorNewLevel(final int level) {}
			public void noiseMonitorIntervalChanged(final int interval) {}
			public void noiseMonitorCallbackChanged(final NoiseMonitorCallback newCallback) {}
		};
	}

	static private void mIntervalChanged(final int interval) {
		mCallback.noiseMonitorIntervalChanged(interval);
	}
	static private void mThreadStarted() {}
	static private void mThreadStopped() {}
	static private void mCallbackChanged(NoiseMonitorCallback oldCallback,
	                                     NoiseMonitorCallback newCallback)
	{
		oldCallback.noiseMonitorCallbackChanged(newCallback);
		newCallback.noiseMonitorCallbackChanged(newCallback);
	}
	
	static private NoiseMonitorThread mThread = new NoiseMonitorThread();
	static private NoiseMonitorCallback mCallback = NoiseMonitorCallback.NULL;
	static private int mInterval = DEFAULT_INTERVAL;	
	
	static private class NoiseMonitorThread extends Thread {
		
	}
}
