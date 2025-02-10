package com.gideon.contact_manager.application.usecase.user;

import com.gideon.contact_manager.application.service.user.PasswordService;
import com.gideon.contact_manager.application.service.user.TokenValidationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {
    private final String salt;
    private final String pepper;
    private final String signingKey;
    private final int expirationDuration;
    private final int numberOfIterations;

    /**
     * Computes a hashed version of the given password by applying SHA-256 hashing
     * iteratively with salt and pepper.
     * <p>
     * The method works by taking the initial password value and, for a fixed number
     * of iterations, concatenating the current value with a salt and pepper. It then
     * computes the SHA-256 hash of the concatenated string and encodes the result in Base64.
     * The final computed value is returned as the hashed password.
     * </p>
     *
     * @param password the plain text password to be hashed.
     * @return a Base64 encoded string representing the hashed password.
     * @throws RuntimeException if an error occurs during the hashing process.
     */
    @Override
    public String ComputePasswordHash(String password){

        String result = password;
//        int numberOfIterations = 3;
        for (int i = 0; i < numberOfIterations; i++) {
            try {
                // Combine the current hash (or password) with salt and pepper
                String passwordSaltPepper = String.format("%s%s%s", result, salt, pepper);
                byte[] byteValue = passwordSaltPepper.getBytes(StandardCharsets.UTF_8);

                // Create a SHA-256 digest and compute the hash
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] byteHash = digest.digest(byteValue);

                // Convert the hash to a Base64 encoded string
                result = Base64.getEncoder().encodeToString(byteHash);
            } catch (Exception e) {
                throw new RuntimeException("Error computing password hash", e);
            }
        }
        return result;
    }

    @Override
    public String GenerateForgotPasswordToken(String email) {
        //
//        @Value("${app.passwordsource.token-expiration-minutes}")
//        int expirationDuration;
        //String signingKey = "apple";
        // Calculate expiration time by adding minutes to the current time
        Instant expirationTime = Instant.now().plus(expirationDuration, ChronoUnit.MINUTES);
        //Create the payload in the format "email:expirationTime"
        String payload = String.format("%s:%s", email, expirationTime.toString());
        byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);

        // Get the secretKey Byte
        byte[] secretKeyBytes = signingKey.getBytes(StandardCharsets.UTF_8);
        String token = "";

        try {
            // Create HMAC-SHA256 instance and initialize with the secret key
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
            hmac.init(keySpec);

            // Compute the hash for the payload
            byte[] hash = hmac.doFinal(payloadBytes);

            // Build the token: Base64(payload) + "." + Base64(hash) with '/' replaced by '_'
            token = Base64.getEncoder().encodeToString(payloadBytes)
                    + "." + Base64.getEncoder().encodeToString(hash).replace('/', '_');
            return token;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.warn("Error generating forgot password token: {}", e.getMessage());
        }
        return token;
    }

    @Override
    public TokenValidationResult ValidateResetPasswordToken(String token) {
        // Split the token into two parts separated by a dot.
        String[] parts = token.split("\\.");
        if (parts.length != 2) {
            log.info("Token is not in a valid format");
            return new TokenValidationResultImpl("", false);
        }

        // Decode the payload and hash parts from Base64.
        byte[] payloadBytes;
        byte[] hashBytes;
        try {
            payloadBytes = Base64.getDecoder().decode(parts[0]);
            // Replace '_' with '/' in the hash part as done in C#
            String hashPart = parts[1].replace('_', '/');
            hashBytes = Base64.getDecoder().decode(hashPart);
        } catch (IllegalArgumentException e) {
            log.info("Base64 decoding failed: {}", e.getMessage());
            return new TokenValidationResultImpl("", false);
        }

        // Compute HMAC-SHA256 hash using the signing key.
        try {
            Mac hmac = Mac.getInstance("HmacSHA256");
            byte[] secretKeyBytes = signingKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
            hmac.init(keySpec);
            byte[] computedHash = hmac.doFinal(payloadBytes);

            if (!Arrays.equals(computedHash, hashBytes)) {
                log.info("Computed hash does not match provided hash");
                return new TokenValidationResultImpl("", false);
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.info("Error computing HMAC: {}", e.getMessage());
            return new TokenValidationResultImpl("", false);
        }

        // Convert the payload bytes back to a string.
        String payload = new String(payloadBytes, StandardCharsets.UTF_8);
        int separatorIndex = payload.indexOf(':');
        if (separatorIndex == -1) {
            log.info("Payload is not in the correct format");
            return new TokenValidationResultImpl("", false);
        }

        String userEmail = payload.substring(0, separatorIndex);
        String expirationTimeString = payload.substring(separatorIndex + 1);

        // Parse the expiration time. Expecting ISO-8601 format.
        Instant expirationTime;
        try {
            expirationTime = Instant.parse(expirationTimeString);
        } catch (Exception e) {
            log.info("Failed to parse expiration time: {}", e.getMessage());
            return new TokenValidationResultImpl("", false);
        }

        // Check if the token has expired.
        if (Instant.now().isAfter(expirationTime)) {
            log.info("Token has expired");
            return new TokenValidationResultImpl(userEmail, false);
        }
        return new TokenValidationResultImpl(userEmail, true);
    }
}
