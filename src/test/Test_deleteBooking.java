package test;
import Fsfbs.*;
import Util.UtilTime;

import org.junit.jupiter.api.Test;

import Exception.ExFacilityIdNotExist;
import Exception.ExIOErrorinGetConfig;
import Exception.ExMemberShipFilePathNotExist;
import Exception.ExSCFilesNotExist;

import static org.junit.jupiter.api.Assertions.*;

public class Test_deleteBooking {

	class UtilTime_stub extends UtilTime {
		public String getCurrentTime() {
			return "15:00:00";
		}
	}

	@Test
	public void test_deleteBooking() throws ExMemberShipFilePathNotExist, ExSCFilesNotExist, ExIOErrorinGetConfig, ExFacilityIdNotExist {
		Controller controller = Controller.getInstance();
		UtilTime utilTime_stub = UtilTime_stub.getTimeInstance();
		controller.importData();
		User tester = new User("Mr A", "password", "Membership_Adult", "E1", "Facility_Badminton");
		tester.addBooking("E1B2", 1617,utilTime_stub);
		int result = tester.getTodayBookingNum();
		tester.deleteBooking("20181125E1B21617",utilTime_stub);
		assertEquals(0, result-1);
	}
	
//	@Test
//	public void test_deleteBooking2() throws ExFullBooking, ExBookingNotExist, ExMemberShipFilePathNotExist, ExSCFilesNotExist, ExIOErrorinGetConfig, ExFacilityIdNotExist {
//		Controller controller = Controller.getInstance();
//		controller.importData();
//		User tester = new User("Mr A", "password", "Membership_Audit", "E1", "Facility_Badminton");
//		tester.addBooking("E1B2", 2021);
//		tester.addBooking("E1B2", 2122);
//		tester.deleteBooking("20181125E1B22122");
//		int result = (tester.getTodayBookingNum())-1;
//		assertEquals(1, result);
//	}
//	
}
