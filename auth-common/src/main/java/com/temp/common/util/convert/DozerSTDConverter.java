package com.temp.common.util.convert;

import org.dozer.DozerConverter;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DozerSTDConverter extends DozerConverter<String, Date> {

    public DozerSTDConverter() {
        super(String.class, Date.class);
    }

    @Override
    public String convertFrom(Date source, String destination) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        destination = formatter.format(source);
        return destination;
    }

    @Override
    public Date convertTo(String source, Date destination) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        destination = formatter.parse(source, pos);
        return destination;
    }
}
