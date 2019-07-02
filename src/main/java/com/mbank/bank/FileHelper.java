package com.mbank.bank;

import java.io.File;
import java.util.Date;

public class FileHelper {

	private static final String DIR = "src/main/resources";

	public static File generateFile() {
		File file = new File(DIR + "/" + new Date().toInstant().toString() + ".pdf");
		return file;
	}
}
