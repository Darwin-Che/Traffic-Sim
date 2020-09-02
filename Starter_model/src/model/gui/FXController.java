package model.gui;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.map.GridMapLoc;
import model.map.MapLoc;
import model.traffic.Traffic;

public class FXController implements Initializable {

	@FXML
	private Text setUpStatus;

	@FXML
	private TextField initBoxInputRow;

	@FXML
	private TextField initBoxInputColumn;

	public FXView view;
	
	@FXML
	private void setUpMap(ActionEvent event) {
		try {
			String colinput = initBoxInputColumn.getText();
			int colnum = Integer.parseInt(colinput.trim());
			
			String rowinput = initBoxInputRow.getText();
			int rownum = Integer.parseInt(rowinput.trim());

			if (rownum <= 2 || colnum <= 2) {
				setUpStatus.setText("Input incorrect");
			} else {
				setUpStatus.setText("Setting up Map of size (" + rownum + ", " + colnum + ") ...");
				MapLoc map = new GridMapLoc(colnum - 1, rownum - 1);
				Traffic tr = new Traffic(map);
				tr.view = view;
				view.maxX = colnum;
				view.maxY = rownum;
				view.closeInit(tr);
			}

		} catch (Exception e) {
			setUpStatus.setText("Input incorrect");
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

}
