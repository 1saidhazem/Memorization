package com.example.memorization;

import static com.example.memorization.Constant.COLLECTION_CENTER;
import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.example.memorization.RoomDatabase.Center;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class ScheduleDataSync extends JobService {

    private backgroundTask task;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        task = new backgroundTask();
        task.execute();

        Toast.makeText(this, "جاري مزامنة البيانات", Toast.LENGTH_SHORT).show();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Toast.makeText(this, "تم الانتهاء من مزامنة البيانات", Toast.LENGTH_SHORT).show();
        return false;
    }

    public class backgroundTask extends AsyncTask<Void,Void,Void>{
        private MyViewModel mvm;
        private FirebaseFirestore fireStore;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mvm = new ViewModelProvider((ViewModelStoreOwner) getApplicationContext()).get(MyViewModel.class);
            fireStore = FirebaseFirestore.getInstance();
            Log.d("ddd","onPreExecute");

        }
        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {
            mvm.getAllCenter().observe((LifecycleOwner) getApplicationContext(), new Observer<List<Center>>() {
                @Override
                public void onChanged(List<Center> centers) {
                    fireStore.collection(COLLECTION_CENTER).add(centers).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("ttt", "onSuccess done...");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("ttt", "onFailure done..." + e.getMessage());
                        }
                    });
                }
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Log.d("ddd","onPostExecute");
        }


        //        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//        }
    }

//    mvm.getAllCenter().observe((LifecycleOwner) getApplicationContext(), new Observer<List<Center>>() {
//        @Override
//        public void onChanged(List<Center> centers) {
//            Center c = new Center("Said",null,0,0,0,0,"Gaza");
//            fireStore.collection(COLLECTION_CENTER).add(c).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                @Override
//                public void onSuccess(DocumentReference documentReference) {
//                    Log.d("ttt", "onSuccess done...");
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.d("ttt", "onFailure done..." + e.getMessage());
//                }
//            });
////                SyncInfoCenter(centers);
//        }
//    });

//    public void SyncInfoCenter(List<Center> centers){
//        fireStore.collection(COLLECTION_CENTER).add(centers).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Log.d("ttt", "onSuccess done...");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("ttt", "onFailure done...");
//            }
//        });
//    }

}

