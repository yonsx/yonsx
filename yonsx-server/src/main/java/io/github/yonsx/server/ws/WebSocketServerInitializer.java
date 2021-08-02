package io.github.yonsx.server.ws;

import io.github.yonsx.server.YonsxServerConst;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.ssl.SslContext;

import java.util.Objects;

/**
 * WebSocketServerInitializer
 *
 * @author yakir <a href="yakirchen.github.io">yakirchen.github.io</a> on 2019/04/10 17:47.
 */
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {

    private final String     chatPath;
    private final SslContext sslCtx;

    public static WebSocketServerInitializerBuilder builder() {
        return new WebSocketServerInitializerBuilder();
    }

    public WebSocketServerInitializer(String chatPath, SslContext sslCtx) {
        this.chatPath = chatPath;
        this.sslCtx   = sslCtx;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        var channelPipeline = ch.pipeline();

        if (sslCtx != null) {
            channelPipeline.addLast(sslCtx.newHandler(ch.alloc()));
        }

        channelPipeline
                // .addFirst(new SslHandler(....)))
                .addLast(
//                        new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS),
                        new HttpServerCodec(),
                        new HttpObjectAggregator(64 * 1024/*65536*/),
                        new WebSocketServerCompressionHandler(),
                        new WebSocketServerProtocolHandler(chatPath, null, true),
                        new WebSocketIndexPageHandler(chatPath),
                        new WebSocketFrameHandler()
                );
    }

    public static class WebSocketServerInitializerBuilder {

        private String     chatPath;
        private SslContext sslCtx;

        private WebSocketServerInitializerBuilder() {

        }

        public WebSocketServerInitializerBuilder chatPath(String chatPath) {
            if (Objects.isNull(chatPath) || chatPath.isBlank()) {
                this.chatPath = YonsxServerConst.WS_PATH;
            } else {
                this.chatPath = null;
            }
            return this;
        }

        public WebSocketServerInitializerBuilder sslCtx(SslContext sslCtx) {
            this.sslCtx = sslCtx;
            return this;
        }

        public WebSocketServerInitializer build() {
            return new WebSocketServerInitializer(chatPath, sslCtx);
        }

    }
}
