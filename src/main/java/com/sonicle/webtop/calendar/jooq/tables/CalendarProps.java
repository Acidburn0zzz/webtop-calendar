/**
 * This class is generated by jOOQ
 */
package com.sonicle.webtop.calendar.jooq.tables;

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
public class CalendarProps extends org.jooq.impl.TableImpl<com.sonicle.webtop.calendar.jooq.tables.records.CalendarPropsRecord> {

	private static final long serialVersionUID = -149415422;

	/**
	 * The reference instance of <code>calendar.calendar_props</code>
	 */
	public static final com.sonicle.webtop.calendar.jooq.tables.CalendarProps CALENDAR_PROPS = new com.sonicle.webtop.calendar.jooq.tables.CalendarProps();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.sonicle.webtop.calendar.jooq.tables.records.CalendarPropsRecord> getRecordType() {
		return com.sonicle.webtop.calendar.jooq.tables.records.CalendarPropsRecord.class;
	}

	/**
	 * The column <code>calendar.calendar_props.domain_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.calendar.jooq.tables.records.CalendarPropsRecord, java.lang.String> DOMAIN_ID = createField("domain_id", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

	/**
	 * The column <code>calendar.calendar_props.user_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.calendar.jooq.tables.records.CalendarPropsRecord, java.lang.String> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * The column <code>calendar.calendar_props.calendar_id</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.calendar.jooq.tables.records.CalendarPropsRecord, java.lang.Integer> CALENDAR_ID = createField("calendar_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>calendar.calendar_props.hidden</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.calendar.jooq.tables.records.CalendarPropsRecord, java.lang.Boolean> HIDDEN = createField("hidden", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

	/**
	 * The column <code>calendar.calendar_props.color</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.calendar.jooq.tables.records.CalendarPropsRecord, java.lang.String> COLOR = createField("color", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "");

	/**
	 * The column <code>calendar.calendar_props.sync</code>.
	 */
	public final org.jooq.TableField<com.sonicle.webtop.calendar.jooq.tables.records.CalendarPropsRecord, java.lang.String> SYNC = createField("sync", org.jooq.impl.SQLDataType.VARCHAR.length(1), this, "");

	/**
	 * Create a <code>calendar.calendar_props</code> table reference
	 */
	public CalendarProps() {
		this("calendar_props", null);
	}

	/**
	 * Create an aliased <code>calendar.calendar_props</code> table reference
	 */
	public CalendarProps(java.lang.String alias) {
		this(alias, com.sonicle.webtop.calendar.jooq.tables.CalendarProps.CALENDAR_PROPS);
	}

	private CalendarProps(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.calendar.jooq.tables.records.CalendarPropsRecord> aliased) {
		this(alias, aliased, null);
	}

	private CalendarProps(java.lang.String alias, org.jooq.Table<com.sonicle.webtop.calendar.jooq.tables.records.CalendarPropsRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.sonicle.webtop.calendar.jooq.Calendar.CALENDAR, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.sonicle.webtop.calendar.jooq.tables.records.CalendarPropsRecord> getPrimaryKey() {
		return com.sonicle.webtop.calendar.jooq.Keys.CALENDAR_PROPS_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.sonicle.webtop.calendar.jooq.tables.records.CalendarPropsRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.sonicle.webtop.calendar.jooq.tables.records.CalendarPropsRecord>>asList(com.sonicle.webtop.calendar.jooq.Keys.CALENDAR_PROPS_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.sonicle.webtop.calendar.jooq.tables.CalendarProps as(java.lang.String alias) {
		return new com.sonicle.webtop.calendar.jooq.tables.CalendarProps(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.sonicle.webtop.calendar.jooq.tables.CalendarProps rename(java.lang.String name) {
		return new com.sonicle.webtop.calendar.jooq.tables.CalendarProps(name, null);
	}
}