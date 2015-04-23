package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.javapla.jawn.core.db.DatabaseConnections.DatabaseConnection;
import net.javapla.jawn.core.exceptions.InitException;
import app.models.Fortune;
import app.models.World;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DbManager {


    private final Connection conn;
    private final PreparedStatement retrieveWorld;
    private final PreparedStatement retrieveFortune;

    @Inject
    public DbManager(DatabaseConnection spec) throws ClassNotFoundException, SQLException {
        if (spec == null) throw new InitException("DatabaseConnection is null");
        
        Class.forName(spec.driver());
        conn = DriverManager.getConnection(spec.url(), spec.user(), spec.password());
        
        retrieveWorld   = conn.prepareStatement("SELECT id, randomNumber FROM World WHERE id = ?");
        retrieveFortune = conn.prepareStatement("SELECT id, message FROM Fortune");
    }
    
    public synchronized World getWorld(int id) {
        try {
            retrieveWorld.setInt(1, id);
            try (ResultSet set = retrieveWorld.executeQuery()) {
                if (!set.next()) return null;
                
                return new World(set.getInt(1), set.getInt(2));
            }
        } catch (SQLException e) {
            return null;
        }
    }
    
    public List<Fortune> fetchAllFortunes() {
        try (ResultSet set = retrieveFortune.executeQuery()) {
            List<Fortune> list = new ArrayList<>();
            while (set.next()) {
                list.add(new Fortune(set.getInt(1), escape(set.getString(2))));
            }
            return list;
        } catch (SQLException e) {
            return null;
        }
    }
    
    private static final String escape(String html) {
        StringBuilder bob = new StringBuilder();
        char[] arr = html.toCharArray();
        for (char c : arr) {
            switch(c) {
            case '<':
                bob.append("&lt;");
                break;
            case '>':
                bob.append("&gt;");
                break;
            case '&':
                bob.append("&amp;");
                break;
            case '"':
                bob.append("&quot;");
                break;
            default:
                bob.append(c);
            }
        }
        return bob.toString();
    }
}
