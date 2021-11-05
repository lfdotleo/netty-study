package cn.dotleo.netty.chapter2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Echo 客户端
 */
public class EchoClient {

    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建 Bootstrap
            Bootstrap b = new Bootstrap();
            // 指定 EventLoopGroup 以处理客户端事件；需要适用于 NIO 的实现
            b.group(group)
                    // 适用于 NIO 传输的 Channel 类型
                    .channel(NioSocketChannel.class)
                    // 设置服务端连接地址
                    .remoteAddress(new InetSocketAddress(host, port))
                    // 在创建 Channel 时向 ChannelPipeline 中添加一个 EchoClientHandler 实例
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            // 追加 ChannelHandler 到（该 Channel 的) ChannelPipeline 中
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });

            // 连接到远程节点，阻塞等待直到连接完成
            ChannelFuture f = b.connect().sync();
            //  阻塞，直到 Channel 关闭
            f.channel().closeFuture().sync();
        } finally {
            // 关闭线程池并且释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoClient("localhost", 2222).start();
    }
}
