package app;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import net.javapla.jawn.server.RequestDispatcher;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;


public class JettyMain {
    
    public static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        // Automatically set environment to production
        // Framework defaults to development
        String environment = "production";
        if (args.length > 0) environment = args[0];
        System.setProperty("JAWN_ENV", environment);
        
        setupAndStartServer();
    }
    
    private static void setupAndStartServer() throws Exception {
        // Using thread pool for benchmark - this is not necessarily needed in ordinary setups
        // Normal use is just: 
        // Server server = new Server(PORT);
        QueuedThreadPool pool = new QueuedThreadPool(80, 10);
        Server server = new Server(pool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(PORT);
        server.setConnectors(new ServerConnector[] {connector});
        
        // Setup the server application
        WebAppContext contextHandler = new WebAppContext();
        contextHandler.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false"); //disables directory listing
        contextHandler.setContextPath("/");
        contextHandler.setBaseResource(Resource.newResource("src/main/webapp"));
        contextHandler.setParentLoaderPriority(true);
        
        // Add the framework
        contextHandler.addFilter(RequestDispatcher.class, "/*", EnumSet.allOf(DispatcherType.class));
        
        // Make the server use the framework
        server.setHandler(contextHandler);
        
        server.start();
        server.join();
    }
}
