import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GoogleAPIDemo {

public static void main(String[] args) throws Exception {
		
		//javafx.application.Application.launch(MainWindow.class);
		
		GoogleAPI test;
		System.out.println("Axel Addict - Supernaturals");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter a search phrase: ");
        String qry = br.readLine();
		test = new GoogleAPI(qry);
		System.out.println(test.getDescriptions());
	}
}
