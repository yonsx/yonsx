package io.github.yonsx.server.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http2.Http2FrameCodecBuilder;
import io.netty.handler.ssl.ApplicationProtocolNames;
import io.netty.handler.ssl.ApplicationProtocolNegotiationHandler;

/**
 * Http2OrHttpHandler
 *
 * @author yakir on 2020/08/03 10:55.
 */
public class Http2OrHttpHandler extends ApplicationProtocolNegotiationHandler {

    private static final int MAX_CONTENT_LENGTH = 1024 * 100;

    protected Http2OrHttpHandler() {
        super(ApplicationProtocolNames.HTTP_1_1);
    }

    @Override
    protected void configurePipeline(ChannelHandlerContext ctx, String protocol) throws Exception {
        var channelPipeline = ctx.pipeline();
        if (ApplicationProtocolNames.HTTP_2.equals(protocol)) {
            channelPipeline.addLast(
                    Http2FrameCodecBuilder.forServer().build()
//                    new HelloWorldHttp2Handler()
            );
            return;
        }

        if (ApplicationProtocolNames.HTTP_1_1.equals(protocol)) {
            ctx.pipeline().addLast(new HttpServerCodec(),
                    new HttpObjectAggregator(MAX_CONTENT_LENGTH)
//                    new HelloWorldHttp1Handler("ALPN Negotiation")
            );
            return;
        }

        throw new IllegalStateException("unknown protocol: " + protocol);
    }
}
