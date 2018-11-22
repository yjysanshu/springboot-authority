package com.temp.permission.util.convert;

import java.text.*;
import java.util.Date;

public class CustomDataFormat extends DateFormat {

    private DateFormat dateFormat;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public CustomDataFormat(DateFormat dataFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        return dateFormat.format(date, toAppendTo, fieldPosition);
    }

    @Override
    public Date parse(String source, ParsePosition pos) {
        Date date;
        try {
            date = simpleDateFormat.parse(source, pos);
        } catch (Exception e) {
            date = dateFormat.parse(source, pos);
        }
        return date;
    }

    @Override
    public Date parse(String source) throws ParseException {
        Date date;
        try {
            date = simpleDateFormat.parse(source);
        } catch (Exception e) {
            date = dateFormat.parse(source);
        }
        return date;
    }

    @Override
    public Object clone() {
        Object format = dateFormat.clone();
        return new CustomDataFormat((DateFormat) format);
    }
}
