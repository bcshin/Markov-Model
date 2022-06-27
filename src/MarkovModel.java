import java.io.File;
import java.util.HashMap;
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


   /**
    * Reads the contents of the file sourceText into a string, then calls
    * buildModel to construct the order K model.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, File sourceText) {
      model = new HashMap<>();
      try {
         String text = new Scanner(sourceText).useDelimiter("\\Z").next();
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
      String firstKgram = sourceText.substring(0, K);
      int start = 0;
      for (int i = start + K; i <= sourceText.length(); i++) {
         String key = sourceText.substring(i, i + K);
         if (!model.containsKey(key)) {
            if (i == sourceText.length()) {
               model.put(key, Character.toString('\u0000'));
            }
            else {
               model.put(key, Character.toString(sourceText.charAt(i)));
            }
         }
         else {
            String map = model.get(key);
            if (i == sourceText.length()) {
               map += Character.toString('\u0000');
            }
            else {
               map += sourceText.charAt(i);
            }
            model.replace(key, map);
         }
      }

   }


   /** Returns the first kgram found in the source text. */
   public String getFirstKgram() {
      return null;
   }


   /** Returns a kgram chosen at random from the source text. */
   public String getRandomKgram() {
      return null;
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
   public char getNextChar(String kgram) {
      return '\u0000';
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
