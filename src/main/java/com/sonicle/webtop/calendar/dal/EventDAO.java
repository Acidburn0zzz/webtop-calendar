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
package com.sonicle.webtop.calendar.dal;

import com.sonicle.webtop.calendar.bol.VVEvent;
import com.sonicle.webtop.calendar.bol.OEvent;
import static com.sonicle.webtop.calendar.jooq.Sequences.SEQ_EVENTS;
import static com.sonicle.webtop.calendar.jooq.Tables.CALENDARS;
import static com.sonicle.webtop.calendar.jooq.Tables.EVENTS;
import static com.sonicle.webtop.calendar.jooq.Tables.EVENTS_ATTENDEES;
import static com.sonicle.webtop.calendar.jooq.Tables.RECURRENCES;
import static com.sonicle.webtop.calendar.jooq.Tables.RECURRENCES_BROKEN;
import com.sonicle.webtop.calendar.jooq.tables.Events;
import com.sonicle.webtop.calendar.jooq.tables.RecurrencesBroken;
import com.sonicle.webtop.calendar.jooq.tables.records.EventsRecord;
import com.sonicle.webtop.core.dal.BaseDAO;
import com.sonicle.webtop.core.dal.DAOException;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;
import static org.jooq.impl.DSL.*;

/**
 *
 * @author malbinola
 */
public class EventDAO extends BaseDAO {
	private final static EventDAO INSTANCE = new EventDAO();
	public static EventDAO getInstance() {
		return INSTANCE;
	}
	
	public Long getSequence(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		Long nextID = dsl.nextval(SEQ_EVENTS);
		return nextID;
	}
	
	public Integer selectRecurrenceId(Connection con, int eventId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select(
				EVENTS.RECURRENCE_ID
			)
			.from(EVENTS)
			.where(
				EVENTS.EVENT_ID.equal(eventId)
			)
			.fetchOne(0, Integer.class);
	}
	
	public OEvent selectById(Connection con, int eventId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select()
			.from(EVENTS)
			.where(
				EVENTS.EVENT_ID.equal(eventId)
			)
			.fetchOneInto(OEvent.class);
	}
	
	public OEvent selectAliveById(Connection con, int eventId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select()
			.from(EVENTS)
			.where(
					EVENTS.EVENT_ID.equal(eventId)
					.and(
						EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_NEW)
						.or(EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_MODIFIED))
					)
			)
			.fetchOneInto(OEvent.class);
	}
	
	public List<Integer> selectAliveIdsByPublicUid(Connection con, String publicUid) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select(EVENTS.EVENT_ID)
			.from(EVENTS)
			.where(
					EVENTS.PUBLIC_UID.equal(publicUid)
					.and(
						EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_NEW)
						.or(EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_MODIFIED))
					)
			)
			.fetchInto(Integer.class);
	}
	
	public List<Integer> selectAliveIdsByCalendarsPublicUid(Connection con, List<Integer> calendarIds, String publicUid) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select(EVENTS.EVENT_ID)
			.from(EVENTS)
			.where(
				EVENTS.CALENDAR_ID.in(calendarIds)
				.and(EVENTS.PUBLIC_UID.equal(publicUid))
				.and(
					EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_NEW)
					.or(EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_MODIFIED))
				)
			)
			.fetchInto(Integer.class);
	}
	
	public Map<String, Integer> selectHrefsByByCalendar(Connection con, int calendarId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select(
				EVENTS.EVENT_ID,
				EVENTS.HREF
			)
			.from(EVENTS)
			.where(
				EVENTS.CALENDAR_ID.equal(calendarId)
				.and(
					EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_NEW)
					.or(EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_MODIFIED))
				)
			)
			.fetchMap(EVENTS.HREF, EVENTS.EVENT_ID);
	}
	
	public int insert(Connection con, OEvent item, DateTime revisionTimestamp) throws DAOException {
		DSLContext dsl = getDSL(con);
		item.ensureCoherence();
		item.setRevisionStatus(OEvent.REV_STATUS_NEW);
		item.setRevisionTimestamp(revisionTimestamp);
		item.setRevisionSequence(0);
		EventsRecord record = dsl.newRecord(EVENTS, item);
		return dsl
			.insertInto(EVENTS)
			.set(record)
			.execute();
	}
	
	public int update(Connection con, OEvent item, DateTime revisionTimestamp) throws DAOException {
		DSLContext dsl = getDSL(con);
		item.ensureCoherence();
		item.setRevisionStatus(OEvent.REV_STATUS_MODIFIED);
		item.setRevisionTimestamp(revisionTimestamp);
		return dsl
			.update(EVENTS)
			.set(EVENTS.CALENDAR_ID, item.getCalendarId())
			.set(EVENTS.REVISION_STATUS, item.getRevisionStatus())
			.set(EVENTS.REVISION_TIMESTAMP, item.getRevisionTimestamp())
			.set(EVENTS.RECURRENCE_ID, item.getRecurrenceId())
			.set(EVENTS.START_DATE, item.getStartDate())
			.set(EVENTS.END_DATE, item.getEndDate())
			.set(EVENTS.TIMEZONE, item.getTimezone())
			.set(EVENTS.ALL_DAY, item.getAllDay())
			.set(EVENTS.ORGANIZER, item.getOrganizer())
			.set(EVENTS.TITLE, item.getTitle())
			.set(EVENTS.DESCRIPTION, item.getDescription())
			.set(EVENTS.LOCATION, item.getLocation())
			.set(EVENTS.IS_PRIVATE, item.getIsPrivate())
			.set(EVENTS.BUSY, item.getBusy())
			.set(EVENTS.REMINDER, item.getReminder())
			.set(EVENTS.HREF, item.getHref())
			.set(EVENTS.ETAG, item.getEtag())
			.set(EVENTS.ACTIVITY_ID, item.getActivityId())
			.set(EVENTS.MASTER_DATA_ID, item.getMasterDataId())
			.set(EVENTS.STAT_MASTER_DATA_ID, item.getStatMasterDataId())
			.set(EVENTS.CAUSAL_ID, item.getCausalId())
			.where(
				EVENTS.EVENT_ID.equal(item.getEventId())
			)
			.execute();
	}
	
	public int updateCalendar(Connection con, int eventId, int calendarId, DateTime revisionTimestamp) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.update(EVENTS)
			.set(EVENTS.CALENDAR_ID,calendarId)
			.set(EVENTS.REVISION_STATUS, OEvent.REV_STATUS_MODIFIED)
			.set(EVENTS.REVISION_TIMESTAMP, revisionTimestamp)
			.where(
				EVENTS.EVENT_ID.equal(eventId)
			)
			.execute();
	}
	
	public int updateRevision(Connection con, int eventId, DateTime revisionTimestamp) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.update(EVENTS)
			.set(EVENTS.REVISION_TIMESTAMP, revisionTimestamp)
			.where(
				EVENTS.EVENT_ID.equal(eventId)
			)
			.execute();
	}
	
	public int updateRevisionStatus(Connection con, int eventId, String revisionStatus, DateTime revisionTimestamp) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.update(EVENTS)
			.set(EVENTS.REVISION_STATUS, revisionStatus)
			.set(EVENTS.REVISION_TIMESTAMP, revisionTimestamp)
			.where(
				EVENTS.EVENT_ID.equal(eventId)
			)
			.execute();
	}
	
	public int updateRemindedOn(Connection con, int eventId, DateTime remindedOn) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.update(EVENTS)
			.set(EVENTS.REMINDED_ON, remindedOn)
			.where(
				EVENTS.EVENT_ID.equal(eventId)
			)
			.execute();
	}
	
	public int updateRemindedOnIfNull(Connection con, int eventId, DateTime remindedOn) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.update(EVENTS)
			.set(EVENTS.REMINDED_ON, remindedOn)
			.where(
				EVENTS.EVENT_ID.equal(eventId)
				.and(EVENTS.REMINDED_ON.isNull())
			)
			.execute();
	}
	
	public int deleteById(Connection con, int eventId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(EVENTS)
			.where(
				EVENTS.EVENT_ID.equal(eventId)
			)
			.execute();
	}
	
	public int deleteByCalendar(Connection con, int calendarId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(EVENTS)
			.where(
				EVENTS.CALENDAR_ID.equal(calendarId)
			)
			.execute();
	}
	
	public int logicDeleteById(Connection con, int eventId, DateTime revisionTimestamp) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.update(EVENTS)
			.set(EVENTS.REVISION_STATUS, OEvent.REV_STATUS_DELETED)
			.set(EVENTS.REVISION_TIMESTAMP, revisionTimestamp)
			.where(
				EVENTS.EVENT_ID.equal(eventId)
			)
			.execute();
	}
	
	public int logicDeleteByCalendar(Connection con, int calendarId, DateTime revisionTimestamp) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.update(EVENTS)
			.set(EVENTS.REVISION_STATUS, OEvent.REV_STATUS_DELETED)
			.set(EVENTS.REVISION_TIMESTAMP, revisionTimestamp)
			.where(
				EVENTS.CALENDAR_ID.equal(calendarId)
			)
			.execute();
	}
	
	public int deleteBrokenOrphansByEventId(Connection con, int eventId) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.delete(EVENTS)
			.where(
				EVENTS.EVENT_ID.in(
					DSL.select(
						RECURRENCES_BROKEN.NEW_EVENT_ID
					)
					.from(RECURRENCES_BROKEN)
					.where(
						RECURRENCES_BROKEN.EVENT_ID.equal(eventId)
					)
				)				
			)
			.execute();
	}
	
	public VVEvent viewById(Connection con, int eventId) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		RecurrencesBroken rbk1 = RECURRENCES_BROKEN.as("rbk1");
		Events eve1 = EVENTS.as("eve1");
		Field<Integer> referencedEventId = DSL
			.select(eve1.EVENT_ID)
			.from(rbk1.join(eve1).on(rbk1.EVENT_ID.equal(eve1.EVENT_ID)))
			.where(
				rbk1.NEW_EVENT_ID.equal(EVENTS.EVENT_ID)
				.and(eve1.REVISION_STATUS.notEqual(OEvent.REV_STATUS_DELETED))
			).asField();
		Field<Integer> originalEventId = DSL.coalesce(referencedEventId, EVENTS.EVENT_ID).as("original_event_id");
		
		Field<Boolean> hasAttendees = DSL.field(DSL.exists(
			selectOne()
			.from(EVENTS_ATTENDEES)
			.where(
				EVENTS_ATTENDEES.EVENT_ID.equal(EVENTS.EVENT_ID)
			)
		)).as("has_attendees");
		
		return dsl
			.select(
				EVENTS.fields()
			)
			.select(
				originalEventId,
				hasAttendees,
				CALENDARS.DOMAIN_ID.as("calendar_domain_id"),
				CALENDARS.USER_ID.as("calendar_user_id")
			)
			.from(EVENTS)
			.join(CALENDARS).on(EVENTS.CALENDAR_ID.equal(CALENDARS.CALENDAR_ID))
			.where(
				EVENTS.EVENT_ID.equal(eventId)
				.and(
					EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_NEW)
					.or(EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_MODIFIED))
				)
			)
			.fetchOneInto(VVEvent.class);
	}
	
	public VVEvent viewByPublicUid(Connection con, String publicUid) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		RecurrencesBroken rbk1 = RECURRENCES_BROKEN.as("rbk1");
		Events eve1 = EVENTS.as("eve1");
		Field<Integer> referencedEventId = DSL
			.select(eve1.EVENT_ID)
			.from(rbk1.join(eve1).on(rbk1.EVENT_ID.equal(eve1.EVENT_ID)))
			.where(
				rbk1.NEW_EVENT_ID.equal(EVENTS.EVENT_ID)
				.and(eve1.REVISION_STATUS.notEqual(OEvent.REV_STATUS_DELETED))
			).asField();
		Field<Integer> originalEventId = DSL.coalesce(referencedEventId, EVENTS.EVENT_ID).as("original_event_id");
		
		Field<Boolean> hasAttendees = DSL.field(DSL.exists(
			selectOne()
			.from(EVENTS_ATTENDEES)
			.where(
				EVENTS_ATTENDEES.EVENT_ID.equal(EVENTS.EVENT_ID)
			)
		)).as("has_attendees");
		
		return dsl
			.select(
				EVENTS.fields()
			)
			.select(
				originalEventId,
				hasAttendees,
				CALENDARS.DOMAIN_ID.as("calendar_domain_id"),
				CALENDARS.USER_ID.as("calendar_user_id")
			)
			.from(EVENTS)
			.join(CALENDARS).on(EVENTS.CALENDAR_ID.equal(CALENDARS.CALENDAR_ID))
			.where(
				EVENTS.PUBLIC_UID.equal(publicUid)
				.and(
					EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_NEW)
					.or(EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_MODIFIED))
				)
			)
			.fetchOneInto(VVEvent.class);
	}
	
	public List<VVEvent> viewDatesByCalendarFromTo(Connection con, Integer calendarId, DateTime fromDate, DateTime toDate) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		RecurrencesBroken rbk1 = RECURRENCES_BROKEN.as("rbk1");
		Events eve1 = EVENTS.as("eve1");
		Field<Integer> referencedEventId = DSL
			.select(eve1.EVENT_ID)
			.from(rbk1.join(eve1).on(rbk1.EVENT_ID.equal(eve1.EVENT_ID)))
			.where(
				rbk1.NEW_EVENT_ID.equal(EVENTS.EVENT_ID)
				.and(eve1.REVISION_STATUS.notEqual(OEvent.REV_STATUS_DELETED))
			).asField();
		Field<Integer> originalEventId = DSL.coalesce(referencedEventId, EVENTS.EVENT_ID).as("original_event_id");
		
		return dsl
			.select(
				EVENTS.EVENT_ID,
				EVENTS.RECURRENCE_ID,
				EVENTS.START_DATE,
				EVENTS.END_DATE,
				EVENTS.TIMEZONE,
				EVENTS.ALL_DAY,
				EVENTS.ORGANIZER,
				EVENTS.REVISION_TIMESTAMP,
				originalEventId,
				CALENDARS.DOMAIN_ID.as("calendar_domain_id"),
				CALENDARS.USER_ID.as("calendar_user_id")
				//field("false", Boolean.class).as("is_recurring")
				/*
				field(
					exists(
							selectOne()
							.from(rbk.join(eve).on(rbk.EVENT_ID.equal(eve.EVENT_ID)))
							.where(
									rbk.NEW_EVENT_ID.equal(EVENTS.EVENT_ID)
									.and(eve.STATUS.notEqual(OEvent.STATUS_DELETED))
							)
					)
				).as("is_broken")
				*/
			)
			.from(EVENTS)
			.join(CALENDARS).on(EVENTS.CALENDAR_ID.equal(CALENDARS.CALENDAR_ID))
			.where(
				EVENTS.CALENDAR_ID.equal(calendarId)
				.and(
					EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_NEW)
					.or(EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_MODIFIED))
				)
				.and(EVENTS.RECURRENCE_ID.isNull())
				.and(
					EVENTS.START_DATE.between(fromDate, toDate) // Events that start in current range
					.or(EVENTS.END_DATE.between(fromDate, toDate)) // Events that end in current range
					.or(EVENTS.START_DATE.lessThan(fromDate).and(EVENTS.END_DATE.greaterThan(toDate))) // Events that start before and end after
				)
			)
			.orderBy(
				EVENTS.START_DATE
			)
			.fetchInto(VVEvent.class);
	}
	
	public List<VVEvent> viewRecurringDatesByCalendarFromTo(Connection con, Integer calendarId, DateTime fromDate, DateTime toDate) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select(
				EVENTS.EVENT_ID,
				EVENTS.RECURRENCE_ID,
				EVENTS.START_DATE,
				EVENTS.END_DATE,
				EVENTS.TIMEZONE,
				EVENTS.ALL_DAY,
				EVENTS.ORGANIZER,
				EVENTS.REVISION_TIMESTAMP,
				EVENTS.EVENT_ID.as("original_event_id"), // For recurring events, originalEventId is always equal to eventId
				CALENDARS.DOMAIN_ID.as("calendar_domain_id"),
				CALENDARS.USER_ID.as("calendar_user_id")
			)
			.from(EVENTS)
			.join(CALENDARS).on(EVENTS.CALENDAR_ID.equal(CALENDARS.CALENDAR_ID))
			.join(RECURRENCES).on(EVENTS.RECURRENCE_ID.equal(RECURRENCES.RECURRENCE_ID))
			.where(
				EVENTS.CALENDAR_ID.equal(calendarId)
				.and(
					EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_NEW)
					.or(EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_MODIFIED))
				)
				.and(EVENTS.RECURRENCE_ID.isNotNull())
				.and(
					RECURRENCES.START_DATE.between(fromDate, toDate) // Recurrences that start in current range
					.or(RECURRENCES.UNTIL_DATE.between(fromDate, toDate)) // Recurrences that end in current range
					.or(RECURRENCES.START_DATE.lessThan(fromDate).and(RECURRENCES.UNTIL_DATE.greaterThan(toDate))) // Recurrences that start before and end after
				)
			)
			.orderBy(
				EVENTS.START_DATE
			)
			.fetchInto(VVEvent.class);
	}
	
	public List<VVEvent> viewByCalendarFromToPattern(Connection con, int calendarId, DateTime fromDate, DateTime toDate, String pattern) throws DAOException {
		return viewByCalendarFromToPattern(con, Arrays.asList(calendarId), fromDate, toDate, pattern);
	}
	
	public List<VVEvent> viewByCalendarFromToPattern(Connection con, Collection<Integer> calendarIds, DateTime fromDate, DateTime toDate, String pattern) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		Condition patternCndt = DSL.trueCondition();
		if (!StringUtils.isBlank(pattern)) {
			patternCndt = EVENTS.TITLE.likeIgnoreCase(pattern)
				.or(EVENTS.LOCATION.likeIgnoreCase(pattern));
		}
		Condition rangeCndt = DSL.trueCondition();
		if ((fromDate != null) && (toDate != null)) {
			rangeCndt = EVENTS.START_DATE.between(fromDate, toDate) // Events that start in current range
				.or(EVENTS.END_DATE.between(fromDate, toDate)) // Events that end in current range
				.or(EVENTS.START_DATE.lessThan(fromDate).and(EVENTS.END_DATE.greaterThan(toDate))); // Events that start before and end after
		}
		
		RecurrencesBroken rbk1 = RECURRENCES_BROKEN.as("rbk1");
		Events eve1 = EVENTS.as("eve1");
		Field<Integer> referencedEventId = DSL
			.select(eve1.EVENT_ID)
			.from(rbk1.join(eve1).on(rbk1.EVENT_ID.equal(eve1.EVENT_ID)))
			.where(
				rbk1.NEW_EVENT_ID.equal(EVENTS.EVENT_ID)
				.and(eve1.REVISION_STATUS.notEqual(OEvent.REV_STATUS_DELETED))
			).asField();
		Field<Integer> originalEventId = DSL.coalesce(referencedEventId, EVENTS.EVENT_ID).as("original_event_id");
		
		Field<Boolean> hasAttendees = field(exists(
			selectOne()
			.from(EVENTS_ATTENDEES)
			.where(
				EVENTS_ATTENDEES.EVENT_ID.equal(EVENTS.EVENT_ID)
			)
		)).as("has_attendees");
		
		return dsl
			.select(
				EVENTS.fields()
			)
			.select(
				originalEventId,
				CALENDARS.DOMAIN_ID.as("calendar_domain_id"),
				CALENDARS.USER_ID.as("calendar_user_id"),
				hasAttendees
			)
			.from(EVENTS)
			.join(CALENDARS).on(EVENTS.CALENDAR_ID.equal(CALENDARS.CALENDAR_ID))
			.where(
				EVENTS.CALENDAR_ID.in(calendarIds)
				.and(
					EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_NEW)
					.or(EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_MODIFIED))
				)
				.and(EVENTS.RECURRENCE_ID.isNull())
				.and(
					rangeCndt
				)
				.and(
					patternCndt
				)
			)
			.orderBy(
				EVENTS.START_DATE
			)
			.fetchInto(VVEvent.class);
	}
	
	public List<OEvent> selectHandleInvitationByRevision(Connection con) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.select(
				EVENTS.EVENT_ID,
				EVENTS.CALENDAR_ID,
				EVENTS.REVISION_STATUS
			)
			.from(EVENTS)
			.where(
					EVENTS.HANDLE_INVITATION.equal(true)
			)
			.fetchInto(OEvent.class);
	}
	
	public int updateHandleInvitationIn(Connection con, Collection<Integer> eventIds, boolean handleInvitation) throws DAOException {
		DSLContext dsl = getDSL(con);
		return dsl
			.update(EVENTS)
			.set(EVENTS.HANDLE_INVITATION, handleInvitation)
			.where(
				EVENTS.EVENT_ID.in(eventIds)
			)
			.execute();
	}
	
	public List<VVEvent> viewRecurringByCalendarFromToPattern(Connection con, int calendarId, DateTime fromDate, DateTime toDate, String pattern) throws DAOException {
		return viewRecurringByCalendarFromToPattern(con, Arrays.asList(calendarId), fromDate, toDate, pattern);
	}
	
	public List<VVEvent> viewRecurringByCalendarFromToPattern(Connection con, Collection<Integer> calendarIds, DateTime fromDate, DateTime toDate, String pattern) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		Condition rangeCndt = DSL.trueCondition();
		if ((fromDate != null) && (toDate != null)) {
			rangeCndt = RECURRENCES.START_DATE.between(fromDate, toDate) // Recurrences that start in current range
					.or(RECURRENCES.UNTIL_DATE.between(fromDate, toDate)) // Recurrences that end in current range
					.or(RECURRENCES.START_DATE.lessThan(fromDate).and(RECURRENCES.UNTIL_DATE.greaterThan(toDate))); // Recurrences that start before and end after
		}
		Condition patternCndt = DSL.trueCondition();
		if (!StringUtils.isBlank(pattern)) {
			patternCndt = EVENTS.TITLE.likeIgnoreCase(pattern)
				.or(EVENTS.LOCATION.likeIgnoreCase(pattern));
		}
		
		Field<Boolean> hasAttendees = field(exists(
			selectOne()
			.from(EVENTS_ATTENDEES)
			.where(
				EVENTS_ATTENDEES.EVENT_ID.equal(EVENTS.EVENT_ID)
			)
		)).as("has_attendees");
		
		return dsl
			.select(EVENTS.fields())
			.select(
				EVENTS.EVENT_ID.as("original_event_id"),
				CALENDARS.DOMAIN_ID.as("calendar_domain_id"),
				CALENDARS.USER_ID.as("calendar_user_id"),
				hasAttendees
			)
			.from(EVENTS)
			.join(CALENDARS).on(EVENTS.CALENDAR_ID.equal(CALENDARS.CALENDAR_ID))
			.join(RECURRENCES).on(EVENTS.RECURRENCE_ID.equal(RECURRENCES.RECURRENCE_ID))
			.where(
				EVENTS.CALENDAR_ID.in(calendarIds)
				.and(
					EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_NEW)
					.or(EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_MODIFIED))
				)
				.and(EVENTS.RECURRENCE_ID.isNotNull())
				.and(
					rangeCndt
				)
				.and(
					patternCndt
				)
			)
			.orderBy(
				EVENTS.START_DATE
			)
			.fetchInto(VVEvent.class);
	}
	
	
	/**
	 * @deprecated use viewByCalendarFromToPattern instead
	 */
	public List<VVEvent> searchByCalendarQuery(Connection con, Integer calendarId, String query) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		Events eve = EVENTS.as("eve");
		RecurrencesBroken rbk = RECURRENCES_BROKEN.as("rbk");
		Field<Integer> originalEventId = dsl
			.select(eve.EVENT_ID)
			.from(rbk.join(eve).on(rbk.EVENT_ID.equal(eve.EVENT_ID)))
			.where(
				rbk.NEW_EVENT_ID.equal(EVENTS.EVENT_ID)
				.and(eve.REVISION_STATUS.notEqual(OEvent.REV_STATUS_DELETED))
			)
			.asField("original_event_id");
		
		Field<Boolean> hasAttendees = field("false", Boolean.class).as("has_attendees");
		
		return dsl
			.select(
				EVENTS.fields()
			)
			.select(
				originalEventId,
				CALENDARS.DOMAIN_ID.as("calendar_domain_id"),
				CALENDARS.USER_ID.as("calendar_user_id")
			)
			.from(EVENTS)
			.join(CALENDARS).on(EVENTS.CALENDAR_ID.equal(CALENDARS.CALENDAR_ID))
			.where(
				EVENTS.CALENDAR_ID.equal(calendarId)
				.and(
					EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_NEW)
					.or(EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_MODIFIED))
				)
				.and(EVENTS.RECURRENCE_ID.isNull())
				.and(
					EVENTS.TITLE.likeIgnoreCase(query)
					.or(EVENTS.LOCATION.likeIgnoreCase(query))
				)
			)
			.orderBy(
				EVENTS.START_DATE
			)
			.fetchInto(VVEvent.class);
	}
	
	public List<VVEvent> viewExpiredForUpdateByFromTo(Connection con, DateTime fromDate, DateTime toDate) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		RecurrencesBroken rbk1 = RECURRENCES_BROKEN.as("rbk1");
		Events eve1 = EVENTS.as("eve1");
		Field<Integer> referencedEventId = DSL
			.select(eve1.EVENT_ID)
			.from(rbk1.join(eve1).on(rbk1.EVENT_ID.equal(eve1.EVENT_ID)))
			.where(
				rbk1.NEW_EVENT_ID.equal(EVENTS.EVENT_ID)
				.and(eve1.REVISION_STATUS.notEqual(OEvent.REV_STATUS_DELETED))
			).asField();
		Field<Integer> originalEventId = DSL.coalesce(referencedEventId, EVENTS.EVENT_ID).as("original_event_id");
		
		Field<Boolean> hasAttendees = field(exists(
			selectOne()
			.from(EVENTS_ATTENDEES)
			.where(
				EVENTS_ATTENDEES.EVENT_ID.equal(EVENTS.EVENT_ID)
			)
		)).as("has_attendees");
		
		return dsl
			.select(
				EVENTS.EVENT_ID,
				EVENTS.CALENDAR_ID,
				EVENTS.RECURRENCE_ID,
				EVENTS.START_DATE,
				EVENTS.END_DATE,
				EVENTS.TIMEZONE,
				EVENTS.ALL_DAY,
				EVENTS.TITLE,
				EVENTS.REMINDER,
				EVENTS.ORGANIZER,
				EVENTS.REVISION_TIMESTAMP,
				originalEventId,
				CALENDARS.DOMAIN_ID.as("calendar_domain_id"),
				CALENDARS.USER_ID.as("calendar_user_id"),
				hasAttendees
			)
			.from(EVENTS)
			.join(CALENDARS).on(EVENTS.CALENDAR_ID.equal(CALENDARS.CALENDAR_ID))
			.where(
				EVENTS.REMINDER.isNotNull().and(EVENTS.REMINDED_ON.isNull())
				.and(
					EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_NEW)
					.or(EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_MODIFIED))
				)
				.and(EVENTS.RECURRENCE_ID.isNull())
				.and(
					EVENTS.START_DATE.between(fromDate, toDate) // Events that start in current range
					.or(EVENTS.END_DATE.between(fromDate, toDate)) // Events that end in current range
					.or(EVENTS.START_DATE.lessThan(fromDate).and(EVENTS.END_DATE.greaterThan(toDate))) // Events that start before and end after
				)
			)
			.orderBy(
				EVENTS.START_DATE
			)
			.forUpdate()
			.fetchInto(VVEvent.class);
	}
	
	public List<VVEvent> viewRecurringExpiredForUpdateByFromTo(Connection con, DateTime fromDate, DateTime toDate) throws DAOException {
		DSLContext dsl = getDSL(con);
		
		Field<Boolean> hasAttendees = field(exists(
			selectOne()
			.from(EVENTS_ATTENDEES)
			.where(
				EVENTS_ATTENDEES.EVENT_ID.equal(EVENTS.EVENT_ID)
			)
		)).as("has_attendees");
		
		return dsl
			.select(
				EVENTS.EVENT_ID,
				EVENTS.CALENDAR_ID,
				EVENTS.RECURRENCE_ID,
				EVENTS.START_DATE,
				EVENTS.END_DATE,
				EVENTS.TIMEZONE,
				EVENTS.ALL_DAY,
				EVENTS.REMINDER,
				EVENTS.ORGANIZER,
				EVENTS.EVENT_ID.as("original_event_id"), // For recurring events, originalEventId is always equal to eventId
				CALENDARS.DOMAIN_ID.as("calendar_domain_id"),
				CALENDARS.USER_ID.as("calendar_user_id"),
				hasAttendees
			)
			.from(EVENTS)
			.join(CALENDARS).on(EVENTS.CALENDAR_ID.equal(CALENDARS.CALENDAR_ID))
			.join(RECURRENCES).on(EVENTS.RECURRENCE_ID.equal(RECURRENCES.RECURRENCE_ID))
			.where(
				EVENTS.REMINDER.isNotNull().and(EVENTS.REMINDED_ON.isNull())
				.and(
					EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_NEW)
					.or(EVENTS.REVISION_STATUS.equal(OEvent.REV_STATUS_MODIFIED))
				)
				.and(
					RECURRENCES.START_DATE.between(fromDate, toDate) // Recurrences that start in current range
					.or(RECURRENCES.UNTIL_DATE.between(fromDate, toDate)) // Recurrences that end in current range
					.or(RECURRENCES.START_DATE.lessThan(fromDate).and(RECURRENCES.UNTIL_DATE.greaterThan(toDate))) // Recurrences that start before and end after
				)
			)
			.orderBy(
				EVENTS.START_DATE
			)
			.forUpdate()
			.fetchInto(VVEvent.class);
	}
}
