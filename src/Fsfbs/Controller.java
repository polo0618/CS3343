package Fsfbs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import Exception.ExFacilityIdNotExist;
import Exception.ExFacilityNameNotExist;
import Exception.ExIOErrorinGetConfig;
import Exception.ExMemberShipFilePathNotExist;
import Exception.ExSCFilesNotExist;
import Exception.ExSportCentreNotExist;
import Exception.ExUserIdNotExist;
import Facility.Facility;
import Facility.Facility_ActivityRoom;
import Facility.Facility_Badminton;
import Facility.Facility_TableTennis;
import Util.UtilsExport;
import Util.UtilsLoadconfig;

public class Controller {
	private static Map<String, User> userMap;
	private static Map<String, SportCentre> sportCentreMap;
	private static Controller instance=null;

	private Controller() {
	    userMap = new HashMap<>();
	    sportCentreMap = new HashMap<>();
	}

	public void importData() throws ExMemberShipFilePathNotExist, ExSCFilesNotExist, ExIOErrorinGetConfig, ExFacilityIdNotExist {
        //import membership
		System.out.println("importing Member..");
        this.importAllMember();
        System.out.println("importing Sport Facility..");
        this.importAllSportFacility();
        System.out.println("importing Schedule..");
        this.importAllSchedule();
    }

    public static Controller getInstance() {
        if(instance == null)
            instance = new Controller();
        return instance;
    }

	public User getUserbyID(String userName) {
		return userMap.get(userName);
	}

	private SportCentre getSportCentrebyID(String scid) {
		return sportCentreMap.get(scid);
	}
	public void printAllFacilities() {
		System.out.println("Please enter the key for selecting Sport Centre");
		for (String key: sportCentreMap.keySet()) {
		    System.out.println("key : " + key);
		    System.out.println("Name : " + sportCentreMap.get(key).getScName());
		    System.out.println("Address : " + sportCentreMap.get(key).getScAddress());
		    System.out.println("Tel : "+ sportCentreMap.get(key).getScTel());
		}
	}


    public SportCentre searchSportCentre (String inputSCId) throws ExSportCentreNotExist{
	    SportCentre sc = getSportCentrebyID(inputSCId);
        if (sc == null){
            throw new ExSportCentreNotExist();
        }
        return sc;
    }
    public Facility searchFacility (String facilityId) throws ExFacilityIdNotExist,ExSportCentreNotExist {
            SportCentre sc;
            Facility facility;
            String scId = facilityId.substring(0, 2);
            sc = searchSportCentre(scId);
            facility = sc.findFacilityByID(facilityId);
            return facility;
    }

    public User searchUserById (String userId) throws ExUserIdNotExist{
        User user = getUserbyID(userId);
        if(user == null){
            throw new ExUserIdNotExist(userId);
        }
        return user;
    }

    public void addUser(User newuser){
	    userMap.putIfAbsent(newuser.getUserName(),newuser);
    }

    public Set<SportCentre> searchSCByDistrict (String preferredSC, boolean isSameDistrict){
	    Set<SportCentre> sportCentreSet = new HashSet<>();
	    String scType = preferredSC.substring(0,1);
	    if(isSameDistrict) {
            sportCentreSet.add(sportCentreMap.get(preferredSC)); //added prefer sport centre first
            for (String key : sportCentreMap.keySet()) {
                if (key.substring(0, 1).equals(scType)) {
                    sportCentreSet.add(sportCentreMap.get(key));
                }
            }
        }
        else{
            for (String key : sportCentreMap.keySet()) {
                if (!(key.substring(0, 1).equals(scType))){
                    sportCentreSet.add(sportCentreMap.get(key));
                }
            }
        }
        return sportCentreSet;
    }

    public ArrayList<Facility> searchFacilitiesByType(SportCentre sc, String sfType) throws ExFacilityNameNotExist, ExFacilityIdNotExist{
        ArrayList<Facility> facilitiesList = new ArrayList<>();
	    String type;
        switch (sfType) {
            case "badminton":
                type = "B";
                break;
            case "tableTennis":
                type = "T";
                break;
            case "activityRoom":
                type = "A";
                break;
            default:
                throw new ExFacilityNameNotExist();
        }
        for (String key : sc.getKeySet()){
            if(key.contains(sc.getScId() + type)){
                facilitiesList.add(sc.findFacilityByID(key));
            }
        }
        return facilitiesList;
    }


	//import Membership
    private void importAllMember() throws ExMemberShipFilePathNotExist,ExIOErrorinGetConfig {
	    try {
            File file = new File(UtilsLoadconfig.getConfig("membershipFilePath"));
            File[] files = file.listFiles((dir, name) -> !name.equals(".DS_Store"));
            for (File f : files) {
                Scanner inFile = new Scanner(f);
                if(SimulationMode.getSimulationMode())
                	System.out.println(f.getAbsolutePath());
                User temp = new User(inFile.next(), inFile.next(), inFile.next(), inFile.next(), inFile.next());
                temp.importBooking();
                userMap.put(f.getName().substring(0, f.getName().length() - 4), temp);
                

            }
        }
        catch(FileNotFoundException fe){
            throw new ExMemberShipFilePathNotExist();
        }
        catch(IOException ioe){
            throw new ExIOErrorinGetConfig();
        }
	}
	public void exportAllMembeer() throws IOException {
		for(String key:userMap.keySet()) {
			userMap.get(key).exportBooking();
		}
	}
	//import SportFacility
    private void importAllSportFacility() throws ExSCFilesNotExist,ExIOErrorinGetConfig {
	    try {
            File file = new File(UtilsLoadconfig.getConfig("sportCentreFilePath"));
            File[] files = file.listFiles((dir, name) -> !name.equals(".DS_Store"));
            for (File f : files) {
                Scanner inFile = new Scanner(f);
                SportCentre temp = new SportCentre(inFile.nextLine(), inFile.nextLine(), inFile.nextLine(), inFile.nextLine());
                while (inFile.hasNext()) {
                    String fid = inFile.next();
                    if(SimulationMode.getSimulationMode())
                    	System.out.println(fid + "...created and added to " + temp.getScId());
                    if (fid.charAt(2) == 'B')
                        temp.addfacilitytosc(fid, new Facility_Badminton(fid));
                    else if (fid.charAt(2) == 'A')
                        temp.addfacilitytosc(fid, new Facility_ActivityRoom(fid));
                    else if (fid.charAt(2) == 'T')
                        temp.addfacilitytosc(fid, new Facility_TableTennis(fid));
                }
                sportCentreMap.put(temp.getScId(), temp);
                if(SimulationMode.getSimulationMode())
                System.out.println("Added " + temp.getScId() +" to Sport Centre List");
            }
        }
        catch (FileNotFoundException fe){
	        throw new ExSCFilesNotExist();
        }
	    catch (IOException ioe){
	        throw new ExIOErrorinGetConfig();
        }
	}
	//export all time scheudle
	public void exportAllSchedule() throws ExIOErrorinGetConfig{
		String tsfp;
		try {
			tsfp = UtilsLoadconfig.getConfig("timeScheduleFilePath");
			for(String sckey: sportCentreMap.keySet()) {			//loop each Sport Centre in Sport Centre list
			SportCentre tempsc= sportCentreMap.get(sckey);

			for(String fackey:tempsc.getKeySet()) {							//loop each Facilities in Facility list
					Facility tempfac = tempsc.getFacilitiesMap().get(fackey);
		            UtilsExport.printToFile(tsfp+tempfac.getFacilityId()+".txt", "");
					UtilsExport.printToFile(tsfp+tempfac.getFacilityId()+".txt","");
					UtilsExport.appendToFile(tsfp+tempfac.getFacilityId()+".txt", tempfac.getFacilityId());
					if(!tempfac.getTimetableMap().isEmpty())
					{
					for(Integer timekey: tempfac.getkeyset()) {								//loop each timeslot
						UtilsExport.appendToFile(tsfp+tempfac.getFacilityId()+".txt",timekey.toString());
						UtilsExport.appendToFile(tsfp+tempfac.getFacilityId()+".txt",tempfac.getBookingIdbyTime(timekey));
					}
			}
				}
			}
		}
			catch (IOException e) {
					throw new ExIOErrorinGetConfig();
				}
	}

	private void importAllSchedule() throws ExIOErrorinGetConfig, ExFacilityIdNotExist {
		try {
		File file = new File(UtilsLoadconfig.getConfig("timeScheduleFilePath"));
        File[] files = file.listFiles((dir, name) -> !name.equals(".DS_Store"));
        for (File f : files) {
            Scanner inFile = new Scanner(f);
            if(SimulationMode.getSimulationMode())
            	System.out.println(f.getAbsolutePath()); //debug
            SportCentre sc = Controller.getInstance().getSportCentrebyID(f.getName().substring(0,2));
            if(SimulationMode.getSimulationMode())
            	System.out.println("Inside "+f.getName().substring(0,2));
            String fcid = inFile.next();
            if(SimulationMode.getSimulationMode())
            	System.out.println("Adding "+fcid);
            Facility fc = sc.findFacilityByID(fcid);
            while(inFile.hasNext()) {
            int time = inFile.nextInt();
            String bkid = inFile.next();
            if(SimulationMode.getSimulationMode())
            System.out.println("Time = "+time+", bookingid: "+bkid);
            fc.addToTimeTable(time,bkid);
        }
        }
		}
		catch (IOException e) {
			throw new ExIOErrorinGetConfig();
		}

	}
}
