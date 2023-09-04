package com.driver.services;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repositories.HotelManagementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelManagementService {
    HotelManagementRepository hotelManagementRepository= new HotelManagementRepository();

    public String addHotel(Hotel hotel) {
        if(hotel==null || hotel.getHotelName()==null) return "FAILURE";
        return hotelManagementRepository.addHotel(hotel);

    }

    public Integer addUser(User user) {
        return hotelManagementRepository.addUser(user);
    }

    public String getHotelWithMostFacilities() {
        return hotelManagementRepository.getHotelWithMostFacilities();
    }

    public int bookARoom(Booking booking) {
        // generate UUID for bookingId
        String bookingId= UUID.randomUUID().toString();

        // get hotel for more details
        Hotel hotel= hotelManagementRepository.getHotel(booking.getHotelName());
        // if hotel does not have the required no of rooms return -1
        if(hotel==null || booking.getNoOfRooms()>hotel.getAvailableRooms()) return -1;

        // calculate the total amount to be paid
        int totalAmountPaidByPerson= booking.getNoOfRooms()* hotel.getPricePerNight();

        // complete the booking entity
        booking.setBookingId(bookingId);
        booking.setAmountToBePaid(totalAmountPaidByPerson);

        // book the room;
        hotelManagementRepository.bookARoom(bookingId,booking);

        // hotel rooms are booked so reduce the available rooms in hotel
        hotel.setAvailableRooms(hotel.getAvailableRooms()-booking.getNoOfRooms());

        // return the amount to be paid
        return totalAmountPaidByPerson;
    }

    public int getBookings(Integer aadharCard) {
        return hotelManagementRepository.getBookings(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel=hotelManagementRepository.getHotel(hotelName);
        List<Facility> hotelFacilities=hotel.getFacilities();
        for(Facility facility: newFacilities){
            if(!hotelFacilities.contains(facility)){
                hotelFacilities.add(facility);
            }
        }
        hotel.setFacilities(hotelFacilities);
        return hotel;
    }
}
