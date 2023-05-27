package com.example.kit_bbs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

public class CreateThreadFragment extends Fragment {

    private EditText titleEditText;
    private EditText subtitleEditText;
    private MultiAutoCompleteTextView multiAutoCompleteTextView;
    private Button addButton;
    private LinearLayout chipsLayout;

    private ArrayList<String> itemsList;
    private Button createButton;
    private Button cancelButton;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_thread, container, false);

        titleEditText = view.findViewById(R.id.titleEditText);
        subtitleEditText = view.findViewById(R.id.subtitleEditText);
        multiAutoCompleteTextView = view.findViewById(R.id.multiAutoCompleteTextView);
        addButton = view.findViewById(R.id.addButton);
        chipsLayout = view.findViewById(R.id.chipsLayout);
        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        db = FirebaseFirestore.getInstance();
        itemsList = new ArrayList<>();
        // MultiAutoCompleteTextViewの設定
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, itemsList);
        multiAutoCompleteTextView.setAdapter(adapter);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createThread();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });

        return view;
    }
    private void addNewItem() {
        String newItem = multiAutoCompleteTextView.getText().toString().trim();
        if (!newItem.isEmpty()) {
            // 新しい項目をリストに追加
            itemsList.add(newItem);

            // チップを作成して表示
            Chip chip = new Chip(getContext());
            chip.setText(newItem);
            chip.setCloseIconVisible(true);
            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(chip);
                }
            });
            chipsLayout.addView(chip);
            // 入力欄をクリア
            multiAutoCompleteTextView.setText("");
        }
    }

    private void removeItem(Chip chip) {
        // リストから項目を削除
        String item = chip.getText().toString();
        itemsList.remove(item);

        // チップを削除
        chipsLayout.removeView(chip);
    }
    private void createThread() {
        String title = titleEditText.getText().toString();
        String subtitle = subtitleEditText.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        Timestamp createdAt = new Timestamp(new Date());

        // 必要な入力項目を取得し、Threadオブジェクトを作成
        Thread thread = new Thread(null, subtitle, itemsList, title, userId, createdAt);

        CollectionReference threadsCollection = FirebaseFirestore.getInstance().collection("thread");

        // ドキュメントIDは自動生成されるので、add()メソッドを使用してデータを追加する
        threadsCollection.add(thread)
                .addOnSuccessListener(documentReference -> {
                    // スレッドの追加が成功した場合の処理
                    // 新しく作成されたドキュメントのIDを取得
                    String threadId = documentReference.getId();
                    // ドキュメントのIDをスレッドオブジェクトに設定
                    thread.setId(threadId);
                    // createdAtフィールドに現在のタイムスタンプを設定
                    // スレッドの更新を反映させる
                    threadsCollection.document(threadId).set(thread);
                })
                .addOnFailureListener(e -> {

                });

        closeFragment();
    }

    private void closeFragment() {
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
