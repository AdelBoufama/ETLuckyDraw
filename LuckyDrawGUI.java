import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.control.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class LuckyDrawGUI extends Application {

    private String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    private Stage mainWindow;
    private Scene shuffleScene;
    private Scene startScene;

    public Button goButton;
    public Button winnerButton;

    private Text winner;
    private ArrayList<Text> winnerList = new ArrayList<>();

    public VBox winnerVbox;

    private static LuckyDraw luckyDraw;

    private static int winCount;

    public static void main(String[] args){
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

    /**
     * This button starts the lucky draw game and enters the
     * shuffle scene
     * it also writes to the ParticipantsByDateTime.txt file
     * @throws IOException
     */
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

        luckyDraw.writeToParticipantFile();

        mainWindow.setScene(shuffleScene);
    }

    /**
     * This button is built in the gif of the shuffle animation
     * each time it is pressed, a random winner will pop up in an
     * AlertBox and the last 4 digits of their phone number will show up
     * on the scene
     * You could cross out numbers and uncross them by clicking if
     * the participants leave or don't accept their prize
     * @throws IOException
     */
    public void randomWinnerButtonPressed() throws IOException{
        winCount++;
        int random;
        ArrayList<String> numList = luckyDraw.getNumList();
        random = luckyDraw.getRandomIndex();

        //System.out.println(numList.get(random));

        winner = new Text(winCount + ": (***)-***-" +
                numList.get(random).substring(5, numList.get(random).length() - 1));
        final double fontSize = 32.0;

        winnerList.add(winner);

        for(Text winner : winnerList){
            winner.setOnMouseClicked(e ->
            {if(winner.isStrikethrough()){
                winner.setStrikethrough(false);
            }else {
                winner.setStrikethrough(true);
            }});
        }

        winner.setFont(new Font(fontSize));
        //firstWinner.setBoundsType(Pos.BOTTOM_LEFT);
        winnerVbox.getChildren().add(winner);

        AlertBox.display("WINNER NUMBER " + winCount, "(***)-***-" +
        numList.get(random).substring(5, numList.get(random).length() - 1));

        luckyDraw.addPersonToWinList(numList.get(random));
        luckyDraw.removePersonFromList(random);
    }

    /**
     * This button takes you back to the startScene where you could
     * play the game again with the same phone numbers or change the numbers
     * in the LuckyDrawParticipants.txt file
     * It also puts all the winners on the WinnersByDateTime.txt file
     * @throws IOException
     */
    public void finishButtonPressed() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SceneBuilder.fxml"));
        Parent root = loader.load();
        ( (LuckyDrawGUI) loader.getController() ).setPrimaryStage(mainWindow);
        startScene = new Scene(root, 800,600);

        String text = "";

        for(Text phoneNum : winnerList){
            if(!phoneNum.isStrikethrough()) {
                text = text + System.lineSeparator() + phoneNum.getText();
            }
        }

        text = text + System.lineSeparator() + "Date and Time: " + date +
                System.lineSeparator();

        File file = new File("WinnersByDateTime.txt");

        if(file.exists()){
            file.createNewFile();
        }
        //Files.write(Paths.get("ParticipantsByDateTime.txt"), text.getBytes());
        FileWriter writer = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(text);
        bufferedWriter.close();

        mainWindow.setTitle("Lucky Draw!");
        mainWindow.setScene(startScene);
    }

    private void setPrimaryStage(Stage stage){
        this.mainWindow = stage;
    }
}
