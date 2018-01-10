package com.mlnx.mp_server.support;

import com.mlnx.mp_server.protocol.RegisterMessage;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by amanda.shan on 2017/12/19.
 */
public class Action {

    private ActionType actionType;

    private RegisterMessage registerMessage;

    private ChannelHandlerContext ctx;

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public RegisterMessage getRegisterMessage() {
        return registerMessage;
    }

    public void setRegisterMessage(RegisterMessage registerMessage) {
        this.registerMessage = registerMessage;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public enum ActionType {
        USR_REGISTER, ECG_DEVICE_REGISTER;
    }
}
