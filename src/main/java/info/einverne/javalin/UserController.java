package info.einverne.javalin;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a CURD handler for User
 * @author einverne
 */
public class UserController implements CrudHandler {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public void create(@NotNull Context context) {
    logger.info("create");
    context.result("create");
  }

  @Override
  public void delete(@NotNull Context context, @NotNull String s) {
    logger.info("delete");
  }

  @Override
  public void getAll(@NotNull Context context) {

    logger.info("getAll");
  }

  @Override
  public void getOne(@NotNull Context context, @NotNull String s) {

    logger.info("getOne");
    context.result("getOne");
  }

  @Override
  public void update(@NotNull Context context, @NotNull String s) {

    logger.info("update");
  }
}
