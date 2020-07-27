package io.github.yonsx;

import io.github.yonsx.server.ws.WebSocketServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * App
 *
 * @author yakir on 2020/07/23 21:53.
 */
public class App {

    private static final Logger log = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        log.info("Start Yonsx Server App !");
        WebSocketServer.builder()
                .port(8901)
                .build()
                .start();


    }

}
