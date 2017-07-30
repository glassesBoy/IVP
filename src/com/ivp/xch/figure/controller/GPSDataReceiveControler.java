package com.ivp.xch.figure.controller;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;

import com.ivp.xch.anotation.RequestURI;
import com.ivp.xch.anotation.RequestURI.RequestMethod;
import com.ivp.xch.controller.BaseController;
import com.ivp.xch.db.DBEM;
import com.ivp.xch.figure.entity.GPSEquipment;
import com.ivp.xch.figure.entity.TestingVehicle;
import com.ivp.xch.figure.entity.VehicleTrack;
import com.ivp.xch.helper.JsonHelper;
import com.ivp.xch.map.FieldGPS;
import com.ivp.xch.result.JsonResult;


/**
 * 接收GPS信息 Controller
 * 
 * @author hcx
 *
 */


@MultipartConfig
@WebServlet(name = "GPSDataReceiveControler", urlPatterns = {
    "/s/gps/receive", "/s/gps/upld"
})
public class GPSDataReceiveControler extends BaseController {

    /**
     * 
     */
    private static final long serialVersionUID = -415617214151334604L;



    /**
     * 接收 gps设备信息
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.GET, urlPatterns = "/s/gps/receive")
    public void gpsReceive1() {
        gpsReceive();
    }

    /**
     * 接收 gps信息
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/gps/receive")
    public void gpsReceive2() {
        gpsReceive();

    }


    /**
     * 无论GET 还是POST 都用这个处理
     * 接收GPS数据
     * 问题，没法确定 是那个GPS设备传来的信息
     */
    public void gpsReceive() {

        // String gpsData = "$GPGGA,093821.80,3135.5183973,N,12046.3509778,E,4,16,2.4,2.6374,M,8.295,M,00,0004*67" +
        // "$GPHPD,1957,34719.600,61.98,1.77,17.12,31.5919733,120.7725163,2.637,13630389.939,24921434.505,2.637,-2.102,-1.920,1.289,0.646,1.565,0.840,0.000,16,15,4*76" +
        // "$GPGGA,093822.00,3135.5185619,N,12046.3513615,E,4,16,2.5,2.6145,M,8.295,M,01,0004*68" +
        // "$GPHPD,1957,34719.800,61.71,-0.49,17.22,31.5919760,120.7725227,2.615,13630388.372,24921438.174,2.615,-2.121,-2.078,1.305,0.646,1.565,0.840,0.000,16,14,4*50" +
        // "$GPGGA,093822.20,3135.5187247,N,12046.3517398,E,4,16,2.3,2.6341,M,8.295,M,01,0004*63" +
        // "$GPHPD,1957,34720.000,62.11,0.94,17.29,31.5919787,120.7725290,2.634,13630386.820,24921441.787,2.634,-2.122,-2.344,1.252,0.664,1.567,0.840,0.000,16,13,4*7B" +
        // "$GPGGA,093822.40,3135.5189043,N,12046.3521442,E,4,16,2.3,2.6180,M,8.295,M,01,0004*67" +
        // "$GPHPD,1957,34720.200,62.90,0.59,17.28,31.5919817,120.7725357,2.618,13630385.081,24921445.618,2.618,-2.166,-2.534,1.243,0.664,1.567,0.840,0.000,16,13,4*7E";
        //

        try {
            InputStream gpsIn = request.getInputStream();

            InputStreamReader read = new InputStreamReader(gpsIn, "UTF-8");
            // 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            // StringBuilder sbuilder = new StringBuilder();

            EntityManager em = DBEM.em();
            em.getTransaction().begin();
            while ((lineTxt = bufferedReader.readLine()) != null) {
                // System.out.println("===== " + lineTxt);
                // sbuilder +=;
                // 如果 开头是 $GPHPD
                if (lineTxt.startsWith("$GPHPD")) {
                    String[] gpsData = lineTxt.split(",");
                    VehicleTrack f = new VehicleTrack();
                    // f.setTestingVehicle(testingVehicle);
                    // f.setg
                    f.setGpsDataType("GPHPD");
                    f.setGPSWeek(Integer.valueOf(gpsData[1]));
                    f.setGPSTime(Double.valueOf(gpsData[2]));
                    f.setHeading(Double.valueOf(gpsData[3]));
                    f.setPitch(Double.valueOf(gpsData[4]));
                    f.setTrack(Double.valueOf(gpsData[5]));
                    f.setLatitude(Double.valueOf(gpsData[6]));
                    f.setLongitude(Double.valueOf(gpsData[7]));
                    f.setAltitude(Double.valueOf(gpsData[8]));
                    f.setVe(Double.valueOf(gpsData[9]));
                    f.setVn(Double.valueOf(gpsData[10]));
                    f.setVu(Double.valueOf(gpsData[11]));
                    f.setAe(Double.valueOf(gpsData[12]));
                    f.setAn(Double.valueOf(gpsData[13]));
                    f.setBaseline(Double.valueOf(gpsData[14]));
                    f.setNSV1(Float.valueOf(gpsData[15]));

                    em.persist(f);
                }

                // 如果 开头是 $GPGGA
            }
            em.getTransaction().commit();
            em.close();
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 用户导入GPS信息
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/gps/upld")
    public void gpsReceiveFromFile() {
        String vehicleID = request.getParameter("vid");
        Long vID = Long.valueOf(vehicleID);

        String stime = request.getParameter("startTrackTime");
        String etime = request.getParameter("stopTrackTime");

        if (stime == null || stime.length() == 0 || etime == null || etime.length() == 0) {
            String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("请选择测试时间和结束时间"));
            result(json);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



        try {
            Date startTrackTime = sdf.parse(stime);
            Date stopTrackTime = sdf.parse(etime);

            Part part = request.getPart("gpsDataFile");
            if (part.getSize() == 0) {

                String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("没有接收到GPS文件"));
                result(json);
            } else {

                InputStream gpsIn = part.getInputStream();

                InputStreamReader read = new InputStreamReader(gpsIn, "UTF-8");
                // 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                // StringBuilder sbuilder = new StringBuilder();

                EntityManager em = DBEM.em();

                TestingVehicle testingVehicle = em.find(TestingVehicle.class, vID);
                em.getTransaction().begin();
                Long times = testingVehicle.getTestedTimes() + 1;
                testingVehicle.setTestedTimes(times);
                em.merge(testingVehicle);


                while ((lineTxt = bufferedReader.readLine()) != null) {
                    // System.out.println("===== " + lineTxt);
                    // sbuilder +=;
                    // 如果 开头是 $GPHPD
                    if (lineTxt.startsWith("$GPHPD")) {
                        String[] gpsData = lineTxt.split(",");
                        VehicleTrack f = new VehicleTrack();

                        f.setStartTrackTime(startTrackTime);
                        f.setStopTrackTime(stopTrackTime);
                        f.setTestingTimes(times);
                        f.setTestingVehicle(testingVehicle);

                        f.setGpsDataType("GPHPD");
                        f.setGPSWeek(Integer.valueOf(gpsData[1]));
                        f.setGPSTime(Double.valueOf(gpsData[2]));
                        f.setHeading(Double.valueOf(gpsData[3]));
                        f.setPitch(Double.valueOf(gpsData[4]));
                        f.setTrack(Double.valueOf(gpsData[5]));
                        f.setLatitude(Double.valueOf(gpsData[6]));
                        f.setLongitude(Double.valueOf(gpsData[7]));
                        f.setAltitude(Double.valueOf(gpsData[8]));
                        f.setVe(Double.valueOf(gpsData[9]));
                        f.setVn(Double.valueOf(gpsData[10]));
                        f.setVu(Double.valueOf(gpsData[11]));
                        f.setAe(Double.valueOf(gpsData[12]));
                        f.setAn(Double.valueOf(gpsData[13]));
                        f.setBaseline(Double.valueOf(gpsData[14]));
                        f.setNSV1(Float.valueOf(gpsData[15]));

                        em.persist(f);
                    }

                    // 如果 开头是 $GPGGA
                }
                em.getTransaction().commit();
                em.close();
                read.close();


                String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("导入成功"));
                result(json);
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String json = JsonHelper.toJson(JsonResult.getFailResultMsg("数据导入失败"));
        result(json);

    }
}
