import java.io.*;
import java.util.*;


public class AesU {

    public static void main(String args[]) {
		
		//checks if user puts any initial arguments in. This effectively allows a completely untrained user to utelize openssl
		if(args.length == 0){
			promptUser();//method that takes in the arguments by prompting the user
		}
		else{
			opensslRunner(args);//if user is completely familiar with this program and includes proper arguments, it will just run automatically
		}
		
	}
	private static void promptUser(){

		String[] myArgs = new String[10];
		String iv; //initial vector variable created here so that the loop doesn't cause issues with it later
		Scanner in = new Scanner(System.in);//basic scanner input
		
		//prompt for encryption or decryption
		System.out.print("Encrypt/Decrypt (e/d)? ");
		String encDec = "-"+in.nextLine();
		
		//prompt for key
		System.out.print("Input Key: ");
		String key = in.nextLine();
		
		//loop checks for proper key length. Will keep prompting user until proper value input.
		while(key.length() != 32 && key.length() != 48 && key.length() != 64){
			System.out.println("Key length not supported! Key must be 16, 24, or 32 bytes.");
			System.out.print("Input Key: ");
			key = in.nextLine();
		}
		
		//prompt for the encryption/decryption mode
		System.out.print("Mode (ECB/CBC/CFB/OFB/CTR)? ");
		String mode = in.nextLine();
		mode = mode.toLowerCase();
		
		//check if mode is ecb. if it is, the prompt for initial vector is optional.
		if(mode.equals("ecb")){
			
			//prompt for initial vector
			System.out.print("Initial Vector (optional): ");
			iv = in.nextLine();
			
			//check if initial vector is proper length. Since this is optional, length of zero is also allowed
			while(iv.length() > 0 && iv.length() != 32){
				System.out.println("Invalid initial value length!");
				System.out.print("Initial Value (optional): ");
				iv = in.nextLine();
			}
		}
		
		//if mode is not ECB
		else{
			
			//prompt for initial vector
			System.out.print("Initial Vector: ");
			iv = in.nextLine();
			
			//loop will keep prompting user until initial vector is 16 bytes
			while(iv.length() != 32){
				System.out.println("Invalid initial value length!");
				System.out.print("Initial Value: ");
				iv = in.nextLine();
			}
		}
		
		//prompts for input and output files on lines 75-79
		System.out.print("Location of input file: ");
		String inputFile = in.nextLine();
		
		System.out.print("Location of output file: ");
		String outputFile = in.nextLine();
		
		//place all variables into array properly. 
		if(iv.length() > 0){
			myArgs[0] = encDec;
			myArgs[2] = key;
			myArgs[3] = "-i";
			myArgs[4] = iv;
			myArgs[6] = mode;
			myArgs[8] = inputFile;
			myArgs[10] = outputFile;
		}
		else{
			myArgs[0] = encDec;
			myArgs[2] = key;
			myArgs[4] = mode;
			myArgs[6] = inputFile;
			myArgs[8] = outputFile;
		}
		
		//set all null entities of array to empty string. this allows for proper compilation of code and use with the actual run method
		for(int x=0;x<myArgs.length;x++){
			if(myArgs[x] == null){
				myArgs[x] = "";
			}
		}
		
		//call the openssl method
		opensslRunner(myArgs);
		
		
		
	}
	
	private static void opensslRunner(String inputs[]){

        String s = null;
		
		//these two variables are created outside of the try/catch so that the method can check for an initial vector without using more memory than needed
		String openssl;
		String cipher;
        try {
    		int keylen = inputs [2].length();
      
        	// run openssl command, check for initial vector
			
			//if there is an initial vector
			if(inputs[3].equals("-i")){
				cipher = "aes-" + Integer.toString(keylen * 4) + "-" + inputs [6];
				openssl = String.format("openssl %s %s -K %s -iv %s -v -nosalt -in %s -out %s", cipher, inputs[0], inputs[2], inputs[4], inputs[8], inputs[10]);
			}
			
			//if there is no initial vector
			else{
				cipher = "aes-" + Integer.toString(keylen * 4) + "-" + inputs [4];
				openssl = String.format("openssl %s %s -K %s -v -nosalt -in %s -out %s", cipher, inputs[0], inputs[2], inputs[6], inputs[8]);}
				
				System.out.println("Command entered: ");
				System.out.println(openssl);

            // using the Runtime exec method:
            Process p = Runtime.getRuntime().exec(openssl);
            
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            
            System.exit(0);
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}