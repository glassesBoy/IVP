package com.ivp.xch.map.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.annotation.WebServlet;

import com.ivp.xch.anotation.RequestURI;
import com.ivp.xch.anotation.RequestURI.RequestMethod;
import com.ivp.xch.controller.BaseController;
import com.ivp.xch.db.DBEM;
import com.ivp.xch.map.FieldGPS;
import com.ivp.xch.map.LineStrack;
import com.ivp.xch.result.JsonResult;
import com.ivp.xch.result.MapJsonResult;



@WebServlet(name = "FieldGPSController", urlPatterns = {
    "/c/m/fgps", "/c/m/d3gps", "/c/m/gps", "/c/m/gps/a", "/c/m/gps/v"
})
public class FieldGPSController extends BaseController {


    /**
     * 
     */
    private static final long serialVersionUID = 2516804466937959541L;


    /**
     * 获取场地的GPS
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.GET, urlPatterns = "/c/m/fgps")
    public List<FieldGPS> queryFieldGPS() {

        return null;
    }


    /**
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.GET, urlPatterns = "/c/m/d3gps")
    public void queryFieldGPSAsD3() {


        String sec = request.getParameter("section");
        System.out.println("==================  section " + sec);
        EntityManager em = DBEM.em();
        // 先看看有多少个 区块
        // em.createQuery(" select f.section from FieldGPS f ", FieldGPS.class).getResultList();



        List<FieldGPS> fgList = em.createQuery(" select f  from  FieldGPS f where f.section=:sec", FieldGPS.class).setParameter("sec", sec).getResultList();
        Double[][][] gsArr = new Double[1][fgList.size()][2];

        // int[][] er = new int[2][3];
        for (int i = 0; i < fgList.size(); i++) {
            FieldGPS fg = fgList.get(i);
            // int[] qq = new int[]{1,2,3,4,5,6,7,22};
            // er[0] = new int[]{1,2,3,4,5,6,7,22};
            gsArr[0][i] = new Double[] {
                fg.getLongitude(), fg.getLatitude()
            };
            // new String[]{"",""};
            // fg.getLatitude();
        }

        String json = com.ivp.xch.helper.JsonHelper.toJson(MapJsonResult.getSuccessResult(gsArr, "查询成功"));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        // response.getWriter().append("Served at: ").append(request.getContextPath());
        try {
            response.getWriter().append(json);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @RequestURI(method = RequestMethod.GET, urlPatterns = "/c/m/gps")
    public void queryFieldGPSAsGPS() {
        String sec = request.getParameter("section");
        // System.out.println("================== section " + sec);
        EntityManager em = DBEM.em();
        // 先看看有多少个 区块
        // em.createQuery(" select f.section from FieldGPS f ", FieldGPS.class).getResultList();

        List<FieldGPS> fgList = em.createQuery(" select f  from  FieldGPS f where f.section=:sec", FieldGPS.class).setParameter("sec", sec).getResultList();
        em.close();
        Double[][] gsArr = new Double[fgList.size()][2];

        for (int i = 0; i < fgList.size(); i++) {
            FieldGPS fg = fgList.get(i);
            gsArr[i] = new Double[] {
                fg.getLongitude(), fg.getLatitude()
            };
        }

        String json = com.ivp.xch.helper.JsonHelper.toJson(gsArr);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        // response.getWriter().append("Served at: ").append(request.getContextPath());
        try {
            response.getWriter().append(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    /**
    * 
    */
    @RequestURI(method = RequestMethod.GET, urlPatterns = "/c/m/gps/a")
    public void queryAllFieldGPS() {


        EntityManager em = DBEM.em();
        // 先看看有多少个 区块
        List<Object> sections = em.createQuery(" select f.section from FieldGPS f GROUP BY f.section ").getResultList();
        List<Double[][]> gpsList = new LinkedList<Double[][]>();
        System.out.println("================== sections " + sections.size());
        for (Object object : sections) {
            List<FieldGPS> fgList = em.createQuery(" select f  from  FieldGPS f where f.section=:sec", FieldGPS.class).setParameter("sec", object.toString()).getResultList();
            Double[][] gsArr = new Double[fgList.size()][2];

            for (int i = 0; i < fgList.size(); i++) {
                FieldGPS fg = fgList.get(i);
                gsArr[i] = new Double[] {
                    fg.getLongitude(), fg.getLatitude()
                };
            }

            gpsList.add(gsArr);

        }

        em.close();

        String json = com.ivp.xch.helper.JsonHelper.toJson(gpsList);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        // response.getWriter().append("Served at: ").append(request.getContextPath());
        try {
            response.getWriter().append(json);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    /**
     * 查询当前 小车的坐标点
     */
    @RequestURI(method = RequestMethod.GET, urlPatterns = "/c/m/gps/v")
    public void queryCurrentVehicleFieldGPS() {
        String pageNum = request.getParameter("page");
        String json = null;
        if (pageNum == null) {
            json = com.ivp.xch.helper.JsonHelper.toJson(new double[] {});
        } else {

            Integer pg = Integer.valueOf(pageNum);
            EntityManager em = DBEM.em();
          //  List<FieldGPS> gps = em.createQuery(" select f  from FieldGPS f where  f.section = 'LCESHI' ").setFirstResult(pg*5).setMaxResults(5).getResultList();
            List<LineStrack> gps = em.createQuery("  select f from  LineStrack  f where f.carID = 1  ", LineStrack.class).getResultList();
            
            em.close();
            Double[][] gsArr = new Double[gps.size()][2];
            
            for (int i = 0; i < gps.size(); i++) {
               // FieldGPS fg = gps.get(i);
                LineStrack fg  = gps.get(i);
                gsArr[i] = new Double[] {
                    fg.getLongitude(), fg.getLatitude()
                };
            }


            json = com.ivp.xch.helper.JsonHelper.toJson(gsArr);
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        // response.getWriter().append("Served at: ").append(request.getContextPath());
        try {
            response.getWriter().append(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


