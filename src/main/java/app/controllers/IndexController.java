package app.controllers;

import net.javapla.jawn.core.AppController;
import app.models.Message;

public class IndexController extends AppController {

    public void getJson() {
        respond().json(new Message("Hello, World!"));
    }
    
    public void getPlaintext() {
        respond().text("Hello, World!");
    }
}
