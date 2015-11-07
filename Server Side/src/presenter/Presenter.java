package presenter;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import model.MyModel;
import view.ServerView;

/**
 * The Class Presenter.
 * 
 * Responsible for communication model - view
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public class Presenter implements Observer{

	/** The model. */
	MyModel model;

	/** The view. */
	ServerView view;

	/**
	 * Instantiates a new presenter.
	 *
	 * @param view the view
	 * @param model the model
	 */
	public Presenter(ServerView view, MyModel model) {
		this.view = view;
		this.model = model;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {	
		if(o instanceof ServerView){
			String command = ((ArrayList<String>) arg).get(0);
			switch (command) {
			case "start":
				if(((ArrayList<String>)arg).size() > 1){
					model.start(Integer.valueOf(((ArrayList<String>) arg).get(1)), Integer.valueOf(((ArrayList<String>) arg).get(2)));
				}
				else{
					model.start();
				}
				break;
			case "stop":
				model.shutDown();
				break;
			case "deleteClient":
				model.deleteClient(((ArrayList<String>)arg).get(1));
				break;
			}
		}

		else if(o instanceof MyModel){
			if(arg instanceof String){
				if(((String)arg).equals("stop")){
					view.stopRunning();
				}
				else{
					view.printMessage((String)arg);

				}
			}
			else if(arg instanceof Socket){
				view.addToList((Socket)arg);
			}
			else if(arg instanceof Integer){
				view.solutionsSize((int)arg);
			}
		}
	}

}
