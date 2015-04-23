package app.config;

import app.DbModule;
import net.javapla.jawn.application.ApplicationBootstrap;
import net.javapla.jawn.core.ConfigApp;

public class Bootstrap implements ApplicationBootstrap {

    @Override
    public void bootstrap(ConfigApp config) {
        config.registerModules(new DbModule());
    }

    @Override
    public void destroy() {}

}
