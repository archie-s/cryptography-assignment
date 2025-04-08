import java.io.*;
import java.math.BigInteger;
import java.net.*;

public class Bob {
    public static void main(String[] args) throws Exception {
        BigInteger n, e;

        // Receive public key from Alice
        try (ServerSocket serverSocket = new ServerSocket(5000);
             Socket socket = serverSocket.accept();
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            n = (BigInteger) in.readObject();
            e = (BigInteger) in.readObject();
            System.out.println("Received public key from Alice: (n=" + n + ", e=" + e + ")");
        }

        // User input message
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter a message to encrypt: ");
        String message = reader.readLine();
        BigInteger plaintext = new BigInteger(message.getBytes());

        // Encrypt message
        BigInteger ciphertext = plaintext.modPow(e, n);
        System.out.println("Encrypted ciphertext: " + ciphertext);

        // Send ciphertext to Alice
        try (Socket socket = new Socket("localhost", 6000);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            out.writeObject(ciphertext);
            System.out.println("Ciphertext sent to Alice.");
        }
    }
}
