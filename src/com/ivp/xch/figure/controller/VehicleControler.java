package com.ivp.xch.figure.controller;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;

import com.ivp.xch.anotation.RequestURI;
import com.ivp.xch.anotation.RequestURI.RequestMethod;
import com.ivp.xch.controller.BaseController;
import com.ivp.xch.db.DBEM;
import com.ivp.xch.figure.entity.Vehicle;
import com.ivp.xch.figure.entity.VehicleTeam;
import com.ivp.xch.helper.JsonHelper;
import com.ivp.xch.result.JsonResult;


/**
 * 处理车辆的 Controller
 * 
 * @author hcx
 *
 */

@MultipartConfig
@WebServlet(name = "VehicleControler", urlPatterns = {
    "/s/vt/s", "/s/vt/add", "/s/vt/udt", "/s/vt/dlt", "/s/v/s", "/s/v/all", "/s/v/add", "/s/v/udt", "/s/v/dlt"
})
public class VehicleControler extends BaseController {

    /**
     * 
     */
    private static final long serialVersionUID = -415617214151334604L;



    /**
     * 获取所有车队信息
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.GET, urlPatterns = "/s/vt/s")
    public void vehicleTeams() {
        EntityManager em = DBEM.em();
        List<VehicleTeam> vtList =
                em.createQuery(" select vt from VehicleTeam  vt order by vt.id DESC", VehicleTeam.class)
                        .getResultList();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResult(vtList, "查询成功"));
        result(json);

    }

    /**
     * 添加车队
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/vt/add")
    public void createVehicleTeam() {
        // String vtID = request.getParameter("id");
        String vtName = request.getParameter("teamName");
        String vtCode = request.getParameter("teamCode");
        VehicleTeam vt = new VehicleTeam();
        vt.setTeamName(vtName);
        vt.setTeamCode(vtCode);
        try {
            Part part = request.getPart("logoFile");
            if (part.getSize() == 0) {} else {
                String pName = part.getName();
                // System.out.println("======================= pName " + pName);

                String fileHeader = part.getHeader("content-disposition");

                int startIndex = fileHeader.indexOf("filename");
                int endIndex = fileHeader.indexOf("\"", startIndex + 10);
                String docRealName = fileHeader.substring(startIndex + 10, endIndex);

                // String suffix = docRealName.substring(docRealName.lastIndexOf(".") + 1);

                // long partSize = part.getSize();
                String filePath = request.getServletContext().getRealPath("") + "/upload/logo/";
                // String fileName = "notify.file." + MyFileHelper.getCurrentFullTime() + "." + suffix;
                part.write(filePath + docRealName);

                vt.setLogoPath("/upload/logo/" + docRealName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        EntityManager em = DBEM.em();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(vt);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("添加成功"));
        result(json);
    }


    /**
     * 更新车队
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/vt/udt")
    public void updateVehicleTeam() {
        String vtID = request.getParameter("id");
        String vtName = request.getParameter("teamName");
        String vtCode = request.getParameter("teamCode");

        EntityManager em = DBEM.em();
        VehicleTeam vt = em.find(VehicleTeam.class, Long.valueOf(vtID));

        vt.setTeamName(vtName);
        vt.setTeamCode(vtCode);
        try {
            Part part = request.getPart("logoFile");
            if (part.getSize() == 0) {} else {
                String pName = part.getName();
                // System.out.println("======================= udt pName " + pName);

                String fileHeader = part.getHeader("content-disposition");

                int startIndex = fileHeader.indexOf("filename");
                int endIndex = fileHeader.indexOf("\"", startIndex + 10);
                String docRealName = fileHeader.substring(startIndex + 10, endIndex);

                // String suffix = docRealName.substring(docRealName.lastIndexOf(".") + 1);

                // long partSize = part.getSize();
                String filePath = request.getServletContext().getRealPath("") + "/upload/logo/";
                // String fileName = "notify.file." + MyFileHelper.getCurrentFullTime() + "." + suffix;
                part.write(filePath + docRealName);

                vt.setLogoPath("upload/logo/" + docRealName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(vt);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("修改成功"));
        result(json);
    }


    /**
     * 删除车队
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/vt/dlt")
    public void deleteVehicleTeam() {
        String vtID = request.getParameter("id");
        EntityManager em = DBEM.em();
        VehicleTeam vt = em.find(VehicleTeam.class, Long.valueOf(vtID));

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(vt);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("删除成功"));
        result(json);
    }



    /**
     * 获取所有车辆信息
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.GET, urlPatterns = "/s/v/s")
    public void vehicles() {
        String vtID = request.getParameter("vtID");
        Long teamID = Long.valueOf(vtID);

        EntityManager em = DBEM.em();
        List<Vehicle> vtList =
                em.createQuery(" select v from Vehicle  v  where v.team.id = :vtID order by v.id DESC", Vehicle.class)
                        .setParameter("vtID", teamID).getResultList();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResult(vtList, "查询成功"));
        result(json);

    }

    /**
     * 查询所有车辆，不分车队
     */
    @RequestURI(method = RequestMethod.GET, urlPatterns = "/s/v/all")
    public void vehicleAll() {

        EntityManager em = DBEM.em();
        List<Vehicle> vtList =
                em.createQuery(" select v from Vehicle  v order by v.id DESC", Vehicle.class).getResultList();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResult(vtList, "查询成功"));
        result(json);

    }


    /**
     * 添加车辆
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/v/add")
    public void createVehicle() {
        String vName = request.getParameter("vName");
        String vCode = request.getParameter("vCode");
        String vtID = request.getParameter("teamID");
        String vLength = request.getParameter("vLength");
        String vWidth = request.getParameter("vWidth");

        EntityManager em = DBEM.em();
        VehicleTeam vt = em.find(VehicleTeam.class, Long.valueOf(vtID));

        Vehicle v = new Vehicle();
        v.setTeamName(vt.getTeamName());
        v.setTeam(vt);

        v.setvName(vName);
        v.setvCode(vCode);
        v.setvLength(Float.valueOf(vLength));
        v.setvWidth(Float.valueOf(vWidth));

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(v);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("保存成功"));
        result(json);

    }


    /**
     * 更新车辆
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/v/udt")
    public void updateVehicle() {
        String vID = request.getParameter("id");
        String vName = request.getParameter("vName");
        String vCode = request.getParameter("vCode");
        String vLength = request.getParameter("vLength");
        String vWidth = request.getParameter("vWidth");

        EntityManager em = DBEM.em();

        Vehicle v = em.find(Vehicle.class, Long.valueOf(vID));
        v.setvName(vName);
        v.setvCode(vCode);
        v.setvLength(Float.valueOf(vLength));
        v.setvWidth(Float.valueOf(vWidth));

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(v);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("修改成功"));
        result(json);

    }


    /**
     * 删除车辆
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/v/dlt")
    public void deleteVehicle() {
        String vID = request.getParameter("id");
        EntityManager em = DBEM.em();
        Vehicle v = em.find(Vehicle.class, Long.valueOf(vID));


        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(v);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("删除成功"));
        result(json);
    }



}
