package ru.vniia.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import controller.dto.Dto;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void action(Dto dto,AsyncCallback<String> callback);
	
	void login(Dto dto, AsyncCallback<Dto> callback);
    void loginFromServer(AsyncCallback<Boolean> callback); // already logged
    void logout(Dto dto, AsyncCallback<Void> callback);
}

