package com.ivp.xch.figure.controller;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.annotation.WebServlet;
import com.ivp.xch.anotation.RequestURI;
import com.ivp.xch.anotation.RequestURI.RequestMethod;
import com.ivp.xch.controller.BaseController;
import com.ivp.xch.db.DBEM;
import com.ivp.xch.figure.entity.GPSEquipment;
import com.ivp.xch.helper.JsonHelper;
import com.ivp.xch.result.JsonResult;


/**
 * GPS设备Controller
 * 
 * @author hcx
 *
 */


@WebServlet(name = "GPSEquipmentControler", urlPatterns = {
    "/s/gpse/s", "/s/gpse/add", "/s/gpse/udt", "/s/gpse/dlt"
})
public class GPSEquipmentControler extends BaseController {

    /**
     * 
     */
    private static final long serialVersionUID = -415617214151334604L;



    /**
     * 获取所有gps设备信息
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.GET, urlPatterns = "/s/gpse/s")
    public void allGPSEquipment() {
        EntityManager em = DBEM.em();
        List<GPSEquipment> vtList =
                em.createQuery(" select r from GPSEquipment  r order by r.id DESC", GPSEquipment.class)
                        .getResultList();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResult(vtList, "查询成功"));
        result(json);
    }

    /**
     * 添加gps设备
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/gpse/add")
    public void createGPSEquipment() {

        String eName = request.getParameter("eName");
        String eCode = request.getParameter("eCode");
        String eNotes = request.getParameter("notes");

        GPSEquipment gpse = new GPSEquipment();

        gpse.seteName(eName);
        gpse.seteCode(eCode);
        gpse.setNotes(eNotes);

        EntityManager em = DBEM.em();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(gpse);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("添加成功"));
        result(json);
    }


    /**
     * 更新gps设备
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/gpse/udt")
    public void updateGPSEquipment() {

        String rID = request.getParameter("id");

        String eName = request.getParameter("eName");
        String eCode = request.getParameter("eCode");
        String eNotes = request.getParameter("notes");

        EntityManager em = DBEM.em();
        GPSEquipment gpse = em.find(GPSEquipment.class, Long.valueOf(rID));

        gpse.seteName(eName);
        gpse.seteCode(eCode);
        gpse.setNotes(eNotes);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(gpse);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("修改成功"));
        result(json);

    }


    /**
     * 删除gps设备
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/gpse/dlt")
    public void deleteGPSEquipment() {
        String vtID = request.getParameter("id");
        EntityManager em = DBEM.em();
        GPSEquipment vt = em.find(GPSEquipment.class, Long.valueOf(vtID));

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(vt);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("删除成功"));
        result(json);
    }



}
