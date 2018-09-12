import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Onfon {
  private String name;
  private int id;

  public Onfon(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static List<Onfon> all() {
    String sql = "SELECT id, name FROM onfon";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Onfon.class);
    }
  }

  public int getId() {
    return id;
  }

 public static Onfon find(int id) {
     try(Connection con = DB.sql2o.open()) {
       String sql = "SELECT * FROM onfon where id=:id";
       Onfon onfon = con.createQuery(sql)
         .addParameter("id", id)
         .executeAndFetchFirst(Onfon.class);
       return onfon;
     }
   }

 public List<Client> getClients() {
   try(Connection con = DB.sql2o.open()) {
     String sql = "SELECT * FROM client where onfonId=:id";
     return con.createQuery(sql)
       .addParameter("id", this.id)
       .executeAndFetch(Client.class);
   }
 }

 @Override
 public boolean equals(Object otherOnfon) {
   if (!(otherOnfon instanceof Onfon)) {
     return false;
   } else {
     Onfon newOnfon = (Onfon) otherOnfon;
     return this.getName().equals(newOnfon.getName()) &&
            this.getId() == newOnfon.getId();
   }
 }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO onfon(name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

}