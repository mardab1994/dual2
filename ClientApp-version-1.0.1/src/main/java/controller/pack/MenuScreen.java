package controller.pack;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class MenuScreen {

	private MainController mainController;
	final ToggleGroup group = new ToggleGroup();
	
    @FXML
    private TextField nickName;

    @FXML
    private RadioButton cipherNONE;

    @FXML
    private RadioButton cipherXOR;

    @FXML
    private RadioButton cipherCesar;
    
    @FXML
    void OpenApplication() {
    		String name = nickName.getText();
    		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/AppScreen.fxml"));
    		Pane pane = null;
    		try {
				pane = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	
    		AppController appController = loader.getController();
    		appController.setParam(nickName.getText(), getCipherMode());
    		appController.setMainController(mainController);
    		mainController.setScreen(pane);
   }

    private int getCipherMode() {
    	return (Integer) group.getSelectedToggle().getUserData();
    }
   
    public void setMainController(MainController mainController) {
    	cipherNONE.setToggleGroup(group);
    	cipherNONE.setSelected(true);
    	cipherXOR.setToggleGroup(group);
    	cipherCesar.setToggleGroup(group);
    	
    	cipherNONE.setUserData(0);
       	cipherXOR.setUserData(1);
    	cipherCesar.setUserData(2);
    	
    	this.mainController = mainController;
    }
}
