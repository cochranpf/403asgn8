import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RaceTrack extends Application{
    public static void main(String[] args){
        Application.launch(RaceTrack.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Richmond Raceway");
        primaryStage.setHeight(200);
        primaryStage.setWidth(500);

        VBox parent = new VBox();
        Scene startScene = new Scene(parent);
        primaryStage.setScene(startScene);


        primaryStage.show();
        
    }
}