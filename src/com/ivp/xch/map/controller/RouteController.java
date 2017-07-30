package com.ivp.xch.map.controller;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.annotation.WebServlet;

import com.ivp.xch.anotation.RequestURI;
import com.ivp.xch.anotation.RequestURI.RequestMethod;
import com.ivp.xch.controller.BaseController;
import com.ivp.xch.db.DBEM;
import com.ivp.xch.map.FieldGPS;
import com.ivp.xch.sample.BaiduPoint;


/**
 * 路线controller
 * 
 * @author hcx
 *
 */
@WebServlet(name = "RouteController", urlPatterns = {
    "/c/m/routes"
})
public class RouteController extends BaseController {

    /**
     * 
     */
    private static final long serialVersionUID = 3269983896806005248L;



    @RequestURI(method = RequestMethod.GET, urlPatterns = "/c/m/routes")
    public void testFieldGPS() {

        EntityManager em = DBEM.em();
        List<FieldGPS> fgList = em.createQuery(" select f  from  FieldGPS f where f.section=:sec", FieldGPS.class).setParameter("sec", "LCESHI").getResultList();
        em.close();
        System.out.println("============================ fgList.size() " + fgList.size());
        System.out.println("============================ fgList.size() / 50 " + fgList.size() / 50);
//         BaiduPoint[] bd = new BaiduPoint[fgList.size() / 50];
//         for (int i = 0; i < bd.length; i++) {
//         System.out.println("=================== i " + i);
//         System.out.println("=================== i 50 " + (i * 50));
//         FieldGPS fg = fgList.get(i * 50);
//         bd[i] = new BaiduPoint(fg.getLatitude(), fg.getLongitude());
//        
         // gsArr[i] = new Double[] {
         // // 不知道为什么，百度地图这帮脑残设计者，纬度放前面
         // // 路线坐标点的格式是 纬度，经度
         // fg.getLatitude(), fg.getLongitude()
         // };
//         }

        BaiduPoint[] bd = new BaiduPoint[fgList.size()];
        for (int i = 0; i < bd.length; i++) {
            FieldGPS fg = fgList.get(i);
            bd[i] = new BaiduPoint(fg.getLatitude(), fg.getLongitude());

            // gsArr[i] = new Double[] {
            // // 不知道为什么，百度地图这帮脑残设计者，纬度放前面
            // // 路线坐标点的格式是 纬度，经度
            // fg.getLatitude(), fg.getLongitude()
            // };
        }
        String json = com.ivp.xch.helper.JsonHelper.toJson(bd);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            response.getWriter().append(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
