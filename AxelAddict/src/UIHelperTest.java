import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class UIHelperTest {

	@Test
	public void test() {
		ArrayList<String> normalString = new ArrayList<>();
		normalString.add("honda");
		normalString.add("am-general");
		normalString.add("rolls royce");
		JavaUIHelper helper = new JavaUIHelper();
		ArrayList<String> prettyString = helper.prettifyArrayList(normalString);
		for (int i = 0; i < prettyString.size(); i++){
			String temp = prettyString.get(i);
			assertTrue(Character.isUpperCase(temp.charAt(0)));
			for (int j = 0; j < temp.length(); j++){
				assertTrue(
						Character.isAlphabetic(temp.charAt(j)) || 
						Character.isDigit(temp.charAt(j)) ||
						temp.charAt(j) == ' '
						);
				if (temp.charAt(j) == ' ' && j+1 < prettyString.size()) {
					assertTrue(Character.isUpperCase(temp.charAt(j+1)));
				}
			}
		}
	}

}
