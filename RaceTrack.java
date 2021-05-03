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


        //SET UP CARS
        Car Car1 = new Car(1, car1);
        Car Car2 = new Car(2, car2);
        Car Car3 = new Car(3, car3);


        //EVENT HANDLERS

        EventHandler<ActionEvent> startPress = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                
            }
        };

        EventHandler<ActionEvent> pausePress = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                System.out.println("Pause Pressed.");
            }
        };

        EventHandler<ActionEvent> resetPress = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                System.out.println("Reset Pressed.");
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
}

class Car extends Thread{
    private int ID;
    private int distance;
    private ImageView carImage;

    public Car(int id, ImageView aCar){
        this.ID = id;
        this.distance = 0;
        this.carImage = aCar;
    }

    @Override
    public void run(){
        while(true) {
            Platform.runLater(()->{
                int increase = 0;
                increase = ThreadLocalRandom.current().nextInt(0, 11);
                this.carImage.setTranslateX(increase);
                this.distance = this.distance + increase;
                if (this.distance >= 500){
                    System.out.println("Car #" + this.ID + " wins!");
                }
                else{
                    System.out.println("Car #" + this.ID + " moved " + increase + " pixels.");
                }
            });
        }
    }
}