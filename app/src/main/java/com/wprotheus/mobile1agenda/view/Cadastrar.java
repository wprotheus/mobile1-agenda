package com.wprotheus.mobile1agenda.view;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.wprotheus.mobile1agenda.R;
import com.wprotheus.mobile1agenda.model.Contato;
import com.wprotheus.mobile1agenda.model.DBSqlite;
import java.util.Objects;

public class Cadastrar extends AppCompatActivity {
    private DBSqlite dbSqlite;
    private ImageView ivFotoContato;
    private TextInputEditText tietNome;
    private TextInputEditText tietFone;
    private TextInputEditText tietEmail;
    private Button btnCancelar;
    private Button btnSalvar;


    @SuppressLint({"RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itens_card);
        ivFotoContato = findViewById(R.id.ivContato);
        tietNome = findViewById(R.id.tietNome);
        tietFone = findViewById(R.id.tietFone);
        tietEmail = findViewById(R.id.tietEmail);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnSalvar = findViewById(R.id.btnSalvar);
        dbSqlite = Objects.requireNonNull(new DBSqlite(Cadastrar.this));

        btnCancelar.setText("Cancelar");
        btnCancelar.setOnClickListener(v1 -> voltar());
        btnSalvar.setText("Salvar");
        btnSalvar.setOnClickListener(this::cadastrar);

        tietNome.setOnFocusChangeListener((v, hasFocus) -> { if(!hasFocus) hideKeyboard(v); });
        tietFone.setOnFocusChangeListener((v, hasFocus) -> { if(!hasFocus) hideKeyboard(v); });
        tietEmail.setOnFocusChangeListener((v, hasFocus) -> { if(!hasFocus) hideKeyboard(v); });
    }

    public void cadastrar(View view)
    {
        Contato c = new Contato();
        c.setFotoContato(R.drawable.add);
        c.setNomeContato(tietNome.getText().toString());
        c.setFoneContato(tietFone.getText().toString());
        c.setEmailContato(tietEmail.getText().toString());

        if(c.getNomeContato().isEmpty() || c.getFoneContato().isEmpty() || c.getEmailContato().isEmpty())
        {
            Toast.makeText(Cadastrar.this, "Preencher todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        dbSqlite.adicionarContato(c);
        AlertDialog.Builder builder = new AlertDialog.Builder(Cadastrar.this);
        builder.setTitle("Os dados foram salvos!\n")
                .setMessage("Cadastro realizado com sucesso.")
                .setPositiveButton("OK", (dialog, which) -> view.notify());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void voltar() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}