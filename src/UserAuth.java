import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UserAuth {

    private final Map<String, String> users = new HashMap<>();

    public boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, sha256(password));
        return true;
    }

    public boolean login(String username, String password) {
        if (!users.containsKey(username)) {
            return false;
        }
        String hashed = sha256(password);
        return users.get(username).equals(hashed);
    }

    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            // Should never happen in modern Java
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void main(String[] args) {
        UserAuth auth = new UserAuth();
        System.out.println("Register alice: " + auth.register("alice", "password123"));
        System.out.println("Login alice (correct): " + auth.login("alice", "password123"));
        System.out.println("Login alice (wrong): " + auth.login("alice", "wrongpass"));
    }
}
