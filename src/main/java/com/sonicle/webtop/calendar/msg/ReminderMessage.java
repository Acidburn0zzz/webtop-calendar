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
package com.sonicle.webtop.calendar.msg;

import com.sonicle.commons.time.DateTimeUtils;
import com.sonicle.webtop.calendar.CalendarManager;
import com.sonicle.webtop.calendar.bol.js.JsSchedulerEvent;
import com.sonicle.webtop.calendar.bol.model.SchedulerEvent;
import com.sonicle.webtop.core.sdk.ServiceMessage;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author malbinola
 */
public class ReminderMessage extends ServiceMessage {
	
	public static final String REMIND_ON = "remindOn";
	public static final String TEXT = "text";
	public static final String EVENT = "event";
	public static final String EVENT_ID = "id";
	public static final String EVENT_EVENT_ID = "eventId";
	
	private String remindOn;
	private String text;
	private EventData event;
	
	public ReminderMessage(String serviceId, DateTime remindOn, SchedulerEvent event, DateTimeZone profileTz) {
		super(serviceId, "notifyReminder");
		setRemindOn(remindOn, profileTz);
		setText(event);
		setEvent(event);
	}
	
	public String getRemindOn() {
		return remindOn;
	}
	
	public final ServiceMessage setRemindOn(DateTime remindOn, DateTimeZone profileTz) {
		DateTimeFormatter ymdhmsZoneFmt = DateTimeUtils.createYmdHmsFormatter(profileTz);
		this.remindOn = ymdhmsZoneFmt.print(remindOn);
		return this;
	}
	
	public String getText() {
		return text;
	}
	
	public final ServiceMessage setText(SchedulerEvent event) {
		text = StringUtils.isEmpty(event.getLocation()) ? event.getTitle() : event.getTitle()+" @ "+event.getLocation();
		return this;
	}
	
	public EventData getEvent() {
		return event;
	}
	
	public final ServiceMessage setEvent(SchedulerEvent event) {
		this.event = new EventData(event.getId(), event.getEventId());
		return this;
	}
	
	public static class EventData {
		public String id;
		public Integer eventId;
		
		public EventData(String id, Integer eventId) {
			this.id = id;
			this.eventId = eventId;
		}
	}
}
