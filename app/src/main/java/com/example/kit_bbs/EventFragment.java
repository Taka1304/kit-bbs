package com.example.kit_bbs;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment {
    private ProgressBar progressBar;
    private ProgressBar recProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout newEventCardContainer = view.findViewById(R.id.newEventCardContainer);
        LinearLayout recEventCardContainer = view.findViewById(R.id.recEventCardContainer);
        progressBar = view.findViewById(R.id.newEventProgressBar);
        recProgressBar = view.findViewById(R.id.recommendEventProgressBar);
        // 非同期通信開始前にProgressBarを表示
        progressBar.setVisibility(View.VISIBLE);
        recProgressBar.setVisibility(View.VISIBLE);
        getEventData(new OnEventDataLoadedListener() {
            @Override
            public void onEventDataLoaded(List<Event> eventDataList) {
                createCardViews(eventDataList, newEventCardContainer);
                createCardViews(eventDataList, recEventCardContainer);
            }
        });
    }
    // 非同期処理のためのコールバック
    public interface OnEventDataLoadedListener {
        void onEventDataLoaded(List<Event> eventDataList);
    }
    private List<Event> getEventData(final OnEventDataLoadedListener listener) {
        List<Event> eventDataList = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("event").get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            if (task.isSuccessful()) {
                                Event event = document.toObject(Event.class);
                                eventDataList.add(event);
                            }
                        }
                        listener.onEventDataLoaded(eventDataList);
                        progressBar.setVisibility(View.GONE);
                        recProgressBar.setVisibility(View.GONE);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
        return eventDataList;
    }
    private void createCardViews(List<Event> eventDataList, LinearLayout cardContainer) {
        for (Event event : eventDataList) {
            View cardView = LayoutInflater.from(getActivity()).inflate(R.layout.event_card, cardContainer, false);

            // CardView内のビューを取得
            ImageView imageViewThumbnail = cardView.findViewById(R.id.imageViewThumbnail);
            TextView textViewTitle = cardView.findViewById(R.id.textViewTitle);
            TextView textViewHashtags = cardView.findViewById(R.id.textViewHashtags);

            // データをセット
            imageViewThumbnail.setImageResource(R.drawable.event_image);
            textViewTitle.setText(event.getTitle());
            textViewHashtags.setText(TextUtils.join(", ", event.getTags()));

            // CardViewにクリックイベントを追加
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // カードがクリックされた時の処理
                }
            });
            cardContainer.addView(cardView); // CardViewをLinearLayoutに追加
        }
    }
}
