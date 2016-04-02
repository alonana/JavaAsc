package com.javaasc.entity.entities;

import com.javaasc.entity.api.JascOperation;
import com.javaasc.entity.api.JascParameter;

public class OperationCalendar {
    @SuppressWarnings("unused")
    @JascOperation()
    public String calendarDay(
            @JascParameter(name = "week-day", values = WeekDayValues.class) String weekDay) throws Exception {
        return "You have selected day:\r\n" + weekDay;
    }
}
