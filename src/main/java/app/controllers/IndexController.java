package app.controllers;

import app.models.Message;
import net.javapla.jawn.core.AppController;

public class IndexController extends AppController {

    public void getBenchmark() {
        respond().json(new Message("Hello, World!"));
    }
}
