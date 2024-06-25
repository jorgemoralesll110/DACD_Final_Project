package ulpgc.dacd.jorgemorales.control;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ulpgc.dacd.jorgemorales.model.Booking;
import ulpgc.dacd.jorgemorales.model.Hotel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.ArrayList;
import java.net.URL;



public class XoteloHotelProvider implements HotelProvider{
    private static final String API_URL = "https://data.xotelo.com/api/rates";
    private final HotelInfoReader hotelInfoReader;

    public XoteloHotelProvider(HotelInfoReader hotelInfoReader) {
        this.hotelInfoReader = hotelInfoReader;
    }

    @Override
    public List<Hotel> searchHotel(String island, Booking booking) {
        try {
            List<String> hotelKeys = hotelInfoReader.getHotelKeysForIsland(island);
            List<Hotel> allHotels = new ArrayList<>();
            for (String hotelKey : hotelKeys) {
                String APIResponse = getAPIResponse(hotelKey, booking);
                List<Hotel> hotels = parseAPIResponse(APIResponse);
                allHotels.addAll(hotels);
            }
            return allHotels;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    private String getAPIResponse(String hotelKey, Booking booking) throws IOException {
        String URLString = String.format("%s?hotelKey=%s&checkInDate=%s&checkOutDate=%s", API_URL, hotelKey, booking.getCheckInDate(), booking.getCheckOutDate());
        URL url = new URL(URLString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        connection.disconnect();

        return sb.toString();
    }

    private List<Hotel> parseAPIResponse(String apiResponse) {
        List<Hotel> hotels = new ArrayList<>();
        JsonObject jsonObject = JsonParser.parseString(apiResponse).getAsJsonObject();

        if (jsonObject.has("result")) {
            JsonArray result = jsonObject.getAsJsonObject("result").getAsJsonArray("rates");
            for (int i = 0; i < result.size(); i++) {
                JsonObject hotelObject = result.get(i).getAsJsonObject();
                Hotel hotel = new Hotel();
                hotel.setName(hotelObject.get("name").getAsString());
                hotel.setPrice(hotelObject.get("rating").getAsDouble());
                hotels.add(hotel);
            }
        }
        return hotels;
    }
}
