package com.example.capstone1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Faq extends AppCompatActivity {

   // public CardView card1, card2, card3;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    FirebaseAuth rootAuthen;
    String userId;
    public static final String TAG = "TAG";
    ImageButton back;
    long accounttype ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        back = findViewById(R.id.imageBack);
        rootAuthen = FirebaseAuth.getInstance();
        userId = rootAuthen.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("users").document(userId);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentReference.addSnapshotListener(Faq.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "listen:error", error);
                            return;
                        }
                        try {
                            accounttype = value.getLong("accounttype");
                            Log.d("TAG","ID: "+ accounttype);

                            Log.d("TAG", "tag: " + accounttype);
                            if (accounttype == 1)
                            {
                                Intent intent = new Intent(Faq.this, user_information.class);
                                startActivity(intent);
                            }
                            else if (accounttype == 2)
                            {
                                Intent intent = new Intent(Faq.this, guestLogout.class);
                                startActivity(intent);
                            }
                        }catch (Exception e){
                            Intent intent = new Intent(Faq.this, guestLogout.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });

     /*   card1 = (CardView) findViewById(R.id.FAQ_OCR);
        card2 = (CardView) findViewById(R.id.FAQ_TTS);
        card3 = (CardView) findViewById(R.id.FAQ_type);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Faq.this, OCR_FAQ.class);
                startActivity(intent);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Faq.this, TTS_FAQ.class);
                startActivity(intent);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Faq.this, TypeUnit_FAQ.class);
                startActivity(intent);
            }
        });*/

    }

    public void Faq_To_User(View view) {
        Intent intent = new Intent(Faq.this, user_information.class);
        startActivity(intent);
    }

    public void FAQOCR_Faq(View view) {
        Intent intent = new Intent(Faq.this, OCR_FAQ.class);
        startActivity(intent);
    }
    public void FAQTTS_Faq(View view) {
        Intent intent = new Intent(Faq.this, FAQTTS.class);
        startActivity(intent);
    }
    public void FAQUnit_Faq(View view) {
        Intent intent = new Intent(Faq.this, TYPE_FAQ.class);
        startActivity(intent);
    }
}