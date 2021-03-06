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
public class Recurrences implements java.io.Serializable {

	private static final long serialVersionUID = 98110371;

	private java.lang.Integer      recurrenceId;
	private org.joda.time.DateTime startDate;
	private org.joda.time.DateTime untilDate;
	private java.lang.Integer      repeat;
	private java.lang.Boolean      permanent;
	private java.lang.String       type;
	private java.lang.Integer      dailyFreq;
	private java.lang.Integer      weeklyFreq;
	private java.lang.Boolean      weeklyDay_1;
	private java.lang.Boolean      weeklyDay_2;
	private java.lang.Boolean      weeklyDay_3;
	private java.lang.Boolean      weeklyDay_4;
	private java.lang.Boolean      weeklyDay_5;
	private java.lang.Boolean      weeklyDay_6;
	private java.lang.Boolean      weeklyDay_7;
	private java.lang.Integer      monthlyFreq;
	private java.lang.Integer      monthlyDay;
	private java.lang.Integer      yearlyFreq;
	private java.lang.Integer      yearlyDay;
	private java.lang.String       rule;

	public Recurrences() {}

	public Recurrences(
		java.lang.Integer      recurrenceId,
		org.joda.time.DateTime startDate,
		org.joda.time.DateTime untilDate,
		java.lang.Integer      repeat,
		java.lang.Boolean      permanent,
		java.lang.String       type,
		java.lang.Integer      dailyFreq,
		java.lang.Integer      weeklyFreq,
		java.lang.Boolean      weeklyDay_1,
		java.lang.Boolean      weeklyDay_2,
		java.lang.Boolean      weeklyDay_3,
		java.lang.Boolean      weeklyDay_4,
		java.lang.Boolean      weeklyDay_5,
		java.lang.Boolean      weeklyDay_6,
		java.lang.Boolean      weeklyDay_7,
		java.lang.Integer      monthlyFreq,
		java.lang.Integer      monthlyDay,
		java.lang.Integer      yearlyFreq,
		java.lang.Integer      yearlyDay,
		java.lang.String       rule
	) {
		this.recurrenceId = recurrenceId;
		this.startDate = startDate;
		this.untilDate = untilDate;
		this.repeat = repeat;
		this.permanent = permanent;
		this.type = type;
		this.dailyFreq = dailyFreq;
		this.weeklyFreq = weeklyFreq;
		this.weeklyDay_1 = weeklyDay_1;
		this.weeklyDay_2 = weeklyDay_2;
		this.weeklyDay_3 = weeklyDay_3;
		this.weeklyDay_4 = weeklyDay_4;
		this.weeklyDay_5 = weeklyDay_5;
		this.weeklyDay_6 = weeklyDay_6;
		this.weeklyDay_7 = weeklyDay_7;
		this.monthlyFreq = monthlyFreq;
		this.monthlyDay = monthlyDay;
		this.yearlyFreq = yearlyFreq;
		this.yearlyDay = yearlyDay;
		this.rule = rule;
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

	public org.joda.time.DateTime getUntilDate() {
		return this.untilDate;
	}

	public void setUntilDate(org.joda.time.DateTime untilDate) {
		this.untilDate = untilDate;
	}

	public java.lang.Integer getRepeat() {
		return this.repeat;
	}

	public void setRepeat(java.lang.Integer repeat) {
		this.repeat = repeat;
	}

	public java.lang.Boolean getPermanent() {
		return this.permanent;
	}

	public void setPermanent(java.lang.Boolean permanent) {
		this.permanent = permanent;
	}

	public java.lang.String getType() {
		return this.type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public java.lang.Integer getDailyFreq() {
		return this.dailyFreq;
	}

	public void setDailyFreq(java.lang.Integer dailyFreq) {
		this.dailyFreq = dailyFreq;
	}

	public java.lang.Integer getWeeklyFreq() {
		return this.weeklyFreq;
	}

	public void setWeeklyFreq(java.lang.Integer weeklyFreq) {
		this.weeklyFreq = weeklyFreq;
	}

	public java.lang.Boolean getWeeklyDay_1() {
		return this.weeklyDay_1;
	}

	public void setWeeklyDay_1(java.lang.Boolean weeklyDay_1) {
		this.weeklyDay_1 = weeklyDay_1;
	}

	public java.lang.Boolean getWeeklyDay_2() {
		return this.weeklyDay_2;
	}

	public void setWeeklyDay_2(java.lang.Boolean weeklyDay_2) {
		this.weeklyDay_2 = weeklyDay_2;
	}

	public java.lang.Boolean getWeeklyDay_3() {
		return this.weeklyDay_3;
	}

	public void setWeeklyDay_3(java.lang.Boolean weeklyDay_3) {
		this.weeklyDay_3 = weeklyDay_3;
	}

	public java.lang.Boolean getWeeklyDay_4() {
		return this.weeklyDay_4;
	}

	public void setWeeklyDay_4(java.lang.Boolean weeklyDay_4) {
		this.weeklyDay_4 = weeklyDay_4;
	}

	public java.lang.Boolean getWeeklyDay_5() {
		return this.weeklyDay_5;
	}

	public void setWeeklyDay_5(java.lang.Boolean weeklyDay_5) {
		this.weeklyDay_5 = weeklyDay_5;
	}

	public java.lang.Boolean getWeeklyDay_6() {
		return this.weeklyDay_6;
	}

	public void setWeeklyDay_6(java.lang.Boolean weeklyDay_6) {
		this.weeklyDay_6 = weeklyDay_6;
	}

	public java.lang.Boolean getWeeklyDay_7() {
		return this.weeklyDay_7;
	}

	public void setWeeklyDay_7(java.lang.Boolean weeklyDay_7) {
		this.weeklyDay_7 = weeklyDay_7;
	}

	public java.lang.Integer getMonthlyFreq() {
		return this.monthlyFreq;
	}

	public void setMonthlyFreq(java.lang.Integer monthlyFreq) {
		this.monthlyFreq = monthlyFreq;
	}

	public java.lang.Integer getMonthlyDay() {
		return this.monthlyDay;
	}

	public void setMonthlyDay(java.lang.Integer monthlyDay) {
		this.monthlyDay = monthlyDay;
	}

	public java.lang.Integer getYearlyFreq() {
		return this.yearlyFreq;
	}

	public void setYearlyFreq(java.lang.Integer yearlyFreq) {
		this.yearlyFreq = yearlyFreq;
	}

	public java.lang.Integer getYearlyDay() {
		return this.yearlyDay;
	}

	public void setYearlyDay(java.lang.Integer yearlyDay) {
		this.yearlyDay = yearlyDay;
	}

	public java.lang.String getRule() {
		return this.rule;
	}

	public void setRule(java.lang.String rule) {
		this.rule = rule;
	}
}
