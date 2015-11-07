package boot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Observable;

import gui.MazeWindow;
import model.ClientModel;
import model.Model;
import presenter.Presenter;
import view.MyViewCLI;
import view.View;

/**
 * The Class Run.
 * 
 * @author Rea Bar and Tom Eileen Hirsc
 */
public class Run {

	public static void main(String[] args) throws IOException{

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(System.out, true);
		View view = null;
		Model model = new ClientModel();
		view = new MazeWindow("Java project", 800, 530);
		Presenter presenter = new Presenter(view, model);
		((Observable) model).addObserver(presenter);
		((Observable) view).addObserver(presenter);

		view.start();
		if(view.whatKindOfView().equals("CLI")){
			view = new MyViewCLI(writer, reader);
			Presenter presenter2 = new Presenter(view, model);

			 ((Observable) view).addObserver(presenter2);
			 ((Observable) model).deleteObserver(presenter);
			 ((Observable) model).addObserver(presenter2);
			 view.start();
		 }

	}
}
