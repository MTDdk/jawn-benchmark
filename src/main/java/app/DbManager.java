package app;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import net.javapla.jawn.core.db.DatabaseConnections.DatabaseConnection;
import net.javapla.jawn.core.exceptions.InitException;
import app.models.World;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DbManager {

    private final Sql2o sql2o;

    @Inject
    public DbManager(DatabaseConnection spec) throws ClassNotFoundException {
        if (spec == null) throw new InitException("DatabaseConnection is null");
        
        Class.forName(spec.driver());
        sql2o = new Sql2o(spec.url(), spec.user(), spec.password());
    }
    
    public World getWorld(int id) {
        String sql = "SELECT id, randomNumber FROM World WHERE id = :id";
        
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql).addParameter("id", id).executeAndFetchFirst(World.class);
        }
    }
}
