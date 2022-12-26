package info.einverne.javalin;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.util.HashMap;

import static io.javalin.apibuilder.ApiBuilder.crud;
import static io.javalin.apibuilder.ApiBuilder.path;

/**
 * @author einverne
 * @since 2022-09-26
 */
public class Main {

  public static void main(String[] args) {
    Javalin app = createJavalinApp();

    appBasic(app);
    appRoutes(app);
    appIntercept(app);
    websocket(app);
    app.exception(Exception.class, (e, ctx) -> e.printStackTrace());

    app.start(7001);
  }

  private static Javalin createJavalinApp() {
    return Javalin.create(config -> config.staticFiles.add(userConfig -> {
      userConfig.hostedPath = "/assets";
      userConfig.directory = "/public";
      userConfig.location = Location.CLASSPATH;
      userConfig.precompress = true;
      userConfig.aliasCheck = null;
    }));
  }

  private static void appRoutes(Javalin app) {
    app.routes(() -> crud("users/{user-id}", new UserController()));

    app.routes(() -> path("url", () -> {
    }));
  }

  private static void appIntercept(Javalin app) {
    app.before("/query*", ctx -> System.out.println("before path query/*"));
    app.before(ctx -> System.out.println("something happened before"));
    app.after(ctx -> System.out.println("something happened after"));
  }

  private static void websocket(Javalin app) {
    app.ws("/websocket/", wsConfig -> {
      wsConfig.onConnect(ctx -> System.out.println("Connected"));
      wsConfig.onMessage(ctx -> {
        System.out.println("Received message: " + ctx.message());
        ctx.send("Hello from server");
      });
      wsConfig.onClose(ctx -> System.out.println("Closed"));
      wsConfig.onError(ctx -> System.out.println("Error"));
    });
  }

  private static void appBasic(Javalin app) {
    // plain get post
    app.get("/", ctx -> ctx.result("Hello World"));
    app.get("/query", ctx -> {
      String name = ctx.queryParam("name");
      var age = ctx.queryParamAsClass("age", Integer.class).getOrDefault(0);
      ctx.result("Hello " + name + ", age is " + age);
    });
    // path variable
    app.get("/path1/{name}", ctx -> {
      String name = ctx.pathParam("name");
      ctx.result("Hello " + name);
      System.out.println("do not allow slash");
    });
    app.get("/path2/<name>", ctx -> {
      String name = ctx.pathParam("name");
      ctx.result("Hello " + name);
      System.out.println("allow slash");
    });
    app.post("/post", ctx -> {
      String name = ctx.formParam("name");
      ctx.result("Hello " + name);
    });
    // put
    app.put("/put", ctx -> {
      String name = ctx.formParam("name");
      ctx.result("Hello " + name);
    });
    // delete
    app.delete("/delete", ctx -> {
      String id = ctx.formParam("id");
      ctx.result("delete " + id);
    });

    app.get("/json", ctx -> {
      HashMap<String, String> map = new HashMap<>(8);
      map.put("abc", "def");
      map.put("name", "g2");
      ctx.json(map);
    });
  }

}
