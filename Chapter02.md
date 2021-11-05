## 目标

- [x] 按照书上例子实现一个 EchoServer 和 EchoClient

## 编写 Echo 服务器

所有的 Netty 服务器都需要以下两部分：

- 至少一个 ChannelHandler - 该组件实现了服务器对从客户端接收的数据的处理，即它的业务逻辑。
- 引导 - 这是配置服务器的启动代码。它会将服务器绑定到它要监听连接请求的
端口上。

### ChannelHandler 和业务逻辑

[EchoServerHandler.java](/src/main/java/cn/dotleo/netty/chapter2/EchoServerHandler.java)

- 针对不同类型的事件来调用 ChannelHandler
- 应用程序通过实现或者扩展 ChannelHandler 来挂钩到事件的生命周期，并且提供自定义的应用程序逻辑
- 在架构上，ChannelHandler 有助于保持业务逻辑与网络处理代码的分离。这简化了开发过程，因为代码必须不断地演化以响应不断变化的需求。

### 引导服务器

引导服务器涉及以下内容：

- 绑定到服务器将在其上监听并接受传入连接请求的端口
- 配置 Channel，以将有关的入站消息通知给 EchoServerHandler 实例

[EchoServer.java](/src/main/java/cn/dotleo/netty/chapter2/EchoServer.java)

## 编写 Echo 客户端

如同服务器，客户端也需要 ChannelHandler 和引导客户端。

### ChannelHandler 和业务逻辑

[EchoClientHandler.java](/src/main/java/cn/dotleo/netty/chapter2/EchoClientHandler.java)

### 引导服务器

引导客户端类似于引导服务器，不同的是，客户端是使用主机和端口参数来连接远程地址，也就是这里的 Echo 服务器的地址。

以下是一个客户端引导服务器的要点：

- 为客户端初始化一个 Bootstrap 实例
- 为进行事件处理分配一个 NioEventLoopGroup 实例，其中事件处理包括创建新的连接以及处理入站和出站数据
- 为连接服务端创建一个 InetSocketAddress 实例
- 当连接被建立时，一个 EchoClientHandler 实例会被追加到（该 Channel 的) ChannelPipeline 中
- 在一切都设置完成后，调用 Bootstrap.connect() 方法连接到远程节点

[EchoClient.java](/src/main/java/cn/dotleo/netty/chapter2/EchoClient.java)

## 运行它们

至此，可以先运行 EchoServer，然后再运行EchoClient，可以在 Server 端看到输出 `Sever received: Netty rocks!`, Client 端看到输出 `Client received: Netty rocks!`。
