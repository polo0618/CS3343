package test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.Test;
import Fsfbs.Booking;
import Util.UtilTime;

public class Test_Booking {
	class UtilTime_stub extends UtilTime {

		public String getCurrentTime() {
			return "15:00:00";
		}
		public String getCurrentDate() {
			return "20180101";
		}
	}
	
	@Test
	public void test_Booking1() throws IOException {
		UtilTime utilTime = UtilTime_stub.getTimeInstance();
		Booking bk = new Booking("Ada",1314,"E1B1",utilTime);
	}
	
	@Test
	public void test_Booking2() throws IOException {
	UtilTime utilTime = UtilTime_stub.getTimeInstance();
	Booking bk = new Booking("Ada",1314,"E1B1",utilTime);
	System.out.println(bk.getBookingID());
	assertEquals("20180101E1B111314",bk.getBookingID());
	}
	
	@Test
	public void test_Booking3() throws IOException {
		UtilTime utilTime = UtilTime_stub.getTimeInstance();
		Booking bk = new Booking("Ada",1314,"E1B1",utilTime);
		System.out.println(bk.getBookingTime());
		assertEquals(1314,bk.getBookingTime());
	}
	@Test
	public void test_Booking4() throws IOException {
		UtilTime utilTime = UtilTime_stub.getTimeInstance();
		Booking bk = new Booking("Ada",1314,"E1B1",utilTime);
		assertEquals("E1B1",bk.getFacilitiesID());
	}
	
	@Test
	public void test_Booking5() throws IOException {
		UtilTime utilTime = UtilTime_stub.getTimeInstance();
		Booking bk = new Booking("Ada",1314,"E1B1",utilTime);
		assertEquals("Ada",bk.getuserName());
	}
}
