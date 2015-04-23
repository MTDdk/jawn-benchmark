package app.config;

import net.javapla.jawn.application.ApplicationRoutes;
import net.javapla.jawn.core.Router;
import app.controllers.IndexController;


public class Routing implements ApplicationRoutes {
    
    @Override
    public void router(Router router) {
        router.GET().route("/json").to(IndexController.class, "json");
        router.GET().route("/plaintext").to(IndexController.class, "plaintext");
    }

}
