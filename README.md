## 第一章

- [x] 手写一个 bio
- [ ] 手写一个 java nio
- [ ] 熟悉 java nio 中的概念

### Netty 的核心组件

#### Channel

Channel 是 Java NIO 的一个基本构造。 它代表一个到实体(如一个硬件设备、一个文件、一个网络套接字或者一个能够执行一个或者多个不同的I/O操作的程序组件)的开放连接，如读操作和写操作。

目前，可以把 Channel 看作是传入(入站)或者传出(出站)数据的载体。因此，它可以被打开或者被关闭，连接或者断开连接。

#### Callback

回调在广泛的编程场景中都有应用，而且也是在操作完成后通 知相关方最常见的方式之一。

Netty 在内部使用了回调来处理事件;当一个回调被触发时，相关的事件可以被一个 interface- ChannelHandler 的实现处理。

```java
public class ConnectHandler extends ChannelInboundHandlerAdapter { 
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client " + ctx.channel().remoteAddress() + " connected"); 
    }
}
```

当一个新的连接已经被建立时， ChannelHandler 的 channelActive()回调方法将会被调用，并将打印出一条信息。