# cryptography-assignment
APT3090 Cryptography class group project

# RSA Encryption over TCP in Java

This project implements the **RSA public-key encryption algorithm** and demonstrates secure communication between two entities, **Alice** and **Bob**, using **Java TCP sockets**. Alice generates a public-private key pair and shares the public key with Bob. Bob uses this key to encrypt a message and sends it back to Alice for decryption.

## Features

- RSA key pair generation with 1024-bit key size
- Secure random prime generation using `SecureRandom`
- Public and private key creation
- Java socket programming with TCP
- Bidirectional communication:
  - Alice sends the public key to Bob
  - Bob sends the encrypted message to Alice

## How It Works

### Alice:
- Generates two 512-bit primes `p` and `q`
- Computes modulus `n = p * q` and totient φ(n)
- Chooses public exponent `e = 65537`
- Calculates private exponent `d = e⁻¹ mod φ(n)`
- Sends `(n, e)` to Bob over TCP socket (port 5000)
- Receives ciphertext from Bob over TCP socket (port 6000)
- Decrypts ciphertext using private key `d`

### Bob:
- Listens on port 5000 to receive `(n, e)` from Alice
- Accepts user input message
- Encrypts the message using `C = M^e mod n`
- Sends ciphertext `C` to Alice over port 6000

## 📁 Project Structure


