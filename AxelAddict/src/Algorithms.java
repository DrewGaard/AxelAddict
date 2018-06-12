import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.json.JSONException;
import java.io.BufferedReader;	
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Object;

/*A Boyer-Moore Algorithm 
to find a certain pattern in a string,
and give the starting address of the
pattern, along with another Algorithm search 
that is similar to Boyer-moore, KMP. We 
can use either one, they both give an index
of whatever string we have to find.
I'll work on using that index to 
extract words we are looking for,
or Peter can use these two classes
for his string search as well.
*/

public class Algorithms{

	private int[] textInput; //Array of INT to show where the index pattern has started
	private final int maxIndex; 
    private String pattern; //Pattern variable to find where the first index of the specified string starts
    private static String textOcc;
    
    private static CarAPI check = new CarAPI();
    
    public static void mostFreqString(){
    	
    	ArrayList<String> list = new ArrayList<>();
        
    	try{
			list = check.getCarFromYear(2016);
			System.out.println(list);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Map<String, Integer> stringsCount = new HashMap<>();
        
        for(String s: list)
        {
          Integer c = stringsCount.get(s);
          if(c == null) c = new Integer(0);
          c++;
          stringsCount.put(s,c);
        }	
        
        Map.Entry<String,Integer> mostRepeated = null;
        for(Map.Entry<String, Integer> e: stringsCount.entrySet())
        {
            if(mostRepeated == null || mostRepeated.getValue()<e.getValue())
                mostRepeated = e;
        }
        
        if(mostRepeated != null)
            System.out.println("Most common string: " + mostRepeated.getKey());
    }
    
    public static int NumOccurences(String text, String pattern){
    		
    	int lastIndex = 0;
    	int count = 0;

    	while (lastIndex != -1) {

        lastIndex = text.indexOf(pattern, lastIndex);

        if (lastIndex != -1) {
            count++;
            lastIndex += pattern.length();
          } 
    	}
    	return count;
    }
 
    public Algorithms(String pattern){

        this.maxIndex = 256; //# of indexes
        this.pattern = pattern; 

        textInput = new int[maxIndex]; //create new array with STRING indexes
        
        for (int k = 0; k < maxIndex; k++)
        	textInput[k] = -1;
        for (int j = 0; j < pattern.length(); j++) //For the length of the pattern..
        	textInput[pattern.charAt(j)] = j; //find the location of the specified string
    }
    
    public void setPattern(String pat) {
    	pattern = pat;
        textInput = new int[maxIndex]; //create new array with STRING indexes
        
        for (int k = 0; k < maxIndex; k++)
        	textInput[k] = -1;
        for (int j = 0; j < pattern.length(); j++) //For the length of the pattern..
        	textInput[pattern.charAt(j)] = j; //find the location of the specified string
    }
    
    public ArrayList<CarMatches> matchCarsWithGoogle(String text) {
    	ArrayList<CarMatches> matches = new ArrayList<CarMatches>();
    	CarAPI carApi = new CarAPI();
    	ArrayList<String> makes = carApi.getCarMakes();
    	setPattern(pattern.toLowerCase());
    	for (int i = 0; i < makes.size(); i++) {
    		matches.add(carMatch(makes.get(i).toLowerCase(), text.toLowerCase()));
    	}
    	return matches;
    }
    
    private CarMatches carMatch(String make, String text) {
    	CarMatches carMatch = new CarMatches();
    	// somehow get the number of occurrences of each make
    	setPattern(make);
    	carMatch.setMake(make);
    	carMatch.setModel("");
    	carMatch.setOccurence(NumOccurences(text, make));
    		
    	return carMatch;
    }
    
    private ArrayList<CarMatches> isAlreadyMatch(ArrayList<CarMatches> matches, CarMatches match) {
    	for (int i =0;i<matches.size();i++){
    		if (matches.get(i).getMake() == match.getMake()){
    			matches.remove(i);
    			i--;
    		}
    	}
    	return matches;
    }
    
    public ArrayList<CarMatches> topCars(ArrayList<CarMatches> match, int top) {
    	if (match.size() < 1) return new ArrayList<CarMatches>();
		ArrayList<CarMatches> matches = new ArrayList<CarMatches>();
    	for (int i = 0;i<top;i++){
    		int max = 0;
    		int index = 0;
    		for(int j =0; j<match.size();j++){
    			if (max < match.get(j).getOccurence()){
    				max = match.get(j).getOccurence();
    				index = j;
    			}
    		}
    		matches.add(match.get(index));
    		match = isAlreadyMatch(match, match.get(index));
    	}
    	
    	return matches;
    }
    
    public int search(String text){ //search function was found online, with minor changes

        int string_length = text.length();
        int string_pattern = pattern.length();
        int skip;

        for (int i = 0; i < string_length - string_pattern; i += skip){

            skip = 0;
            for (int j = string_pattern - 1; j >= 0; j--){

                if (pattern.charAt(j) != text.charAt(i + j)){
                	if (text.charAt(i + j) < textInput.length)
                		skip = Math.max(1, j - textInput[text.charAt(i + j)]);
                	else
                		skip = string_pattern;
                    break;
                }
            }

          if (skip == 0)
          return i;
        }
        
      return string_length;
    }

	private void NoPatternMatch(String pattern){ 
		int[] error_check = {-1};   
		int n = pattern.length(); //
	   
	        for (int j = 1; j < n; j++)
	        {
	            int i = error_check[j - 1]; //for the length of the pattern string - 1
	            while ((pattern.charAt(j) != pattern.charAt(i + 1)) && i >= 0) //If the subsequent string letter doesn't match..
	                i = error_check[i]; //set that value into error_check array
	            if (pattern.charAt(j) == pattern.charAt(i + 1))
	                error_check[j] = i + 1; //the new array index holds the start of error string, or incorrect pattern 
	            else
	                error_check[j] = -1;
	       }
	    }
	 
    public static void main(String[] args) throws IOException{

        //Scanner sc = new Scanner(System.in); //New Scanner
        //System.out.print("Boyer Moore Algo: ");
        //System.out.print("Enter string: ");

        //String text = sc.nextLine();
        //System.out.print("Enter string to find: ");
        //String pattern = sc.nextLine();
    	
    	mostFreqString();
        
    	//System.out.println(NumOccurences(text, pattern));

        /*Algorithms pattern_string = new Algorithms(pattern); //Create new pattern_string object to search through
        int pattern_index = pattern_string.search(text); //Find the pattern index in the pattern string 
        System.out.println("'" + pattern + "' is first found after the " + pattern_index + " (st/th) index position.");*/
        //sc.close();

        /*	Algorithms al = new Algorithms("");
    	al.matchCarsWithGoogle("Hondatoyotamitsubishifordbuicknissan");
    	ArrayList<CarMatches> test = new ArrayList<CarMatches>();
    	CarMatches test1 = new CarMatches();
    	test1.setMake("Honda");
    	test1.setOccurence(5);
    	CarMatches test2 = new CarMatches();
    	test2.setMake("Toyota");
    	test2.setOccurence(15);
    	CarMatches test3 = new CarMatches();
    	test3.setMake("Mitsubishi");
    	test3.setOccurence(100);
    	CarMatches test4 = new CarMatches();
    	test4.setMake("Subaru");
    	test4.setOccurence(1);
    	CarMatches test5 = new CarMatches();
    	test5.setMake("Ford");
    	test5.setOccurence(8);
    	CarMatches test6 = new CarMatches();
    	test6.setMake("Chevy getin kinda heavy");
    	test6.setOccurence(16);
    	CarMatches test7 = new CarMatches();
    	test7.setMake("Saturn");
    	test7.setOccurence(13);
    	CarMatches test8 = new CarMatches();
    	test8.setMake("Dodge");
    	test8.setOccurence(19);
    	CarMatches test9 = new CarMatches();
    	test9.setMake("Bugatti");
    	test9.setOccurence(20);
    	CarMatches test10 = new CarMatches();
    	test10.setMake("Mcclaren");
    	test10.setOccurence(25);
    	test.add(test1);
    	test.add(test2);
    	test.add(test3);
    	test.add(test4);
    	test.add(test5);
    	test.add(test6);
    	test.add(test7);
    	test.add(test8);
    	test.add(test9);
    	test.add(test10);
    	Algorithms al = new Algorithms("");
    	System.out.println(al.topCars(test, 10).toString());*/
    }
}