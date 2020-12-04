package com.example.apicategory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class MyDialogFragment extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = View.inflate(getContext(), R.layout.dialog_text, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText login = v.findViewById(R.id.text_login);
        EditText password = v.findViewById(R.id.text_password);

        builder.setTitle("Авторизация")
                .setMessage("Введите лоин и пароль")
                .setView(v);
        builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (login.getText().toString().equals("123")) {
                    Intent intent = new Intent(getActivity(), CamActivity.class);
                    startActivity(intent);
                } else
                {
                    Toast.makeText(getContext(),"Неверный логин или пароль!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        return builder.create();
    }
}
