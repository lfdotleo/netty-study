## 目标

- [x] 手写一个 bio
- [ ] 手写一个支持并发的 bio
- [ ] 熟悉 java nio 中的概念
- [ ] 手写一个 java nio
- [ ] 熟悉 netty 核心概念

## Netty 的核心组件

### Channel

Channel 是 Java NIO 的一个基本构造。 它代表一个到实体(如一个硬件设备、一个文件、一个网络套接字或者一个能够执行一个或者多个不同的 I/O 操作的程序组件)的开放连接，如读操作和写操作。

目前，可以把 Channel 看作是传入(入站)或者传出(出站)数据的载体。因此，它可以被打开或者被关闭，连接或者断开连接。

### Callback

回调在广泛的编程场景中都有应用，而且也是在操作完成后通 知相关方最常见的方式之一。

Netty 在内部使用了回调来处理事件; 当一个回调被触发时，相关的事件可以被一个 interface- ChannelHandler 的实现处理。

```java
public class ConnectHandler extends ChannelInboundHandlerAdapter { 
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client " + ctx.channel().remoteAddress() + " connected"); 
    }
}
```

当一个新的连接已经被建立时， ChannelHandler 的 channelActive()回调方法将会被调用，并将打印出一条信息。

### Future

Future 提供了另一种在操作完成时通知应用程序的方式。这个对象可以看作是一个异步操作的结果的占位符; 它将在未来的某个时刻完成，并提供对其结果的访问。

JDK 预置了 interface java.util.concurrent.Future，但是其所提供的实现，只允许手动检查对应的操作是否已经完成，或者一直阻塞直到它完成。这是非常繁琐的，所以 Netty 提供了它自己的实现 —— ChannelFuture，用于在执行异步操作的时候使用。

ChannelFuture 提供了几种额外的方法，这些方法使得我们能够注册一个或者多个 ChannelFutureListener 实例。监听器的回调方法 operationComplete()，将会在对应的操作完成时被调用。然后监听器可以判断该操作是成功地完成了还是出错了。如果是后者，我们可以检索产生的 Throwable。简而言之，由 ChannelFutureListener 提供的通知机制消除了手动检查对应的操作是否完成的必要。


