package com.w2.springtemplate.framework.netty.modjn.example;


import com.w2.springtemplate.framework.netty.modjn.ModbusClient;
import com.w2.springtemplate.framework.netty.modjn.ModbusServer;
import com.w2.springtemplate.framework.netty.modjn.entity.exception.ConnectionException;
import com.w2.springtemplate.framework.netty.modjn.entity.exception.ErrorResponseException;
import com.w2.springtemplate.framework.netty.modjn.entity.exception.NoResponseException;
import com.w2.springtemplate.framework.netty.modjn.entity.func.response.ReadCoilsResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ares
 */
public class Example {

    public static void main(String[] args) {
        ModbusServer modbusServer = ServerForTests.getInstance().getModbusServer();
        ModbusClient modbusClient = ClientForTests.getInstance().getModbusClient();

        ReadCoilsResponse readCoils = null;
        try {
            readCoils = modbusClient.readCoils(12321, 10);
        } catch (NoResponseException | ErrorResponseException | ConnectionException ex) {
            Logger.getLogger(Example.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
        }

        System.out.println(readCoils);

        modbusClient.close();
        modbusServer.close();
    }
}
