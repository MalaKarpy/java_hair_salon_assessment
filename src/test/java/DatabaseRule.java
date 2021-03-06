import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    //$DB = new PDO('pgsql:host=localhost;dbname=shoes_test', 'chitra', '1234');
    //DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/best_restaurants_test","chitra","1234");
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test","null","null");
   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteStylistsQuery = "DELETE FROM stylists *;";
      String deleteClientsQuery = "DELETE FROM clients *;";

      con.createQuery(deleteStylistsQuery).executeUpdate();
      con.createQuery(deleteClientsQuery).executeUpdate();
    }
  }
}
