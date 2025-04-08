import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.security.SecureRandom;

public class Alice {
    private static final int BIT_LENGTH = 512;
    private static final BigInteger e = BigInteger.valueOf(65537); // common public exponent
    private static BigInteger n, d;

    public static void main(String[] args) throws Exception {
        // Generate primes
        SecureRandom random = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(BIT_LENGTH, random);
        BigInteger q = BigInteger.probablePrime(BIT_LENGTH, random);

        // Compute n and phi
        n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // Compute private key d
        d = e.modInverse(phi);

        System.out.println("Public key (n, e): (" + n + ", " + e + ")");
        System.out.println("Private key d: " + d);

        // Send public key to Bob
        try (Socket socket = new Socket("localhost", 5000);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            out.writeObject(n);
            out.writeObject(e);
            System.out.println("Public key sent to Bob.");
        }

        // Wait for ciphertext from Bob
        try (ServerSocket serverSocket = new ServerSocket(6000);
             Socket socket = serverSocket.accept();
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            BigInteger ciphertext = (BigInteger) in.readObject();
            System.out.println("Received ciphertext: " + ciphertext);

            // Decrypt ciphertext
            BigInteger plaintext = ciphertext.modPow(d, n);
            System.out.println("Decrypted message (as BigInteger): " + plaintext);
            System.out.println("Decrypted message (as text): " + new String(plaintext.toByteArray()));
        }
    }
}
