import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Usage : java -jar test-url.jar <url>");
			System.out.println("\t url is the url to call");
			System.exit(1);
		}
		URL url = new URL(args[0]);
		InputStream is = null;
		HttpURLConnection cnx = (HttpURLConnection) url.openConnection();
		int responseCode = cnx.getResponseCode();
		if (responseCode >= 400) {
			is = cnx.getErrorStream();
		} else {
			is = cnx.getInputStream();

		}
		System.out.println(responseCode + ": " + readTextFromStream(is, "UTF8"));
	}

	public static String readTextFromStream(InputStream inputStream, String encoding) throws IOException {
		InputStreamReader fileReader = null;
		try {
			fileReader = new InputStreamReader(inputStream, encoding);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			StringBuffer res = new StringBuffer();
			while ((line = bufferedReader.readLine()) != null) {
				res.append(line);
				res.append('\n');
			}
			if (res.length() > 0 && res.charAt(res.length() - 1) == '\n')
				// Deletes last \n
				res.deleteCharAt(res.length() - 1);
			return res.toString();
		} finally {
			if (fileReader != null)
				fileReader.close();
		}
	}
}
