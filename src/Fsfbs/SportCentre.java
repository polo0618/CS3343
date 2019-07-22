package Fsfbs;

import Exception.ExAllowToBookOneHourOnly;
import Exception.ExFacilityIdNotExist;
import Exception.ExFacilityNameNotExist;
import Exception.ExInputTimeEarlierThanCurrentTime;
import Exception.ExSportCentreNotExist;
import Exception.ExTimeRangeNotCurrent;
import Exception.ExTimeSlotNotInOpeningHour;
import Facility.Facility;
import Util.UtilTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SportCentre {
    public String getScId() {
		return scId;
	}

	public String getScName() {
		return scName;
	}

	public String getScTel() {
		return scTel;
	}

	public String getScAddress() {
		return scAddress;
	}

	private String scId;
    private String scName;
    private String scTel;
    private String scAddress;
    private Map<String, Facility> facilitiesMap;		//facility id, Facility Obj


    public Map<String, Facility> getFacilitiesMap() {
		return facilitiesMap;
	}
    public Set<String> getKeySet() {
    	return facilitiesMap.keySet();
    }

	public SportCentre(String scId, String scName, String scTel, String scAddress){
        this.scId = scId;
        this.scName = scName;
        this.scTel = scTel;
        this.scAddress = scAddress;
        facilitiesMap = new HashMap<>();
    }

    public ArrayList<String> searchVacanciesInSC(int timeSlot, String facType, UtilTime utilTime) throws ExFacilityIdNotExist, ExFacilityNameNotExist, ExSportCentreNotExist, ExTimeRangeNotCurrent, ExAllowToBookOneHourOnly, ExTimeSlotNotInOpeningHour, ExInputTimeEarlierThanCurrentTime, IOException {
        Controller controller = Controller.getInstance();
        SportCentre sc = controller.searchSportCentre(scId);
        ArrayList<Facility> facilities = controller.searchFacilitiesByType(sc,facType);
        ArrayList<String> vacancyList = new ArrayList<>();
        for (Facility fac: facilities){
            if(fac.canBook(timeSlot,utilTime)) {
                String status = fac.getBookingStatus(timeSlot);
                if(status.equals("Available")) {
                    vacancyList.add(fac.getFacilityId());
                }
            }
        }
        return vacancyList;
    }

    public Facility findFacilityByID(String facilityId) throws ExFacilityIdNotExist{
        Facility facility = facilitiesMap.get(facilityId);
        if (facility == null){
            throw new ExFacilityIdNotExist();
        }
        return facility;
    }

	public void addfacilitytosc(String facilitiesId, Facility facility) {
		facilitiesMap.putIfAbsent(facilitiesId, facility);
	}
}
