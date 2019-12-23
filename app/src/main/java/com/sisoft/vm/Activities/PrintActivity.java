package com.sisoft.vm.Activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sisoft.vm.Adapters.VisitDetailAdapter;
import com.sisoft.vm.Database.DBHelper;
import com.sisoft.vm.R;
import com.sisoft.vm.VisitDetails;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PrintActivity extends AppCompatActivity {


    private LinearLayout ll_1;

    private Button btn_print;

    private LinearLayout ll_2;


    private ImageView img_qr;

    private TextView tv_visitor_name;
    private TextView tv_mob;
    private TextView tv_meet_to;
    private TextView tv_date;
    private LinearLayout ll_3;


    private TextView tv_office;
    private TextView tv_visitor_sign;
    private LinearLayout ll_4;

    private VisitDetails visitDetails;

    private LinearLayout ll_pdf;
    private Button btn_pdf;
    private Bitmap bitmap1;

    private ScrollView llScroll;

    private byte[] imageInByte;
    private ImageView img_dp;


    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        ll_1 =findViewById(R.id.ll_1);

        btn_print =findViewById(R.id.btn_print);
        btn_pdf =findViewById(R.id.btn_pdf);
        ll_2 =findViewById(R.id.ll_2);

        img_dp =findViewById(R.id.img_dp);
        img_qr =findViewById(R.id.img_qr);

        tv_visitor_name =findViewById(R.id.tv_visitor_name);
        tv_mob =findViewById(R.id.tv_mob);
        tv_meet_to =findViewById(R.id.tv_meet_to);
        tv_date =findViewById(R.id.tv_date);
        ll_3 =findViewById(R.id.ll_3);

        tv_office =findViewById(R.id.tv_office);
        tv_visitor_sign =findViewById(R.id.tv_visitor_sign);
        ll_4 =findViewById(R.id.ll_4);

        ll_pdf = findViewById(R.id.ll_pdf);
        llScroll = findViewById(R.id.llScroll);





        dbHelper = new DBHelper(this);

        visitDetails = getIntent().getParcelableExtra("visitDetails");
        Toast.makeText(this, "check"+visitDetails.getVisitor_name(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+visitDetails.getId(), Toast.LENGTH_SHORT).show();

        tv_visitor_name.setText(visitDetails.getVisitor_name());
        tv_mob.setText(visitDetails.getVisitor_mob());
        tv_meet_to.setText(visitDetails.getMeet_to());
        tv_date.setText(visitDetails.getVisit_date());




        btn_pdf.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Log.d("size"," "+llScroll.getWidth() +"  "+llScroll.getWidth());
                bitmap1 = loadBitmapFromView(llScroll, llScroll.getWidth(), llScroll.getHeight());
                createPdf();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap1 = Bitmap.createScaledBitmap(bitmap1, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap1, 0, 0 , null);
        document.finishPage(page);

        // write the document content
        String targetPdf = "/sdcard/Mvisitor.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPDF();
    }

    private void openGeneratedPDF() {
        File file = new File("/sdcard/Mvisitor.pdf");
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(PrintActivity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }

    private Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbHelper = new DBHelper(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dbHelper!=null){
            dbHelper.close();
        }
    }

}


