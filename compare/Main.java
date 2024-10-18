import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main implements Comparator<String> {

	public int compare(String o1, String o2) {
		o1 = o1.toLowerCase();
		o2 = o2.toLowerCase();
		// System.out.println(o1 + " " + o2);
		int len = Math.min(o1.length(), o2.length());
		// return o1.compareTo(o2);
		for (int i = 0; i < len; i++) {
			char c1 = o1.charAt(i);
			char c2 = o2.charAt(i);
			if (c1 == c2) {
				continue;
			}
			if (c1 == '_') {
				return 1;
			}
			if (c2 == '_') {
				return -1;
			}
			return c1 - c2;
		}
		if (o1.length() == o2.length()) {
			return 0;
		}

		if (o1.length() < o2.length()) {
			if (o2.charAt(len) == '-' || o2.charAt(len) == '.') {
				return 1;
			}
			return -1;
		} else {
			if (o1.charAt(len) == '-' || o1.charAt(len) == '.') {
				return -1;
			}
			return 1;
		}
	}

	public boolean equals(Object o) {
		return this == o;
	}

	public static void main(String[] args) throws Exception {
		String fileName = args[0];
		copy(fileName);
	}

	public static void copy(String fileName) throws Exception {
		File file = new File("src", fileName);

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
		StringBuilder buff = new StringBuilder();
		String line = null;

		boolean isMultipleLine = false;
		int index = 1;

		while ((line = in.readLine()) != null) {
            // System.out.println(line);
			String[] array = line.split("=");
			if (isMultipleLine) {
				buff.append("\n");
			} else {
				buff.append(array[0].trim())
						.append("\n");
			}
			isMultipleLine = line.trim().endsWith("\\");
			//if (isMultipleLine) {
			//	System.out.println(index + " " + line);
			//}
			//if (index == 759) {
			//	System.out.println(originLine);
			//}
			index++;
		}
		in.close();

		File outputFile = new File("dest", fileName);
		FileOutputStream fos = new FileOutputStream(outputFile);
		fos.write(buff.toString().getBytes());
		fos.flush();
		fos.close();
	}

	public static void sort(String fileName) throws Exception {
		File file = new File("src", fileName);

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
		// StringBuffer buff = new StringBuffer();
		String line = null;

		boolean isMultipleLine = false;
		int index = 1;

		Map<String, String> map = new TreeMap<String, String>(new Main());

		String key = "";
		StringBuilder value = new StringBuilder();

		while ((line = in.readLine()) != null) {
			if (line.startsWith("#")) {
				// comment
				continue;
			}
			if (line.trim().isEmpty()) {
				// blank
				continue;
			}

            // System.out.println(line);
			int indexPart = line.indexOf("=");
			String firstPart;
			String secondPart;
			if (isMultipleLine) {
				firstPart = line;
				secondPart = "";
			} else {
				if (indexPart == -1) {
					firstPart = line;
					secondPart = "";
				} else {
					firstPart = line.substring(0, indexPart);
					secondPart = line.substring(indexPart + 1);
				}
			}

			// String[] array = line.split("=");
			if (isMultipleLine) {
				// buff.append("\n");
				value.append(firstPart).append("\n");
			} else {
				// buff.append(array[0])
				//	.append("\n");
				key = firstPart;
				value.append(secondPart).append("\n");
			}
			isMultipleLine = line.trim().endsWith("\\");
			if (!isMultipleLine) {
				// System.out.println(key + " " + value);
				map.put(key, value.toString());
				key = "";
				value = new StringBuilder();
			}
			index++;
		}
		in.close();

		// System.out.println(map);

		File outputFile = new File("dest", fileName);
		FileOutputStream fos = new FileOutputStream(outputFile);
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8));
		// fos.write(buff.toString().getBytes("UTF-8"));
		for (Map.Entry<String, String> entry : map.entrySet()) {
			writer.print(entry.getKey() + "=" + entry.getValue());
		}
		writer.flush();
		writer.close();
	}

	static void fillProperties(String sourceFileName, String templateFileName, String targetFileName) throws Exception {
		File templateFile = new File(templateFileName);
		File targetFile = new File(targetFileName);

		Properties prop = readProperties(sourceFileName);

		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(templateFile)));
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(targetFile)));
		String line = null;

		while ((line = reader.readLine()) != null) {
			if (line.trim().isEmpty()) {
				writer.println("");
				continue;
			}
			if (line.startsWith("#")) {
				writer.println(line);
				continue;
			}
			String value = prop.getProperty(line);
			if (value != null) {
				writer.println(line + "=" + value);
			}
		}
		reader.close();
		writer.flush();
		writer.close();
	}

	static Properties readProperties(String sourceFileName) throws Exception {
		File sourceFile = new File(sourceFileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), StandardCharsets.UTF_8));
		String line = null;

		Properties prop = new Properties();

		while ((line = reader.readLine()) != null) {
			if (line.trim().isEmpty()) {
				continue;
			}
			if (line.startsWith("#")) {
				continue;
			}
			String[] array = line.trim().split("=");
			if (array.length != 2) {
				System.out.println(line);
				continue;
			}
			String key = array[0];
			String value = array[1];
			prop.setProperty(key, value);
		}
		return prop;
	}
}