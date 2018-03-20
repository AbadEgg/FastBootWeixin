package com.mlnx.mp_server.server.initializer;

import com.mlnx.mp_server.handle.*;
import com.mlnx.mp_server.handle.cms.CmsServerHandle;
import com.mlnx.mp_server.handle.cms.CmsVerify;
import com.mlnx.qcms_server.server.initializer.CmsServerInitializer;

import io.netty.channel.socket.SocketChannel;

/**
 * Created by amanda.shan on 2018/2/12.
 */
public class MpCmsServerInitializer extends CmsServerInitializer {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        super.initChannel(ch);

        ch.pipeline().addLast(new CmsVerify());
        ch.pipeline().addLast(new CmsServerHandle());
        ch.pipeline().addLast(new RegisterHandle());
        ch.pipeline().addLast(new PushEcgHandle());
        ch.pipeline().addLast(new PushBpHandle());
        ch.pipeline().addLast(new PushSpoHandle());
        ch.pipeline().addLast(new PushTempHandle());
        ch.pipeline().addLast(new PushCO2Handle());
    }
}
