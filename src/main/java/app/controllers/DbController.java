package app.controllers;

import java.util.concurrent.ThreadLocalRandom;

import com.google.inject.Inject;

import app.DbManager;
import net.javapla.jawn.core.AppController;

public class DbController extends AppController {

    private static final int NUMBER_OF_ROWS = 10_000;
    
    @Inject
    private DbManager db;
    
    public void index() {
        respond().json(db.getWorld(getRandomNumber()));
    }
    
    private int getRandomNumber() {
        return ThreadLocalRandom.current().nextInt(NUMBER_OF_ROWS) + 1;
    }
}
