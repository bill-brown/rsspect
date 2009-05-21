package com.colorful.rss;

import java.io.Serializable;

public class Hour implements Serializable {

	private static final long serialVersionUID = -6736105071042205154L;

	private final String hour;

	Hour(String hour) throws RSSpectException {
		if (hour != null) {
			try {
				int localHour = Integer.parseInt(hour);
				if (localHour > 23 || localHour < 0) {
					throw new RSSpectException(
							"hour elements must be between 0 and 23 inclusive.");
				}
			} catch (NumberFormatException n) {
				throw new RSSpectException("invalid number format for hour.");
			}
		}
		this.hour = hour;
	}

	public String getHour() {
		return hour;
	}
}
