import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.application.Application;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class LuckyDrawGUI extends Application {

    private Stage mainWindow;
    private Scene shuffleScene;
    private Scene startScene;
    private LuckyDraw luckyDraw;

    {
        try {
            luckyDraw = new LuckyDraw("LuckyDrawParticipants.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    /**
     * The method that starts the window of the GUi up.
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        //needs to read from text or Excel file
        mainWindow = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SceneBuilder.fxml"));
        Parent root = loader.load();
        ( (LuckyDrawGUI) loader.getController() ).setPrimaryStage(primaryStage);
        startScene = new Scene(root, 800,600);

        mainWindow.setTitle("Lucky Draw!");
        mainWindow.setScene(startScene);

        //FXMLLoader loader2 = new FXMLLoader (getClass().getResource("ShuffleScene.fxml"));
        //Parent shuffleRoot = loader2.load();
        //shuffleScene = new Scene(shuffleRoot, 800,600);
        //mainWindow.setScene(shuffleScene);

        //( (LuckyDrawGUI) loader.getController() ).setPrimaryStage(primaryStage);
        mainWindow.show();
    }

    public void shuffleButtonPressed() throws IOException{
        FXMLLoader loader2 = new FXMLLoader (getClass().getResource("ShuffleScene.fxml"));
        Parent shuffleRoot = loader2.load();
        shuffleScene = new Scene(shuffleRoot, 800,600);
        mainWindow.setScene(shuffleScene);
    }

    public void setPrimaryStage(Stage stage){
        this.mainWindow = stage;
    }
}
