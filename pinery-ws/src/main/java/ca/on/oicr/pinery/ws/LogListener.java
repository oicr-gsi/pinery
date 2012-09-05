package ca.on.oicr.pinery.ws;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LogListener implements ServletContextListener {
	
//	Logger log = LoggerFactory.getLogger(LogListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {

//		log.error("starting up yo");
		System.out.println("*********** start2");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
//		log.error("hello  shutting down yo");
		System.out.println("*********** end2");
//		LogManager.shutdown();
//		ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
//		if(loggerFactory instanceof LogManager) {
//			LogManager context = (LogManager) loggerFactory;
////			context.shutdown();
//			log.error("doing a shutdown");
//			LogManager.shutdown();
//		}
	}

}
