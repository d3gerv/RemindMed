package com.example.capstone1;


import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.crypto.KeyGenerator;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
//import com.google.firebase.database.FirebaseDatabase;

public class create_account extends AppCompatActivity {
    private  static final String UNICODE_FORMAT = "UTF-8";

    public static final String TAG = "TAG";
    EditText first, last, password, confirm, emailInput, gender, birthyr, height, weight;
    TextView result, resultem;
    Button buttonSignUp;
    Button buttonSave;
    //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    //FirebaseDatabase root;
    DatabaseReference reference;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    String userId;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = rootAuthen.getCurrentUser();
        if(currentUser != null){
            //reload();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        first = findViewById(R.id.first_nameBox);
        last = findViewById(R.id.last_nameBox);
        password = findViewById(R.id.passwordBox);
        confirm = findViewById(R.id.confimpassword);
        emailInput = findViewById(R.id.emailBox);
        buttonSignUp = findViewById(R.id.btnSign);
        //gender = findViewById(R.id.editTextgender);
        birthyr = findViewById(R.id.editTextbirth);
        height = findViewById(R.id.editTextheight);
        weight = findViewById(R.id.editTextweight);
        buttonSave = findViewById(R.id.btnSave);

        result = findViewById(R.id.textViewresult);
        resultem = findViewById(R.id.textViewresultem);

        //root = FirebaseDatabase.getInstance();
        // reference = root.getReference("User");
        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        if (rootAuthen.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), main_page.class));
            finish();
        }

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computeMD5Hash(password.toString());
                //computeMD5Hashem(emailInput.toString());
                String Email = emailInput.getText().toString().trim();
                String Password = password.getText().toString().trim();
                String Confirm_Password = confirm.getText().toString().trim();
                String firstname = first.getText().toString().trim();
                String lastname = last.getText().toString().trim();
                String empass = result.getText().toString().trim();
                //String empass1 = resultem.getText().toString().trim();

                //encrypt decrypt
                try
                {
                    SecretKey key = generateKey("AES");
                    Cipher chipher;
                    chipher = Cipher.getInstance("AES");

                    byte[] encryptedData = encryptString(Email, key, chipher);
                    String encryptedString = new String(encryptedData);
                    emailInput.setText(encryptedString);
                    String decrypted = decryptString(encryptedData,key,chipher);
                    emailInput.setText(decrypted);
                }
                catch (Exception e)
                {

                }



                if (TextUtils.isEmpty(Email)) {
                    emailInput.setError("Email is required");
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    emailInput.setError("Enter a valid email");
                }
                if (TextUtils.isEmpty(Confirm_Password)) {
                    confirm.setError("Confirm Password is required");
                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    password.setError("Password is required");
                    return;
                }
                if (Password.length() < 6) {
                    password.setError("Password must be at least 6 characters");
                    return;
                }
                if (!Password.equals(Confirm_Password)){
                    confirm.setError("Password does not match");
                    return;
                }

                rootAuthen.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verify email
                            rootAuthen.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                    }else {
                                        Toast.makeText(create_account.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });





                            Toast.makeText(create_account.this, "Please check your email for verification", Toast.LENGTH_SHORT).show();
                            userId = rootAuthen.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("users").document(userId);
                            Map<String,Object> user = new HashMap<>();
                            user.put("firstname",firstname);
                            user.put("lastname",lastname);
                            user.put("email",Email);
                            user.put("password",empass);


                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onSuccess: user profile is created for " + userId);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), main_page.class));
                        } else {
                            Toast.makeText(create_account.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
    }
    public void computeMD5Hash(String password)
    {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer MD5Hash = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[1]);
                while (h.length() < 2)
                    h = "0" + h;
                MD5Hash.append(h);
            }
            result.setText( MD5Hash);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    //encrypt decrypt
    public  static SecretKey generateKey(String encryptionType)
    {
        try
        {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(encryptionType);
            SecretKey myKey = keyGenerator.generateKey();
            return myKey;

        }catch (Exception e)
        {
            return null;
        }

    }

    //encrypt
    public static byte[] encryptString(String dataToEncrypt, SecretKey myKey, Cipher cipher)
    {
        try
        {
            byte[] Email = dataToEncrypt.getBytes(UNICODE_FORMAT);
            cipher.init(Cipher.ENCRYPT_MODE, myKey);
            byte[] textEncrypted = cipher.doFinal(Email);

            return textEncrypted;
        }catch (Exception e)
        {
            return null;
        }

    }

    //decrypt
    public static String decryptString(byte[] dataToDecrypt, SecretKey myKey, Cipher cipher)
    {
        try
        {
            cipher.init(Cipher.DECRYPT_MODE, myKey);
            byte[] textDecrypted = cipher.doFinal(dataToDecrypt);
            String result = new String(textDecrypted);

            return result;
        }catch (Exception e)
        {
            return null;
        }
    }

    public void Create_To_Main(View view) {
        Intent intent = new Intent(create_account.this, main_page.class);
        startActivity(intent);
    }

}