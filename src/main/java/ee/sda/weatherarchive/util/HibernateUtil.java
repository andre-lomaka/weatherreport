package ee.sda.weatherarchive.util;

import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HibernateUtil {

   private static EntityManager entityManager;
   private static EntityManagerFactory entityManagerFactory;

   public static EntityManager getEntityManager() {
      if (entityManager == null) {
         entityManagerFactory = Persistence.createEntityManagerFactory("WRDB");
         entityManager = entityManagerFactory.createEntityManager();
      }
      return entityManager;
   }

   public static void closeEntityManager() {
      if (entityManager != null) {
         entityManager.close();
         entityManagerFactory.close();
         entityManager = null;
      }
   }
}
