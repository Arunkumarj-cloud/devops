package com.oasys.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtill {
	
	public Date fromDateFormat(String date) {
		Date newDate = new Date();
		String oldstring = date + " 00:00:00";
		try {
			newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldstring);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newDate;
	}

	public Date toDateFormat(String date) {
		Date newDate = new Date();
		String oldstring = date + " 23:59:59";
		try {
			newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldstring);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newDate;
	}
	public long toDatelong() {
		
		long millis=0;
		try {
			Date newDate = new Date();
			String dateString = null;
			SimpleDateFormat sdfr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			dateString = sdfr.format( newDate );
			Date date = sdfr.parse(dateString);
			millis = date.getTime();
		
		}catch(Exception e) {
			
		}
		return millis;
	}

}
