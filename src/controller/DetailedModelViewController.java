/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.personalTrainer;

/**
 *
 * @author Karina
 */
public class DetailedModelViewController {
    
    @FXML 
    private Button backButton;

    @FXML 
    private ResourceBundle resources;

    @FXML 
    private URL location;

    @FXML 
    private Label detailedTrainerIDLabel; 

    @FXML 
    private Label detailedNameLabel; 

    @FXML 
    private ImageView imageView; 
    
    
    @FXML
    void detailedBackButton(ActionEvent event ){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        if (previousScene != null) {
            stage.setScene(previousScene);
        }

    }
    
        personalTrainer selectedModel;
        Scene previousScene;
    
    public void setPreviousScene(Scene scene) {
        previousScene = scene;
        backButton.setDisable(false);

    }
    
    public void initData(personalTrainer model) {
        selectedModel = model;
        detailedTrainerIDLabel.setText(model.getId().toString());
        detailedNameLabel.setText(model.getFirstname() + model.getLastname());

        try {
            String imagename = "/resource/images/" + model.getFirstname() + model.getLastname() + ".png";
            
            Image profile = new Image(getClass().getResourceAsStream(imagename));
            System.out.println(profile);
            
            imageView.setImage(profile);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML 
    void initialize() {
        assert backButton != null : "fx:id=\"backButtong\" was not injected: check your FXML file 'DetailedModelView.fxml'.";
        assert detailedTrainerIDLabel != null : "fx:id=\"detailedTrainerIDLabel\" was not injected: check your FXML file 'DetailedModelView.fxml'.";
        assert detailedNameLabel != null : "fx:id=\"detailedNameLabel\" was not injected: check your FXML file 'DetailedModelView.fxml'.";
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'DetailedModelView.fxml'.";

        backButton.setDisable(true);

    }
    
    
    
    
}
