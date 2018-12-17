package cipher.pack;

/**
* Caesar cipher is to replace each plaintext letter with a different one a fixed number of places down the alphabet. 
* The cipher uses a right shift of <code>key = 4</code>, so that (for example) each occurrence of A in the plaintext becomes E in the ciphertext.
*/

public class Cesar {
	/**Key to shift*/private int key=4;
	
	/**Function <code>decrypt</code> takes cipher text and decrypt to plaintext
	 * @param cipher
	 * @return plaintext
	 * */
	public String decrypt(String cipher) {		
		String plaintext="";
		int i=0;
		while(i < cipher.length()) {
			char c = cipher.charAt(i);
			int asciCode = (int)c-this.key;
			c=(char)asciCode;
			plaintext+=c;
			i++;
		}
		return plaintext;
	}
	
	/**Function <code>encrypt</code> takes plaintext text and encrypt to ciphertext
	 * @param plaintext
	 * @return ciphertext
	 * */
	public String encrypt(String plaintext) {	
		String cipher="";
		int i=0;
		while(i < plaintext.length()) {
			char c = plaintext.charAt(i);
			int asciCode = (int)c+this.key;
			c=(char)asciCode;
			cipher+=c;
			i++;
		}
		return cipher;
	}
}

