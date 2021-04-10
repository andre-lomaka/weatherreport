package ee.sda.weatherarchive.jpamodel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name = "cities")
public class JPACity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private int id;

   @Column(name = "city_name")
   private String name;

   public JPACity() {
   }

   public JPACity(int id, String name) {
      this.id = id;
      this.name = name;
   }

   public void setId(int value) {
      id = value;
   }

   public int getId() {
      return id;
   }

   public void setName(String value) {
      name = value;
   }

   public String getName() {
      return name;
   }
}
