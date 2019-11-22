package com.lzttest.socket.QQ;

import com.lzttest.socket.Utils.MessageFormat;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class MyChatClientHandler extends ChannelHandlerAdapter {


    /**
     *连接成功时，执行只执行一次
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //狱警上线后将自己的地址发给服务器，
        String msg = MessageFormat.getMessage(MessageFormat.KIND_YUJING, ctx.channel().localAddress().toString(), "连接成功");
        ctx.writeAndFlush(msg);
        System.out.println("我是狱警，使用的设备是" +ctx.channel().localAddress().toString() );

        //犯人上线后表明身份并将自己的地址发给服务器，
        /*String msg = MessageFormat.getMessage(MessageFormat.KIND_FANREN, ctx.channel().localAddress().toString(), "连接成功");
        System.out.println(msg);
        ctx.writeAndFlush(msg);
        System.out.println("我是犯人，使用的设备是" +ctx.channel().localAddress().toString() );*/


    }


    /**
     * 接收服务端信息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(msg.toString());
        String[] arr = MessageFormat.getArr((String) msg);

        if (arr[0].equals(MessageFormat.KIND_YUJING)) {  //来自狱警
            //犯人客户端根据狱警的命令，进行对应的操作
            System.out.println("来自狱警的命令");

        } else {                                        //来自犯人
            //狱警上线后，犯人将根据狱警提供的地址，发送请求过来。狱警根据请求中的地址，将处理结果发送给犯人（中间经过服务端）
            String message = MessageFormat.getMessage(MessageFormat.KIND_YUJING,arr[1],"处理结果");
            channel.writeAndFlush(message);
        }
    }




    /**
     * 异常捕捉机制
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }



}