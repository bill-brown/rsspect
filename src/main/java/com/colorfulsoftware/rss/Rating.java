/**
 * Copyright ${year} Bill Brown
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.colorfulsoftware.rss;

import java.io.Serializable;

/**
 * <p>
 * The &lt;rating> element.
 * </p>
 * <p>
 * From the <a href="http://cyber.law.harvard.edu/rss/rss.html">RSS 2.0
 * specification</a>...
 * </p>
 * <p>
 * The <a href="http://www.w3.org/PICS/">PICS</a> rating for the channel.
 * </p>
 * 
 * @author Bill Brown
 * 
 */
public class Rating implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1511199969383702784L;

	private final String rating;

	Rating(String rating) throws RSSpectException {
		if (rating == null || rating.equals("")) {
			throw new RSSpectException("rating SHOULD NOT be blank.");
		}
		this.rating = rating;
	}

	Rating(Rating rating) {
		this.rating = rating.rating;
	}

	/**
	 * @return the rating.
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * Shows the contents of the &lt;rating> element.
	 */
	@Override
	public String toString() {
		return "<rating>" + rating + "</rating>";
	}
}
