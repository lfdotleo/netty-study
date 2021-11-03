## 随笔

IO 优化的主思路：

- 一个线程控制一个 socket
- 一个线程轮询多个 socket
- 一个线程轮询多个已就绪的 socket

IO 优化的变量和不变量：

- 变量为线程，优化可以让线程数减少
- 不变量为 socket

## 章节

- [Chapter-01 Netty概述](/Chapter01.md)
- [Chapter-02 Netty Quick Start](/Chapter02.md)
