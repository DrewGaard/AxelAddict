import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONException;
import java.io.BufferedReader;	
import java.io.IOException;
import java.io.InputStreamReader;

public class StringSearch{
	
	private static CarAPI check2 = new CarAPI();	
	
	public static void main(String[] args){
		
		//API CarSearch String Result
		//am-general,acura,alfa-romeo,aston-martin,audi,bmw,bentley,bugatti,buick,cadillac,chevrolet,chrysler,daewoo,dodge,eagle,fiat,ferrari,fisker,ford,gmc,genesis,geo,hummer,honda,hyundai,infiniti,isuzu,jaguar,jeep,kia,lamborghini,land-rover,lexus,lincoln,lotus,mini,maserati,maybach,mazda,mclaren,mercedes-benz,mercury,mitsubishi,nissan,oldsmobile,panoz,plymouth,pontiac,porsche,ram,rolls-royce,saab,saturn,scion,spyker,subaru,suzuki,tesla,toyota,volkswagen,volvo,smart
		//make object of carAPI to call functions from there

		ArrayList<String> stringResults = new ArrayList<String>(); //ArrayList of Strings from API
		ArrayList<String> arr = new ArrayList<String>(); //New ArrayList of String Keywords
		boolean keepGoing = true;
		String yes = "Y"; String y   = "y";
		
		try{
			stringResults = check2.getCarFromYear(2016);
			System.out.println(stringResults);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scanner input = new Scanner(System.in); //New Scanner String for text word and patter
		Scanner test  = new Scanner(System.in); //New Scanner String for checking if user want's to keep finding a word
 
		while(keepGoing==true){
			System.out.print("Enter string to find: ");
			String pattern = input.nextLine();
				
			if(stringResults.indexOf(pattern) != -1){
				System.out.printf("String found: " + pattern);
				arr.add(pattern);
			}		
			else
				System.out.printf("String not found");
			
				System.out.print("\nKeep Going?(Y/N)");
				String s = test.nextLine();
			 	
			if(s.indexOf(yes)!= -1 || s.indexOf(y)!= -1)
			 	continue;
			 else
			 	keepGoing = false;		 	
		}
      System.out.println("ArrayList of keywords: " + arr);
	}
}