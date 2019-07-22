package Facility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import Exception.ExAllowToBookOneHourOnly;
import Exception.ExAllowToDeleteOneHourOnly;
import Exception.ExBookingHasPassed;
import Exception.ExInputTimeEarlierThanCurrentTime;
import Exception.ExTimeRangeNotCurrent;
import Exception.ExTimeSlotNotInOpeningHour;
import Util.UtilTime;

import java.util.Set;

public abstract class Facility {
        private String facilityId; //court num can be consist of district, sport centre and facility number
        private Map<Integer, String> timetableMap; // time, bookingID

        public Facility(String facilityId) {
            this.facilityId = facilityId;
            timetableMap = new HashMap<>();
        }

    public String getFacilityId() {
        return facilityId;
    }
    public String getBookingIdbyTime(Integer time) {
    	return timetableMap.get(time);
    }

    public Set<Integer> getkeyset() {
    	return timetableMap.keySet();
    }
    public Map<Integer, String> getTimetableMap() {
		return timetableMap;
	}


    public String addToTimeTable(int time, String bookingID) {   	
    	return timetableMap.putIfAbsent(time, bookingID);
    }
    
    public String removeFromTimeTable(int time) {
    	return timetableMap.remove(time);
    }

    public abstract double getPrice();

    public abstract String getFacilityType();
    
    public boolean canBook(int timeslot, UtilTime utilTime) throws ExTimeRangeNotCurrent, ExAllowToBookOneHourOnly,ExInputTimeEarlierThanCurrentTime, ExTimeSlotNotInOpeningHour, IOException {
        int timeRangeResult = utilTime.isTimeRangeExceed(timeslot);
            switch (timeRangeResult) {
                case -1:
                    throw new ExTimeRangeNotCurrent();
                case -2:
                    throw new ExAllowToBookOneHourOnly();
                case -3:
                    throw new ExTimeSlotNotInOpeningHour();
                default:
                    break;
            }

        String startTime = Integer.toString(timeslot).substring(0,2) + ":00:00";
        if (utilTime.isTimeLaterThanCurrentTime(startTime)) {
            for (Integer key : timetableMap.keySet()) {
                if (key == timeslot)
                    return false;
            }
            return true;
        }
        else{
            throw new ExInputTimeEarlierThanCurrentTime();
        }
    }

    public static boolean canDelete(int timeslot, UtilTime utilTime) throws ExTimeRangeNotCurrent, ExAllowToDeleteOneHourOnly,ExBookingHasPassed, ExTimeSlotNotInOpeningHour, IOException {
        int timeRangeResult = utilTime.isTimeRangeExceed(timeslot);
        switch (timeRangeResult) {
            case -1:
                throw new ExTimeRangeNotCurrent();
            case -2:
                throw new ExAllowToDeleteOneHourOnly();
            case -3:
                throw new ExTimeSlotNotInOpeningHour();
            default:
                break;
        }

        String startTime = Integer.toString(timeslot).substring(0, 2) + ":00:00";
        if (!utilTime.isTimeLaterThanCurrentTime(startTime)) {
            throw new ExBookingHasPassed();
        }
        return true;
    }

    public String getBookingStatus(int timeslot){
        String bookingID = getBookingIdbyTime(timeslot);
        if (bookingID == null){
            return "Available";
        }
        else {
            return "Booked, Booking ID: " + bookingID;
        }
    }


    public void showVacancies(){
        TreeMap<Integer,String> vaccanciesMap = new TreeMap<>();
        final int [] timeRange = {1011,1112,1213,1314,1415,1516,1617,1718,1819,1920,2021,2122,2223,2324};
        for (int i = 0; i < 14; i++){
                vaccanciesMap.putIfAbsent(timeRange[i],getBookingStatus(timeRange[i]));
        }
        for(int keys :vaccanciesMap.keySet()){
            System.out.println(keys + " : " + vaccanciesMap.get(keys));
        }
        System.out.println();
    }
}
