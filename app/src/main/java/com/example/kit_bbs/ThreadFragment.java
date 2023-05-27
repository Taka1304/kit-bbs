package com.example.kit_bbs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ThreadFragment extends Fragment {

    private LinearLayout threadList;
    private FirebaseFirestore db;
    private Button newThreadButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thread, container, false);

        threadList = view.findViewById(R.id.threadList);
        db = FirebaseFirestore.getInstance();

        newThreadButton = view.findViewById(R.id.newThreadButton);
        newThreadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment createThreadFragment = new CreateThreadFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, createThreadFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        // 最新の10件のスレッドを取得するクエリ
        CollectionReference threadsRef = db.collection("thread");
        Query query = threadsRef.orderBy("createdAt", Query.Direction.DESCENDING).limit(10);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Thread> threads = new ArrayList<>();

                    for (DocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        String title = document.getString("title");
                        String subtitle = document.getString("subtitle");
                        ArrayList<String> tags = (ArrayList<String>) document.get("tags");
                        String userId = document.getString("userId");
                        Timestamp createdAt = document.getTimestamp("createdAt");

                        Thread thread = new Thread(id, subtitle, tags, title, userId, createdAt);
                        threads.add(thread);
                    }
                    populateThreadList(threads);
                } else {
                    // データの取得に失敗した場合の処理
                }
            }
        });

        return view;
    }

    private void populateThreadList(List<Thread> threads) {
        for (Thread thread : threads) {
            View itemView = getLayoutInflater().inflate(R.layout.thread_item, null);
            TextView titleTextView = itemView.findViewById(R.id.titleTextView);
            TextView timestampTextView = itemView.findViewById(R.id.timestampTextView);
            TextView usernameTextView = itemView.findViewById(R.id.usernameTextView);

            titleTextView.setText(thread.getTitle());
            timestampTextView.setText(formatDate(thread.getCreatedAt().toDate()));
            usernameTextView.setText(thread.getUserId());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // FirebaseAuthでユーザーがログインしているか確認し、遷移する処理を追加
                    // 未ログインの場合はログイン画面に遷移する旨を表示
                }
            });

            threadList.addView(itemView);
        }
    }
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd (E)", Locale.getDefault());
        return dateFormat.format(date);
    }
}
