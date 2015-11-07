package boot;

import java.util.Observable;

import model.MyModel;
import presenter.Presenter;
import view.ServerGui;
import view.ServerView;

/**
 * The Class Driver.
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public class Driver {

	public static void main(String[] args) {
		MyModel model = new MyModel();	
		ServerView view = new ServerGui();
		Presenter presenter = new Presenter(view,model);
		((Observable) model).addObserver(presenter);
		((Observable) view).addObserver(presenter);

		view.Run();
	}
}
