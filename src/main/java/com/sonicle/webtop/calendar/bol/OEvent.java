/* 
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
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301 USA.
 *
 * You can contact Sonicle S.r.l. at email address sonicle[at]sonicle[dot]com
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License version 3.
 *
 * In accordance with Section 7(b) of the GNU Affero General Public License
 * version 3, these Appropriate Legal Notices must retain the display of the
 * Sonicle logo and Sonicle copyright notice. If the display of the logo is not
 * reasonably feasible for technical reasons, the Appropriate Legal Notices must
 * display the words "Copyright (C) 2014 Sonicle S.r.l.".
 */
package com.sonicle.webtop.calendar.bol;

import com.sonicle.commons.time.DateTimeUtils;
import com.sonicle.webtop.calendar.model.Event;
import com.sonicle.webtop.calendar.jooq.tables.pojos.Events;
import org.joda.time.DateTime;

/**
 *
 * @author malbinola
 */
public class OEvent extends Events {
	public final static String REV_STATUS_NEW = "N";
	public final static String REV_STATUS_MODIFIED = "M";
	public final static String REV_STATUS_DELETED = "D";
	
	public OEvent() {
		super();
		setReadOnly(false);
		setRevisionStatus(REV_STATUS_NEW);
	}
	
	public void fillFrom(Event event) {
		setCalendarId(event.getCalendarId());
		
		setStartDate(event.getStartDate());
		setEndDate(event.getEndDate());
		setTimezone(event.getTimezone());
		setAllDay(event.getAllDay());
		ensureCoherence(this);
		
		setTitle(event.getTitle());
		setDescription(event.getDescription());
		setLocation(event.getLocation());
		setIsPrivate(event.getIsPrivate());
		setBusy(event.getBusy());
		setReminder(event.getReminder());
		setActivityId(event.getActivityId());
		setMasterDataId(event.getMasterDataId());
		setStatMasterDataId(event.getStatMasterDataId());
		setCausalId(event.getCausalId());
		setOrganizer(event.getOrganizer());
		setRevisionTimestamp(event.getRevisionTimestamp());
		setPublicUid(event.getPublicUid());
	}
	
	public static void ensureCoherence(OEvent event) {
		// Ensure start < end
		if(event.getStartDate().compareTo(event.getEndDate()) > 0) {
			// Swap dates...
			DateTime dt = event.getEndDate();
			event.setEndDate(event.getStartDate());
			event.setStartDate(dt);
		}
		
		// If event is all day, take max time as possible
		if(event.getAllDay()) {
			event.setStartDate(event.getStartDate().withTimeAtStartOfDay());
			event.setEndDate(DateTimeUtils.withTimeAtEndOfDay(event.getEndDate()));
		}
	}
}
