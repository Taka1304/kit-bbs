package com.example.kit_bbs;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventDetailFragment extends Fragment {
    private TextView eventTitleTextView;
    private TextView eventTagsTextView;
    private TextView eventContentTextView;
    private TextView eventLocationTextView;
    private TextView eventStartDateTimeTextView;
    private TextView eventEndDateTimeTextView;
    private TextView eventMaxParticipantsTextView;
    private TextView eventCurrentParticipantsTextView;
    private Button joinButton;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    public static EventDetailFragment newInstance(Event event) {
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("event", event);
        fragment.setArguments(args);
        return fragment;
    }
    public EventDetailFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        eventTitleTextView = view.findViewById(R.id.eventTitle);
        eventTagsTextView = view.findViewById(R.id.eventTags);
        eventContentTextView = view.findViewById(R.id.eventContent);
        eventLocationTextView = view.findViewById(R.id.eventLocation);
        eventStartDateTimeTextView = view.findViewById(R.id.eventStartDateTime);
        eventEndDateTimeTextView = view.findViewById(R.id.eventEndDateTime);
        eventMaxParticipantsTextView = view.findViewById(R.id.eventMaxParticipants);
        eventCurrentParticipantsTextView = view.findViewById(R.id.eventCurrentParticipants);

        if (getArguments() != null) {
            Event event = (Event) getArguments().getSerializable("event");
            if (event != null) {
                bindEventDetails(event);
            }
        }
        joinButton = view.findViewById(R.id.joinButton);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        // 「参加する」ボタンのクリックイベントを設定
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView titleTextView = view.findViewById(R.id.eventTitle);
        TextView descriptionTextView = view.findViewById(R.id.eventContent);
        ImageView thumbnailImageView = view.findViewById(R.id.eventThumbnailImageView);

        Bundle args = getArguments();
        if (args != null) {
            Event event = (Event) args.getSerializable("event");
            if (event != null) {
                titleTextView.setText(event.getTitle());
                descriptionTextView.setText(event.getContent());
                thumbnailImageView.setImageResource(getResources().getIdentifier(event.getThumbnailId(), "drawable", requireContext().getPackageName()));
            }
        }
    }
    private void bindEventDetails(Event event) {
        eventTitleTextView.setText(event.getTitle());
        List<String> tags = event.getTags();
        List<String> formattedTags = new ArrayList<>();
        for (String tag : tags) {
            formattedTags.add("#" + tag);
        }
        eventTagsTextView.setText(TextUtils.join(" ", formattedTags));
        eventContentTextView.setText(event.getContent());
        eventLocationTextView.setText(event.getLocation());
        Date startDate = event.getStartDateTime().toDate();
        String startDateTimeFormatted = new SimpleDateFormat("yyyy/MM/dd (E)", Locale.getDefault()).format(startDate);
        eventStartDateTimeTextView.setText(startDateTimeFormatted);

        // End DateTime
        Date endDate = event.getEndDateTime().toDate();
        String endDateTimeFormatted = new SimpleDateFormat("yyyy/MM/dd (E)", Locale.getDefault()).format(endDate);
        eventEndDateTimeTextView.setText(endDateTimeFormatted);
        eventMaxParticipantsTextView.setText(String.valueOf(event.getMaxParticipants()));
        eventCurrentParticipantsTextView.setText(String.valueOf(event.getCurrentParticipants()));
    }
}

