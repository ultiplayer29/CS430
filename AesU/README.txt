#Thank you for using my simple AES tool. This program uses the functionalities of openssl in order to do it's work so if you haven't downloaded this tool, please do so now. 

#openssl can be downloaded from here https://www.openssl.org/source/

#To use the AesU program follow these steps:
1. Download AesU.java
2. Place AesU.java in the bin of the openssl file system
3. Open command prompt
4. Change directory to the bin of the openssl file system
5. Compile the AesU utility using the command "javac AesU.java" without the quotation marks
6. Once compiled you can run the AesU utility by using the command "java AesU"
7. This program can take several arguments when run, the are (in order):
	0. -e/-d (encrypt or decrypt)
	1. -k
	2. <your key> (supports aes-128, aes-192, aes-256. Key must be 16, 24, or 32 bytes in hex
	3. -i (optional for ECB, mandatory for all others)
	4. <initial vector> (optional for ECB, mandatory for all others)
	5. -m
	6. <mode> (ECB/CBC,CFB,OFB,CTR)
	7. -in
	8. <input file>
	9. -out
	10. <output file>
8. If you do not wish to remember all of this or keep referring back to this README all the time, you do not have to place any arguments into the intial run of AesU. If you do not, the program will prompt you for the proper entries and then will run the encryption or decryption

Have fun! 