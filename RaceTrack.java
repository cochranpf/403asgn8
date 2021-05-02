import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RaceTrack extends Application{
    public static void main(String[] args){
        Application.launch(RaceTrack.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Richmond Raceway");
        primaryStage.setMinHeight(200);
        primaryStage.setMinWidth(500);
        primaryStage.setMaxHeight(200);
        primaryStage.setMaxWidth(500);

        VBox root = new VBox();
        Scene startScene = new Scene(root);

        Image carOne = new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTSWbkDwxzF8xFFS92Hj_alTdablTa322lxNw&usqp=CAU");
        Image carTwo = new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTSWbkDwxzF8xFFS92Hj_alTdablTa322lxNw&usqp=CAU");
        Image carThree = new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTSWbkDwxzF8xFFS92Hj_alTdablTa322lxNw&usqp=CAU");

        ImageView car1 = new ImageView(carOne);
        ImageView car2 = new ImageView(carTwo);
        ImageView car3 = new ImageView(carThree);

        car1.setFitHeight(20);
        car1.setFitWidth(45);
        car2.setFitHeight(20);
        car2.setFitWidth(45);
        car3.setFitHeight(20);
        car3.setFitWidth(45);

        root.getChildren().addAll(car1, car2, car3);

        primaryStage.setScene(startScene);
        primaryStage.show();
        
    }
}