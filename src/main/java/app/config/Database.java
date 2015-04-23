package app.config;

import net.javapla.jawn.application.ApplicationDatabaseBootstrap;
import net.javapla.jawn.core.Modes;
import net.javapla.jawn.core.db.DatabaseConnections;

public class Database implements ApplicationDatabaseBootstrap {

    @Override
    public void dbConnections(DatabaseConnections connections) {
        
        String jdbcParams = "jdbcCompliantTruncation=false&elideSetAutoCommits=true" +
                "&useLocalSessionState=true" +
                "&cachePrepStmts=true" +
                "&cacheCallableStmts=true" +
                "&alwaysSendSetIsolation=false" +
                "&prepStmtCacheSize=4096" +
                "&cacheServerConfiguration=true" +
                "&prepStmtCacheSqlLimit=2048" +
                "&zeroDateTimeBehavior=convertToNull" +
                "&traceProtocol=false" +
                "&useUnbufferedInput=false" +
                "&useReadAheadInput=false" +
                "&maintainTimeStats=false" +
                "&useServerPrepStmts" +
                "&cacheRSMetadata=true";
        
        connections
            .environment(Modes.prod)
            .jdbc()
            .driver("com.mysql.jdbc.Driver")
            .url("jdbc:mysql://localhost/hello_world?" + jdbcParams)
            .user("benchmarkdbuser")
            .password("benchmarkdbpass");
        
        connections
            .environment(Modes.dev)
            .jdbc()
            .driver("com.mysql.jdbc.Driver")
            .url("jdbc:mysql://192.168.100.11/hello_world?" + jdbcParams)
            .user("benchmarkdbuser")
            .password("benchmarkdbpass");
    }

}
