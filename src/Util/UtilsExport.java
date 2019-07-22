package Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import Fsfbs.SimulationMode;

public class UtilsExport {

public static void printToFile(String filepath, String input[]) throws IOException{
		//
	if(!SimulationMode.getSimulationMode())
	{
		try {
			PrintWriter writer;
			writer = new PrintWriter(filepath, "UTF-8");
			for(String s: input)
				writer.println(s);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }

}
public static void printToFile(String filepath, String input) throws IOException {
	//
	File file = new File(filepath);
	file.createNewFile();
	if(!SimulationMode.getSimulationMode()) {
	try {
		PrintWriter writer;
		writer = new PrintWriter(filepath, "UTF-8");
		writer.print(input);
	} catch (FileNotFoundException | UnsupportedEncodingException e) {
		e.printStackTrace();
	}
    }
}
	public static void appendToFile(String filepath, String input) throws IOException{
		if(!SimulationMode.getSimulationMode()) {
		 File file = new File(filepath);
		 try {
             FileWriter fr = new FileWriter(file, true);
             fr.write(input + "\n");
             fr.close();
         }catch (IOException e){
		     e.printStackTrace();
         }
		}
	}


}
