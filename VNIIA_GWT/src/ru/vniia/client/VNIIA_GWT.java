package ru.vniia.client;

import model.Chain;
import model.IResultJoiner;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.shell.CloseButton;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Tree;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;


//import com.sencha.gxt.widget.core.client.tree.Tree;
import java.util.HashSet; 

import controller.dto.Dto;
import controller.action.ACTION;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VNIIA_GWT implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again. ";
	
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = (GreetingServiceAsync) GWT
			.create(GreetingService.class);

	boolean logged = false;
	//TODO include IResultJoiner to ru/vniia/...xml
	final String MSYS_MARK = "2";
	final String UMR_MARK = "1";
	final String OTHER_MARK = "3";
	
	Dto model = new Dto();
	
		TextField login = new TextField();
		PasswordTextBox pass = new PasswordTextBox();
		//ListBox appList = new ListBox();
		Button loginBut = new Button("Войти");
		
		//Button updateBut = new Button("Обновить");
		//Button executeBut = new Button("Запустить");
		//Tree<M, C> t = new Tree<M, C>(store, valueProvider);
		Tree tree = new Tree();
		Set<Tree> trees = new HashSet<Tree>();
		
		Label loginLabel = new Label("Логин");
		Label passLabel = new Label("Пароль");

		Label textToServerLabel = new Label();
		TextArea serverResponseLabel = new TextArea();
		Button closeButton = new Button("Закрыть");
		DialogBox dialogBox = initDialog(textToServerLabel,
				serverResponseLabel,closeButton);
	{		
		model.id = -1;
		
		RootPanel.get("nameFieldContainer").add(login);

		RootPanel.get("nameFieldContainer").add(loginLabel);
		RootPanel.get("nameFieldContainer").add(pass);
		
		RootPanel.get("nameFieldContainer").add(passLabel);
		RootPanel.get("nameFieldContainer").add(tree);
		
		RootPanel.get("sendButtonContainer").add(loginBut);
		//RootPanel.get("sendButtonContainer").add(updateBut);
		//RootPanel.get("sendButtonContainer").add(executeBut);
	}
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		class Util{
			public void setVisible(boolean v,Dto model_){
//				installBut.setVisible(v);
				//updateBut.setVisible(v);
				//executeBut.setVisible(v);
				tree.setVisible(v);
				login.setVisible(!v);
				pass.setVisible(!v);
				if (!v){
					loginBut.setText("Войти");
					tree.clear();
					loginLabel.setText("Имя");
					passLabel.setText("Пароль");
				}
				else{
					loginBut.setVisible(false);
					if (model_.apps!=null){
/*						for(Integer i=0;i<model_.apps.size();i++){// keep sort format
							appList.addItem(model_.apps.get( new Integer(i+1).toString() ));
						}*/
						String prevSS = null;
						TreeItem prev = null;
						String mark = null;
						//TODO make application sorting use map <ss,app_sorted_list>
						//for (Iterator<String> iterator=model_.subsystem.iterator();iterator.hasNext();){ add trees
						for(Integer i=0;i<model_.apps.size();i++){
							//String ss = (String) iterator.next();
							String ss = model_.subsystem.get(String.valueOf(i+1));
							TreeItem item = null;
							if(prevSS==null||!prevSS.equalsIgnoreCase(ss)){ 
								prevSS = ss;
								item = new TreeItem();
								prev = item;
								//item.setStyleName("header");
								item.setText(ss);
								tree.addItem(item);
								//tree.add(t1);
							}else{
								item = prev;
								//item.setStyleName("header");
							}
							//for (Iterator<Integer> iterator1=app_sorted_list.iterator();iterator1.hasNext();){add items} }
							TreeItem item1 = new TreeItem();
							item.addItem(item1);
							String appName = (String) model_.apps.get(String.valueOf(i+1));
							//String mark = model_.marksystem.get(model.invApps.get(appName));
							mark = model_.marksystem.get(String.valueOf(i+1));
							if (mark==null)
								Window.alert("mark is null");
							if (mark.equalsIgnoreCase(MSYS_MARK))
								mark = "msysApp";
							else if (mark.equalsIgnoreCase(UMR_MARK))
								mark = "umrApp";
							else if (mark.equalsIgnoreCase(OTHER_MARK))
								mark = "otherApp";								
							//else
							//	Window.alert("such mark isn't exist");
							item1.setStyleName(mark);
							item1.setText(appName);
							////trees.add(t1);
						}
						for (int i=0; i<tree.getItemCount();i++){
							TreeItem item = tree.getItem(i);
							//item.getParentItem().setStyleName("header");//not work
							//if (i<2)
							//TODO apply medium 
								item.setStyleName("header");// apply to child too ?, 
							item.setState(true); // open top level items
							if (item.getText().indexOf("PDM")!=-1) //Управление данными об изделии
								item.setState(false);
						}
					}
					loginLabel.setText("");
					passLabel.setText("");
					login.clear();
					pass.setText("");
					model=model_;
				}
				logged = v;
			}
			public void action(final Dto dto){
				serverResponseLabel.setText("Ожидайте запуска...");
				//closeButton.setVisible(false);
				dialogBox.center();
				if (!dto.xmlName.isEmpty()){ 
					greetingService.action(dto, new AsyncCallback<String>() {						
						@Override
						public void onSuccess(String result) {
							if (result.equalsIgnoreCase("ok"))
								dialogBox.hide();
								//closeButton.setVisible(true);
								//Window.alert(""+dto.updatePath+" finished fine");
							else{
								dialogBox.hide();
								Window.alert(result);
							}
						}						
						@Override
						public void onFailure(Throwable caught) {
							dialogBox.hide();
							Window.alert(SERVER_ERROR+caught.getMessage());							
						}
					});
				}else{
					dialogBox.hide();
					Window.alert("updateDir is empty");
				}
			}
			public Dto setAction(ACTION a){
				Dto dto = new Dto();
				// invApps -> updateDir
				String idUpdateDir = model.invApps.get(tree.getSelectedItem().getText());
				//TODO remove next 2 lines
				if (idUpdateDir.equalsIgnoreCase("undefined")||idUpdateDir.equalsIgnoreCase("")){
					dialogBox.hide();
					Window.alert(idUpdateDir);
				}
				dto.xmlName=model.updater.get(idUpdateDir);
				dto.action = a;
				//dto.domain = Window.Location.getHostName();
				dto.id = model.id;
				dto.srv = model.srV.get(idUpdateDir);
				return dto;
			}
			public void logining(){
				final Dto dto = new Dto();
				dto.name = login.getText();
				dto.password = pass.getText();
						
				if (!logged){
					greetingService.login(dto, new AsyncCallback<Dto>() {						
						@Override
						public void onSuccess(Dto result) {
							//Window.alert(""+result);
							if (result!=null){
								setVisible(true,result);
							}else {
								// do not put dialogBox.hide();
								Window.alert("Неверное имя или пароль");
							}
						}
					@Override
					public void onFailure(Throwable caught) {
						//do not put 								dialogBox.hide();
						Window.alert(SERVER_ERROR+caught.getMessage());
					}
				});
				} else {	
					greetingService.logout(model,new AsyncCallback<Void>() {						
						@Override
						public void onSuccess(Void result) {
							setVisible(false,null);
						}
						@Override
						public void onFailure(Throwable caught) {
							//do not put 								dialogBox.hide();
							Window.alert(SERVER_ERROR+caught.getMessage());					
						}
					});
				}
			}
		}
		final Util util = new Util();
		util.setVisible(false,null);

		class LoginHandler implements ClickHandler {
			@Override
			public void onClick(ClickEvent event) {
				util.logining();
			}
		}
		class LoginEnterHandler implements KeyDownHandler {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode()==KeyCodes.KEY_ENTER)
					util.logining();
			}			
		}
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();				
			}
		});
		
/*		class UpdateHandler implements ClickHandler {
			@Override
			public void onClick(ClickEvent event) {
				final controller.dto.Dto dto = util.setAction(ACTION.UPDATE);
				util.action(dto);
			}
		}
		class ExecuteHandler implements ClickHandler {
			@Override
			public void onClick(ClickEvent event) {
				final controller.dto.Dto dto = util.setAction(ACTION.EXECUTE);
				util.action(dto);
			}
		}*/
		class ExecuteHandler implements SelectionHandler<TreeItem> {
			@Override
			public void onSelection(SelectionEvent<TreeItem> event){
				final controller.dto.Dto dto = util.setAction(ACTION.EXECUTE);
				util.action(dto);
			}
		}
		loginBut.addClickHandler(new LoginHandler());
		//loginBut.addKeyDownHandler(new LoginEnterHandler());
		LoginEnterHandler leh = new LoginEnterHandler();
		pass.addKeyDownHandler(leh);
		login.addKeyDownHandler(leh);
		//updateBut.addClickHandler(new UpdateHandler());
		tree.addSelectionHandler(new ExecuteHandler());
/*		for (Iterator iterator = trees.iterator(); iterator.hasNext();) {
			Tree type = (Tree) iterator.next();
			type.addSelectionHandler(new ExecuteHandler());
		}*/
		//executeBut.addClickHandler(new ExecuteHandler());
	}
	DialogBox initDialog(Label textToServerLabel,TextArea serverResponseLabel,Button closeButton){
		// Create the popup dialog box
		DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Процесс");
		dialogBox.setAnimationEnabled(true);
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		closeButton.setVisible(false);
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		//dialogVPanel.add(new HTML("<b>Отправлено на север:</b>"));
		dialogVPanel.add(textToServerLabel);
		//dialogVPanel.add(new HTML("<br><b>Ответ сервера:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		return dialogBox;
	}
}
