/**
 * This class is generated by jOOQ
 */
package com.sonicle.webtop.calendar.jooq.tables.pojos;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.3"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Events implements java.io.Serializable {

	private static final long serialVersionUID = -356938173;

	private java.lang.Integer      eventId;
	private java.lang.Integer      calendarId;
	private java.lang.Integer      recurrenceId;
	private org.joda.time.DateTime startDate;
	private org.joda.time.DateTime endDate;
	private java.lang.String       timezone;
	private java.lang.Boolean      allDay;
	private java.lang.String       title;
	private java.lang.String       description;
	private java.lang.String       location;
	private java.lang.Boolean      isPrivate;
	private java.lang.Boolean      busy;
	private java.lang.Integer      reminder;
	private java.lang.Boolean      readOnly;
	private java.lang.String       revisionStatus;
	private org.joda.time.DateTime revisionTimestamp;
	private java.lang.String       publicUid;
	private org.joda.time.DateTime remindedOn;
	private java.lang.Integer      activityId;
	private java.lang.String       customerId;
	private java.lang.String       statisticId;
	private java.lang.Integer      causalId;
	private java.lang.String       organizer;
	private java.lang.Integer      revisionSequence;

	public Events() {}

	public Events(
		java.lang.Integer      eventId,
		java.lang.Integer      calendarId,
		java.lang.Integer      recurrenceId,
		org.joda.time.DateTime startDate,
		org.joda.time.DateTime endDate,
		java.lang.String       timezone,
		java.lang.Boolean      allDay,
		java.lang.String       title,
		java.lang.String       description,
		java.lang.String       location,
		java.lang.Boolean      isPrivate,
		java.lang.Boolean      busy,
		java.lang.Integer      reminder,
		java.lang.Boolean      readOnly,
		java.lang.String       revisionStatus,
		org.joda.time.DateTime revisionTimestamp,
		java.lang.String       publicUid,
		org.joda.time.DateTime remindedOn,
		java.lang.Integer      activityId,
		java.lang.String       customerId,
		java.lang.String       statisticId,
		java.lang.Integer      causalId,
		java.lang.String       organizer,
		java.lang.Integer      revisionSequence
	) {
		this.eventId = eventId;
		this.calendarId = calendarId;
		this.recurrenceId = recurrenceId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.timezone = timezone;
		this.allDay = allDay;
		this.title = title;
		this.description = description;
		this.location = location;
		this.isPrivate = isPrivate;
		this.busy = busy;
		this.reminder = reminder;
		this.readOnly = readOnly;
		this.revisionStatus = revisionStatus;
		this.revisionTimestamp = revisionTimestamp;
		this.publicUid = publicUid;
		this.remindedOn = remindedOn;
		this.activityId = activityId;
		this.customerId = customerId;
		this.statisticId = statisticId;
		this.causalId = causalId;
		this.organizer = organizer;
		this.revisionSequence = revisionSequence;
	}

	public java.lang.Integer getEventId() {
		return this.eventId;
	}

	public void setEventId(java.lang.Integer eventId) {
		this.eventId = eventId;
	}

	public java.lang.Integer getCalendarId() {
		return this.calendarId;
	}

	public void setCalendarId(java.lang.Integer calendarId) {
		this.calendarId = calendarId;
	}

	public java.lang.Integer getRecurrenceId() {
		return this.recurrenceId;
	}

	public void setRecurrenceId(java.lang.Integer recurrenceId) {
		this.recurrenceId = recurrenceId;
	}

	public org.joda.time.DateTime getStartDate() {
		return this.startDate;
	}

	public void setStartDate(org.joda.time.DateTime startDate) {
		this.startDate = startDate;
	}

	public org.joda.time.DateTime getEndDate() {
		return this.endDate;
	}

	public void setEndDate(org.joda.time.DateTime endDate) {
		this.endDate = endDate;
	}

	public java.lang.String getTimezone() {
		return this.timezone;
	}

	public void setTimezone(java.lang.String timezone) {
		this.timezone = timezone;
	}

	public java.lang.Boolean getAllDay() {
		return this.allDay;
	}

	public void setAllDay(java.lang.Boolean allDay) {
		this.allDay = allDay;
	}

	public java.lang.String getTitle() {
		return this.title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getDescription() {
		return this.description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.String getLocation() {
		return this.location;
	}

	public void setLocation(java.lang.String location) {
		this.location = location;
	}

	public java.lang.Boolean getIsPrivate() {
		return this.isPrivate;
	}

	public void setIsPrivate(java.lang.Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public java.lang.Boolean getBusy() {
		return this.busy;
	}

	public void setBusy(java.lang.Boolean busy) {
		this.busy = busy;
	}

	public java.lang.Integer getReminder() {
		return this.reminder;
	}

	public void setReminder(java.lang.Integer reminder) {
		this.reminder = reminder;
	}

	public java.lang.Boolean getReadOnly() {
		return this.readOnly;
	}

	public void setReadOnly(java.lang.Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public java.lang.String getRevisionStatus() {
		return this.revisionStatus;
	}

	public void setRevisionStatus(java.lang.String revisionStatus) {
		this.revisionStatus = revisionStatus;
	}

	public org.joda.time.DateTime getRevisionTimestamp() {
		return this.revisionTimestamp;
	}

	public void setRevisionTimestamp(org.joda.time.DateTime revisionTimestamp) {
		this.revisionTimestamp = revisionTimestamp;
	}

	public java.lang.String getPublicUid() {
		return this.publicUid;
	}

	public void setPublicUid(java.lang.String publicUid) {
		this.publicUid = publicUid;
	}

	public org.joda.time.DateTime getRemindedOn() {
		return this.remindedOn;
	}

	public void setRemindedOn(org.joda.time.DateTime remindedOn) {
		this.remindedOn = remindedOn;
	}

	public java.lang.Integer getActivityId() {
		return this.activityId;
	}

	public void setActivityId(java.lang.Integer activityId) {
		this.activityId = activityId;
	}

	public java.lang.String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(java.lang.String customerId) {
		this.customerId = customerId;
	}

	public java.lang.String getStatisticId() {
		return this.statisticId;
	}

	public void setStatisticId(java.lang.String statisticId) {
		this.statisticId = statisticId;
	}

	public java.lang.Integer getCausalId() {
		return this.causalId;
	}

	public void setCausalId(java.lang.Integer causalId) {
		this.causalId = causalId;
	}

	public java.lang.String getOrganizer() {
		return this.organizer;
	}

	public void setOrganizer(java.lang.String organizer) {
		this.organizer = organizer;
	}

	public java.lang.Integer getRevisionSequence() {
		return this.revisionSequence;
	}

	public void setRevisionSequence(java.lang.Integer revisionSequence) {
		this.revisionSequence = revisionSequence;
	}
}
