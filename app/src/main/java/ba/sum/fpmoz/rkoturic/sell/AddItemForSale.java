package ba.sum.fpmoz.rkoturic.sell;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import ba.sum.fpmoz.rkoturic.sell.model.Upload;

public class AddItemForSale extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView productImg;
    private ImageView chooseProductBtn,addProductBtn, showProductsBtn;
    private EditText productName, productDesc, productPrice;
    private ProgressBar uploadProgress;

    private FirebaseStorage storage;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;


    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_for_sale);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange)));

        productImg = findViewById(R.id.productImg);
        chooseProductBtn = findViewById(R.id.chooseProductBtn);
        addProductBtn = findViewById(R.id.addProductBtn);
        showProductsBtn = findViewById(R.id.showProductsBtn);
        productName = findViewById(R.id.productName);
        productDesc = findViewById(R.id.productDesc);
        productPrice = findViewById(R.id.productPrice);
        uploadProgress = findViewById(R.id.uploadProgress);

        storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        chooseProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        showProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProductsActivity.class);
                startActivity(i);
            }
        });

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(AddItemForSale.this,"Upload in progress", Toast.LENGTH_SHORT).show();
                }else {
                    //Metoda za dodavanje datoteka u firebase storage
                    uploadFile();
                }
            }
        });
    }

    private void openFileChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    //Metoda koja se poziva kada izaberemo datoteku za upload
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(productImg);
            //productImg.setImageURI(mImageUri);
        }
    }

    //POgledati kasnije na guglu... Ova metoda vraće ekstenziju slike(.jpg)
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
       //Ako nije null izabrali smo sliku
        if(mImageUri != null){
            //Postavlja unikatan naziv preko svakoj datoteci preko milisekunda... Naziv izgleda ovako velikBroj.jpg
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Handler koristimo za uploadBar da ga ne vraćemo odmah na nulu
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            uploadProgress.setProgress(0);
                        }
                    }, 1000);

                    Toast.makeText(AddItemForSale.this, "Upload successful", Toast.LENGTH_LONG).show();

                    /*storageReference.child("YOUR_CHILD")
    .putFile("FILE")
    .addOnSuccessListener(new OnSuccessListener() {
       @Override
       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
             taskSnapshot
               .getStorage()
               .getDownloadUrl()
               .addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //Put your result here
                                }
                            });

                   }   */
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();

                    final String sdownload_url = String.valueOf(downloadUrl);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String userInfo = user.getUid();

                    Upload upload = new Upload(productName.getText().toString().trim(),
                            productDesc.getText().toString().trim(),
                            sdownload_url,
                            productPrice.getText().toString(), /*user.getUid()*/ userInfo);

                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(upload);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                  Toast.makeText(AddItemForSale.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                   double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                   uploadProgress.setProgress((int) progress);
                }
            });
        }else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}