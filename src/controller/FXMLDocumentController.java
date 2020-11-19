/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.personalTrainer;

/**
 *
 * @author Karina
 */
public class FXMLDocumentController implements Initializable {
    // methods below use demo code 
    @FXML
    private Label label;
    
    @FXML
    private Button createButton; 
    
    @FXML
    private Button readButton;
    
    @FXML
    private Button updateButton;
    
    @FXML
    private Button findById;
    
    @FXML
    private Button findByName; 
    
    
    private Button advancedSearchButton; 
    
    @FXML
    private Button deleteButton; 
    
    @FXML
    private Button clickButton; 
    
    @FXML
    private Button findByNameAndID;
    
    //quiz 4 start
    
    @FXML 
    private Button searchButton; 
    
    @FXML
    private Label inputLabel;
    

    
    @FXML 
    private TableView<personalTrainer> trainerTable; 
    
    @FXML
    private TableColumn<personalTrainer, String> trainerFirstName;
    @FXML
    private TableColumn<personalTrainer, Integer> trainerID;
    @FXML
    private TableColumn<personalTrainer, String> trainerLastName;
    @FXML
    private TableColumn<personalTrainer, String> trainerCredentials;
    
    @FXML
    private TextField inputTextField; 
    
    private ObservableList<personalTrainer> trainerData;

  
    public void setTableData(List<personalTrainer> trainerList) {

        trainerData = FXCollections.observableArrayList();
        trainerList.forEach(pt -> {
            trainerData.add(pt);
        });

        trainerTable.setItems(trainerData);
        
        trainerTable.refresh();
        System.out.println( trainerTable);
    }
    
    
    @FXML
    void searchData(ActionEvent event){
        System.out.println("Click");
      
        String name = inputTextField.getText();
        List<personalTrainer> ptPerson = findByFirstName(name);

        if (ptPerson == null || ptPerson.isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ERROR, table is empty");
            alert.setHeaderText("No trainers in table!");
            alert.setContentText("add a trainer to list");
            alert.showAndWait(); 
        } else {
            setTableData(ptPerson);
        }
    }
    
    @FXML
     void advancedSearch(ActionEvent event) {
       
        String name = inputTextField.getText();
        List<personalTrainer> trainers = findByFirstNameAdvanced(name);
        
        if (trainers == null || trainers.isEmpty()) {

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ERROR, table is empty");
            alert.setHeaderText("No trainers in table!");
            alert.setContentText("add a trainer to list");
            alert.showAndWait(); 
        } else {
            setTableData(trainers);
            
        }
        
    }
    
        @FXML
        void showDetails(ActionEvent event) throws IOException {
        System.out.println("clicked");

        personalTrainer selectedTrainer = trainerTable.getSelectionModel().getSelectedItem();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailedModelView.fxml"));
        Parent detailedModelView = loader.load();
        
        Scene tableViewScene = new Scene(detailedModelView);
        
        DetailedModelViewController detailedControlled = loader.getController();
        detailedControlled.initData(selectedTrainer);

        Stage stage = new Stage();
        stage.setScene(tableViewScene);
        stage.show();

    }
        
    @FXML
    void showDetailsInPlace(ActionEvent event) throws IOException {
        System.out.println("clicked");

        personalTrainer selectedStudent = trainerTable.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailedModelView.fxml"));

        Parent detailedModelView = loader.load();

        Scene tableViewScene = new Scene(detailedModelView);

        DetailedModelViewController detailedControlled = loader.getController();


        detailedControlled.initData(selectedStudent);

   
        Scene currentScene = ((Node) event.getSource()).getScene();
        detailedControlled.setPreviousScene(currentScene);

  
        Stage stage = (Stage) currentScene.getWindow();

        stage.setScene(tableViewScene);
        stage.show();
    }
    
    //quiz four end 
    //Quiz three start
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
        
        //add query here 
        Query query = manager.createNamedQuery("personalTrainer.findAll");
        List<personalTrainer> list = query.getResultList();
        for(personalTrainer pt : list){
            System.out.println(pt.getId() + " " 
                                + pt.getFirstname() + " "
                                + pt.getCredentials());          
        }
    }
    
    //database manager
    EntityManager manager;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager = (EntityManager) Persistence.createEntityManagerFactory("KarinaSanRomanFXMLPU").createEntityManager();
              
        trainerFirstName.setCellValueFactory(new PropertyValueFactory<>("Firstname"));
        trainerLastName.setCellValueFactory(new PropertyValueFactory<>("Lastname"));
        trainerID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        trainerCredentials.setCellValueFactory(new PropertyValueFactory<>("Credentials"));

      
       trainerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
       trainerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }
    

    
    //following code from demo
    public void Create(personalTrainer personalTrainer){
        try {
            // begin transaction
            manager.getTransaction().begin();
            
            // sanity check
            if (personalTrainer.getId() != null) {
                
        
                manager.persist(personalTrainer);
                
            
                manager.getTransaction().commit();
                
                System.out.println(personalTrainer.toString() + " is created");
                
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    
    //following code from demo
    /**
     *
     * @return
     */
    public List<personalTrainer> findAll(){
        Query query = manager.createNamedQuery("personalTrainer.findAll");
        List<personalTrainer> personalTrainer = query.getResultList();

        for (personalTrainer t : personalTrainer) {
            System.out.println(t.getId() + " " + t.getFirstname() + " " + t.getLastname() 
            + " " + t.getCredentials());
        }
        return personalTrainer;
        
    }
    
    public List<personalTrainer> findByCredentials(String credentials){
        Query query = manager.createNamedQuery("personalTrainer.findByCredentials");
        query.setParameter("credentials", credentials);
        List<personalTrainer> trainers = query.getResultList();
        for(personalTrainer trainer : trainers){
            System.out.println(trainer.getId() + " "
                               + trainer.getFirstname() + " "
                               + trainer.getCredentials());
        }
     return trainers; 
    }
    
    public personalTrainer findById (int id){
        Query query = manager.createNamedQuery("personalTrainer.findById");
        
        query.setParameter("id", id);
        personalTrainer trainer = (personalTrainer) query.getSingleResult();
        if(trainer != null){
            System.out.println(trainer.getId() + " "
                                + trainer.getFirstname() + " " 
                                + trainer.getCredentials());
        }
        return trainer;
    }
    
    public List<personalTrainer> findByFirstName(String firstName){
        Query query = manager.createNamedQuery("personalTrainer.findByFirstname");
        query.setParameter("firstname", firstName);
        List<personalTrainer> trainers = query.getResultList();
        for(personalTrainer trainer : trainers){
            System.out.println(trainer.getId() + " "+ trainer.getFirstname() + " " + trainer.getCredentials());
        }
     return trainers; 
    }
    
     public List<personalTrainer> findByLastName(String LastName){
        Query query = manager.createNamedQuery("personalTrainer.findByLastname");
        query.setParameter("lastname", LastName);
        List<personalTrainer> trainers = query.getResultList();
        for(personalTrainer trainer : trainers){
            System.out.println(trainer.getId() + " "
                               + trainer.getLastname() + " "
                               + trainer.getCredentials());
        }
     return trainers; 
    }
    
   public List<personalTrainer> findByNameAndID(String fname, int id){
        Query query = manager.createNamedQuery("personalTrainer.findByNameAndID");
   
        query.setParameter("id", id);
        query.setParameter("firstname", fname);
 
        List<personalTrainer> trainers =  query.getResultList();
        for (personalTrainer trainer: trainers) {
            System.out.println(trainer.getId() + " " + trainer.getFirstname());
            
        }
        return trainers;
    }      
   
       public List<personalTrainer> findByFirstNameAndLastName(String fname, String lname){
        Query query = manager.createNamedQuery("personalTrainer.findByNameAndLastName");
   
        query.setParameter("lastname", lname);
        query.setParameter("firstname", fname);
 
        List<personalTrainer> trainers =  query.getResultList();
        for (personalTrainer trainer: trainers) {
            System.out.println(trainer.getFirstname()+ " " + trainer.getLastname());
            
        }
        return trainers;
    } 
    
     public List<personalTrainer> findByFirstNameAdvanced(String fname) {
        Query query = manager.createNamedQuery("personalTrainer.findByFirstNameAdvanced");

  
        query.setParameter("firstname", fname);

        List<personalTrainer> trainers = query.getResultList();
        for (personalTrainer trainer : trainers) {
            System.out.println(trainer.getId() + " " + trainer.getFirstname()+ " " + trainer.getCredentials());
        }

        return trainers;
    }     
 
    
    @FXML
    //following code from demo
    public void update(personalTrainer model){
        try {
            personalTrainer personalTrainer = manager.find(personalTrainer.class, model.getId());

            if (personalTrainer != null) {
                // begin transaction
                manager.getTransaction().begin();
                
                // update all atttributes
                
                personalTrainer.setFirstname(model.getFirstname());
                personalTrainer.setLastname(model.getLastname());
                personalTrainer.setCredentials(model.getCredentials());
                

                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
    
    @FXML
    //following code from demo
    public void delete(personalTrainer trainer){
        try {
            personalTrainer existingTrainer = manager.find(personalTrainer.class, trainer.getId());

            // sanity check
            if (existingTrainer != null) {
                
                // begin transaction
                manager.getTransaction().begin();
                
                //remove student
                manager.remove(existingTrainer);
                
                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
@FXML
//used source code to help
public void createPersonalTrainer(ActionEvent event){
    Scanner userInput = new Scanner(System.in);
    
    System.out.println("Enter credentials: ");
    String cred = userInput.next();
    
    System.out.println("Enter First Name: ");
    String fName = userInput.next();
    
    System.out.println("Enter Last Name: ");
    String lName = userInput.next();
    
    System.out.println("Enter ID: ");
    int id = userInput.nextInt(); 
    
    personalTrainer trainer = new personalTrainer(); 
    
    trainer.setCredentials(cred);
    trainer.setFirstname(fName);
    trainer.setLastname(lName);
    trainer.setId(id);
    
    Create(trainer);
}    

    @FXML void readPersonalTrainer(ActionEvent event){
        Scanner userInput = new Scanner(System.in);
        
        System.out.println("Enter a way to find the person");
        System.out.println("1) All, 2) ID, 3) first name, 4) last name, 5) Credentials");
        System.out.println("Enter which number you chose:");
        int choice = userInput.nextInt(); 
        
        if(choice ==1){
            List <personalTrainer> ptPerson = findAll(); 
        }
        
        if(choice ==2){
            System.out.println("Enter ID: ");
            int id = userInput.nextInt();
            System.out.println("");
            
            personalTrainer ptPerson = findById(id);
             
        }
        
        if(choice ==3){
            System.out.println("Enter First Name:");
            String fName = userInput.next();
            System.out.println("");
            
            List <personalTrainer> ptPerson = findByFirstName(fName);
        }
        
        
        if(choice ==4){
            System.out.println("Enter Last Name:");
            String LName = userInput.next();
            System.out.println("");
            
            List <personalTrainer> ptPerson = findByLastName(LName);
        }
        
        
        if(choice ==5){
            System.out.println("Enter Credentials: ");
            String credentials = userInput.next(); 
            System.out.println("");
            
            List<personalTrainer> ptPerson = findByCredentials(credentials);
           
        }
   
    }
   
    
    
    
 @FXML
    void findByNameAndID(ActionEvent event) {
 
        Scanner input = new Scanner(System.in);
  
        System.out.println("Enter First Name:");
        String name = input.next();
        
        System.out.println("Enter ID:");
        int id = input.nextInt();
            
        List<personalTrainer> students =  findByNameAndID(name, id);

    }
  
    
    @FXML
    void findByFirstName(ActionEvent event ){
        Scanner input = new Scanner(System.in);

        // read input from command line
        System.out.println("Enter First Name:");
        String name = input.next();

        List<personalTrainer> pt = findByFirstName(name);
       
    }
    
    
     @FXML
    void findByFirstNameAndLastName(ActionEvent event) {
 
        Scanner input = new Scanner(System.in);
  
        System.out.println("Enter First Name:");
        String fname = input.next();
        
        System.out.println("Enter Last Name:");
        String lName = input.next();
            
        List<personalTrainer> personalTrainer =  findByFirstNameAndLastName(fname, lName);

    }
   

//used demo code, same as create method but call update instead 
@FXML
public void UpdatePersonalTrainer(ActionEvent event){
    Scanner userInput = new Scanner(System.in);
    
    System.out.println("Enter ID: ");
    int id = userInput.nextInt();
    
    System.out.print("Orginal: "); 
    findById(id);
    System.out.println("");
    
    
    System.out.println("Enter new or orgininal credentials: ");
    String cred = userInput.next();
    
    System.out.println("Enter new or original First Name: ");
    String fName = userInput.next();
    
    System.out.println("Enter new or original Last Name: ");
    String lName = userInput.next();
    
    personalTrainer trainer = new personalTrainer(); 
    
    //set them 
    trainer.setCredentials(cred);
    trainer.setFirstname(fName);
    trainer.setLastname(lName);
    trainer.setId(id);
    
    update(trainer);
    System.out.print("Update: ");
    findById(id);
}

//used demo/source code to help
@FXML
public void deletePersonalTrainer(ActionEvent event){
    Scanner userInput = new Scanner(System.in);
    System.out.println("Enter ID");
    int id = userInput.nextInt();
    
    personalTrainer pt = findById(id); 
    System.out.println("Delete: " + pt.toString());
    delete(pt);
    
}
    
}
