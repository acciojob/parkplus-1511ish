//package com.driver.services.impl;
//
//import com.driver.model.ParkingLot;
//import com.driver.model.Spot;
//import com.driver.model.SpotType;
//import com.driver.repository.ParkingLotRepository;
//import com.driver.repository.SpotRepository;
//import com.driver.services.ParkingLotService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ParkingLotServiceImpl implements ParkingLotService {
//    @Autowired
//    ParkingLotRepository parkingLotRepository1;
//    @Autowired
//    SpotRepository spotRepository1;
//    @Override
//    public ParkingLot addParkingLot(String name, String address) {
//       ParkingLot parkingLot = new ParkingLot();
//       parkingLot.setName(name);
//       parkingLot.setAddress(address);
//       parkingLotRepository1.save(parkingLot);
//       return parkingLot;
//    }
//
//    @Override
//    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) throws Exception {
//       Spot spot = new Spot();
//
//       try{
//           ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
//           spot.setParkingLot(parkingLot);
//           SpotType spotType;
//           if(numberOfWheels<=2)
//               spotType = SpotType.TWO_WHEELER;
//
//           else if(numberOfWheels>2&&numberOfWheels<=4)
//               spotType = SpotType.FOUR_WHEELER;
//
//           else spotType = SpotType.OTHERS;
//
//           spot.setSpotType(spotType);
//           spot.setPricePerHour(pricePerHour);
//           spot.setOccupied(false);
//           List<Spot> spotList = parkingLot.getSpotList();
//           spotList.add(spot);
//           parkingLot.setSpotList(spotList);
//
//           parkingLotRepository1.save(parkingLot);
//       }catch (Exception e){
//           throw new Exception("NullPointer exception");
//       }
//       return spot;
//    }
//
//    @Override
//    public void deleteSpot(int spotId) {
////       Spot spot = spotRepository1.findById(spotId).get();
////       ParkingLot parkingLot = spot.getParkingLot();
////       parkingLot.getSpotList().remove(spot);
////       parkingLotRepository1.save(parkingLot);
//        spotRepository1.deleteById(spotId);
//    }
//
//    @Override
//    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) throws Exception {
//        try{
//            Spot spot = spotRepository1.findById(spotId).get();
//            ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
//
//            spot.setParkingLot(parkingLot);
//            spot.setPricePerHour(pricePerHour);
//
//            spotRepository1.save(spot);
//            return spot;
//        }
//        catch (Exception e){
//            throw new Exception("NoSuchElement No value present");
//        }
////        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
////        List<Spot> spotList = parkingLot.getSpotList();
////        for(Spot spot:spotList){
////            if(spot.getId()==spotId)
////            {
////                spot.setPricePerHour(pricePerHour);
////                parkingLotRepository1.save(parkingLot);
////                return spot;
////            }
////        }
//
//    }
//
//    @Override
//    public void deleteParkingLot(int parkingLotId) {
//        parkingLotRepository1.deleteById(parkingLotId);
//    }
//}



package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        parkingLot.setSpotList(new ArrayList<>());
        return parkingLotRepository1.save(parkingLot);
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        Spot spot = new Spot();
        spot.setOccupied(false);
        spot.setPricePerHour(pricePerHour);
        spot.setReservationList(new ArrayList<>());
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        spot.setParkingLot(parkingLot);
        if(numberOfWheels <= 2){
            spot.setSpotType(SpotType.TWO_WHEELER);
        } else if (numberOfWheels <= 4) {
            spot.setSpotType(SpotType.FOUR_WHEELER);
        } else {
            spot.setSpotType(SpotType.OTHERS);
        }
        List<Spot> spotList = parkingLot.getSpotList();
        spotList.add(spot);
        spot.setParkingLot(parkingLot);
        parkingLot.setSpotList(spotList);
        parkingLotRepository1.save(parkingLot);
        return spot;
    }

    @Override
    public void deleteSpot(int spotId) {
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        List<Spot> spotList = parkingLot.getSpotList();
        List<Spot> newSpotList = new ArrayList<>();
        Spot toBeChanged = null;
        for(Spot spot: spotList){
            if(spot.getId() == spotId){
                spot.setPricePerHour(pricePerHour);
                toBeChanged = spotRepository1.save(spot);
            }
            newSpotList.add(spot);
        }
        toBeChanged.setParkingLot(parkingLot);
        parkingLot.setSpotList(spotList);
        parkingLotRepository1.save(parkingLot);
        return toBeChanged;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        parkingLotRepository1.deleteById(parkingLotId);
    }
}
