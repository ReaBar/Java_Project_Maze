package view;

import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import configuration.ServerProperties;

/**
 * The Class ServerGui.
 * 
 * server GUI window
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public class ServerGui extends Observable implements ServerView{

	/** The shell. */
	Shell shell;

	/** The display. */
	Display display;

	/** The Constant SERVER_PROPERTIES. */
	private static final String SERVER_PROPERTIES = "./Properties/serverProperties.xml";

	/** The server on. */
	private boolean serverOn = false;

	/** The commands array. */
	ArrayList<Object> commandsArray = new ArrayList<Object>();

	/** The solutions size. */
	List list,solutionsSize;

	/**
	 * Instantiates a new server GUI window.
	 */
	public ServerGui(){
		display=new Display();
		shell  = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);
		shell.setText("Server GUI");
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);

		Image image = new Image(display, "./Pictures/green_power_button.jpg");
		Image scaled = new Image(Display.getDefault(), 233, 100);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, 
				image.getBounds().width, image.getBounds().height, 
				0, 0, 200, 100);
		gc.dispose();
		image.dispose();

		image = new Image(display, "./Pictures/stop_button.jpg");
		Image scaled2 = new Image(Display.getDefault(), 233, 100);
		gc = new GC(scaled2);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, 
				image.getBounds().width, image.getBounds().height, 
				0, 0, 200, 100);
		gc.dispose();
		image.dispose();
		
		Button powerOnButton = new Button(shell, SWT.PUSH);
		powerOnButton.setImage(scaled);
		powerOnButton.setText("Power On");

		Button powerOffButton = new Button(shell, SWT.PUSH);
		powerOffButton.setImage(scaled2);
		powerOffButton.setText("Power off");

		powerOnButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ServerProperties p = setProperties();
				if(p == null){
					commandsArray.add("start");
					setChanged();
					notifyObservers(commandsArray);
					commandsArray.clear();
				}
				else{
					commandsArray.add("start");
					commandsArray.add(String.valueOf(p.getNumOfClients()));
					commandsArray.add(String.valueOf(p.getPort()));
					setChanged();
					notifyObservers(commandsArray);
					commandsArray.clear();
				}
				serverOn = true;
				powerOnButton.setEnabled(false);
			}


			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		powerOffButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				commandsArray.add("stop");
				setChanged();
				notifyObservers(commandsArray);
				commandsArray.clear();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		Composite composite2 = new Composite(shell, SWT.NULL);
		list = new List(composite2, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		GridLayout gridLayout2 = new GridLayout(1, false);
		gridLayout2.marginWidth = 0;
		gridLayout2.marginHeight = 0;
		gridLayout2.verticalSpacing = 0;
		gridLayout2.horizontalSpacing = 0;
		composite2.setLayout(gridLayout2);
		GridData gridData2 = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData2.heightHint = 9 * ((List)list).getItemHeight();
		gridData2.widthHint = 280;
		list.setLayoutData(gridData2);

		Composite composite = new Composite(shell, SWT.NULL);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		composite.setLayout(gridLayout);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		composite.setLayoutData(gridData);
		Button deleteClient = new Button(composite, SWT.PUSH);
		deleteClient.setText("Disconnect Client");

		list.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				deleteClient.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent arg1) {
						if(serverOn==true && list.getSelectionIndex() != -1){	
							commandsArray.add("deleteClient");
							commandsArray.add(list.getItem(list.getSelectionIndex()));
							list.remove(list.getSelectionIndex());
							setChanged();
							notifyObservers(commandsArray);		
						}
					}
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}	
				});
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Composite composite3 = new Composite(composite, SWT.NULL);
		new Label(composite3, SWT.LEFT | SWT.BORDER | SWT.BOLD).setText("Number of solutions cached:");
		GridLayout solutionsLayoutInfo = new GridLayout(2, false);
		composite3.setLayout(solutionsLayoutInfo);
		composite3.setLayoutData(gridData);
		solutionsSize = new List(composite3, SWT.NULL);
		GridLayout solutionsLayout = new GridLayout(1, false);
		solutionsLayout.marginWidth = 0;
		solutionsLayout.marginHeight = 0;
		solutionsLayout.verticalSpacing = 0;
		solutionsLayout.horizontalSpacing = 0;
	}

	/* (non-Javadoc)
	 * @see view.ServerView#solutionsSize(int)
	 */
	public void solutionsSize(int num){
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				solutionsSize.removeAll();
				solutionsSize.add(String.valueOf(num));
			}
		});
	}

	/* (non-Javadoc)
	 * @see view.ServerView#addToList(java.net.Socket)
	 */
	public void addToList(Socket socket){
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				list.add(socket.toString());
			}
		});
	}

	/* (non-Javadoc)
	 * @see view.ServerView#printMessage(java.lang.String)
	 */
	public void printMessage(String string){
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION| SWT.OK);
				messageBox.setText("Notification");
				messageBox.setMessage(string);
				messageBox.open();
			}
		});

	}

	/* (non-Javadoc)
	 * @see view.ServerView#Run()
	 */
	public void Run(){
		shell.setSize(637,300);
		shell.open();
				
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}
		display.dispose();
	}

	/**
	 * Sets the server properties.
	 *
	 * @return the server properties
	 */
	public ServerProperties setProperties(){
		ServerProperties p = new ServerProperties();
		Shell propertiesShell;
		File f = new File(SERVER_PROPERTIES);
		if(f.exists()){
			return null;
		}
		else{
			propertiesShell = new Shell(display);
			propertiesShell.setSize(400,400);
			propertiesShell.setText("Server Properties");
			GridLayout layout = new GridLayout(2, false);
			propertiesShell.setLayout(layout);
			new Label(propertiesShell, SWT.LEFT).setText("Number of Clients:");
			Text numOfClients = new Text(propertiesShell, SWT.BORDER);
			new Label(propertiesShell, SWT.LEFT).setText("Port numer:");
			Text portNum = new Text(propertiesShell,SWT.BORDER);

			Button setButton = new Button(propertiesShell, SWT.PUSH);
			setButton.setText("Set");
			setButton.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					p.setNumOfClients(Integer.valueOf(numOfClients.getText()));
					p.setPort(Integer.valueOf(portNum.getText()));
					propertiesShell.close();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {}
			});
		}
		propertiesShell.pack();
		propertiesShell.open();
		while(!propertiesShell.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}
		return p;
	}

	/* (non-Javadoc)
	 * @see view.ServerView#stopRunning()
	 */
	public void stopRunning() {
		MessageBox popMsg = new MessageBox(shell, SWT.ICON_INFORMATION);
		popMsg.setText("info!");
		popMsg.setMessage("A successful exit");
		popMsg.open();

		shell.close();
		display.close();
	}
}
