package Util;

import java.io.File;
import java.io.IOException;

public class UtilValidation {
	private static UtilValidation validationInstance= new UtilValidation();
    public static UtilValidation getValidationInstance() {return validationInstance;}
	

  //validate account ----------------------------------------------------------------------------------------
    public boolean existedAC(String account) throws IOException, NullPointerException {
    	 File file = new File(UtilsLoadconfig.getConfig("membershipFilePath"));
         File[] files = file.listFiles();
         for(File f: files){
        	 String filename = f.getName().substring(0,f.getName().length()-4);
        	 if(filename.equals(account))
        		 return true;
         }
    	return false;
    	}
	public int validateTimeFormat(String inputTimeFormat) {
        try {
            int number = Integer.parseInt(inputTimeFormat);
            if (inputTimeFormat.length() == 4) {
                return number;
            }
        }
        catch(Exception e) {
           return 0;
        }
        return 0;
    }
}
