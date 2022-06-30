import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * MarkovModel.java Creates an order K Markov model of the supplied source
 * text. The value of K determines the size of the "kgrams" used to generate
 * the model. A kgram is a sequence of k consecutive characters in the source
 * text.
 *
 * @author     Brian Shin - bcs0058@auburn.edu
 * @author     Dean Hendrix (dh@auburn.edu)
 * @version    2022-6-26
 *
 */
public class MarkovModel {

   // Map of <kgram, chars following> pairs that stores the Markov model.
   private HashMap<String, String> model;

   // add other fields as you need them ...
   private String firstKgram;
   private List<String> keyList;
   private final Random rng = new Random();


   /**
    * Reads the contents of the file sourceText into a string, then calls
    * buildModel to construct the order K model.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(final int K, final File sourceText) {
      model = new HashMap<>();
      try {
         final String text = new Scanner(sourceText).useDelimiter("\\Z").next();
         buildModel(K, text);
      }
      catch (IOException e) {
         System.out.println("Error loading source text: " + e);
      }
   }


   /**
    * Calls buildModel to construct the order K model of the string sourceText.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, String sourceText) {
      model = new HashMap<>();
      buildModel(K, sourceText);
   }


   /**
    * Builds an order K Markov model of the string sourceText.
    */
   private void buildModel(int K, String sourceText) {
      // get the first kgram 
      firstKgram = sourceText.substring(0, K);

      // loop through the source text and extract all kgrams
      int currentIndex = 0;
      while (currentIndex + K <= sourceText.length()) {
         // getting the next kgram: will be used as key in our hashmap
         final String kgram = sourceText.substring(currentIndex, currentIndex + K);

         // if the model already contains that kgram then 
         if (model.containsKey(kgram)) {
            String kgramKeyValue = model.get(kgram);

            // base case: the source text length is <= k or reached the end of file
            if (currentIndex + K == sourceText.length()) {
               kgramKeyValue += Character.toString('\u0000');
            }
            else { // attach the kgram value to the key in the hashmap
               kgramKeyValue += Character.toString(sourceText.charAt(currentIndex + K));
            }
            model.replace(kgram, kgramKeyValue);
         }
         else { // if not then make a new key in the map, then append that keyvalue to the key
            if (currentIndex + K == sourceText.length()) {
               model.put(kgram, Character.toString('\u0000'));
            }
            else {
               model.put(kgram, Character.toString(sourceText.charAt(currentIndex + K)));
            }
         }
         currentIndex++;
      }

   }


   /** Returns the first kgram found in the source text. */
   public String getFirstKgram() {
      return firstKgram;
   }


   /** Returns a kgram chosen at random from the source text. */
   public String getRandomKgram() {
      if (keyList == null || keyList.size() != model.size()) {
         keyList = new ArrayList<String>(getAllKgrams());
      }
      return keyList.get(rng.nextInt(keyList.size()));
   }


   /**
    * Returns the set of kgrams in the source text.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
    public Set<String> getAllKgrams() {
      return model.keySet();
   }


   /**
    * Returns a single character that follows the given kgram in the source
    * text. This method selects the character according to the probability
    * distribution of all characters that follow the given kgram in the source
    * text.
    */
   public char getNextChar(final String kgram) {
      if (model.containsKey(kgram)) {
         final String value = model.get(kgram);
         return value.charAt(rng.nextInt(value.length()));
      }
      else {
         return '\u0000';
      }
   }


   /**
    * Returns a string representation of the model.
    * This is not part of the provided shell for the assignment.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
    @Override
    public String toString() {
      return model.toString();
   }

}
