package com.lcwd.hotel.services;


import com.lcwd.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

     Hotel create(Hotel hotel);

     Hotel getById(String hotelId);

     List<Hotel> getAll();

     Hotel Delete(String hotelId);

}
