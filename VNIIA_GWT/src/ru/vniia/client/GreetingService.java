package ru.vniia.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import controller.dto.Dto;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	String action(Dto dto);
	
	// https://varuntayur.wordpress.com/2012/01/25/session-management-in-gwt/
	// http://grokbase.com/t/gg/google-web-toolkit/12caqwbsmk/multiple-gwt-sessions-possible
	
	Dto login(Dto dto);
    Boolean loginFromServer(); // already logged, see cookies
    void logout(Dto dto);// close session if all tabs is closed
}
