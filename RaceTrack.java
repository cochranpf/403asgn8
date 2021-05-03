import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.concurrent.ThreadLocalRandom;

// Paul Cochran
// V00824587
// Assignment 8 - CMSC 403
// This is my last assignment of undergrad, wow. Great class to end on.
// 5/3/2021

//begin class
public class RaceTrack extends Application{

    //global Car objects and ImageViews
    public Car Car1;
    public Car Car2;
    public Car Car3;

    ImageView car1;
    ImageView car2;
    ImageView car3;

    //global alert for win condition
    public Alert a = new Alert(AlertType.INFORMATION);

    public static void main(String[] args){
        Application.launch(RaceTrack.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        //setting stage properties
        primaryStage.setMinHeight(200);
        primaryStage.setMinWidth(500);
        primaryStage.setMaxHeight(200);
        primaryStage.setMaxWidth(500);

        //setting primary layout manager
        VBox root = new VBox();
        Scene startScene = new Scene(root);

        //setting up buttons
        HBox buttonZone = new HBox();

        Button startButton = new Button("Start");
        Button pauseButton = new Button("Pause");
        Button resetButton = new Button("Reset");

        buttonZone.getChildren().addAll(startButton, pauseButton, resetButton);

        //setting up all images and drawings
        Image carOne = new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTSWbkDwxzF8xFFS92Hj_alTdablTa322lxNw&usqp=CAU");
        Image carTwo = new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTSWbkDwxzF8xFFS92Hj_alTdablTa322lxNw&usqp=CAU");
        Image carThree = new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTSWbkDwxzF8xFFS92Hj_alTdablTa322lxNw&usqp=CAU");

        car1 = new ImageView(carOne);
        car2 = new ImageView(carTwo);
        car3 = new ImageView(carThree);

        car1.setFitHeight(30);
        car1.setFitWidth(65);
        car2.setFitHeight(30);
        car2.setFitWidth(65);
        car3.setFitHeight(30);
        car3.setFitWidth(65);

        Rectangle track1 = new Rectangle(500, 2);
        Rectangle track2 = new Rectangle(500, 2);
        Rectangle track3 = new Rectangle(500, 2);

        track1.setFill(Color.DARKGREY);
        track2.setFill(Color.DARKGREY);
        track3.setFill(Color.DARKGREY);


        //EVENT HANDLERS

        //start button
        EventHandler<ActionEvent> startPress = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                System.out.println("Start pressed.");

                startCars();
            }
        };

        //pause button
        EventHandler<ActionEvent> pausePress = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                System.out.println("Pause pressed.");
                stopCars();
            }
        };

        //reset button
        EventHandler<ActionEvent> resetPress = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                System.out.println("Reset pressed.");

                resetCars();

            }
        };

        
        //setting button actions
        startButton.setOnAction(startPress);
        pauseButton.setOnAction(pausePress);
        resetButton.setOnAction(resetPress);


        //START GUI

        root.getChildren().addAll(buttonZone, car1, track1, car2, track2, car3, track3);

        primaryStage.setTitle("Richmond Raceway");
        primaryStage.setScene(startScene);
        primaryStage.show();
        
    }

    //helper method for instantiating cars and starting the threads
    public void startCars(){
        Car1 = new Car(1, car1.getTranslateX(), car1);
        Car2 = new Car(2, car2.getTranslateX(), car2);
        Car3 = new Car(3, car3.getTranslateX(), car3);

        Car1.start();
        Car2.start();
        Car3.start();
    }

    //helper method for stopping all the car threads
    public void stopCars(){
        Car1.interrupt();
        Car2.interrupt();
        Car3.interrupt();
    }

    //helper method for reseting all the cars to the beginnings
    public void resetCars(){
        stopCars();

        //prevents race conditions
        while(Car1.isAlive() || Car2.isAlive() || Car3.isAlive()){
            System.out.println("Waiting for threads to stop.");
        }

        Car1.reset();
        Car2.reset();
        Car3.reset();
        
    }

    //function to call alert window with winner
    public void alertFunc(int id){
        a.setHeaderText("Alert!");
        a.setContentText("Car #" + id + " is the winner!");
        a.show();
    }

    //called when a car wins, uses lambda function to call actual alert function
    public void endGame(int id){
        stopCars();
        Platform.runLater(()->{
            alertFunc(id);
        });
        //alert
    }


    //Car object for keeping track of variables and running threads
    class Car extends Thread{
        int ID;
        double distance;
        ImageView carImage;
        boolean isFinished;
    
        //constructor
        public Car(int id, double dist, ImageView aCar){
            this.ID = id;
            this.distance = dist;
            this.carImage = aCar;
            this.isFinished = false;
        }
        
        //helper method for setting distance traveled
        public void setDistance(double dist){
            this.distance = dist;
            Platform.runLater(()->{
                this.carImage.setTranslateX(dist);
            });
        }
    
        //helper method for resetting variables
        public void reset(){
            this.setDistance(0);
            this.isFinished = false;
        }
    
        //thread run method override
        @Override
        public void run(){
            double increase = 0;
            while(true) {
                increase = ThreadLocalRandom.current().nextInt(0, 11);
                this.setDistance(this.distance + increase);
    
                Platform.runLater(()->{
                    this.carImage.setTranslateX(this.distance);
                });
    
                if (this.distance >= 435){
                    this.isFinished = true;
                    endGame(this.ID);
                }
    
                if (Thread.currentThread().isInterrupted()){
                    break;
                }
    
                try{
                    Thread.sleep(50);
                }
                catch (InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}

