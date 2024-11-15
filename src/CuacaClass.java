import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class CuacaClass {

    private static final String API_KEY = "0576e34b593e44899c8101022241511"; // Ganti dengan API key Anda dari WeatherAPI
    private static final String BASE_URL = "http://api.weatherapi.com/v1/current.json";

    public static String getWeather(String city) {
        try {
            // Buat URL API dengan nama kota
            String urlString = BASE_URL + "?key=" + API_KEY + "&q=" + city + "&aqi=no";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = connection.getResponseCode();
            if (responseCode == 401) {
                return "Error: Unauthorized. API key tidak valid.";
            } else if (responseCode != 200) {
                return "Error: Server mengembalikan kode " + responseCode;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONObject current = jsonResponse.getJSONObject("current");
            String condition = current.getJSONObject("condition").getString("text");

            return condition;
        } catch (Exception e) {
            e.printStackTrace();
            return "Terjadi kesalahan saat mengambil data cuaca.";
        }
    }
}
