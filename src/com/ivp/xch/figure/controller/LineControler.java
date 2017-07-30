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
import com.ivp.xch.map.FieldGPS;
import com.ivp.xch.map.LineStrack;
import com.ivp.xch.result.JsonResult;


/**
 * 测试用
 * 
 * @author hcx
 *
 */


@WebServlet(name = "LineControler", urlPatterns = {
    "/s/line/track"
})
public class LineControler extends BaseController {

    /**
     * 
     */
    private static final long serialVersionUID = 451561721415334604L;



    /**
     * 
     * 
     * @return
     */
    @RequestURI(method = RequestMethod.GET, urlPatterns = "/s/line/track")
    public void allRating() {
        EntityManager em = DBEM.em();
        List<LineStrack> pList = em.createQuery("  select f from  LineStrack  f where f.carID = 1  ", LineStrack.class).getResultList();
        em.close();

        Double[][] gsArr = new Double[pList.size()][2];

        for (int i = 0; i < pList.size(); i++) {
            LineStrack fg = pList.get(i);
            gsArr[i] = new Double[] {
                fg.getLongitude(), fg.getLatitude()
            };
        }

        String json = JsonHelper.toJson(JsonResult.getSuccessResult(gsArr, "查询成功"));
        result(json);
    }



}
