import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.io.*;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class LuckyDraw {

    private ArrayList<String> numList = new ArrayList<>();
    private ArrayList<String> winnerList = new ArrayList<>();
    private Random randomNum;
    private String date = new SimpleDateFormat
            ("MM--dd--yyyy----HH:mm").format(new Date());

    /**
     * This constructor takes a file name to read particpants'
     * phone numbers and puts them in the numList to use for later
     * @param fileName
     * @throws IOException
     */
    public LuckyDraw(String fileName) throws IOException{
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            String line = fileReader.readLine();
            while (line != null) {
                //String[] lines = line.split(", ");
                // Create a new Ingredient object for each line in the file and add the ingredient to the inventory list
                numList.add(line);
                line = fileReader.readLine();
            }
        }
        randomNum = new Random();
    }

    public void addPersonToWinList(String n){
        this.winnerList.add(n);
    }

    public ArrayList<String> getWinnerList() {
        return winnerList;
    }

    public void removePersonFromList(int n){
        this.numList.remove(n);
    }

    public ArrayList<String> getNumList(){
        return numList;
    }

    public int getRandomIndex(){
        return randomNum.nextInt(numList.size());
    }

    /**
     * writes the list of all phone numbers plus date onto the
     * ParticipantsByDateTime.txt file
     * creates the file if it isnt found
     * @throws IOException
     */
    public void writeToParticipantFile() throws IOException{
        String text = "";

        for(String phoneNum : numList){
            text = text + System.lineSeparator() + phoneNum;
        }

        text = text + System.lineSeparator() + "Date and Time: " + date +
        System.lineSeparator();

        File file = new File("ParticipantsByDateTime.txt");

        if(file.exists()){
            file.createNewFile();
        }
        //Files.write(Paths.get("ParticipantsByDateTime.txt"), text.getBytes());
        FileWriter writer = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(text);
        bufferedWriter.close();

    }

    public static void main(String[] args) {

        LuckyDraw luckyDraw;

        {
            try {
                luckyDraw = new LuckyDraw("LuckyDrawParticipants.txt");
            } catch (IOException e) {
                luckyDraw = null;
                e.printStackTrace();
            }
        }

        int random;
        ArrayList<String> numList = luckyDraw.getNumList();
        random = luckyDraw.getRandomIndex();
        String num = null;
        num = numList.get(random).substring(4, numList.get(random).length() - 1);

        System.out.println(num);
        luckyDraw.addPersonToWinList(numList.get(random));
        luckyDraw.removePersonFromList(random);
        System.out.println(numList);

    }
}
