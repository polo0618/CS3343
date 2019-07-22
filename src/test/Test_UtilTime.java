package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;

import Util.UtilTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class UtilTime_stub extends UtilTime {
    private static UtilTime_stub timeInstance = new UtilTime_stub();

    public static UtilTime getTimeInstance() {
        return timeInstance;
    }
	public String getCurrentTime() {
		return "15:00:00";
	}

	@Test
	public void test_getCurrentDate() throws IOException {
		UtilTime time = new UtilTime().getTimeInstance();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.now();
		assertEquals(dtf.format(localDate), time.getCurrentDate());
	}
	
	@Test
	public void test_getCurrentTime() throws IOException {
		UtilTime time = new UtilTime().getTimeInstance();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        assertEquals(dtf.format(now), time.getCurrentTime());
	}
	
	@Test
	public void UtilTime_hour() throws IOException {
		UtilTime_stub time = new UtilTime_stub();
		boolean result = time.isTimeLaterThanCurrentTime("14:00:00");
		assertEquals(false, result);
	}

	@Test
	public void UtilTime_hour2() throws IOException {
		UtilTime_stub time = new UtilTime_stub();
		boolean result = time.isTimeLaterThanCurrentTime("16:00:00");
		assertEquals(true, result);
	}
	
	
	@Test
	public void test_getTimeSlot() {
		//UtilTime time = UtilTime.getTimeInstance();
		//assertEquals(time.getTimeSlot("15:00:00", "16:00:00"),"1516");
	}
	
	
	
}
