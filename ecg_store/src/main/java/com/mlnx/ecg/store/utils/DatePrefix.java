package com.mlnx.ecg.store.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DatePrefix implements Comparable<DatePrefix> {

    private static enum Granularity {

        MONTH(Calendar.MONTH), DAY(Calendar.DAY_OF_MONTH), HOUR(
                Calendar.HOUR_OF_DAY), MINUTE(Calendar.MINUTE);

        private final int field;

        private Granularity(int field) {

            this.field = field;
        }

        int getField() {

            return field;
        }
    }

    private final Granularity granularity;

    private final Calendar calendar;

    private DatePrefix(Granularity granularity, Date dateTime) {

        this.granularity = granularity;
        calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));//Asia/Shanghai  ，宁宇的设计为GMT
        calendar.setTime(dateTime);
        switch (granularity) {
        case MONTH:
            calendar.set(Calendar.DAY_OF_MONTH, 1);

        case DAY:
            calendar.set(Calendar.HOUR_OF_DAY, 0);

        case HOUR:
            calendar.set(Calendar.MINUTE, 0);

        case MINUTE:
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            break;

        default:
            throw new IllegalArgumentException(String.format(
                    "Invalid granularity %s", granularity));
        }
    }

    public static DatePrefix yyyyMM(Date dateTime) {

        return new DatePrefix(Granularity.MONTH, dateTime);
    }

    public static DatePrefix yyyyMMdd(Date dateTime) {

        return new DatePrefix(Granularity.DAY, dateTime);
    }

    public static DatePrefix yyyyMMddHH(Date dateTime) {

        return new DatePrefix(Granularity.HOUR, dateTime);
    }

    public static DatePrefix yyyyMMddHHmm(Date dateTime) {

        return new DatePrefix(Granularity.MINUTE, dateTime);
    }

    @Override
    public String toString() {

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        switch (granularity) {
        case MONTH:
            return String.format("%04d%02d", year, month);

        case DAY:
            return String.format("%04d%02d%02d", year, month, dayOfMonth);

        case HOUR:
            return String.format("%04d%02d%02d%02d", year, month, dayOfMonth,
                    hourOfDay);

        case MINUTE:
            return String.format("%04d%02d%02d%02d%02d", year, month,
                    dayOfMonth, hourOfDay, minute);

        default:
            throw new RuntimeException("Unexpected error");
        }
    }

    public void increment() {

        calendar.add(granularity.getField(), 1);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof DatePrefix)) {
            return false;
        }

        DatePrefix other = (DatePrefix) o;
        return calendar.compareTo(other.calendar) == 0;
    }

    @Override
    public int hashCode() {

        return calendar.hashCode();
    }

    @Override
    public int compareTo(DatePrefix o) {

        return calendar.compareTo(o.calendar);
    }
}
