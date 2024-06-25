package ulpgc.dacd.jorgemorales.control;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ulpgc.dacd.jorgemorales.model.Booking;
import ulpgc.dacd.jorgemorales.model.Hotel;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import java.util.ArrayList;
import java.util.List;

public class XoteloHotelProvider implements HotelProvider{

    private static final String API_URL = "https://xotelo.com/api/rates";
    private final HotelInfoReader hotelInfoReader;

    public XoteloHotelProvider(HotelInfoReader hotelInfoReader) {
        this.hotelInfoReader = hotelInfoReader;
    }

    @Override
    public List<Hotel> searchHotel(String island, Booking booking) {
        try {
            List<String> hotelKeys = hotelInfoReader.getHotelKeyForIsland(island);
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

    private String getAPIResponse(String hotelKey, Booking booking) throws Exception {
        URL url = new URL(API_URL + "?hotelKey=" + hotelKey + "&checkInDate=" + booking.getCheckInDate() + "&checkOutDate=" + booking.getCheckOutDate());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
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
            JsonObject result = jsonObject.getAsJsonObject("result");
            JsonArray rates = result.getAsJsonArray("rates");
            for (int i = 0; i < rates.size(); i++) {
                JsonObject hotelObject = rates.get(i).getAsJsonObject();
                Hotel hotel = new Hotel();
                hotel.setName(hotelObject.get("name").getAsString());
                hotel.setPrice(hotelObject.get("rate").getAsDouble());
                hotels.add(hotel);
            }
        }
        return hotels;
    }
}
