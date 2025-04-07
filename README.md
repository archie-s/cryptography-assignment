# cryptography-assignment
APT3090 Cryptography class group project

# Bob receiving public key
import socket

def bob_encrypt(plaintext, public_key):
    """Encrypts a plaintext message using RSA."""
    e, n = public_key
    ciphertext = pow(plaintext, e, n)
    return ciphertext

def bob_server():
    """Implements Bob's server role."""
    host = '127.0.0.1'  # Localhost
    port = 65432        # Port to listen on (non-privileged ports are > 1023)

    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind((host, port))
        s.listen()
        print(f"Bob listening on {host}:{port}")
        conn, addr = s.accept()
        with conn:
            print(f"Connected by {addr}")
            # Receive public key from Alice
            public_key_data = conn.recv(1024)
            public_key_str = public_key_data.decode()
            e_str, n_str = public_key_str.split(',')
            public_key = (int(e_str), int(n_str))
            print(f"Received public key: {public_key}")

            # Get plaintext input from user
            plaintext_str = input("Enter plaintext message: ")
            try:
                plaintext = int.from_bytes(plaintext_str.encode(), 'big') #encode the string to bytes, then convert to integer.
            except ValueError:
                print("Plaintext must be convertible to an integer. Terminating")
                return

            # Encrypt the message
            ciphertext = bob_encrypt(plaintext, public_key)
            print(f"Ciphertext: {ciphertext}")
            # Send ciphertext to Alice
            conn.sendall(str(ciphertext).encode())

if __name__ == "__main__":
    bob_server()
