# swoole-java-client
swoole-java-client(use netty 4)

公司主 PHP 语言，服务全都是使用了 Swoole，我所在的项目是 Java 编写的，难免需要用 Java 调用 Swoole 服务。所以才有了这个项目。

第一个版本是 Leader 写的，他是 PHP 高级工程师，感谢。后来发现有些问题，所以我重构了现在的版本，主要重构了 TCP 连接以及数据发送接收等部分。

重构的部分基本照抄了 [NettyRpc](https://github.com/luxiaoxun/NettyRpc) 在此感谢。

 [NettyRpc](https://github.com/luxiaoxun/NettyRpc) 对于新手而言，很容易理解和上手，当时重构的时候准备参照 Dubbo ，但是 Dubbo 使用的是 Netty3，和 Netty 4 的差异太大，然后正好看到了 NettyRpc 这个项目，发现非常适合当时的情况，于是就花了点时间改造成现在的样子了，再次感谢 NettyRpc 。
 
 目前能够在生产环境中稳定运行，不过代码还是比较糟糕的。
 
 如果有人需要的话，可以继续优化优化吧，因为一些原因，本项目应该是不会再有大的更新了。
 
 
 
