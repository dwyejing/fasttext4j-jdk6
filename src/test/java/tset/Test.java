package tset;

import fasttext.FastText;
import fasttext.FastTextPrediction;
import fasttext.FastTextSynonym;
import fasttext.Vector;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		  String path = "E:\\workspace\\fastText4j-master\\text\\model_cooking.bin";
		    FastText model = null;

		    try {

		      /* First you will have to load your model */
		      model = FastText.loadModel(path);


		   

		      // Nearest neighbors queries
		      List<FastTextSynonym> nearestNeighbors = model.nn("apple", 10);

		      System.out.println("Nearest neighbors of \"hello\" are: ");
		      for (FastTextSynonym s : nearestNeighbors) {
		          System.out.println(s.word() + " -> " + s.cosineSimilarity());
		        }

		    } catch (Exception e) {

		      System.out.println("Oops something went wrong. Exception: " + e.getMessage());

		    } finally {

		      // Closing is only mandatory for memory-mapped models
		      if (model != null) {
		        try {
		          model.close();
		        } catch (IOException e) {
		          System.out.println("Error while closing fastText model");
		        }
		      }

		    }
	}
	
  static public void test(String[] args) {

    String path = "E:\\workspace\\fastText4j-master\\text";
    FastText model = null;

    try {

      /* First you will have to load your model */
      model = FastText.loadModel(path);


      /* Using fastText4j for supervised classification */

      FastTextPrediction singlePrediction = model.predict(Arrays.asList("hello", "world", "!"));

      List<FastTextPrediction> predictions = model.predictAll(Arrays.asList("hello", "world", "!"));


      /* Using fastText4j in an unsupervised fashion */

      // Nearest neighbors queries
      List<FastTextSynonym> nearestNeighbors = model.nn("hello", 10);

      System.out.println("Nearest neighbors of \"hello\" are: ");
      for (FastTextSynonym s : nearestNeighbors) {
        System.out.println(s.word() + " -> " + s.cosineSimilarity());
      }

      // Analogy queries
      List<FastTextSynonym> analogies = model.analogies("berlin", "germany", "france", 10);

      System.out.println("Analogies: \"berlin\" + \"germany\" - \"france\" : ");
      for (FastTextSynonym s : analogies) {
        System.out.println(s.word() + " -> " + s.cosineSimilarity());
      }

      // Obtaining a word vector
      Vector wv = model.getWordVector("world");

      // Obtaining word vectors
      List<Vector> wvs = model.getWordVectors(Arrays.asList("hello", "world", "!"));

      // Obtaining sentence (document) embeddings
      Vector sv = model.getSentenceVector(Arrays.asList("hello", "world", "!"));

    } catch (Exception e) {

      System.out.println("Oops something went wrong. Exception: " + e.getMessage());

    } finally {

      // Closing is only mandatory for memory-mapped models
      if (model != null) {
        try {
          model.close();
        } catch (IOException e) {
          System.out.println("Error while closing fastText model");
        }
      }

    }

  }

}
