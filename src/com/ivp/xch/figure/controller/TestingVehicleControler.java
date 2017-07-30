package com.ivp.xch.figure.controller;


import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.annotation.WebServlet;
import com.ivp.xch.anotation.RequestURI;
import com.ivp.xch.anotation.RequestURI.RequestMethod;
import com.ivp.xch.controller.BaseController;
import com.ivp.xch.db.DBEM;
import com.ivp.xch.figure.entity.TestingVehicle;
import com.ivp.xch.figure.entity.Vehicle;
import com.ivp.xch.figure.entity.VehicleTrack;
import com.ivp.xch.helper.HttpHelper;
import com.ivp.xch.helper.JsonHelper;
import com.ivp.xch.result.JsonResult;


/**
 * 比赛车辆Controller
 * 
 * @author hcx
 *
 */


@WebServlet(name = "TestingVehicleControler", urlPatterns = {
    "/s/tstv/s", "/s/tstv/s/id", "/s/tstv/add", "/s/tstv/udt", "/s/tstv/dlt", "/s/tstv/start", "/s/tstv/stop"
})
public class TestingVehicleControler extends BaseController {

    /**
     * 
     */
    private static final long serialVersionUID = -415617214151334604L;



    @RequestURI(method = RequestMethod.GET, urlPatterns = "/s/tstv/s/id")
    public void testingVehicleByID() {
        String testingVehicleID = request.getParameter("tvID");
        Long tvID = Long.valueOf(testingVehicleID);

        EntityManager em = DBEM.em();
        TestingVehicle tv = em.find(TestingVehicle.class, tvID);

        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResult(tv, "查询成功"));
        result(json);
    }

    /**
     * 获取所有比赛车辆信息
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.GET, urlPatterns = "/s/tstv/s")
    public void allTestingVehicle() {
        EntityManager em = DBEM.em();
        List<TestingVehicle> vtList =
                em.createQuery(" select r from TestingVehicle  r order by r.id DESC", TestingVehicle.class)
                        .getResultList();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResult(vtList, "查询成功"));
        result(json);
    }

    /**
     * 添加比赛车辆
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/tstv/add")
    public void createTestingVehicle() {

        String vehicleID = request.getParameter("vid");
        // 车辆名称
        String vName = request.getParameter("vName");
        // 车编号
        String vCode = request.getParameter("vCode");
        // 车队名称
        String teamName = request.getParameter("teamName");
        // 车体长
        String vLength = request.getParameter("vLength");
        // 车体宽
        String vWidth = request.getParameter("vWidth");
        /**
         * 当前车辆使用的 gps设备ID
         */
        String gpsEID = request.getParameter("gpseid");

        String gpsEName = request.getParameter("eName");

        String gpsECode = request.getParameter("eCode");

        String oID = request.getParameter("oid");

        String oName = request.getParameter("oName");

        String ox = request.getParameter("x");
        String oy = request.getParameter("y");

        TestingVehicle tstv = new TestingVehicle();

        tstv.setVehicleID(Long.valueOf(vehicleID));
        
        tstv.setvName(vName);
        tstv.setvCode(vCode);
        tstv.setTeamName(teamName);
        tstv.setvLength(Float.valueOf(vLength));
        tstv.setvWidth(Float.valueOf(vWidth));
        tstv.setGpsEID(Long.valueOf(gpsEID));
        tstv.setGpsEName(gpsEName);
        tstv.setGpsECode(gpsECode);

        tstv.setObstacleID(Long.valueOf(oID));
        tstv.setoName(oName);
        tstv.setX(Double.valueOf(ox));
        tstv.setY(Double.valueOf(oy));

        EntityManager em = DBEM.em();
        Vehicle v = em.find(Vehicle.class, Long.valueOf(vehicleID));
        tstv.setTeamName(v.getTeamName());
        
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(tstv);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("添加成功"));
        result(json);
    }


    /**
     * 更新比赛车辆
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/tstv/udt")
    public void updateTestingVehicle() {

        String tvID = request.getParameter("id");

        String vehicleID = request.getParameter("vid");
        // 车辆名称
        String vName = request.getParameter("vName");
        // 车编号
        String vCode = request.getParameter("vCode");
        // 车队名称
        String teamName = request.getParameter("teamName");
        // 车体长
        String vLength = request.getParameter("vLength");
        // 车体宽
        String vWidth = request.getParameter("vWidth");
        /**
         * 当前车辆使用的 gps设备ID
         */
        String gpsEID = request.getParameter("gpseid");

        String gpsEName = request.getParameter("eName");

        String gpsECode = request.getParameter("eCode");

        String oID = request.getParameter("oid");

        String oName = request.getParameter("oName");

        String ox = request.getParameter("x");
        String oy = request.getParameter("y");


        EntityManager em = DBEM.em();
        TestingVehicle tstv = em.find(TestingVehicle.class, Long.valueOf(tvID));

        tstv.setVehicleID(Long.valueOf(vehicleID));
        tstv.setvName(vName);
        tstv.setvCode(vCode);
        tstv.setTeamName(teamName);
        tstv.setvLength(Float.valueOf(vLength));
        tstv.setvWidth(Float.valueOf(vWidth));
        tstv.setGpsEID(Long.valueOf(gpsEID));
        tstv.setGpsEName(gpsEName);
        tstv.setGpsECode(gpsECode);

        tstv.setObstacleID(Long.valueOf(oID));
        tstv.setoName(oName);
        tstv.setX(Double.valueOf(ox));
        tstv.setY(Double.valueOf(oy));

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(tstv);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("修改成功"));
        result(json);

    }


    /**
     * 删除比赛车辆
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/tstv/dlt")
    public void deleteTestingVehicle() {
        String vtID = request.getParameter("id");
        EntityManager em = DBEM.em();
        TestingVehicle vt = em.find(TestingVehicle.class, Long.valueOf(vtID));

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(vt);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("删除成功"));
        result(json);
    }

    /**
     * 开始测试
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/tstv/start")
    public void startTestingVehicle() {
        String vtID = request.getParameter("id");
        EntityManager em = DBEM.em();
        TestingVehicle vt = em.find(TestingVehicle.class, Long.valueOf(vtID));
        vt.setTesting(true);
        vt.setTestedTimes(vt.getTestedTimes() + 1);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(vt);
        transaction.commit();
        em.close();

        // String gpsData = "$GPGGA,093821.80,3135.5183973,N,12046.3509778,E,4,16,2.4,2.6374,M,8.295,M,00,0004*67" +
        // "$GPHPD,1957,34719.600,61.98,1.77,17.12,31.5919733,120.7725163,2.637,13630389.939,24921434.505,2.637,-2.102,-1.920,1.289,0.646,1.565,0.840,0.000,16,15,4*76" +
        // "$GPGGA,093822.00,3135.5185619,N,12046.3513615,E,4,16,2.5,2.6145,M,8.295,M,01,0004*68" +
        // "$GPHPD,1957,34719.800,61.71,-0.49,17.22,31.5919760,120.7725227,2.615,13630388.372,24921438.174,2.615,-2.121,-2.078,1.305,0.646,1.565,0.840,0.000,16,14,4*50" +
        // "$GPGGA,093822.20,3135.5187247,N,12046.3517398,E,4,16,2.3,2.6341,M,8.295,M,01,0004*63" +
        // "$GPHPD,1957,34720.000,62.11,0.94,17.29,31.5919787,120.7725290,2.634,13630386.820,24921441.787,2.634,-2.122,-2.344,1.252,0.664,1.567,0.840,0.000,16,13,4*7B" +
        // "$GPGGA,093822.40,3135.5189043,N,12046.3521442,E,4,16,2.3,2.6180,M,8.295,M,01,0004*67" +
        // "$GPHPD,1957,34720.200,62.90,0.59,17.28,31.5919817,120.7725357,2.618,13630385.081,24921445.618,2.618,-2.166,-2.534,1.243,0.664,1.567,0.840,0.000,16,13,4*7E";
        //
        // HttpHelper.httpRequest("http://localhost/IVP/s/gps/receive", "POST", gpsData);

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("开始测试"));
        result(json);
    }



    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/tstv/stop")
    public void stopTestingVehicle() {
        String vtID = request.getParameter("id");
        EntityManager em = DBEM.em();
        TestingVehicle vt = em.find(TestingVehicle.class, Long.valueOf(vtID));
        vt.setTesting(false);
        /// 把 本次 所有的 vehicleTrack 的 stopTrackTime 改为
        // VehicleTrack f = new VehicleTrack();
        // f.setStartTrackTime(startTrackTime);
        // f.setStopTrackTime(stopTrackTime);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(vt);

        em.createQuery("  update  VehicleTrack vt set ct.stopTrackTime = :cDate ").setParameter("cDate", new Date()).executeUpdate();

        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("开始测试"));
        result(json);
    }

}
