import java.util.Random;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.Map;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("stylists",Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/stylist-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String stylist_name = request.queryParams("stylist_name");
      Stylist newStylist = new Stylist(stylist_name);
      newStylist.save();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id/clients", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      List<Client> clientList = stylist.getClients();
      model.put("stylist", stylist);
      model.put("clientList", clientList);
      model.put("template", "templates/client-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/clients", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String client_name = request.queryParams("client_name");
      int stylist_id = Integer.parseInt(request.queryParams("stylist_id"));
      Client newClient = new Client(client_name,stylist_id);
      newClient.save();
      Stylist stylist = Stylist.find(stylist_id);
      List<Client> client_list = stylist.getClients();
      model.put("clients", client_list);
      model.put("stylist", stylist);
      model.put("template", "templates/client-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clientslist", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("clients",Client.all());
      model.put("template", "templates/clients-list.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());










}
}
