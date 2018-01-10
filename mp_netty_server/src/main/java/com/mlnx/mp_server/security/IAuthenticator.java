package com.mlnx.mp_server.security;

public interface IAuthenticator {

	boolean checkValid(String username, byte[] password);

	boolean checkValid(String deviceId);
}
