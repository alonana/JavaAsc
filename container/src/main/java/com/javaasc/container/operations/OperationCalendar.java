package com.javaasc.container.operations;

import com.javaasc.entity.api.JascOperation;
import com.javaasc.entity.api.JascOption;

public class OperationCalendar {
    @SuppressWarnings("unused")
    @JascOperation()
    public String calendarDay(
            @JascOption(name = "week-day", values = WeekDayValues.class) String weekDay) throws Exception {
        return "You have selected day:\r\n" + weekDay.toString();
    }
}
