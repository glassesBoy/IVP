package com.ivp.xch.figure.service;

import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
import com.ivp.xch.figure.dao.VehicleDAO;
import com.ivp.xch.figure.entity.Vehicle;


// Spring Bean的标识.
// @Component
// 类中所有public函数都纳入事务管理的标识.
// @Transactional
// @Service
public class VehicleService {


    // @Autowired
    // public VehicleDAO vehicleDao;
    private VehicleDAO vehicleDao = new VehicleDAO();

    // @Transactional(readOnly=false)
    public void saveVehicle(Vehicle vh) {
        // TODO Auto-generated method stub
        vehicleDao.save(vh);
    }

    public Vehicle findVehicleById(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Vehicle> findVehicleByUsername(String vname) {
        // TODO Auto-generated method stub
        return null;
    }

}
