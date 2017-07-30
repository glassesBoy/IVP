package com.ivp.xch.figure.controller;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.annotation.WebServlet;
import com.ivp.xch.anotation.RequestURI;
import com.ivp.xch.anotation.RequestURI.RequestMethod;
import com.ivp.xch.controller.BaseController;
import com.ivp.xch.db.DBEM;
import com.ivp.xch.figure.entity.Rating;
import com.ivp.xch.helper.JsonHelper;
import com.ivp.xch.result.JsonResult;


/**
 * 打分标准 Controller
 * 
 * @author hcx
 *
 */


@WebServlet(name = "RatingControler", urlPatterns = {
    "/s/r/s", "/s/r/add", "/s/r/udt", "/s/r/dlt"
})
public class RatingControler extends BaseController {

    /**
     * 
     */
    private static final long serialVersionUID = -415617214151334604L;



    /**
     * 获取所有评分信息
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.GET, urlPatterns = "/s/r/s")
    public void allRating() {
        EntityManager em = DBEM.em();
        List<Rating> vtList =
                em.createQuery(" select r from Rating  r order by r.id DESC", Rating.class)
                        .getResultList();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResult(vtList, "查询成功"));
        result(json);
    }

    /**
     * 添加评分
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/r/add")
    public void createRating() {

        String rName = request.getParameter("ratingName");
        String rValue = request.getParameter("ratingValue");
        String rNotes = request.getParameter("notes");

        Rating r = new Rating();
        r.setRatingName(rName);
        r.setRatingValue(Float.valueOf(rValue));
        r.setNotes(rNotes);


        EntityManager em = DBEM.em();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(r);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("添加成功"));
        result(json);
    }


    /**
     * 更新评分
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/r/udt")
    public void updateRating() {

        String rID = request.getParameter("id");

        String rName = request.getParameter("ratingName");
        String rValue = request.getParameter("ratingValue");
        String rNotes = request.getParameter("notes");


        EntityManager em = DBEM.em();
        Rating r = em.find(Rating.class, Long.valueOf(rID));


        r.setRatingName(rName);
        r.setRatingValue(Float.valueOf(rValue));
        r.setNotes(rNotes);


        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(r);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("修改成功"));
        result(json);

    }


    /**
     * 删除评分
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.POST, urlPatterns = "/s/r/dlt")
    public void deleteRating() {
        String vtID = request.getParameter("id");
        EntityManager em = DBEM.em();
        Rating vt = em.find(Rating.class, Long.valueOf(vtID));

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(vt);
        transaction.commit();
        em.close();

        String json = JsonHelper.toJson(JsonResult.getSuccessResultMsg("删除成功"));
        result(json);
    }



}
