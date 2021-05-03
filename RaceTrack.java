import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.concurrent.ThreadLocalRandom;

public class RaceTrack extends Application{
    public static void main(String[] args){
        Application.launch(RaceTrack.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        primaryStage.setMinHeight(200);
        primaryStage.setMinWidth(500);
        primaryStage.setMaxHeight(200);
        primaryStage.setMaxWidth(500);


        VBox root = new VBox();
        Scene startScene = new Scene(root);

        HBox buttonZone = new HBox();

        Button startButton = new Button("Start");
        Button pauseButton = new Button("Pause");
        Button resetButton = new Button("Reset");

        buttonZone.getChildren().addAll(startButton, pauseButton, resetButton);

        Image carOne = new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTSWbkDwxzF8xFFS92Hj_alTdablTa322lxNw&usqp=CAU");
        Image carTwo = new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTSWbkDwxzF8xFFS92Hj_alTdablTa322lxNw&usqp=CAU");
        Image carThree = new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTSWbkDwxzF8xFFS92Hj_alTdablTa322lxNw&usqp=CAU");

        ImageView car1 = new ImageView(carOne);
        ImageView car2 = new ImageView(carTwo);
        ImageView car3 = new ImageView(carThree);

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

        Car Car1 = new Car(1, car1.getTranslateX(), car1);
        Car Car2 = new Car(2, car2.getTranslateX(), car2);
        Car Car3 = new Car(3, car3.getTranslateX(), car3);


        //EVENT HANDLERS

        EventHandler<ActionEvent> startPress = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                System.out.println("Start pressed.");

                Car1.start();
                Car2.start();
                Car3.start();
            }
        };

        EventHandler<ActionEvent> pausePress = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                System.out.println("Pause pressed.");
                stopCars(Car1, Car2, Car3);
            }
        };

        EventHandler<ActionEvent> resetPress = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                System.out.println("Reset pressed.");
            }
        };

        startButton.setOnAction(startPress);
        pauseButton.setOnAction(pausePress);
        resetButton.setOnAction(resetPress);

        //START GUI

        root.getChildren().addAll(buttonZone, car1, track1, car2, track2, car3, track3);

        primaryStage.setTitle("Richmond Raceway");
        primaryStage.setScene(startScene);
        primaryStage.show();
        
    }

    public void stopCars(Car c1, Car c2, Car c3){
        c1.interrupt();
        c2.interrupt();
        c3.interrupt();
    }
}

class Car extends Thread{
    int ID;
    double distance;
    ImageView carImage;
    boolean isFinished;

    public Car(int id, double dist, ImageView aCar){
        this.ID = id;
        this.distance = dist;
        this.carImage = aCar;
        this.isFinished = false;
    }

    @Override
    public void run(){
        double increase = 0;
        while(true) {
            increase = ThreadLocalRandom.current().nextInt(0, 11);
            this.distance = this.distance + increase;

            Platform.runLater(()->{
                this.carImage.setTranslateX(this.distance);
            });

            if (this.distance >= 435){
                System.out.println("Car #" + this.ID + " wins!");
                this.isFinished = true;
                break;
            }
            else{
                System.out.println("Car #" + this.ID + " moved " + increase + " pixels.");
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