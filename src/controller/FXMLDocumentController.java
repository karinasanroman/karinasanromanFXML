/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    
    @FXML
    private Button deleteButton; 
    
    @FXML
    private Button clickButton; 
    
    @FXML
    private Button findByNameAndID;
    
    
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
    
    public personalTrainer findByID (int id){
        Query query = manager.createNamedQuery("personalTrainer.findById");
        
        query.setParameter("ID", id);
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
        query.setParameter("FIRSTNAME", firstName);
        List<personalTrainer> trainers = query.getResultList();
        for(personalTrainer trainer : trainers){
            System.out.println(trainer.getId() + " "
                               + trainer.getFirstname() + " "
                               + trainer.getCredentials());
        }
     return trainers; 
    }
    
   public List<personalTrainer> findByNameAndID(String fname, int id){
        Query query = manager.createNamedQuery("personalTrainer.findByNameAndID");
   
        query.setParameter("ID", id);
        query.setParameter("First Name", fname);
 
        List<personalTrainer> trainers =  query.getResultList();
        for (personalTrainer trainer: trainers) {
            System.out.println(trainer.getId() + " " + trainer.getFirstname());
            
        }
        return trainers;
    }      
   
       public List<personalTrainer> findByFirstNameAndLastName(String fname, String lname){
        Query query = manager.createNamedQuery("personalTrainer.findByNameAndID");
   
        query.setParameter("Last Name", lname);
        query.setParameter("First Name", fname);
 
        List<personalTrainer> trainers =  query.getResultList();
        for (personalTrainer trainer: trainers) {
            System.out.println(trainer.getFirstname()+ " " + trainer.getLastname());
            
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

    @FXML void readPersonalTrainer(){
        
    }
   
    //demo code
    @FXML
    void findByID(ActionEvent event){
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter ID: ");
        int id =input.nextInt();
        
        personalTrainer pt = findByID(id); 
        System.out.println(pt.toString());
        
    }
    
    //demo code
    @FXML
    void findByFirstName(ActionEvent event){
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter first name: ");
        String fName = userInput.next(); 
        
        List<personalTrainer> pt = findByFirstName(fName);
        System.out.println(pt.toString());
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
    void findByFirstNameAndLastName(ActionEvent event) {
 
        Scanner input = new Scanner(System.in);
  
        System.out.println("Enter First Name:");
        String name = input.next();
        
        System.out.println("Enter Last Name:");
        int id = input.nextInt();
            
        List<personalTrainer> students =  findByNameAndID(name, id);

    }
   

//used demo code, same as create method but call update instead 
@FXML
public void UpdatePersonalTrainer(ActionEvent event){
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
    
    update(trainer);
}

//used demo/source code to help
@FXML
public void deletePersonalTrainer(ActionEvent event){
    Scanner userInput = new Scanner(System.in);
    System.out.println("Enter ID");
    int id = userInput.nextInt();
    
    personalTrainer pt = findByID(id); 
    System.out.println("Delete: " + pt.toString());
    delete(pt);
    
}
    
}
