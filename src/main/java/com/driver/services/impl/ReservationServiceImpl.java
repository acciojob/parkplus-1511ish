//package com.driver.services.impl;
//
//import com.driver.model.*;
//import com.driver.repository.ParkingLotRepository;
//import com.driver.repository.ReservationRepository;
//import com.driver.repository.SpotRepository;
//import com.driver.repository.UserRepository;
//import com.driver.services.ReservationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ReservationServiceImpl implements ReservationService {
//    @Autowired
//    UserRepository userRepository3;
//    @Autowired
//    SpotRepository spotRepository3;
//    @Autowired
//    ReservationRepository reservationRepository3;
//    @Autowired
//    ParkingLotRepository parkingLotRepository3;
//    @Override
//    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
//////        Step1.
////        Reservation reservation = new Reservation();
////
//////        Step2.
////        User user = userRepository3.findById(userId).get();
////        reservation.setUser(user);
////
//////        Step3.
////        reservation.setNumberOfHours(timeInHours);
////
//////        Step4.
////        SpotType spotType;
////        if(numberOfWheels==2)
////            spotType = SpotType.TWO_WHEELER;
////        else if(numberOfWheels==4)
////            spotType = SpotType.FOUR_WHEELER;
////        else
////            spotType = SpotType.OTHERS;
////        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
////        for(Spot spot:parkingLot.getSpotList()){
////            if(spot.getSpotType()==spotType){
////                spot.setOccupied(true);
////                spotRepository3.save(spot);
////                reservation.setSpot(spot);
////                break;
////            }
////        }
////
//////        Step5.
//////        reservation.setPayment();
////        reservationRepository3.save(reservation);
////        return reservation;
//        try {
//
//            if (!userRepository3.findById(userId).isPresent() || !parkingLotRepository3.findById(parkingLotId).isPresent()) {
//                throw new Exception("Cannot make reservation");
//            }
//
//            User user = userRepository3.findById(userId).get();
//            ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
//
//            List<Spot> spotList = parkingLot.getSpotList();
//            boolean checkForSpots = false;
//            for (Spot spot : spotList) {
//                if (!spot.getOccupied()) {
//                    checkForSpots = true;
//                    break;
//                }
//            }
//
//            if (!checkForSpots) {
//                throw new Exception("Cannot make reservation");
//            }
//
//
//            SpotType requestSpotType;
//
//            if (numberOfWheels > 4) {
//                requestSpotType = SpotType.OTHERS;
//            } else if (numberOfWheels > 2) {
//                requestSpotType = SpotType.FOUR_WHEELER;
//            } else requestSpotType = SpotType.TWO_WHEELER;
//
//
//            int minimumPrice = Integer.MAX_VALUE;
//
//            checkForSpots = false;
//
//            Spot spotChosen = null;
//
//            for (Spot spot : spotList) {
//                if (requestSpotType.equals(SpotType.OTHERS) && spot.getSpotType().equals(SpotType.OTHERS)) {
//                    if (spot.getPricePerHour() * timeInHours < minimumPrice && !spot.getOccupied()) {
//                        minimumPrice = spot.getPricePerHour() * timeInHours;
//                        checkForSpots = true;
//                        spotChosen = spot;
//                        // for extra
//                    }
//                } else if (requestSpotType.equals(SpotType.FOUR_WHEELER) && spot.getSpotType().equals(SpotType.OTHERS) ||
//                        spot.getSpotType().equals(SpotType.FOUR_WHEELER)) {
//                    if (spot.getPricePerHour() * timeInHours < minimumPrice && !spot.getOccupied()) {
//                        minimumPrice = spot.getPricePerHour() * timeInHours;
//                        checkForSpots = true;
//                        spotChosen = spot;
//                    }
//                } else if (requestSpotType.equals(SpotType.TWO_WHEELER) && spot.getSpotType().equals(SpotType.OTHERS) ||
//                        spot.getSpotType().equals(SpotType.FOUR_WHEELER) || spot.getSpotType().equals(SpotType.TWO_WHEELER)) {
//                    if (spot.getPricePerHour() * timeInHours < minimumPrice && !spot.getOccupied()) {
//                        minimumPrice = spot.getPricePerHour() * timeInHours;
//                        checkForSpots = true;
//                        spotChosen = spot;
//                    }
//                }
//
//            }
//
//            if (!checkForSpots) {
//                throw new Exception("Cannot make reservation");
//            }
//
//            assert spotChosen != null;
//            spotChosen.setOccupied(true);
//
//            Reservation reservation = new Reservation();
//            reservation.setNumberOfHours(timeInHours);
//            reservation.setSpot(spotChosen);
//            reservation.setUser(user);
//
//
//            spotChosen.getReservationList().add(reservation);
//            user.getReservationList().add(reservation);
//
//            userRepository3.save(user);
//            spotRepository3.save(spotChosen);
//
//            return reservation;
//        }
//        catch (Exception e){
//            return null;
//            //throwing an exception
//        }
//    }
//}



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
        Reservation reservation = new Reservation();
        if(userRepository3.findById(userId) == null){
            throw new Exception("Cannot make reservation");
        }
        User user = userRepository3.findById(userId).get();
        if(parkingLotRepository3.findById(parkingLotId) == null){
            throw new Exception("Cannot make reservation");
        }
        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
        List<Spot> spotList = parkingLot.getSpotList();
        Spot optimalSpot = null;
        int optimalPrice = Integer.MAX_VALUE;
        for(Spot spot: spotList){
            if(spot.getOccupied().equals(false)){
                if(spot.getSpotType().equals(SpotType.TWO_WHEELER)){
                    if(numberOfWheels <= 2){
                        if(optimalPrice > spot.getPricePerHour()){
                            optimalPrice = spot.getPricePerHour();
                            optimalSpot = spot;
                        }
                    }
                } else if(spot.getSpotType().equals(SpotType.FOUR_WHEELER)){
                    if(numberOfWheels <= 4){
                        if(optimalPrice > spot.getPricePerHour()){
                            optimalPrice = spot.getPricePerHour();
                            optimalSpot = spot;
                        }
                    }
                } else{
                    if(optimalPrice > spot.getPricePerHour()){
                        optimalPrice = spot.getPricePerHour();
                        optimalSpot = spot;
                    }
                }
            }
        }
        if(optimalSpot == null){
            throw new Exception("Cannot make reservation");
        }
        optimalSpot.setOccupied(true);
        reservation.setUser(user);
        reservation.setSpot(optimalSpot);
        reservation.setNumberOfHours(timeInHours);
        List<Reservation> reservations = user.getReservationList();
        List<Reservation> reservationList = optimalSpot.getReservationList();
        reservationList.add(reservation);
        reservations.add(reservation);
        user.setReservationList(reservationList);
        optimalSpot.setReservationList(reservationList);
        userRepository3.save(user);
        spotRepository3.save(optimalSpot);
        return reservation;
    }
}
