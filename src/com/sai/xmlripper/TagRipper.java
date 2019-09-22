package com.sai.xmlripper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagRipper {

	File file = new File("/home/sainath/eclipse-workspace/XMLRIpper/Output/output.csv");

	public void startTagAndEndTag(String startTag, String endTag, File filename, String xmlfilename)
			throws FileNotFoundException, IOException {
		String patternTags = Pattern.quote(startTag) + ".*?" + Pattern.quote(endTag);
		String line;

		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

			StringBuilder b = new StringBuilder();
			try {
				line = reader.readLine();
				while (line != null) {
					b.append(line);
					line = reader.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			String newString = b.toString();

			String pathToFile = "/home/sainath/eclipse-workspace/XMLRIpper/Output/output.csv";
			BufferedWriter csvWriter = new BufferedWriter(new FileWriter(pathToFile, true));

			Pattern pattern = Pattern.compile(patternTags);
			Matcher matcher = pattern.matcher(newString);

			while (matcher.find()) {
				String value = matcher.group();

				StringBuilder strBuilder = new StringBuilder();

				for (int i = 0; i < value.length(); i++) {

					if (!Character.isWhitespace(value.charAt(i))) {
						strBuilder.append(value.charAt(i));
					}

				}
				String finalString = strBuilder.toString().replaceAll("><", ">,<");
				String finalValue = "'" + xmlfilename + "," + finalString;
				csvWriter.newLine();
				csvWriter.write(finalValue);
			}
			csvWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
			
		}
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		TagRipper formatter = new TagRipper();

		File xmlFolder = new File("/home/sainath/eclipse-workspace/XMLRIpper/Resources");

		File[] listOfFiles = xmlFolder.listFiles();

		String startTagSequence = "<ad>";
		String endTagSequence = "</ad>";

		System.out.println("List of Files");

		for (File files : listOfFiles) {

			String fileName = files.getName();

			System.out.println(fileName);

			formatter.startTagAndEndTag(startTagSequence, endTagSequence, files, fileName);
		}
	}
}
