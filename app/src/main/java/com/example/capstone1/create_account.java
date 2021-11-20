package com.example.capstone1;


import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.text.LineBreaker;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
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
import java.util.Base64;
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
    EditText first, last, emailInput, gender, birthyr, height, weight;
    TextInputEditText password, confirm;
    TextView result, resultem;
    Button buttonSignUp;
    Button buttonSave;
    //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    //FirebaseDatabase root;
    DatabaseReference reference;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    String userId;
    CheckBox termsandconditions;

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
        password = findViewById(R.id.passwordBoxtxt);
        confirm = findViewById(R.id.confimpasswordtxt);
        emailInput = findViewById(R.id.emailBox);
        buttonSignUp = findViewById(R.id.btnSign);
        //gender = findViewById(R.id.editTextgender);
        birthyr = findViewById(R.id.editTextbirth);
        height = findViewById(R.id.editTextheight);
        weight = findViewById(R.id.editTextweight);
        buttonSave = findViewById(R.id.btnSave);
        termsandconditions = findViewById(R.id.TandD);

        result = findViewById(R.id.textViewresult);
        resultem = findViewById(R.id.textViewresultem);

        //root = FirebaseDatabase.getInstance();
        // reference = root.getReference("User");
        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        removeRippleEffectFromCheckBox(termsandconditions);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                widget.cancelPendingInputEvents();
                String collectBold = (char)27 + "Collection of Personal Information";

                AlertDialog.Builder aBuilder = new AlertDialog.Builder(create_account.this);
                aBuilder.setCancelable(true);
                aBuilder.setTitle("Terms and Conditions");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    aBuilder.setMessage("These terms and conditions (\"Agreement\") set forth the general terms and conditions of your use of the " +
                            "\"RemindMed: Medication Reminder and Tracker using Optical Character Recognition and Text-to-Speech\" mobile application (\"Mobile Application\" or \"Service\") " +
                            "and any of its related products and services (collectively, \"Services\"). This Agreement is legally binding between you " +
                            "(\"User\", \"you\" or \"your\") and this Mobile Application developer (\"Operator\", \"we\", \"us\" or \"our\"). " +
                            "If you are entering into this agreement on behalf of a business or other legal entity, you represent that you have the authority to bind such an entity to this agreement, " +
                            "in which case the terms \"User\", \"you\" or \"your\" shall refer to such entity. If you do not have such authority, or if you do not agree with the terms of this agreement, " +
                            "you must not accept this agreement and may not access and use the Mobile Application and Services. " +
                            "By accessing and using the Mobile Application and Services, you acknowledge that you have read, understood, and agree to be bound by the terms of this Agreement. " +
                            "You acknowledge that this Agreement is a contract between you and the Operator, " +
                            "even though it is electronic and is not physically signed by you, and it governs your use of the Mobile Application and Services.\n\n\n" + "Collection of Personal Information\n\n" +
                            "You can access and use the Mobile Application and Services without telling us who you are or revealing any information by which someone could identify you as a specific " +
                            "identifiable individual. If, however, you wish to use some of the features offered in the Mobile Application, you may be asked to" +
                            " provide certain Personal Information (for example, your name and e-mail address).\n\n" +
                            "We receive and store any information you knowingly provide to us when you create an account, or fill any forms in the Mobile Application. " +
                            "When required, this information may include the following:\n\n"+
                            "• Account details (unique user ID, password, etc)\n" +
                            "• Contact information (such as email address)\n" +
                            "• Basic personal information (such as name, year of birth, gender, height, weight)\n\n\n" +
                            "Managing information\n\n" +
                            "You are able to delete certain Personal Information we have about you. The Personal Information you can delete may change " +
                            "as the Mobile Application and Services change. When you delete Personal Information," +
                            "however, we may maintain a copy of the unrevised Personal Information in our records for the duration necessary to comply " +
                            "with our obligations to our affiliates and partners, and for the purposes described below."+
                            "If you would like to delete your Personal Information or permanently delete your account, you can do so on the settings page of your account in the Mobile Application.\n\n\n"+
                            "Information security\n\n"+
                            "We secure information you provide on computer servers in a controlled, secure environment, protected from unauthorized access, use, or disclosure."+
                            "We maintain reasonable administrative, technical, and physical safeguards in an effort to protect against unauthorized access," +
                            " use, modification, and disclosure of Personal Information in our control and custody."+
                            " However, no data transmission over the Internet or wireless network can be guaranteed.\n\n"+
                            "Therefore, while we strive to protect your Personal Information, you acknowledge that (i) there are security and privacy " +
                            "limitations of the Internet which are beyond our control; (ii) the security, integrity,"+
                            "and privacy of any and all information and data exchanged between you and the Mobile Application and Services cannot be guaranteed; and (iii) " +
                            "any such information and data may be viewed or tampered with in transit by a third party, despite best efforts.\n\n" +
                            "As the security of Personal Information depends in part on the security of the device you use to communicate with us and the security you use to protect your credentials, " +
                            "please take appropriate measures to protect this information.\n\n\n" +
                            "Data breach\n\n"+
                            "In the event we become aware that the security of the Mobile Application and Services has been compromised or Users' " +
                            "Personal Information been disclosed to unrelated third parties as a result of external activity, including, but not limited to, security attacks " +
                            "or fraud, we reserve the right to take reasonably appropriate measures," +
                            " including, but not limited to, investigation and reporting, as well as notification to and cooperation with law enforcement authorities."+
                            "In the event of a data breach, we will make reasonable efforts to notify affected individuals if we believe that there is a reasonable risk of harm to the User as " +
                            "a result of the breach or if notice is otherwise required by law. When we do, we will send you an email.\n\n\n" +
                            "Intellectual property rights\n\n"+
                            "\"Intellectual Property Rights\" means all present and future rights conferred by statute, common law or equity in or in relation to any copyright and related rights, " +
                            "trademarks, designs, patents, inventions, goodwill and the right to sue for passing off, rights to inventions, rights to use," +
                            " and all other intellectual property rights, in each case whether registered or unregistered and including all applications and rights to apply for and be granted," +
                            "rights to claim priority from, such rights and all similar or equivalent rights or forms of protection and any other results" +
                            " of intellectual activity which subsist or will subsist now or in the future in any part of the world. This Agreement does not transfer to you any intellectual property owned by the" +
                            " Operator or third parties, and all rights, titles, and interests in and to such property will remain (as between the parties) solely with the Operator." +
                            "All trademarks, service marks, graphics and logos used in connection with the Mobile Application and Services, are trademarks " +
                            "or registered trademarks of the Operator or its licensors. Other trademarks, service marks, graphics and logos used in connection with the " +
                            "Mobile Application and Services may be the trademarks of other third parties. " +
                            "Your use of the Mobile Application and Services grants you no right or license to reproduce or otherwise use any of the Operator or third party trademarks.\n\n\n" +
                            "Changes and Amendments\n\n"+
                            "We reserve the right to modify this Agreement or its terms related to the Mobile Application and Services at any time at our discretion. When we do, we will send you an email to notify you. " +
                            "We may also provide notice to you in other ways at our discretion, such as through the contact information you have provided.\n\n" +
                            "An updated version of this Agreement will be effective immediately upon the posting of the revised Agreement unless otherwise specified. " +
                            "Your continued use of the Mobile Application and Services after the effective date of the revised Agreement (or such other act specified at that time) will constitute your consent to those changes.\n\n\n" +
                            "Acceprance of These Terms\n\n" +
                            "You acknowledge that you have read this Agreement and agree to all its terms and conditions. By accessing and using the Mobile Application and Services you agree to be bound by this Agreement. " +
                            "If you do not agree to abide by the terms of this Agreement, you are not authorized to access or use the Mobile Application and Services.\n\n\n" +
                            "Contacting us\n\n" +
                            "If you have any questions, concerns, or complaints regarding this Agreement, we encourage you to contact us using the details below:\n" +
                            "\n" +
                            "RemindMedMobileApp@gmail.com\n" +
                            "\n" +
                            "This document was last updated on November 20, 2021\n" +
                            "\n");
                }

                aBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = aBuilder.show();
                TextView messageText = dialog.findViewById(android.R.id.message);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    messageText.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
                }
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // Show links with underlines (optional)
                ds.setUnderlineText(true);
            }
        };

        SpannableString linkText = new SpannableString("Terms and Conditions");
        linkText.setSpan(clickableSpan, 0, linkText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        CharSequence cs = TextUtils.expandTemplate(
                "I agree with RemindMed's: ^1 ", linkText);

        termsandconditions.setText(cs);
        // Finally, make links clickable
        termsandconditions.setMovementMethod(LinkMovementMethod.getInstance());

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
                String firstname = first.getText().toString().trim();
                String lastname = last.getText().toString().trim();
                String Confirm_Password = confirm.getText().toString().trim();

                String empass = result.getText().toString().trim();
                //String empass1 = resultem.getText().toString().trim();

                //encrypt decrypt





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
                if (Password.length() < 8) {
                    password.setError("Password must be at least 8 characters");
                    return;
                }
                if (!Password.equals(Confirm_Password)){
                    confirm.setError("Password does not match");
                    return;
                }
                if(!termsandconditions.isChecked())
                {
                    Toast.makeText(getApplicationContext(), "Please read and check our terms and conditions", Toast.LENGTH_SHORT).show();
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

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                Base64.Encoder encoder = Base64.getEncoder();
                                String encodedName = encoder.encodeToString(firstname.getBytes());
                                String encodedLastName = encoder.encodeToString(lastname.toString().getBytes());
                                user.put("firstname",encodedName);
                                user.put("lastname",encodedLastName);

                            }

                            user.put("email",Email);
                            user.put("password",empass);
                            user.put("accounttype",1);


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
    private void removeRippleEffectFromCheckBox(CheckBox checkBox) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = checkBox.getBackground();
            if (drawable instanceof RippleDrawable) {
                drawable = ((RippleDrawable) drawable).findDrawableByLayerId(0);
                checkBox.setBackground(drawable);
            }
        }
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


    public void Create_To_Main(View view) {
        Intent intent = new Intent(create_account.this, main_page.class);
        startActivity(intent);
    }

}