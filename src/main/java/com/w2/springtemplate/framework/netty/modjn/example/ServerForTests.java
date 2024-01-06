package com.w2.springtemplate.framework.netty.modjn.example;

import com.w2.springtemplate.framework.netty.modjn.ModbusServer;
import com.w2.springtemplate.framework.netty.modjn.entity.exception.ConnectionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ares
 */
public class ServerForTests {

    private final ModbusServer modbusServer;

    private ServerForTests() {
        modbusServer = new ModbusServer(30502); //ModbusConstants.MODBUS_DEFAULT_PORT);
        try {
            modbusServer.setup(new ModbusRequestHandlerExample());
        } catch (ConnectionException ex) {
            Logger.getLogger(ServerForTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ModbusServer getModbusServer() {
        return modbusServer;
    }

    public static ServerForTests getInstance() {
        return ServerForTestsHolder.INSTANCE;
    }

    private static class ServerForTestsHolder {

        private static final ServerForTests INSTANCE = new ServerForTests();
    }
}
