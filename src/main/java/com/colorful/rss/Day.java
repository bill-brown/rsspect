package com.colorful.rss;

import java.io.Serializable;

public class Day implements Serializable {

	private static final long serialVersionUID = 1428375851718959215L;

	private final String day;

	Day(String day) throws RSSpectException {
		this.day = day;
		if (!this.day.equals("Monday") && !this.day.equals("Tuesday")
				&& !this.day.equals("Wednesday")
				&& !this.day.equals("Thursday") && !this.day.equals("Friday")
				&& !this.day.equals("Saturday") && !this.day.equals("Sunday")) {
			throw new RSSpectException(
					"day elements must have a value of Monday, Tuesday, Wednesday, Thursday, Friday, Saturday or Sunday.");
		}

	}

	public String getDay() {
		return day;
	}
}
