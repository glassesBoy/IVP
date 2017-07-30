package com.ivp.xch.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ivp.xch.anotation.RequestURI;
import com.ivp.xch.anotation.RequestURI.RequestMethod;


public class BaseController extends HttpServlet {

    protected HttpServletRequest request;
    protected HttpServletResponse response;


    public HttpServletRequest getRequest() {
        return request;
    }

    // public void setRequest(HttpServletRequest request) {
    // this.request = request;
    // }

    public HttpServletResponse getResponse() {
        return response;
    }

    // public void setResponse(HttpServletResponse response) {
    // this.response = response;
    // }
    /**
     * 
     */
    private static final long serialVersionUID = 8250262261425171251L;

    public BaseController() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        // System.out.println("========================= init ");
    }

    /**
     * @see Servlet#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
        // System.out.println("========================= destroy ");
    }

    /**
     * @see Servlet#getServletConfig()
     */
    public ServletConfig getServletConfig() {
        // TODO Auto-generated method stub
        // System.out.println("========================= getServletConfig ");
        return null;
    }

    /**
     * @see Servlet#getServletInfo()
     */
    public String getServletInfo() {
        // TODO Auto-generated method stub
        // System.out.println("========================= getServletInfo ");
        return null;
    }

    /**
     * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.request = request;
        this.response = response;
        request.setCharacterEncoding("UTF-8");
        
        
        // System.out.println("========================= BaseServlet service ");
        RequestMethod reMethod = RequestMethod.valueOf(request.getMethod());
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        // StringBuffer requestRUL = request.getRequestURL();

        // System.out.println("=============== reMethod " + reMethod);
        // System.out.println("=============== contextPath " + contextPath);
        // System.out.println("=============== requestURI " + requestURI);
        // System.out.println("=============== requestRUL " + requestRUL);
        String urlPattern = requestURI.replace(contextPath, "");
        // System.out.println("=============== urlPattern " + urlPattern);

        try {
            Method[] methods = this.getClass().getDeclaredMethods();
            // 这是要查找的方法，也是将要执行的方法
            Method targetMethod = null;
            for (Method method : methods) {
                // System.out.println("====== method Name " + method.getName());
                RequestURI reURI = method.getAnnotation(RequestURI.class);
                if (reURI == null) {
                    continue;
                }
                // 方法 处理GET 请求还是 POST 请求，现在暂时处理这两种，以后如果需要，再支持 PUT 和DELETE类型
                RequestMethod requestMethod = reURI.method();

                // 如果方法，要处理的请求方式跟 request中的方式一样，那么继续匹配 urlPattern
                if (requestMethod == reMethod) {
                    String[] urlPatterns = reURI.urlPatterns();
                    // System.out.println("============ requestMethod " + requestMethod);
                    for (String upatn : urlPatterns) {
                        // System.out.println("============ urlPatterns " + up);
                        if (upatn.equals(urlPattern)) {
                            targetMethod = method;
                            break;
                        }
                    }

                    // 如果没找到匹配的方法，那么继续找，
                    // 如果找到了，那么跳出循环
                    if (targetMethod == null) {
                        continue;
                    } else {
                        break;
                    }
                } else {
                    // 虽然这句话可以省略，但写一下，显得专业
                    continue;
                }
            }
            // 整个servlet 的方法都找完了，还是没有找到匹配的方法
            // 那么跳转到 error页面吧
            if (targetMethod == null) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/error.html");
                dispatcher.forward(request, response);
            } else {
                // Method method = this.getClass().getDeclaredMethod("login", new Class[] {
                // HttpServletRequest.class, HttpServletResponse.class
                // });
                // System.out.println("====== targetMethod Name " + targetMethod.getName());
                targetMethod.invoke(this);

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // response.getWriter().append("Served at: ").append(request.getContextPath());

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        // doGet(request, response);
        // System.out.println("========================= doPost ");
    }

    /**
     * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        // System.out.println("========================= doPut ");
    }

    /**
     * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        // System.out.println("========================= doDelete ");
    }

    /**
     * @see HttpServlet#doHead(HttpServletRequest, HttpServletResponse)
     */
    protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        // System.out.println("========================= doHead ");
    }

    /**
     * @see HttpServlet#doOptions(HttpServletRequest, HttpServletResponse)
     */
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        // System.out.println("========================= doOptions ");
    }

    /**
     * @see HttpServlet#doTrace(HttpServletRequest, HttpServletResponse)
     */
    protected void doTrace(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        // System.out.println("========================= doTrace ");
    }

    protected void result(String jsonString) {
        response.setCharacterEncoding("UTF-8");
        // response.setContentType("application/json; charset=utf-8");
        response.setContentType("text/html; charset=utf-8");

        try {
            response.getWriter().append(jsonString);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
