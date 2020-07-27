package io.github.yonsx.server.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * HttpEntry
 *
 * @author yakir <a href="yakirchen.github.io">yakirchen.github.io</a> on 2019/04/10 00:11.
 */
public class HttpEntry {

    public CompositeByteBuf allocate() {
        CompositeByteBuf msg       = Unpooled.compositeBuffer();
        ByteBuf          headerBuf = Unpooled.copiedBuffer("header", StandardCharsets.UTF_8);
        ByteBuf          bodyBuf   = Unpooled.copiedBuffer("body", StandardCharsets.UTF_8);
        msg.addComponents(headerBuf, bodyBuf);
        return msg;
    }
}
