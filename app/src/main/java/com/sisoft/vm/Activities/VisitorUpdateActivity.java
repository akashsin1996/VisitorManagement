package com.sisoft.vm.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.sisoft.vm.Adapters.VisitorListAdapter;
import com.sisoft.vm.Database.DBHelper;
import com.sisoft.vm.R;
import com.sisoft.vm.VisitDetails;
import com.sisoft.vm.Visitor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VisitorUpdateActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText et_vfirstname;
    private EditText et_vlastname;
    private EditText et_vemail;
    private EditText et_vmob;
    private EditText et_vcity;
    private Button btn_visit_update;
    private RadioButton radio_male;
    private RadioButton radio_female;



    private String strVFirstName;
    private String strVLastName;
    private String strVEmail;
    private String strVMobile;
    private String strVCity;
    private String str_gender ="male";
    private String str_done ;
    private String str_deny;

    private DBHelper dbHelper;
    private Pattern pattern;
    private Pattern pattern1;

    private Visitor visitor;

    private Button btn_visit;

    private Button btn_upload;
    private byte[] imageInByte;
    private ImageView img_upload_preview;

    public final static int QRcodeWidth = 900 ;
    private static final String IMAGE_DIRECTORY = "/QRcodeDemonuts";
    Bitmap bitmap ;
    private EditText etqr;
    private ImageView iv;
    private Button btn;

    private TextView tv_no_visitor;
    private RecyclerView recyclerView;
    private ArrayList<VisitDetails> listVisitDetails = new ArrayList<>();
    private VisitDetails adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_update);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view->onBackPressed());
        toolbar.setTitle(" Review Visitor");


        pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        pattern1 = Pattern.compile("[0-9]{10}");
        iv = (ImageView) findViewById(R.id.iv);
        btn = (Button) findViewById(R.id.btn);
        btn_visit =findViewById(R.id.btn_visit);

        recyclerView=findViewById(R.id.mRecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tv_no_visitor = findViewById(R.id.tv_no_visit);
        dbHelper = new DBHelper(this);






        btn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                try {
                    String data =et_vfirstname.getText().toString()+"-"+et_vlastname.getText().toString()+"-"+
                            et_vemail.getText().toString()+"-" + ""+et_vcity.getText().toString()+"-"+et_vmob.
                            getText().toString();

                    bitmap = TextToImageEncode(data);
                    iv.setImageBitmap(bitmap);
                    String path = saveImage(bitmap);  //give read write permission
                    Toast.makeText(VisitorUpdateActivity.this, "QRCode saved to -> "+path, Toast.LENGTH_SHORT).show();
                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
            // }
        });


        btn_visit.setOnClickListener(view ->{
            Visitor v1 = dbHelper.getVisitor(strVMobile);
            String visitorID=String.valueOf(v1.getId());

            Intent intent = new Intent(VisitorUpdateActivity.this,VisitDetailActivity.class);
            intent.putExtra("visitorID",visitorID );
            startActivity(intent);
            Toast.makeText(this, "welcome :"+v1.getId(), Toast.LENGTH_SHORT).show();
            finish();
        });





        dbHelper = new DBHelper(this);

        btn_upload = findViewById(R.id.btn_upload);
        img_upload_preview = findViewById(R.id.img_upload_preview);
        visitor = getIntent().getParcelableExtra("visitor");//getting from actiivty

        et_vfirstname = findViewById(R.id.et_vfirstname);
        et_vlastname = findViewById(R.id.et_vlastname);
        et_vemail = findViewById(R.id.et_vemail);
        et_vmob = findViewById(R.id.et_vmob);
        et_vcity = findViewById(R.id.et_vcity);
        radio_female = findViewById(R.id.radio_female);
        radio_male =findViewById(R.id.radio_male);

        et_vfirstname.setText(visitor.getFirstname());
        et_vlastname.setText(visitor.getLastname());
        et_vemail.setText(visitor.getEmail());
        et_vcity.setText(visitor.getCity());
        et_vmob.setText(visitor.getMob());
        btn_visit= findViewById(R.id.btn_visit);

        if(visitor.getGender().equalsIgnoreCase("male")){
            radio_male.setChecked(true);
            str_gender = "male";
        }
        else {
            radio_female.setChecked(true);
            str_gender = "female";
        }

        if(visitor.getImageInByte()!=null){
            img_upload_preview.setVisibility(View.VISIBLE);
            img_upload_preview.setImageBitmap(getImage(visitor.getImageInByte()));
        }

        btn_visit_update = findViewById(R.id.btn_visit_update);
        btn_visit_update.setOnClickListener(view->{

            if(validateText()){
              if(dbHelper.updateVisitor(visitor.getId(),strVFirstName,strVLastName,strVEmail,
                      strVCity,strVMobile,str_gender,imageInByte,getDateAndTime(),str_deny, str_done)){
                  Toast.makeText(this, "visitor update", Toast.LENGTH_SHORT).show();
                  finish();
              }
              else Toast.makeText(this, "failed to update", Toast.LENGTH_SHORT).show();
            }

        });

        DBHelper dbHelper=new DBHelper(this);

      /*  Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> sAdapter=new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.tv_spinner, VisitDetails.visit_status);
        spinner.setAdapter(sAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ViewGroup vg=(ViewGroup)view;
                TextView tv=(TextView)vg.findViewById(R.id.tv_spinner);
                Toast.makeText(VisitorUpdateActivity.this, tv.getText().toString(), Toast.LENGTH_LONG).show();

                // Update status in database

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/




        btn_upload.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 10);
            intent.setType("image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 0);
            intent.putExtra("aspectY", 0);
            intent.putExtra("outputX", "250");
            intent.putExtra("outputY", "200");
        });

        setupRadioButtons();


    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();   //give read write permission
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }


    @Override
    public void onResume() {
        super.onResume();
        listVisitDetails = dbHelper.getVisitDetail();

        VisitorListAdapter adapter = new VisitorListAdapter(listVisitDetails,this);
        recyclerView.setAdapter(adapter);

        Toast.makeText(this,"Record count:"+adapter.getItemCount(),Toast.LENGTH_SHORT).show();
        if(adapter.getItemCount()>0){
            tv_no_visitor.setVisibility(View.GONE);
        }
        else tv_no_visitor.setVisibility(View.VISIBLE);
    }



    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 900, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }







    private Bitmap getImage(byte[] imageInByte) {
        ByteArrayInputStream inputstream = new ByteArrayInputStream(imageInByte);
        return BitmapFactory.decodeStream(inputstream);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){

            if(requestCode==10){
                assert data != null;
                Bundle extras =data.getExtras();
                if(extras!=null){
                    Bitmap yourImage =extras.getParcelable("data");
                    ByteArrayOutputStream stream= new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG,100,stream);
                    imageInByte = stream.toByteArray();
                    img_upload_preview.setImageBitmap(getImage(imageInByte));
                    img_upload_preview.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private String getDateAndTime(){

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(calendar.getTime());
    }




    private boolean validateText() {

        boolean isValid = false;

        strVFirstName = et_vfirstname.getText().toString();
        strVLastName = et_vlastname.getText().toString();
        strVEmail = et_vemail.getText().toString();
        strVCity = et_vcity.getText().toString();
        strVMobile = et_vmob.getText().toString();
        if (!strVFirstName.isEmpty()) {

            if (!strVLastName.isEmpty()) {


                if (!strVEmail.isEmpty()) {
                    if(validateVEmail(strVEmail.trim())) {

                        if (!strVCity.isEmpty()) {
                                    if (!strVMobile.isEmpty()) {
                                        if (ValidVMobile(strVMobile.trim())) {

                                            isValid = true;

                                        } else Toast.makeText(this, "contact can't be empty", Toast.LENGTH_SHORT).show();
                                    } else Toast.makeText(this, "contact is not valid", Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(this, "city can't be empty", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(this, "email can't be empty", Toast.LENGTH_SHORT).show();

                } else Toast.makeText(this, "email is not valid", Toast.LENGTH_SHORT).show();

            } else Toast.makeText(this, "last name can't be empty", Toast.LENGTH_SHORT).show();

        } else Toast.makeText(this, "first name can't be empty", Toast.LENGTH_SHORT).show();




        return isValid;

    }



    private boolean validateVEmail(String strVEmail) {
        Matcher matcher = pattern.matcher(strVEmail);
        Toast.makeText(this, "" + matcher.matches(), Toast.LENGTH_SHORT).show();
        return matcher.matches();
    }

    private boolean ValidVMobile(String strVMobile) {
        Matcher matcher =pattern1.matcher(strVMobile);
        Toast.makeText(this, ""+ matcher.matches(), Toast.LENGTH_SHORT).show();
        return matcher.matches();
    }


    private void setupRadioButtons() {

        radio_male.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                str_gender="male";
            else str_gender = "female";
        });

    }





}

