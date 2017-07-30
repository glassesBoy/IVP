package com.ivp.xch.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContextType;

import org.hibernate.ejb.EntityManagerImpl;

/**
 * 获取 数据库连接的工具类
 * 
 * @author hcx
 *
 */
public class DBEM {

    // private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(50);


    // private static EntityManagerFactory emf = null;
    //
    // public static EntityManager getEM(String persistenceUnit) {
    // /*
    // * 1、获取EntityManagerFactory实例
    // * 利用Persistence类的静态方法，结合persistence.xml中
    // * persistence-unit标签的name属性值得到
    // */
    // if (emf == null || !emf.isOpen()) {
    // emf = Persistence.createEntityManagerFactory(persistenceUnit);
    // }
    // //emf.close();
    // // 2、获取EntityManager实例
    // EntityManager em = emf.createEntityManager();
    //
    // return em;
    // }

    // public static EntityManager getEM() {
    // if (em == null) {
    // em = getEM("defaultPersistenceUnit");
    // }
    // return em;
    // }

    // public static EntityManager em() {
    // return getEM("defaultPersistenceUnit");
    // }



    // 实体化私有静态实体管理器变量emf
    private static final EntityManagerFactory emf;
    // 实体化私有静态本地线程变量threadLocal
    private static final ThreadLocal<EntityManager> threadLocal;

//    // 用来给两个变量赋初值的静态块
    static {
        emf = Persistence.createEntityManagerFactory("defaultPersistenceUnit");
        threadLocal = new ThreadLocal<EntityManager>();
    }

    // 得到实体管理器的方法
    public static EntityManager em() {
//         if (emf == null || !emf.isOpen()) {
//           emf = Persistence.createEntityManagerFactory(persistenceUnit);
//         }
        EntityManager manager = threadLocal.get();
        if (manager == null || !manager.isOpen()) {
            manager = emf.createEntityManager();
            threadLocal.set(manager);
        }
        return manager;
    }

    // 关闭实体管理器的方法
    public static void closeEM() {
        EntityManager em = threadLocal.get();
        threadLocal.set(null);
        if (em != null) {
            em.close();
        }
    }

//    // 开始事务的方法
//    public static void beginTransaction() {
//        em().getTransaction().begin();
//    }
//
//    // 提交事务的方法
//    public static void commit() {
//        em().getTransaction().commit();
//    }
//
//    // 回滚事务的方法
//    public static void rollback() {
//        em().getTransaction().rollback();
//    }
    // 生成查找的方法
    // public static Query createQuery(String query) {
    // return getEntityManager().createQuery(query);
    // }
    // public static void log(String string, Level info, Object object)
    // {
    // // TODO Auto-generated method stub
    // }
}
