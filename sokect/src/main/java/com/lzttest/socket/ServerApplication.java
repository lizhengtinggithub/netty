package com.lzttest.socket;

import com.lzttest.socket.QQ.MyChatServer;
import io.netty.channel.ChannelFuture;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {

        ChannelFuture channelFuture = null;
        MyChatServer server = new MyChatServer();

        try {
            channelFuture = server.serverBootstrap.bind(9999).sync();
            System.out.println("server started.");
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.shut();
        }

    }

}
