package app.config;

import net.javapla.jawn.core.Router;
import net.javapla.jawn.core.spi.ApplicationRoutes;
import app.controllers.DbController;
import app.controllers.IndexController;


public class Routing implements ApplicationRoutes {
    
    @Override
    public void router(Router router) {
        router.GET().route("/json").to(IndexController.class, "json");
        router.GET().route("/plaintext").to(IndexController.class, "plaintext");
        router.GET().route("/queries").to(DbController.class, "queries");
        router.GET().route("/updates").to(DbController.class, "updates");
    }

}
