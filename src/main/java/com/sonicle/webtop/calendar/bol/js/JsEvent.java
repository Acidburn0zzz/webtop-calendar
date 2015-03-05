/*
 * webtop-calendar is a WebTop Service developed by Sonicle S.r.l.
 * Copyright (C) 2014 Sonicle S.r.l.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License version 3 as published by
 * the Free Software Foundation with the addition of the following permission
 * added to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED
 * WORK IN WHICH THE COPYRIGHT IS OWNED BY SONICLE, SONICLE DISCLAIMS THE
 * WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301 USA.
 *
 * You can contact Sonicle S.r.l. at email address sonicle@sonicle.com
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License version 3.
 *
 * In accordance with Section 7(b) of the GNU Affero General Public License
 * version 3, these Appropriate Legal Notices must retain the display of the
 * "Powered by Sonicle WebTop" logo. If the display of the logo is not reasonably
 * feasible for technical reasons, the Appropriate Legal Notices must display
 * the words "Powered by Sonicle WebTop".
 */
package com.sonicle.webtop.calendar.bol.js;

import com.sonicle.webtop.calendar.bol.OEvent;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 *
 * @author malbinola
 */
public class JsEvent {
	
	public Integer eventId;
	public Integer calendarId;
	public String title;
	public String fromDate;
	public String fromTime;
	public String toDate;
	public String toTime;
	public String timezone;
	public Boolean allDay;
	public String location;
	
	public JsEvent(OEvent event, TimeZone tz) {
		eventId = event.getEventId();
		calendarId = event.getCalendarId();
		title = event.getTitle();
		
		SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
		dateSdf.setTimeZone(tz);
		
		fromDate = dateSdf.format(event.getFromDate());
		toDate = dateSdf.format(event.getToDate());
		
		SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm");
		timeSdf.setTimeZone(tz);
		fromTime = timeSdf.format(event.getFromDate());
		toTime = timeSdf.format(event.getToDate());
		
		timezone = event.getTimezone();
		allDay = event.getAllDay();
		location = event.getLocation();
	}
}