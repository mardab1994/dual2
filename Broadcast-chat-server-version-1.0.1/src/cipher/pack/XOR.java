
package cipher.pack;
	/**XOR class to make cipher xor mode. */
public class XOR {
	/**Key to XOR*/private String key="KCQDA";
	public XOR() {}

	/**Function <code>encryptDecrypt</code> takes text XOR with <code>key</code> and return plaintext or cipher text
	 * @param input (plaintext or ciphertext)
	 * @return output (plaintext or ciphertext)
	 * */
	public String encryptDecrypt(String input) {
		StringBuilder output = new StringBuilder();	
		for(int i = 0; i < input.length(); i++) {
			output.append((char) (input.charAt(i) ^ key.charAt(i % key.length())));
		}
		return output.toString();
	}
}

