package io.github.yonsx.server.ws;

import io.github.yonsx.server.YonsxServerConst;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;

/**
 * WebSocketServerInitializer
 *
 * @author yakir <a href="yakirchen.github.io">yakirchen.github.io</a> on 2019/04/10 17:47.
 */
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {

    private final String chatPath;

    public WebSocketServerInitializer() {
        this.chatPath = YonsxServerConst.WS_PATH;
    }

    public WebSocketServerInitializer(String chatPath) {
        this.chatPath = chatPath;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        var channelPipeline = ch.pipeline();
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
}
