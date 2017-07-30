package com.ivp.xch.figure.controller;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.annotation.WebServlet;

import com.ivp.xch.anotation.RequestURI;
import com.ivp.xch.anotation.RequestURI.RequestMethod;
import com.ivp.xch.bean.Car;
import com.ivp.xch.bean.CarTrack;
import com.ivp.xch.controller.BaseController;
import com.ivp.xch.db.DBEM;
import com.ivp.xch.figure.entity.TestingVehicle;
import com.ivp.xch.figure.entity.TrackResult;
import com.ivp.xch.figure.entity.VehicleTeam;
import com.ivp.xch.figure.entity.VehicleTrack;
import com.ivp.xch.figure.form.VehicleTrackForm;
import com.ivp.xch.helper.JsonHelper;
import com.ivp.xch.map.LineStrack;
import com.ivp.xch.result.JsonResult;

@WebServlet(name = "VehicleTrackController", urlPatterns = {
    "/s/trk/s", "/s/v/trk", "/s/v/trk/test", "/s/v/trk/result"
})
public class VehicleTrackController extends BaseController {

    /**
     * 
     */
    private static final long serialVersionUID = 2023605294429663517L;


    // 查找所有的比赛轨迹
    @RequestURI(method = RequestMethod.GET, urlPatterns = "/s/trk/s")
    public void tracks() {
        EntityManager em = DBEM.em();
        String sql =
                " select t0.id,t0.gpsECode,t0.gpsEName,t0.vCode,t0.vName,t0.vLength,t0.vWidth  ,t0.testedTimes, "
                        + " t1.VTID,t1.VTTMS,t1.TVID,t1.VTSTIME,t1.VTETIME FROM ttesting_vehicle t0 RIGHT JOIN " +
                        " (select vt.id AS VTID ,vt.testingVehicle_id AS TVID ,vt.testingTimes AS  VTTMS , " +
                        " vt.startTrackTime AS VTSTIME, vt.stopTrackTime AS VTETIME " +
                        " from tvehicle_track vt group by vt.testingVehicle_id,vt.testingTimes) t1  on t0.id = t1.TVID ";
        List<Object[]> vtList = em.createNativeQuery(sql).getResultList();
        em.close();

        List<VehicleTrackForm> lst = new LinkedList<VehicleTrackForm>();

        for (int i = 0; i < vtList.size(); i++) {
            Object[] obj = vtList.get(i);
            VehicleTrackForm vt = new VehicleTrackForm();

            vt.setTestingVehicleID(Long.valueOf(obj[0].toString()));
            vt.setGpsECode(obj[1].toString());
            vt.setGpsEName(obj[2].toString());
            vt.setvCode(obj[3].toString());
            vt.setvName(obj[4].toString());
            vt.setvLength(Float.valueOf(obj[5].toString()));
            vt.setvWidth(Float.valueOf(obj[6].toString()));
            vt.setTestedTotalTimes(Long.valueOf(obj[7].toString()));

            vt.setTestedTimes(Long.valueOf(obj[9].toString()));

            vt.setVehicleID(Long.valueOf(obj[10].toString()));
            vt.setStartTrackTime(obj[11].toString());
            vt.setStopTrackTime(obj[12].toString());

            lst.add(vt);
        }

        String json = JsonHelper.toJson(JsonResult.getSuccessResult(lst, "查询成功"));
        result(json);

    }


    @RequestURI(method = RequestMethod.GET, urlPatterns = "/s/v/trk")
    public void vehicleTrackByTV() {

        String testingVehicleID = request.getParameter("tvID");
        String times = request.getParameter("tms");

        Long tvID = Long.valueOf(testingVehicleID);
        Long trackTimes = Long.valueOf(times);

        EntityManager em = DBEM.em();
        List<VehicleTrack> vtList = em.createQuery(" select vt from  VehicleTrack vt where vt.testingVehicle.id = :tvID and vt.testingTimes = :tktimes ", VehicleTrack.class)
                .setParameter("tvID", tvID).setParameter("tktimes", trackTimes).getResultList();
        em.close();

        Double[][] gsArr = new Double[vtList.size()][2];

        for (int i = 0; i < vtList.size(); i++) {
            VehicleTrack fg = vtList.get(i);
            gsArr[i] = new Double[] {
                fg.getLongitude(), fg.getLatitude()
            };
        }

        String json = JsonHelper.toJson(JsonResult.getSuccessResult(gsArr, "查询成功"));
        result(json);

    }


    @RequestURI(method = RequestMethod.GET, urlPatterns = "/s/v/trk/test")
    public void vehicleTrackTest() {

        String testingVehicleID = request.getParameter("tvID");
        String times = request.getParameter("tms");

        Long tvID = Long.valueOf(testingVehicleID);
        Long trackTimes = Long.valueOf(times);

        EntityManager em = DBEM.em();
        TestingVehicle tv = em.find(TestingVehicle.class, tvID);

        List<VehicleTrack> vtList = em.createQuery(" select vt from  VehicleTrack vt where vt.testingVehicle.id = :tvID and vt.testingTimes = :tktimes ", VehicleTrack.class)
                .setParameter("tvID", tvID).setParameter("tktimes", trackTimes).getResultList();
        em.close();


        Car car = new Car(tv.getvLength(), tv.getvWidth());
        CarTrack ctrack = CarTrack.getLineHelper(car);


        EntityManager em2 = DBEM.em();
        EntityTransaction transaction = em2.getTransaction();
        transaction.begin();
        em2.createQuery("  delete from  TrackResult  tr where  tr.testingVehicleID = :tvID and tr.testedTimes = :tms").setParameter("tvID", tvID).setParameter("tms", trackTimes)
                .executeUpdate();

        int bb = 0;
        for (int i = 0; i < vtList.size(); i++) {
            VehicleTrack ltrack = vtList.get(i);

            boolean aa = ctrack.isIntersect(ltrack.getLongitude(), ltrack.getLatitude(), ltrack.getHeading());

            if (aa) {
                bb++;
                TrackResult tr = new TrackResult();
                tr.setTestingVehicleID(tvID);
                tr.setvName(tv.getvName());
                tr.setTestedTimes(trackTimes);
                tr.setScore(-10f);
                tr.setNotes("压实现 扣5分");
                em2.persist(tr);
            }
        }

        if (bb == 0) {
            TrackResult tr = new TrackResult();
            tr.setTestingVehicleID(tvID);
            tr.setvName(tv.getvName());
            tr.setTestedTimes(trackTimes);
            tr.setScore(100);
            tr.setNotes("没有压线100分");
            em2.persist(tr);
        }

        transaction.commit();
        em2.close();

        // 压线次数
        String json = JsonHelper.toJson(JsonResult.getSuccessResult(bb, "测试完成"));
        result(json);


    }

    @RequestURI(method = RequestMethod.GET, urlPatterns = "/s/v/trk/result")
    public void vehicleTrackTestResult() {

        String testingVehicleID = request.getParameter("tvID");
        String times = request.getParameter("tms");

        Long tvID = Long.valueOf(testingVehicleID);
        Long trackTimes = Long.valueOf(times);

        EntityManager em = DBEM.em();
        List<TrackResult> trList = em.createQuery("  select tr  from  TrackResult  tr where  tr.testingVehicleID = :tvID and tr.testedTimes = :tms").setParameter("tvID", tvID)
                .setParameter("tms", trackTimes).getResultList();
        em.close();
        String json = JsonHelper.toJson(JsonResult.getSuccessResult(trList, "测试完成"));
        result(json);

    }
}
