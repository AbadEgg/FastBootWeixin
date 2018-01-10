package com.mlnx.mp_server.server;

public interface Server {

	void start() throws Exception;

	void restart() throws Exception;

	void shutdown();
}
