import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class LuckyDraw {

    private ArrayList<String> numList = new ArrayList<>();
    private ArrayList<String> winnerList = new ArrayList<>();
    private Random randomNum;

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

    public void addPersonToNumList(String n){
        this.numList.add(n);
    }

    public void addPersonToWinList(String n){
        this.winnerList.add(n);
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
        System.out.println(numList.get(random));
        luckyDraw.addPersonToWinList(numList.get(random));
        luckyDraw.removePersonFromList(random);
        System.out.println(numList);

    }
}
