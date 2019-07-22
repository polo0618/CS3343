package test;
import Fsfbs.*;
import Util.UtilTime;
/*
import org.powermock.api.easymock.annotation.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.junit.Test;

import Exception.ExFacilityIdNotExist;
import Exception.ExIOErrorinGetConfig;
import Exception.ExMemberShipFilePathNotExist;
import Exception.ExSCFilesNotExist;


@RunWith(PowerMockRunner.class)
@PrepareForTest({UtilTime.class})
class Test_addBooking {
	
	@Mock
	private UtilTime mockUtilTime;
	
	@Before
	  public void setup(){
	      // initialize all the @Mock objects
	      MockitoAnnotations.initMocks(this);
	      // mock all the statics
	      PowerMockito.mockStatic(UtilTime.class);
	  }
	
	@Test
	public void test_addBooking() throws ExMemberShipFilePathNotExist, ExSCFilesNotExist, ExIOErrorinGetConfig, ExFacilityIdNotExist {
		
		Mockito.when(UtilTime.getTimeInstance()).thenReturn(mockUtilTime);
		PowerMockito.when(mockUtilTime.getCurrentTime()).thenReturn("15:00:00");
		Controller controller = Controller.getInstance();
		controller.importData();
		User tester = new User("Mr A", "password", "Membership_Adult", "E1", "Facility_Badminton");
		tester.addBooking("E1B2", 1415,mockUtilTime);
		int result = tester.getTodayBookingNum();
		assertEquals(1, result);
	}

	@Test
	public void test_addBooking2() throws ExMemberShipFilePathNotExist, ExSCFilesNotExist, ExIOErrorinGetConfig, ExFacilityIdNotExist {
		Controller controller = Controller.getInstance();
		UtilTime utilTime_stub = UtilTime_stub.getTimeInstance();
		controller.importData();
		User tester = new User("Mr B", "password", "Membership_Adult", "E1", "Facility_Badminton");
		tester.addBooking("E1B2", 2211,utilTime_stub);
		int result = tester.getTodayBookingNum();
		assertEquals(0, result);
	}

	@Test
	public void test_addBooking3() throws ExMemberShipFilePathNotExist, ExSCFilesNotExist, ExIOErrorinGetConfig, ExFacilityIdNotExist {
		Controller controller = Controller.getInstance();
		UtilTime utilTime_stub = UtilTime_stub.getTimeInstance();
		controller.importData();
		User tester = new User("Mr C", "password", "Membership_Adult", "E1", "Facility_Badminton");
		tester.addBooking("E2B2", 1617,utilTime_stub);
		tester.addBooking("E2B2", 1718,utilTime_stub);
		tester.addBooking("E2B2", 1819,utilTime_stub);
		tester.addBooking("E2B2", 1920,utilTime_stub);
		int result = tester.getTodayBookingNum();
		assertEquals(3, result);
	}

	@Test
	public void test_addBooking_twoPerson() throws ExMemberShipFilePathNotExist, ExSCFilesNotExist, ExIOErrorinGetConfig, ExFacilityIdNotExist {
		Controller controller = Controller.getInstance();
		UtilTime utilTime_stub = UtilTime_stub.getTimeInstance();
		controller.importData();
		User tester = new User("Mr A", "password", "Membership_Adult", "E1", "Facility_Badminton");
		tester.addBooking("E1B2", 1415,utilTime_stub);

		User tester2 = new User("Mr B", "password", "Membership_Adult", "E1", "Facility_Badminton");
		tester2.addBooking("E1B2", 1415,utilTime_stub);
		int result = tester2.getTodayBookingNum();
		assertEquals(0, result);
	}

}
*/
