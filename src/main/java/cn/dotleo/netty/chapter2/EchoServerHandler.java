package cn.dotleo.netty.chapter2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Echo 服务器
 *
 * 需要响应服务器传入的消息，所以需要实现 ChannelInboundHandler
 * ChannelInboundHandlerAdapter 是 ChannelInboundHandler 的一个默认实现，因为只需要用到少量的方法，所以选择继承该类
 *
 * @Shareable 标示一个 ChannelHandler 可以被多个 Channel 安全地共享
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 对于每个传入的消息都要调用
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Sever received: " + in.toString(CharsetUtil.UTF_8));
        // 将接收到的消息写到发送者，而不冲刷出站消息
        ctx.write(in);
    }

    /**
     *  通知 ChannelInboundHandler 最后一次对 channel-Read() 的调用是当前批量读取中的最后一条消息
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 将未决消息冲刷到远程节点，并且关闭该 Channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 在读取操作期间，有异常抛出时会调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 异常情况下，直接关闭该 Channel
        ctx.close();
    }
}
