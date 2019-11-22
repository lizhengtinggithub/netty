package com.lzttest.socket.QQ;

import com.lzttest.socket.Utils.MessageFormat;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyChatServerHandler extends ChannelHandlerAdapter {
    /*通道组*/
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private String yujingAddress = "";







    /**
     * 	业务处理逻辑
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("message: "+ msg.toString());
        String[] arr = MessageFormat.getArr((String) msg);
        for (String s : arr) {
            System.out.println(s);
        }
        if (arr[0].equals(MessageFormat.KIND_YUJING)) {  //狱警
            //保存狱警地址
            yujingAddress = arr[1];

        } else {                                         //犯人
           //将信息转发给狱警客户端
            boolean b = true;
            while (true) {
                //如果狱警客户端，未上线（狱警未同意申请），等待，直到狱警上线 或 犯人设备倒计时结束犯人客户端断开连接
                if (!yujingAddress.equals("")) {
                    //一旦狱警上线，将接收到犯人发送到请求（犯人客户端地址）
                    for (Channel channel : channelGroup) {
                        if ( channel.remoteAddress().toString().equals(yujingAddress) ){
                            channel.writeAndFlush(msg.toString());
                        }
                    }
                    b = false;
                }
                System.out.println("等待中。。。。");
                Thread.sleep(5000);

            }

        }
    }


    /**
     * 新建连接 处理程序，当有新客户端与客户端建立连接时执行,只执行一次
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "上线\n");
    }


    /**
     * 断开连接 处理程序，当有新客户端与客户端断开连接时执行
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        if (channel.remoteAddress().toString().equals(yujingAddress)) {
            yujingAddress = "";
            System.out.println(channel.remoteAddress() + "狱警下线\n");
        } else {
            System.out.println(channel.remoteAddress() + "下线\n");
        }

    }

    /**
     * 新建连接 处理程序，当有新客户端与客户端建立连接时执行，把这条“连接”（通道）加入通道组
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.add(channel);
    }


    /**
     * 异常扑捉机制
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}



/**
* 我的程序有一个websocket服务器.服务器以两种方式捕获客户端的连接：’handlerAdded’和’channelActive’.当Web客户端到来时我应该使用哪个？

 最佳答案 建议使用ChannelActive,而在服务器上下文中,handlerAdded和channelActive会相互调用,当您将处理程序移植到客户端时,在调用channelActive之前将调用handlerAdded,从而导致代码出错,因为写入不会直到通道活跃.
 在handlerAdded中：channel().isActive()是true或false
 在channelActive中：channel().isActive()始终为true *
 当channel().isActive()为true时,您只能向另一方发送消息.

* */