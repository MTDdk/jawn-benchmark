package app;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import net.javapla.jawn.server.RequestDispatcher;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;


public class JettyMain {
    
    public static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        System.setProperty("JAWN_ENV", "production");
        start();
    }
    
    private static void start() throws Exception {
        Server server = new Server(PORT);
        
        //context
        WebAppContext contextHandler = new WebAppContext();
        
        contextHandler.addFilter(RequestDispatcher.class, "/*", EnumSet.allOf(DispatcherType.class));
        
        contextHandler.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false"); //disables directory listing
        contextHandler.setContextPath("/");
        contextHandler.setBaseResource(Resource.newResource("src/main/webapp"));
        contextHandler.setParentLoaderPriority(true);
        contextHandler.configure();
        
        server.setHandler(contextHandler);
        
        server.start();
        server.join();
    }

}
