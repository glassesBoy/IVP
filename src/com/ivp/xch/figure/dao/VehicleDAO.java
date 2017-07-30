package com.ivp.xch.figure.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// import org.springframework.stereotype.Repository;
// import org.springframework.transaction.annotation.Transactional;

import com.ivp.xch.db.DBEM;
import com.ivp.xch.figure.entity.Vehicle;

/**
 * 
 * 处理车辆的DAO
 * 
 * @author hcx
 *
 */
// @Repository
public class VehicleDAO {


    // @PersistenceContext
    public EntityManager em = DBEM.em();


    // public Vehicle save(AccountInfo accountInfo) {
    // em.persist(accountInfo);
    // return accountInfo.getAccountId();
    // }
    // }


    // @Transactional
    public Vehicle save(Vehicle vh) {
        // TODO Auto-generated method stub
        if (vh == null) {
            System.out.println("==================== VehicleDAO  save   vh  == null ");
        } else {
            if (em == null) {
                System.out.println("==================== VehicleDAO  save   em  == null ");
            } else {
                em.getTransaction().begin();
                em.persist(vh);
                em.getTransaction().commit();
                em.close();
            }
        }
        return vh;
    }

}
