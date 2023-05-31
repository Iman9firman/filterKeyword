package com.filter.keyword;

import com.aliasi.util.Streams;
import com.filter.keyword.DAO.VoiceLogDao;
import com.filter.keyword.Entity.VoiceLog;
import jsastrawi.morphology.DefaultLemmatizer;
import jsastrawi.morphology.Lemmatizer;
import morfologik.stemming.WordData;
import morfologik.stemming.polish.PolishStemmer;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class KeywordApplicationTests {

	@Autowired
	private VoiceLogDao dao;

	@Test
	void looping(){
//		String s = "thisIsMyString with ThisSong idominate theworld";
//		VoiceLog voiceLog = dao.findVoiceLogById(1L);
//		String q = voiceLog.getText_voice();
		boolean keyword1 = false;
		boolean keyword2 = false;

		List<String> list = dao.findVoiceLogAll();
		System.out.println(list.size() + list.toString());
		int i = 1 ;
		for(String q : list) {
			System.out.println(i);
			String[] r = q.split("(?=\\p{Upper})|\\s");
			System.out.println(q +" | "+r.length);
			for (String arr : r){
				arr = arr.toLowerCase();
				if (condition("tidak", arr) || condition("putar", arr)){
					System.out.println("valid 1");
					keyword1 = true;
				}
				if (condition("terdaftar", arr) || condition("salah", arr)){
					System.out.println("valid 2");
					keyword2 = true;
				}
			}
			if (keyword1 && keyword2){
				System.out.println("id valid");
			}
			System.out.println("\n");
			i+=1;
		}

	}

	boolean condition(String a, String b){
		int threshold = 2;
		LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
		int distance = levenshteinDistance.apply(a, b);
		if (distance <= threshold ){
			System.out.println(a + " | " + b + " : " + distance);
			return true;
		}
		return false;
	}

	@Test
	void contextLoads() {
		String inputString = "terdaftar";
		String wordToCheck = "Terdafter";

		int threshold = 2; // Maximum allowed Levenshtein distance

		LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
		String[] words = {"berlari", "bermain", "melompat"};

		for (String word : words) {
			int distance = levenshteinDistance.apply(inputString, wordToCheck);
			System.out.println(distance);
			if (distance <= threshold) {
				System.out.println("The word is close enough to the input string.");
			} else {
				System.out.println("The word is not close enough to the input string.");
			}
		}
	}

	@Test
	void JaroDistance(){
		String inputString = "terdaftar";
		String wordToCheck = "Terdafter";
		double threshold = 0.9; // Minimum required Jaro-Winkler similarity

		JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();

		double similarity = jaroWinklerDistance.apply(inputString, wordToCheck);
		System.out.println(similarity);
		if (similarity >= threshold) {
			System.out.println("The word is close enough to the input string.");
		} else {
			System.out.println("The word is not close enough to the input string.");
		}
	}

	@Test
	void Morfologik(){
		PolishStemmer stemmer = new PolishStemmer();

		String[] words = {"berlari", "bermain", "melompat"};

		for (String word : words) {
			CharSequence  stemmedWord = stemmer.lookup(word).toString();
			String stemmedWord1 = String.valueOf(stemmer.lookup(word));

			System.out.println("Word: " + word + " Stemmed: " + stemmedWord.toString() + " Stemmed1: " + stemmedWord1);
		}
	}

	@Test
	void Aliasi() {
	/*	Stemmer stemmer = new IndonesianStemmer();

		String[] words = {"berlari", "bermain", "melompat"};

		for (String word : words) {
			String stemmedWord = stemmer.stem(word);

			System.out.println("Word: " + word + " Stemmed: " + stemmedWord);
		}

		// Close the stemmer resources
		Streams.closeQuietly(stemmer);
	*/

	}

	@Test
	void sastrawi() throws IOException {
		Set<String> dictionary = new HashSet<String>();

		InputStream in = Lemmatizer.class.getResourceAsStream("/root-words.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String line;
		while ((line = br.readLine()) != null) {
			dictionary.add(line);
		}

		Lemmatizer lemmatizer = new DefaultLemmatizer(dictionary);

		System.out.println(lemmatizer.lemmatize("terdeftar"));
		System.out.println(lemmatizer.lemmatize("meminum"));
		System.out.println(lemmatizer.lemmatize("bernyanyi"));
	}

}
