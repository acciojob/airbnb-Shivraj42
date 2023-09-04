package com.driver.repositories;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class HotelManagementRepository {
    HashMap<String, Hotel> hotelDb= new HashMap<>();     // hotelName vs hotel
    HashMap<Integer, User> userDb= new HashMap();        // aadharCardNo vs user
    HashMap<String, Booking> bookingDb= new HashMap<>(); // bookingId vs bookings


    public String addHotel(Hotel hotel) {
        if(hotelDb.containsKey(hotel.getHotelName()))return "FAILURE";
        hotelDb.put(hotel.getHotelName(), hotel);
        return "SUCCESS";
    }

    public Integer addUser(User user) {
        int aadharNo=user.getaadharCardNo();
        userDb.put(aadharNo, user);
        return aadharNo;
    }

    public String getLexicographicallySmaller(String a, String b){
        int n=Math.min(a.length(), b.length());
        for(int i=0; i<n; i++){
            if(a.charAt(i)>b.charAt(i)) return b;
            else if(a.charAt(i)<b.charAt(i)) return a;
        }
        return a;
    }
    public String getHotelWithMostFacilities() {
        String hotel="";
        int facilities=0;
        for(String hotelName: hotelDb.keySet()){
            int currFacilities=hotelDb.get(hotelName).getFacilities().size();
            if(facilities<currFacilities){
                facilities=currFacilities;
                hotel=hotelName;
            }
            else if(facilities==currFacilities){
                hotel=getLexicographicallySmaller(hotel, hotelName);
            }
        }
        if(facilities==0) hotel="";
        return hotel;
    }


    public Hotel getHotel(String hotel) {
        Hotel h=null;
        if(hotelDb.containsKey(hotel)){
            h=hotelDb.get(hotel);
        }
        return h;
    }

    public void bookARoom(String bookingId,Booking booking) {
        bookingDb.put(bookingId, booking);
    }


    public int getBookings(Integer aadharCard) {
        int cnt=0;
        for(String bookingId: bookingDb.keySet()){
            if(bookingDb.get(bookingId).getBookingAadharCard()==aadharCard) cnt++;
        }
        return cnt;
    }
}
