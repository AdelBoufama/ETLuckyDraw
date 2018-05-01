import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.application.Application;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class LuckyDrawGUI extends Application {

    private Stage mainWindow;
    private Scene shuffleScene;
    private Scene winnerScene;
    private Scene startScene;

    public Button goButton;
    public Button winnerButton;

    public Text firstWinner;
    public ArrayList<Text> winnerList;
    public Label secondWinner;
    public Label thirdWinner;

    public VBox winnerVbox;

    private static LuckyDraw luckyDraw;

    private static int winCount;

    /*{
        try {
            luckyDraw = new LuckyDraw("LuckyDrawParticipants.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

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
        this.mainWindow = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SceneBuilder.fxml"));
        Parent root = loader.load();
        ( (LuckyDrawGUI) loader.getController() ).setPrimaryStage(primaryStage);
        startScene = new Scene(root, 800,600);

        mainWindow.setTitle("Lucky Draw!");
        mainWindow.setScene(startScene);

        mainWindow.show();
    }

    public void goButtonPressed() throws IOException{
        winCount = 0;
        FXMLLoader loader2 = new FXMLLoader (getClass().getResource("ShuffleScene.fxml"));
        Parent shuffleRoot = loader2.load();
        ( (LuckyDrawGUI) loader2.getController() ).setPrimaryStage(mainWindow);
        shuffleScene = new Scene(shuffleRoot, 800,600);

        {
            try {
                luckyDraw = new LuckyDraw("LuckyDrawParticipants.txt");
            } catch (IOException e) {
                luckyDraw = null;
                e.printStackTrace();
            }
        }

        mainWindow.setScene(shuffleScene);
    }

    public void randomWinnerButtonPressed() throws IOException{
        winCount++;
        winnerList = new ArrayList<>();
        int random;
        ArrayList<String> numList = luckyDraw.getNumList();
        random = luckyDraw.getRandomIndex();

        //System.out.println(numList.get(random));

        firstWinner = new Text(winCount + ": (***)-***-" +
                numList.get(random).substring(5, numList.get(random).length() - 1));
        final double fontSize = 32.0;

        winnerList.add(firstWinner);

        for(Text winner : winnerList){
            winner.setOnMouseClicked(e ->
            {if(winner.isStrikethrough()){
                winner.setStrikethrough(false);
            }else {
                winner.setStrikethrough(true);
            }});
        }

        firstWinner.setFont(new Font(fontSize));
        //firstWinner.setBoundsType(Pos.BOTTOM_LEFT);
        winnerVbox.getChildren().add(firstWinner);

        AlertBox.display("WINNER NUMBER " + winCount, "(***)-***-" +
        numList.get(random).substring(5, numList.get(random).length() - 1));

        luckyDraw.addPersonToWinList(numList.get(random));
        luckyDraw.removePersonFromList(random);
    }

    public void finishButtonPressed() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SceneBuilder.fxml"));
        Parent root = loader.load();
        ( (LuckyDrawGUI) loader.getController() ).setPrimaryStage(mainWindow);
        startScene = new Scene(root, 800,600);

        mainWindow.setTitle("Lucky Draw!");
        mainWindow.setScene(startScene);
    }

    public void setPrimaryStage(Stage stage){
        this.mainWindow = stage;
    }
}
