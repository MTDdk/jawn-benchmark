package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.javapla.jawn.core.db.DatabaseConnections.DatabaseConnection;
import net.javapla.jawn.core.exceptions.InitException;
import app.models.World;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DbManager {


    private final Connection conn;
//    private final PreparedStatement retrieveWorld;

    @Inject
    public DbManager(DatabaseConnection spec) throws ClassNotFoundException, SQLException {
        if (spec == null) throw new InitException("DatabaseConnection is null");
        
        Class.forName(spec.driver());
        conn = DriverManager.getConnection(spec.url(), spec.user(), spec.password());
        
//        retrieveWorld = conn.prepareStatement("SELECT id, randomNumber FROM World WHERE id = ?");
    }
    
    public World getWorld(int id) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT id, randomNumber FROM World WHERE id = ?");
            
            st.setInt(1, id);
//            retrieveWorld.setInt(1, id);
            try (ResultSet set = st.executeQuery()) {
                if (!set.next()) return null;
                
                return new World(set.getInt(1), set.getInt(2));
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
