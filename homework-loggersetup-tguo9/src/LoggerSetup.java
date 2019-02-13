import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerSetup {

	public static final Logger log = LogManager.getLogger(LoggerSetup.class);

	public static void main(String[] args) {
		log.trace("tucan");
		log.debug("dodo");
		log.info("ibis");
		log.warn("wren");
		log.error("egret", new Exception("urk"));
		log.fatal("finch", new Exception("ack"));
	}

}
