package com.w2.springtemplate.framework.netty.modjn.handler;

import com.w2.springtemplate.framework.netty.modjn.ModbusServer;
import com.w2.springtemplate.framework.netty.modjn.entity.ModbusFrame;
import com.w2.springtemplate.framework.netty.modjn.entity.ModbusFunction;
import com.w2.springtemplate.framework.netty.modjn.entity.ModbusHeader;
import com.w2.springtemplate.framework.netty.modjn.entity.func.WriteSingleCoil;
import com.w2.springtemplate.framework.netty.modjn.entity.func.WriteSingleRegister;
import com.w2.springtemplate.framework.netty.modjn.entity.func.request.ReadCoilsRequest;
import com.w2.springtemplate.framework.netty.modjn.entity.func.request.ReadDiscreteInputsRequest;
import com.w2.springtemplate.framework.netty.modjn.entity.func.request.ReadHoldingRegistersRequest;
import com.w2.springtemplate.framework.netty.modjn.entity.func.request.ReadInputRegistersRequest;
import com.w2.springtemplate.framework.netty.modjn.entity.func.request.WriteMultipleCoilsRequest;
import com.w2.springtemplate.framework.netty.modjn.entity.func.request.WriteMultipleRegistersRequest;
import com.w2.springtemplate.framework.netty.modjn.entity.func.response.ReadCoilsResponse;
import com.w2.springtemplate.framework.netty.modjn.entity.func.response.ReadDiscreteInputsResponse;
import com.w2.springtemplate.framework.netty.modjn.entity.func.response.ReadHoldingRegistersResponse;
import com.w2.springtemplate.framework.netty.modjn.entity.func.response.ReadInputRegistersResponse;
import com.w2.springtemplate.framework.netty.modjn.entity.func.response.WriteMultipleCoilsResponse;
import com.w2.springtemplate.framework.netty.modjn.entity.func.response.WriteMultipleRegistersResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ares
 */
public abstract class ModbusRequestHandler extends SimpleChannelInboundHandler<ModbusFrame> {

    private static final Logger logger = Logger.getLogger(ModbusRequestHandler.class.getSimpleName());
    private ModbusServer server;

    public void setServer(ModbusServer server) {
        this.server = server;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning(cause.getLocalizedMessage());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        server.removeClient(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        server.addClient(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ModbusFrame frame) throws Exception {
        Channel channel = ctx.channel();

        ModbusFunction function = frame.getFunction();

        ModbusFunction response;

        logger.log(Level.FINER, function.toString());

        if (function instanceof WriteSingleCoil) {
            WriteSingleCoil request = (WriteSingleCoil) function;

            response = writeSingleCoil(request);
        } else if (function instanceof WriteSingleRegister) {
            WriteSingleRegister request = (WriteSingleRegister) function;

            response = writeSingleRegister(request);
        } else if (function instanceof ReadCoilsRequest) {
            ReadCoilsRequest request = (ReadCoilsRequest) function;

            response = readCoilsRequest(request);
        } else if (function instanceof ReadDiscreteInputsRequest) {
            ReadDiscreteInputsRequest request = (ReadDiscreteInputsRequest) function;

            response = readDiscreteInputsRequest(request);
        } else if (function instanceof ReadInputRegistersRequest) {
            ReadInputRegistersRequest request = (ReadInputRegistersRequest) function;

            response = readInputRegistersRequest(request);
        } else if (function instanceof ReadHoldingRegistersRequest) {
            ReadHoldingRegistersRequest request = (ReadHoldingRegistersRequest) function;

            response = readHoldingRegistersRequest(request);
        } else if (function instanceof WriteMultipleRegistersRequest) {
            WriteMultipleRegistersRequest request = (WriteMultipleRegistersRequest) function;

            response = writeMultipleRegistersRequest(request);
        } else if (function instanceof WriteMultipleCoilsRequest) {
            WriteMultipleCoilsRequest request = (WriteMultipleCoilsRequest) function;

            response = writeMultipleCoilsRequest(request);
        } else {
            throw new UnsupportedOperationException("Function not supported!");
        }

        ModbusHeader header = new ModbusHeader(
                frame.getHeader().getTransactionIdentifier(),
                frame.getHeader().getProtocolIdentifier(),
                response.calculateLength(),
                frame.getHeader().getUnitIdentifier());

        ModbusFrame responseFrame = new ModbusFrame(header, response);

        channel.write(responseFrame);
    }

    protected abstract WriteSingleCoil writeSingleCoil(WriteSingleCoil request);

    protected abstract WriteSingleRegister writeSingleRegister(WriteSingleRegister request);

    protected abstract ReadCoilsResponse readCoilsRequest(ReadCoilsRequest request);

    protected abstract ReadDiscreteInputsResponse readDiscreteInputsRequest(ReadDiscreteInputsRequest request);

    protected abstract ReadInputRegistersResponse readInputRegistersRequest(ReadInputRegistersRequest request);

    protected abstract ReadHoldingRegistersResponse readHoldingRegistersRequest(ReadHoldingRegistersRequest request);

    protected abstract WriteMultipleRegistersResponse writeMultipleRegistersRequest(WriteMultipleRegistersRequest request);

    protected abstract WriteMultipleCoilsResponse writeMultipleCoilsRequest(WriteMultipleCoilsRequest request);
}
