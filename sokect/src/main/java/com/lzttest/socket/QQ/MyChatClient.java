package com.lzttest.socket.QQ;

import com.lzttest.socket.Utils.SerializableFactory4Marshalling;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyChatClient {
    /*处理请求和处理服务端响应的线程组*/
    public EventLoopGroup eventLoopGroup = null;
   /* 服务启动相关配置信息*/
    public Bootstrap bootstrap = null;

    /**
     *  初始化，配置一些设置
     */
    public MyChatClient( ) {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        /*绑定线程组*/
        bootstrap.group(eventLoopGroup);
        /*设置通信模式为NIO*/
        bootstrap.channel(NioSocketChannel.class);
        /*Handler: 设置数据的处理器.*/
        bootstrap.handler(new ChannelInitializer<SocketChannel>(){
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(SerializableFactory4Marshalling.buildMarshallingDecoder());
                pipeline.addLast(SerializableFactory4Marshalling.buildMarshallingEncoder());
              /*  pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
                pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));*/
                pipeline.addLast(new MyChatClientHandler());
            }
        });

    }


    /**
     * 温和的关闭连接通道
     */
    public void shut(){
        this.eventLoopGroup.shutdownGracefully();
    }



    public static void main(String[] args) {

        String str = "fdkj@123";
        String substring = str.substring(0, str.indexOf("@"));
        System.out.println(substring);


    }
}