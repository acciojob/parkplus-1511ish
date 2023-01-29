package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
//        Step1.
        Reservation reservation = new Reservation();

//        Step2.
        User user = userRepository3.findById(userId).get();
        reservation.setUser(user);

//        Step3.
        reservation.setNumberOfHours(timeInHours);

//        Step4.
        SpotType spotType;
        if(numberOfWheels==2)
            spotType = SpotType.TWO_WHEELER;
        else if(numberOfWheels==4)
            spotType = SpotType.FOUR_WHEELER;
        else
            spotType = SpotType.OTHERS;
        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
        for(Spot spot:parkingLot.getSpotList()){
            if(spot.getSpotType()==spotType){
                spot.setOccupied(true);
                spotRepository3.save(spot);
                reservation.setSpot(spot);
                break;
            }
        }

//        Step5.
//        reservation.setPayment();
        reservationRepository3.save(reservation);
        return reservation;
    }
}
