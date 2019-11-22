package com.lzttest.socket;

import com.lzttest.socket.QQ.MyChatClient;
import io.netty.channel.ChannelFuture;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lzttest.socket.**.dao")
public class ClientApplication {

    public static void main(String[] args) {
       // SpringApplication.run(ClientApplication.class, args);

        ChannelFuture channelFuture = null;
        MyChatClient client = new MyChatClient();

        try {
            channelFuture = client.bootstrap.connect("127.0.0.1", 9999).sync();

     /*        Channel channel = channelFuture.channel();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "utf-8"));
           while (true) {
                channel.writeAndFlush(br.readLine() + "\r\n");
            }*/

            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shut();
        }

    }

}
