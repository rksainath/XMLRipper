package com.sai.xmlripper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MultiTagRipper {

	File file = new File("/home/sainath/eclipse-workspace/XMLRIpper/Output/output.csv");

	public void startTagAndEndTag(String startTag, String endTag, String fileName)
			throws FileNotFoundException, IOException {
		// ArrayList<String> rippedString = new ArrayList<String>();

		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			while (true) {
				String line = reader.readLine();

				ArrayList<Integer> startTag_indexes = new ArrayList<Integer>();
				ArrayList<Integer> endTag_indexes = new ArrayList<Integer>();

				int startTagWordLength = 0;
				int endTagWordLength = 0;
				int startTagIndex = 0;
				int endTagIndex = 0;

				// Logic for counting the index of start tag

				while (startTagIndex != -1) {
					startTagIndex = line.indexOf(startTag, startTagIndex + startTagWordLength);
					if (startTagIndex != -1) {
						startTag_indexes.add(startTagIndex);
					}
					startTagWordLength = startTag.length();
				}

				// Same logic as above for counting the index of end tag

				while (endTagIndex != -1) {
					endTagIndex = line.indexOf(endTagWordLength, endTagIndex + endTagWordLength);
					if (endTagIndex != -1) {
						endTag_indexes.add(endTagIndex);
					}
					endTagWordLength = startTag.length();
				}

				int totalIndices = startTag_indexes.size();

				String pathToFile = "/home/sainath/eclipse-workspace/XMLRIpper/Output/output.csv";
				BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile, true));

				for (int k = 0; k < totalIndices; k++) {

					String rippedValue = line.substring(startTag_indexes.get(k), endTag_indexes.get(k));
					rippedValue = rippedValue.replaceAll("><", ">,<");
					writer.newLine();
					writer.write(rippedValue);
				}
				writer.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {

		MultiTagRipper formatter = new MultiTagRipper();

		File xmlFolder = new File("/home/sainath/eclipse-workspace/XMLRIpper/Resources");

		File[] listOfFiles = xmlFolder.listFiles();

		String startTagSequence = "<ad>";
		String endTagSequence = "</ad>";

		System.out.println("List of Files");

		for (File files : listOfFiles) {

			String fileName = files.getName();

			

			System.out.println(fileName);

			formatter.startTagAndEndTag(startTagSequence, endTagSequence,
					"/home/sainath/eclipse-workspace/XMLRIpper/Resources/" + fileName);
		}

	}

}
