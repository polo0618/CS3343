package Fsfbs;

import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Util.UtilsLoadconfig;

public class SimulationMode {
	public static boolean getSimulationMode() throws IOException {
		return !UtilsLoadconfig.getConfig("runSimulationMode").equals("0");
	    }
}

