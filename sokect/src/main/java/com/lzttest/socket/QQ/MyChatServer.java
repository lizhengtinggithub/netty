package com.lzttest.socket.QQ;

import com.lzttest.socket.Utils.SerializableFactory4Marshalling;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyChatServer {

    /*//监听线程组，用来监听客户端请求*/
    public EventLoopGroup acceptorGroup = null;
   /* //处理线程组，用来处理客户端请求*/
    public  EventLoopGroup clientGroup = null;
    /* 服务启动相关配置信息*/
    public ServerBootstrap serverBootstrap = null;

    /**
     * 初始化，
     */
    public MyChatServer() {
        this.acceptorGroup = new NioEventLoopGroup();
        this.clientGroup =  new NioEventLoopGroup();
        this.serverBootstrap = new ServerBootstrap();
        this.serverBootstrap
                .group(this.acceptorGroup, this.clientGroup)   //绑定线程组
                .channel(NioServerSocketChannel.class)             //设置通信模式为NIO
                .childHandler(new ChannelInitializer<SocketChannel>(){
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(SerializableFactory4Marshalling.buildMarshallingDecoder());
                        pipeline.addLast(SerializableFactory4Marshalling.buildMarshallingEncoder());
                        /*pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
                        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));*/
                        pipeline.addLast(new MyChatServerHandler());
                    }
                });      // Handler: 设置数据的处理器.

    }

    /**
     * 温和的关闭通道，释放资源
     */
    public void shut(){
        this.acceptorGroup.shutdownGracefully();
        this.clientGroup.shutdownGracefully();
    }



}