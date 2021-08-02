package io.github.yonsx.server.ws;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * WebSocketServer
 *
 * @author yakir on 2020/07/27 15:54.
 */
public class WebSocketServer {

    private final Integer port;

    private WebSocketServer(int port) {
        this.port = port;
    }

    public void start() {

        var bossGroup       = new NioEventLoopGroup(1);
        var workerGroup     = new NioEventLoopGroup();
        var serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(WebSocketServerInitializer.builder().build());

        try {

            var channel = serverBootstrap.bind(port)
                    .sync()
                    .channel();

            channel.closeFuture()
                    .sync();
        } catch (InterruptedException ignored) {
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }


    public static WebSocketServerBuilder builder() {

        return new WebSocketServerBuilder();

    }

    public static class WebSocketServerBuilder {

        private Integer port;

        public WebSocketServerBuilder port(Integer port) {
            this.port = port;
            return this;
        }

        public WebSocketServer build() {
            return new WebSocketServer(port);
        }

    }

}
