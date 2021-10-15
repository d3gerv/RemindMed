package com.example.capstone1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class pdf_pulserate extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<measurment_info> myArrayList;
    pdfAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    TableLayout tableLayout;
    Button savePDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_pulserate);

        tableLayout = (TableLayout) findViewById(R.id.tableLayoutHeader);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Fetching Data...");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        myArrayList = new ArrayList<measurment_info>();
        myAdapter = new pdfAdapter(pdf_pulserate.this, myArrayList);

        showTable();
        recyclerView.setAdapter(myAdapter);

        tableLayout.setDrawingCacheEnabled(true);
        tableLayout.layout(0, 0, tableLayout.getWidth(), tableLayout.getHeight());
        tableLayout.buildDrawingCache(true);


        savePDF = (Button) findViewById(R.id.btnSavePDF_HOS);
        savePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b = Bitmap.createBitmap(tableLayout.getDrawingCache());
                createPdf( v, getScreenshotFromRecyclerView(recyclerView), b);
            }
        });
    }

    private void showTable() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        db.document("users/" + currentFirebaseUser.getUid()).collection("New Health Measurements")
                .document("Pulserate").collection("Pulserate")
                .orderBy("Time", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Firestore error", e.getMessage());
                    return;
                }
                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        myArrayList.add(dc.getDocument().toObject(measurment_info.class));
                    }
                    myAdapter.notifyDataSetChanged();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }
        });
    }



    public Bitmap getScreenshotFromRecyclerView(RecyclerView view) {
        RecyclerView.Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {

                    bitmaCache.put(String.valueOf(i), drawingCache);
                }
//                holder.itemView.setDrawingCacheEnabled(false);
//                holder.itemView.destroyDrawingCache();
                height += holder.itemView.getMeasuredHeight();
            }
            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            bigCanvas.drawColor(Color.WHITE);

            for (int i = 0; i < size; i++) {
                Bitmap bitmap = bitmaCache.get(String.valueOf(i));
                bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
                iHeight += bitmap.getHeight();
                bitmap.recycle();
            }

        }
        return bigBitmap;
    }

    private void createPdf(View title, Bitmap measurements, Bitmap header){
        // create a new document
        PdfDocument document = new PdfDocument();
        // crate a page description
        int height = title.getHeight() + measurements.getHeight();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(measurements.getWidth(), height, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint forLinePaint = new Paint();
        Paint paint = new Paint();

        paint.setColor(Color.BLACK);
        paint.setTextSize(60f);
        canvas.drawText("RemindMed",20,60,paint);
        paint.setTextSize(30.5f);
        canvas.drawText("Monthly report for Pulserate",20,90,paint);
        forLinePaint.setStyle(Paint.Style.STROKE);
        forLinePaint.setPathEffect(new DashPathEffect(new float[]{5,5},0));
        forLinePaint.setStrokeWidth(2);
        canvas.drawLine(20,95,1000,95,forLinePaint);
        canvas.drawBitmap(header, null, new Rect(0, 0, measurements.getWidth()-200,300), null);
        canvas.drawBitmap(measurements, null, new Rect(0, title.getHeight()+100, measurements.getWidth()-200,measurements.getHeight()), null);
        // finish the page
        document.finishPage(page);
        // draw text on the graphics object of the page

        // write the document content
        File folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(folder,"RemindMedPulse.pdf");
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = folder + "RemindMedPulse"  + ".pdf";
        File filePath = new File(this.getExternalFilesDir("/"), "RemindMedPulse.pdf");
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Exported to PDF", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }
    public void pdfpulse_To_hhm(View view) {
        Intent intent = new Intent(pdf_pulserate.this, health_measurement_pdf_choices.class);
        startActivity(intent);
    }
}