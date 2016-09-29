import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;



public class  pw_check  {
	public static void main(String args[]){
		LinkedList DLB = new LinkedList();
		if(args[0].equals("-find")){
			System.out.println("Please wait while all possible passwords are being generated!");
			System.out.println("Loading....");
			find(DLB);
			}else if(args[0].equals("-check")){
			check(DLB);
			}else{
			System.out.println("Error with the cmd line argument. Please compile with -find or -check");
		}
		
		
	}
//user compiles with check	
public static void check(LinkedList DLB){	
	File f = new File("all_passwords.txt");
	if(!f.exists()) { 
		System.out.println("Please wait while all possible passwords are being generated!");
		System.out.println("Loading....");
		find(DLB);
		System.out.println("Thank you for waiting. all_passwords.txt contains the passwords");
	}else{
		String file_name = "dictionary.txt";
		File fName = new File(file_name);
		Scanner fscan = null;;
		try {
			fscan = new Scanner(fName);
				

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String temp_String;
		//take each value in the dictionary and store it in the DLB using the precreated methods
		temp_String = fscan.next();
		char[] arr = temp_String.toCharArray();
		DLB.insertSibling((int)arr[0], DLB.size); //insert the first word into array to get tree started
		Node c1 = placeChild(DLB,(int)arr[1],DLB.first);
		c1 = placeChild(DLB, (int)arr[2],c1 );
		c1.flag = 1;
		while(fscan.hasNext()){
			
			temp_String = fscan.next();
			arr = temp_String.toCharArray();
			if((int)arr[0] < 97){//handles capital letters
				arr[0] = (char) (32+(int)arr[0]); 
			}
			Node temp=	getFirstNode(isLetterPresentSibling((int)arr[0], DLB.first), DLB.first, arr[0], DLB);
			if(arr.length == 1){
				temp.flag = 1;
			}
			for(int i = 1; i < arr.length; i++){
			temp = placeChild(DLB, (int)arr[i],temp);
			if(i+1 == arr.length){
				temp.flag = 1;
			}
			}
						Node test = DLB.first;
			

		}
	}
	LinkedList pass_DLB = new LinkedList();
	Scanner pass_scan = null;
	try {
		pass_scan = new Scanner(f);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String[] temp_arr;
	System.out.println("Loading... Please wait a minute while the Symbol Table is created (Note: This may take a little bit of time due to the size of the file)");
	while(pass_scan.hasNext()){
		String pass_line = pass_scan.nextLine();
		temp_arr = pass_line.split(",");
		String password = temp_arr[0];
		double time = Double.parseDouble(temp_arr[1]);
		char[] arr = password.toCharArray();
		Node temp=	getFirstNode(isLetterPresentSibling((int)arr[0], pass_DLB.first), pass_DLB.first, arr[0], pass_DLB);
		if(arr.length == 1){
			temp.flag = 1;
			temp.time = time;
		}
		for(int i = 1; i < arr.length; i++){
		temp = placeChild(pass_DLB, (int)arr[i],temp);
		if(i+1 == arr.length){
			temp.time = time;
			temp.flag = 1;
		}
		}
	}
	System.out.println("You may now guess passwords. When you are finished guessing passwords enter 'stop' and the program will stop");
	Scanner inScan = new Scanner(System.in);
	String password = inScan.next();
	while(!password.equals("stop")){
		if(password.length() != 5){
			System.out.println("Passwords must be length 5. Please re-enter");
			password = inScan.next();
		}
		double time = lookupTime(password, pass_DLB);
		if(time != 0){
			System.out.println("Valid Password. Took " +time + " ms to find the password");
		}else{
			//not a valid password
			System.out.println("Not a valid password!");

			printTen(pass_DLB, password);
			
			}
		password = inScan.next();
		}
		
		
	}
	
public static void printTen(LinkedList pass_DLB, String password){
	char[] temp_char_arr = password.toCharArray();
	StringBuilder prefix =  new StringBuilder();
	prefix.append(temp_char_arr[0]);
	prefix.append(temp_char_arr[1]);
	prefix.append(temp_char_arr[2]);
	prefix.append(temp_char_arr[3]);
	Node pre_node = lookupPrefix(prefix.toString(), pass_DLB);//returns the node where the prefix ends 
	Node reset_node = pre_node;
	int counter = 0;
	counter++;
	if(pre_node != null){
		pre_node = pre_node.getChild();
		prefix.append((char)pre_node.letter);
		System.out.println(prefix.toString() +"," +pre_node.time);
		prefix.deleteCharAt(4);
		while(counter < 10 && reset_node != null && pre_node != null){
			counter++;
			reset_node = pre_node.getSibling();
			if(reset_node != null){
			prefix.append((char)reset_node.letter);
			System.out.println(prefix.toString() +"," + reset_node.time);
			prefix.deleteCharAt(4);
			pre_node = pre_node.getSibling();}
			else{
				counter--;
			}
		}
	}else{
		counter--;
	}
		
		
	//size 3 prefix 
	if(counter < 10){
		prefix.deleteCharAt(3);
		pre_node = lookupPrefix(prefix.toString(), pass_DLB);//returns the node where the prefix ends 
		if(pre_node != null){
			pre_node = pre_node.getSibling();
			pre_node = pre_node.getChild();
			prefix.append((char)pre_node.letter);
			reset_node = pre_node.getChild();
			prefix.append((char)reset_node.letter);
			System.out.println(prefix.toString() +"," + reset_node.time);
			prefix.deleteCharAt(4);
			counter++;
			while(counter < 10){
			counter++;
			if(reset_node.sibling != null){
				reset_node = reset_node.getSibling();
				if(reset_node != null){
					prefix.append((char)reset_node.letter);
					System.out.println(prefix.toString() +"," + reset_node.time);
					prefix.deleteCharAt(4);
					}

			}else{
				//rest node.sibling is null
				//prefix.deleteCharAt(2);
				pre_node = pre_node.getSibling();
				reset_node = pre_node;
				prefix.insert(3,(char)pre_node.letter);
				reset_node = pre_node.getChild();
				if(reset_node != null){
					//counter--;
					prefix.insert(4,(char)reset_node.letter);
					prefix.deleteCharAt(5);
					System.out.println(prefix.toString() +"," + reset_node.time);
					prefix.deleteCharAt(4);
					}
				
			}
			
			
			}
		}
		
		}
	//size 2 prefix
	if(counter < 10){
		prefix.deleteCharAt(2);
		pre_node = lookupPrefix(prefix.toString(), pass_DLB);//returns the node where the prefix ends 
		if(pre_node != null){
			pre_node = pre_node.getSibling();
			pre_node = pre_node.getChild();
			prefix.append((char)pre_node.letter);
			pre_node = pre_node.getChild();
			prefix.append((char)pre_node.letter);
			reset_node = pre_node.getChild();
			prefix.append((char)reset_node.letter);
			System.out.println(prefix.toString() +"," + reset_node.time);
			prefix.deleteCharAt(4);
			counter++;
			while(counter < 10){
			counter++;
			if(reset_node.sibling != null){
				reset_node = reset_node.getSibling();
				if(reset_node != null){
					prefix.append((char)reset_node.letter);
					System.out.println(prefix.toString() +"," + reset_node.time);
					prefix.deleteCharAt(4);
					}

			}else{
				//rest node.sibling is null
				//prefix.deleteCharAt(2);
				pre_node = pre_node.getSibling();
				reset_node = pre_node;
				prefix.insert(3,(char)pre_node.letter);
				reset_node = pre_node.getChild();
				if(reset_node != null){
					//counter--;
					prefix.insert(4,(char)reset_node.letter);
					prefix.deleteCharAt(5);
					System.out.println(prefix.toString() +"," + reset_node.time);
					prefix.deleteCharAt(4);
					}
				
			}
			
			
			}
		}
		
		}
	//size 1 prefix
	if(counter < 10){
		prefix.deleteCharAt(1);
		pre_node = lookupPrefix(prefix.toString(), pass_DLB);//returns the node where the prefix ends 
		if(pre_node != null){
			pre_node = pre_node.getSibling();
			pre_node = pre_node.getChild();
			prefix.append((char)pre_node.letter);
			pre_node = pre_node.getChild();
			prefix.append((char)pre_node.letter);
			pre_node = pre_node.getChild();
			prefix.append((char)pre_node.letter);
			reset_node = pre_node.getChild();
			prefix.append((char)reset_node.letter);
			System.out.println(prefix.toString() +"," + reset_node.time);
			prefix.deleteCharAt(4);
			counter++;
			while(counter < 10){
			counter++;
			if(reset_node.sibling != null){
				reset_node = reset_node.getSibling();
				if(reset_node != null){
					prefix.append((char)reset_node.letter);
					System.out.println(prefix.toString() +"," + reset_node.time);
					prefix.deleteCharAt(4);
					}

			}else{
				//rest node.sibling is null
				//prefix.deleteCharAt(2);
				pre_node = pre_node.getSibling();
				reset_node = pre_node;
				prefix.insert(3,(char)pre_node.letter);
				reset_node = pre_node.getChild();
				if(reset_node != null){
					//counter--;
					prefix.insert(4,(char)reset_node.letter);
					prefix.deleteCharAt(5);
					System.out.println(prefix.toString() +"," + reset_node.time);
					prefix.deleteCharAt(4);
					}
				
			}
			
			
			}
		}
		
		}
	//one letter prefix
	
}
	
//if user compiles with find
public static void find(LinkedList DLB){
	String file_name = "dictionary.txt";
	//FileReader fileReader = new FileReader(file_name);
	//BufferedReader file = new BufferedReader(fileReader);
    File fName = new File(file_name);
	Scanner fscan = null;;
	try {
		fscan = new Scanner(fName);
			

	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	String temp_String;
	//take each value in the dictionary and store it in the DLB using the precreated methods
	temp_String = fscan.next();
	char[] arr = temp_String.toCharArray();
	DLB.insertSibling((int)arr[0], DLB.size); //insert the first word into array to get tree started
	Node c1 = placeChild(DLB,(int)arr[1],DLB.first);
	c1 = placeChild(DLB, (int)arr[2],c1 );
	c1.flag = 1;
	while(fscan.hasNext()){
		
		temp_String = fscan.next();
		arr = temp_String.toCharArray();
		if((int)arr[0] < 97){//handles capital letters
			arr[0] = (char) (32+(int)arr[0]); 
		}
		Node temp=	getFirstNode(isLetterPresentSibling((int)arr[0], DLB.first), DLB.first, arr[0], DLB);
		if(arr.length == 1){
			temp.flag = 1;
		}
		for(int i = 1; i < arr.length; i++){
		temp = placeChild(DLB, (int)arr[i],temp);
		if(i+1 == arr.length){
			temp.flag = 1;
		}
		}
					Node test = DLB.first;
		

	}
	//check password
	/*String pass = "bbb0$";
	if(checkDictionary(pass, DLB)){
		System.out.println("Valid");
	}else{
		System.out.println("Not valid");
	}*/
	//creates an array with all the possible combinations for a password. All letters, numbers, and valid symbols. WE take out letter i and 1 as well as 'a' and '4'
	int[]  possible_combinations= new int[]{98,99,100,101,102,103,104,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,48,50,51,53,54,55,56,57,33,64,36,94,95,42};
	StringBuilder s2 = new StringBuilder();
	File file = new File("all_passwords.txt");
	FileWriter fw = null;
	try {
		fw = new FileWriter(file.getAbsoluteFile());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	BufferedWriter bw = new BufferedWriter(fw);
	getValidPasswords(DLB, s2, possible_combinations, bw);
	try {
		bw.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
//checks the dictionary to see if the password is valid. If true is returned than the password is valid and should be written to the file
// if false is returned than the password is not valid and next password should be checked
public static boolean checkDictionary(String password, LinkedList l){
	char[] password_arr= password.toCharArray();
	int flag = 0;
	StringBuilder str = new StringBuilder();
	int letter_count = 0;
	int symbol_count = 0;
	int number_count = 0;
	for(int i  = 0; i < password_arr.length; i++){
		//the password cannot contain characters i,1,a,4 based on the rules. 
		//the dictionary contains characters "i" and "a" as words so they cannot be used in the dictionary
		//the rules also state that 1 and 4 are substitutes for i and a respectively so they would be both be invalid 
		if((int)password_arr[i] == 105 ||(int)password_arr[i] == 49 || (int)password_arr[i] == 97 || (int)password_arr[i] == 52){
			return false;
		}
		
		
		int x = (int)password_arr[i];// x= letter
		if(x>=97 && x<122){
			letter_count++;
		}
		if(x>=48 && x<=57){
			number_count++;
		}
		if(x==33 || x==64||x==36||x==94 || x==95 || x==42){
			symbol_count++;
		}
		if(letter_count > 3){
			return false;
		}
		if(number_count > 2){
			return false;
		}if(symbol_count > 2){
			return false;
		}
		//this checks to see if it is a valid letter. If it passes this test add it to the stringbuilder. 
		if((x >= 97 && x <=122)|| x == 36 ||x== 55 ||x == 48 ||x == 51){
		
			if(x == 36){
			//value is $ so change it to letter s for our tests
			x=115;
			}else if(x == 55){
				//value is 7 so change it to t for our tests
				x=116;
			}else if(x == 48){
				//value is 0 so change it to o for our tests
				x=111;
			}else if(x == 51){
				//value is 3 so change it to e for our tests
				x=101;
			}
			str.append((char)x);
			flag++;
			
			if(flag == 2){
				if(lookup(str.toString(), l)){
					//word is found 
					return false;
				}
			}else if(flag == 3){
				if(lookup(str.toString(), l)){
					//word is found 
					return false;
				}
			}else if(flag == 4){
				if(lookup(str.toString(), l)){
					//word is found 
					return false;}
			}else if(flag == 5){
				if(lookup(str.toString(), l)){
					//word is found 
					return false;}
			}
		}
		else{
			//if it is not a letter or sub
			flag = 0;
			str = new StringBuilder();
			
		}
		
		
	}
		
	
	
	//all valid passwords write to pass.txt, not valid skip out
	if((letter_count >= 1 && letter_count <=3) && (number_count == 1 || number_count == 2) && (symbol_count == 1 || symbol_count == 2)){
		return true;
	}else{
		return false;
	}
	
}
public static boolean lookup(String s, LinkedList l){
	char[] str = s.toCharArray();
	Node x = lookupSiblingNode(isLetterPresentSibling((int)str[0], l.first), l.first, (int)str[0]);
	Node temp;
	if(x == null){
		return false;
	}
	//x contains the node which contains the first letter of the array
	for(int i = 1; i < str.length; i++){
		//ensure flag == 1
		temp = lookupChildNode(isLetterPresentChild((int)str[i], x), x, (int)str[i]);
		//see if flag is 1
		if(temp != null){
			if(temp.flag == 1){
				return true;
			}
		}
		if(temp == null){
			if(x.getChild() == null){
				return false;
			}else{
			
				temp = x.getChild();
			}
		}
		temp = lookupSiblingNode(isLetterPresentSibling((int)str[i], temp), temp, (int)str[i]);
		if(temp == null){
			return false;
		}
		if(temp != null){
			if(temp.flag == 1 && (i+1 == str.length)){
				return true;
			}
		}
		 x= temp;
	}
	return false;
	
}
public static Node lookupPrefix(String s, LinkedList l){
	char[] str = s.toCharArray();
	
	Node x = lookupSiblingNode(isLetterPresentSibling((int)str[0], l.first), l.first, (int)str[0]);
	Node temp;
	if(x == null){
		return null;
	}
	//x contains the node which contains the first letter of the array
	for(int i = 1; i < str.length; i++){
		temp = lookupChildNode(isLetterPresentChild((int)str[i], x), x, (int)str[i]);
		if(temp != null && i+1 == str.length){
			
				return temp;
			
		}
		if(temp == null){
			if(x.getChild() == null){
				return null;
			}else{
			
				temp = x.getChild();
			}
			temp = lookupSiblingNode(isLetterPresentSibling((int)str[i], temp), temp, (int)str[i]);
			if(temp == null){
				return null;
			}
			if(temp != null){
				if((i+1 == str.length)){
					return temp;
				}
			}
			
		}
		x= temp;
	}
	return x;
	
}

public static double lookupTime(String s, LinkedList l){
	char[] str = s.toCharArray();
	Node x = lookupSiblingNode(isLetterPresentSibling((int)str[0], l.first), l.first, (int)str[0]);
	Node temp;
	if(x == null){
		return 0;
	}
	//x contains the node which contains the first letter of the array
	for(int i = 1; i < str.length; i++){
		//ensure flag == 1
		temp = lookupChildNode(isLetterPresentChild((int)str[i], x), x, (int)str[i]);
		//see if flag is 1
		if(temp != null){
			if(temp.flag == 1){
				return temp.time;
			}
		}
		if(temp == null){
			if(x.getChild() == null){
				return 0;
			}else{
			
				temp = x.getChild();
			}
			temp = lookupSiblingNode(isLetterPresentSibling((int)str[i], temp), temp, (int)str[i]);
			if(temp == null){
				return 0;
			}
			if(temp != null){
				if(temp.flag == 1 && (i+1 == str.length)){
					return temp.time;
				}
			}
			
		}
		x= temp;
		 
	}
	return 0;
	
}
//places the child if it is null or checks sibling if present 	
public static Node placeChild(LinkedList d, int value, Node n){
	if(n.getChild() == null ){
		Node temp = new Node(value, null, null, 0,0);
		d.setChild(n,temp);
		return d.getChild(n);
		
	}else if(n.getChild().letter == value){
		//value is the same so return the child
		return d.getChild(n);
	}
	else{
		Node temp = n.getChild();//get the child of the letter we are looking at
		if(temp.sibling == null){
			return placeSibling(temp, d, value);
		}
		else if(temp.sibling.letter == value){
			return temp.getSibling();
		}else{
			return placeSibling(temp, d, value);
		}
		
	}
}
public static Node placeSibling(Node n, LinkedList d, int value){
	if(n.getSibling() == null){
		Node temp = new Node(value, null, null, 0,0);
		d.setSibling(n, temp);
		return d.getSibling(n);
	}else if(n.getSibling().letter == value){
		//value is in the sibling
		return n.getSibling();
	}else{
		return placeSibling(d.getSibling(n), d, value);
	}
	
}

//key idea of this is that we are just looking up the values not setting them at alLinkedListl. This function will look at the string we pass and see if it is in the DLB
public static Node lookupSiblingNode(boolean letterPresent,Node n, int value){
	if(!letterPresent){
		
			if(n.sibling == null){
				return null;
			}else{
				return lookupSiblingNode(isLetterPresentSibling(value, n.sibling), n.sibling, value);	
			}
			
				
	
	
	}else{
		if(n.letter == value){
	
			return n;
			
		}else if(n.getSibling().letter == value){
			
			return n.getSibling();
		}else{
			return lookupSiblingNode(isLetterPresentSibling(value, n.sibling), n.sibling, value);

		}
		
	}	
}
/*Same thing as lookup sibling but for the child node*/
public static Node lookupChildNode(boolean letterPresent,Node n, int value){
	if(!letterPresent){
		
		if(n.child == null){
			return null;
		}
		else{
			
			//the child node is not equal but we will return it anyway to test the sibling nodes
			
			return null;//return the child			
				
	
		}
	}else{
		 if(n.getChild().letter == value){
			
			return n.getChild();
		}else{
			System.out.println("SOMETHING WENT WRONG WITH CODE!! THIS LINE SHOULD NOT PRINT. CHECK lookupSiblingNode or getNode()");
			return null;	
		}
		
	}	
}
/*This method will recursively look at the nodes to determine where to place the letter
 * First the method isLetterPresent has to be ran and the value stored inside of the method so we know if we should be checking for null values or if we are looking at adding to the current node
 * If the letter is not  present than we need to check for the node being null or its sibling to be null
 * 		If neither of these is the case than we need to set the node as the next sibling and repeat the process again until we find the letter being present or a null sibling or child
 * 
 * If the letter is present than we need to look first at the current node to see if that is what matches the value. If it does not than we know the sibling is the match
 * 		If the value matches one of these values than we can move onto the next letter in the array based off of the node returned.  
 * 
 * Remember that this functon is only checking for the first node of the string. So it never has to look at the children. If we looked at children there would be some issues becuase we dont
 * want it to look down, only to the right
 * 
 * The program that will check for both the children and sibling nodes (ex. to lookup a value) is called lookupNode()
 * 
 * if flag = 0 than it is in the mode to set the sibling 
 * if flag = 1 than it is in the mode to just look it up*/	
public static Node getFirstNode(boolean letterPresent,Node n, int value, LinkedList l){
	if(!letterPresent){
		if(n == null){
			Node temp = new Node(value, null, null, 0,0);
			return l.first = temp;
		}else if(n.sibling == null){
			Node temp = new Node(value, null, null, 0,0);
			l.setSibling(n, temp);
			//l.insertSibling(value, l.size);
			//.setSibling(temp);
			return l.getSibling(n);
		}
		else{
			
		//letter is not present and it has children and sibling 
			
			n = n.getSibling();//set n to the sibling
			return getFirstNode(isLetterPresentSibling(value, n), n, value, l);
				
	
		}
	}else{
		if(n.letter == value){
			//n.flag = 1;
			//Node temp = new Node(value, null, null, 0); 
			//n.setSibling(temp);
			return n;
			
		}else if(n.getSibling().letter == value){
			//n.flag = 2;
			//Node temp = new Node(value, null, null, 0); 
			//n.setSibling(temp);
			return n.getSibling();
		}else{
			System.out.println("SOMETHING WENT WRONG WITH CODE!! THIS LINE SHOULD NOT PRINT. CHECK GETNODE()");
			return null;	
		}
		
	}
	
}

/*This method is taking the current node and checking to see if it contains the letter that is being checked or if the sibling does
 * If it does in fact contain the value than we are able to move based off of that
 * If it does not include the value than we have to keep checking siblings until there are not any left
 * Each sibling check we also have to check the child
 * If after checking all siblings and all child nodes there are still not any matches on the value than we have to add a new node in the appropriate place (SEE OTHER Method)
 * 
 * Note this is the sibling version to see if the sibling nodes contain the letter. There is also a version for the children node which is used for lookups*/	
public static boolean isLetterPresentSibling(int value, Node n){
	if(n == null){
		return false;	
		}else{
		if(n.letter == value){
			return true;
		}else if(n.getSibling() != null){
			if(n.getSibling().letter == value){
			return true;}
			else{
				return false;
			}
		}else{
		return isLetterPresentSibling(value, n.sibling);
		}
		}
	
}

public static boolean isLetterPresentChild(int value, Node n){
	if(n == null){
	return false;	
	}else{
		if(n.getChild() != null){
		if(n.getChild().letter == value){
		return true;}
		else{
			return false;
		}
	}else{
	return false;
	}
	}
}
//Tried a recursive method but stack becomes to large for this. For this problem an itterative solution is the most efficient 
public static void getValidPasswords(LinkedList l, StringBuilder str, int[] letters,BufferedWriter bw){
	
	for(int i = 0; i < letters.length; i++){
		str.append((char)letters[i]);
		for(int j = 0; j < letters.length; j++){
			str.append((char)letters[j]);
			for(int k = 0; k < letters.length; k++){
				str.append((char)letters[k]);
				for(int m = 0; m < letters.length; m++){
					str.append((char)letters[m]);
					for(int n = 0; n < letters.length; n++){
						str.append((char)letters[n]);
						double time = System.nanoTime();
						boolean dictionary = checkDictionary(str.toString(), l);
						time = System.nanoTime() - time;
						time = time /1000000;
						if(dictionary){
							try {
								bw.write(str.toString());
								bw.write(",");
								String time_string = Double.toString(time);
								bw.write(time_string);
								bw.newLine();
							} catch (IOException e) {
								e.printStackTrace();
							}	

						}
						str.deleteCharAt(4);
					}
					str.deleteCharAt(3);

				}
				str.deleteCharAt(2);

			}
			str.deleteCharAt(1);

		}
		str.deleteCharAt(0);

	}
	
	
	
}
}