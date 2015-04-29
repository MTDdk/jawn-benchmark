package app;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.javapla.jawn.core.db.DatabaseConnection;
import net.javapla.jawn.core.exceptions.InitException;
import app.models.Fortune;
import app.models.World;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Singleton
public class DbManager {

    private ComboPooledDataSource source;

    @Inject
    public DbManager(DatabaseConnection spec) throws ClassNotFoundException, SQLException, PropertyVetoException {
        if (spec == null) throw new InitException("DatabaseConnection is null");
        
        source = new ComboPooledDataSource();
        source.setDriverClass(spec.driver());
        source.setJdbcUrl(spec.url());
        source.setUser(spec.user());
        source.setPassword(spec.password());
        
        source.setMaxPoolSize(100);
        source.setMinPoolSize(0);
        source.setAcquireIncrement(1);
        source.setIdleConnectionTestPeriod(300);
        source.setMaxStatements(0);
        
    }
    
    public World getWorld(int id) {
        try (Connection connection = source.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT id, randomNumber FROM World WHERE id = ?");
            statement.setInt(1, id);
            try (ResultSet set = statement.executeQuery()) {
                if (!set.next()) return null;
                
                return new World(set.getInt(1), set.getInt(2));
            }
        } catch (SQLException e) {
            return null;
        }
    }
    
    public boolean updateWorlds(World[] worlds) {
        try (Connection connection = source.getConnection()) {
            PreparedStatement update = connection.prepareStatement("UPDATE World SET randomNumber = ? WHERE id= ?");
            for (World world : worlds) {
                update.setInt(1, world.randomNumber);
                update.setInt(2, world.id);
                update.addBatch();
            }
            update.executeBatch();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public List<Fortune> fetchAllFortunes() {
        List<Fortune> list = new ArrayList<>();
        try (Connection connection = source.getConnection()) {
            PreparedStatement fetch = connection.prepareStatement("SELECT id, message FROM Fortune");
            ResultSet set = fetch.executeQuery();
            while (set.next()) {
                list.add(new Fortune(set.getInt(1), escape(set.getString(2))));
            }
        } catch (SQLException ignore) { }
        return list;
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
