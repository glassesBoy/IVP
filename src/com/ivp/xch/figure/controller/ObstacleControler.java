package com.ivp.xch.figure.controller;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.annotation.WebServlet;
import com.ivp.xch.anotation.RequestURI;
import com.ivp.xch.anotation.RequestURI.RequestMethod;
import com.ivp.xch.controller.BaseController;
import com.ivp.xch.db.DBEM;
import com.ivp.xch.figure.entity.Obstacle;
import com.ivp.xch.helper.JsonHelper;
import com.ivp.xch.result.JsonResult;


/**
 * 障碍物 Controller
 * 
 * @author hcx
 *
 */


@WebServlet(name = "ObstacleControler", urlPatterns = {
    "/s/obtl/s", "/s/obtl/add", "/s/obtl/udt", "/s/obtl/dlt"
})
public class ObstacleControler extends BaseController {

    /**
     * 
     */
    private static final long serialVersionUID = 115617214151334604L;



    /**
     * 获取所有障碍物信息
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.GET, urlPatterns = "/s/obtl/s")
    public void allObstacle() {
        EntityManager em = DBEM.em();
        List<Obstacle> vtList =
                em.createQuery(" select r from Obstacle  r order by r.id DESC", Obstacle.class)
                        .getResultList();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResult(vtList, "查询成功"));
        result(json);
    }

    /**
     * 添加障碍物
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/obtl/add")
    public void createObstacle() {

        String oName = request.getParameter("oName");
        String lng = request.getParameter("x");
        String lat = request.getParameter("y");
        String oNotes = request.getParameter("notes");

        Obstacle obtl = new Obstacle();

        obtl.setoName(oName);
        obtl.setX(Double.valueOf(lng));
        obtl.setY(Double.valueOf(lat));
        obtl.setNotes(oNotes);

        EntityManager em = DBEM.em();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(obtl);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("添加成功"));
        result(json);
    }


    /**
     * 更新障碍物
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/obtl/udt")
    public void updateObstacle() {

        String rID = request.getParameter("id");

        String oName = request.getParameter("oName");
        String lng = request.getParameter("x");
        String lat = request.getParameter("y");
        String oNotes = request.getParameter("notes");



        EntityManager em = DBEM.em();
        Obstacle obtl = em.find(Obstacle.class, Long.valueOf(rID));

        obtl.setoName(oName);
        obtl.setX(Double.valueOf(lng));
        obtl.setY(Double.valueOf(lat));
        obtl.setNotes(oNotes);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(obtl);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("修改成功"));
        result(json);

    }


    /**
     * 删除障碍物
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/obtl/dlt")
    public void deleteObstacle() {
        String vtID = request.getParameter("id");
        EntityManager em = DBEM.em();
        Obstacle vt = em.find(Obstacle.class, Long.valueOf(vtID));

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(vt);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("删除成功"));
        result(json);
    }



}
