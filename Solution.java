import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        int topN = 10; //Prepopulate top title and ratings with default vals
        List<Double> ratings = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < topN; i++){
            ratings.add(0.0);
            titles.add("temp");
        }
        double lowestRating = ratings.getFirst();

        int lineNumber = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("movies.csv"))){
            String record;
            while ((record = br.readLine()) != null){
                if (lineNumber == 0){ //skip the header of the CSV
                    lineNumber++;
                    continue;
                }
                String[] vals = record.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); //split the comma if comma has an even number of quotes ahead of it
                String currentTitle = vals[0];
                double currentRating = Double.parseDouble(vals[3]);
                if (currentRating > lowestRating){
                    int placement = 0; //if the rating of the current title is at least our lowest rating, find where it belongs
                    while(placement < ratings.size() && ratings.get(placement) < currentRating){
                        placement+=1;
                    }
                    ratings.add(placement, currentRating); //place the current title, remove the first (lowest rated) title and reset the lowestRating
                    titles.add(placement, currentTitle);
                    ratings.removeFirst();
                    titles.removeFirst();
                    lowestRating = ratings.getFirst();

                }


            }
            for (int k = titles.size()-1; k >=0; k--)
                System.out.println("Rating " + ratings.get(k) + ", Title: " + titles.get(k));
//            Output:
//            Rating 9.3, Title: "Whispers of War, Echoes of Peace"
//            Rating 9.2, Title: "Realm of Legends, Chronicles of Heroes"
//            Rating 9.1, Title: "Echoes of Silence, Whispers of Love"
//            Rating 9.1, Title: "Realm of Legends, Chronicles of Heroes"
//            Rating 9.1, Title: "Harmony's Melody, Discord's Symphony"
//            Rating 9.0, Title: "Echoes of Eternity, Moments in Time"
//            Rating 8.9, Title: "Stolen Moments, Hearts Redeemed"
//            Rating 8.9, Title: "Whispers of War, Songs of Peace"
//            Rating 8.9, Title: "Eternal Echoes, Fleeting Moments"
//            Rating 8.9, Title: "Symphony of Silence, Notes of Life"
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}

