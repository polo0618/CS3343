package Fsfbs;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import Exception.ExAllowToBookOneHourOnly;
import Exception.ExFacilityIdNotExist;
import Exception.ExFacilityNameNotExist;
import Exception.ExInputTimeEarlierThanCurrentTime;
import Exception.ExSportCentreNotExist;
import Exception.ExTimeRangeNotCurrent;
import Exception.ExTimeSlotNotInOpeningHour;
import Util.UtilTime;

public class FastRecommendationAlgorithm {
    private static FastRecommendationAlgorithm instance = new FastRecommendationAlgorithm();

    public static FastRecommendationAlgorithm getInstance() {
        return instance;
    }
    public void fastRecommendation(String preferredSC, String preferredFacility, UtilTime utilTime){
        try {
            String currentTime = utilTime.getCurrentTime();
            System.out.println("\n+--------------------Fast Recommendation System---------------------+");
            System.out.println("Current Time: " + currentTime);
            Controller controller = Controller.getInstance();
            int count = 0;
            //priority: SportCentre, District
            Set<SportCentre> sportCentreSet = controller.searchSCByDistrict(preferredSC, true);
            Set<SportCentre> scNotInSameDistrictSet = controller.searchSCByDistrict(preferredSC, false);
            count = showVacancy(sportCentreSet,utilTime, preferredFacility, count);
            if (count < 10){
                System.out.println("\nSport Centres in other districts:");
                showVacancy(scNotInSameDistrictSet,utilTime, preferredFacility,count);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static int showVacancy(Set<SportCentre> scSet, UtilTime utilTime, String facilityType, int count) throws ExFacilityIdNotExist, ExTimeRangeNotCurrent, IOException, ExInputTimeEarlierThanCurrentTime, ExAllowToBookOneHourOnly, ExTimeSlotNotInOpeningHour, ExFacilityNameNotExist, ExSportCentreNotExist {
            String availableTime = utilTime.getNextAvailableTimeSlot(1);
            String endTime = utilTime.getNextAvailableTimeSlot(2);
            int timeSlot = utilTime.getTimeSlot(availableTime,endTime);
            for (SportCentre sc : scSet) {
                System.out.print("List of available courts in " + sc.getScName() + " on " + availableTime);
                ArrayList<String> vacancyList = sc.searchVacanciesInSC(timeSlot, facilityType,utilTime);
                System.out.println(": " + vacancyList.size());
                for (int i = 0; i < vacancyList.size(); i++) {
                    System.out.println((i + 1) + ": " + vacancyList.get(i));
                }
                count += vacancyList.size();
                if (count >= 10){
                    return count;
                }
            }
        return count;
    }
}
