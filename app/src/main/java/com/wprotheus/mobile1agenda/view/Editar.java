package com.wprotheus.mobile1agenda.view;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.wprotheus.mobile1agenda.R;
import com.wprotheus.mobile1agenda.model.Contato;
import com.wprotheus.mobile1agenda.model.DBSqlite;

public class Editar extends AppCompatActivity {
    private ImageView ivFotoContato;
    private TextInputEditText tietNome;
    private TextInputEditText tietFone;
    private TextInputEditText tietEmail;
    private Button btnCancelar;
    private Button btnSalvar;
    private DBSqlite dbSqlite;
    private Contato contato = new Contato();
    
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

        dbSqlite = new DBSqlite(Editar.this);

        btnCancelar.setText("Cancelar");
        btnCancelar.setOnClickListener(v1 -> voltar());
        btnSalvar.setText("Salvar");
        btnSalvar.setOnClickListener(this::salvar);

        tietNome.setOnFocusChangeListener((v, hasFocus) -> {if (!hasFocus) hideKeyboard(v);});
        tietFone.setOnFocusChangeListener((v, hasFocus) -> {if (!hasFocus) hideKeyboard(v);});
        tietEmail.setOnFocusChangeListener((v, hasFocus) -> {if (!hasFocus) hideKeyboard(v);});

        contato = (Contato) getIntent().getSerializableExtra("contato");

        ivFotoContato.setImageResource(R.drawable.add);
        tietNome.setText(contato.getNomeContato());
        tietFone.setText(contato.getFoneContato());
        tietEmail.setText(contato.getEmailContato());
    }
    public void salvar(View view)
    {
        Contato c = new Contato();
        c.setId(getIntent().getIntExtra("contato", contato.getId()));
        c.setFotoContato(R.drawable.add);
        c.setNomeContato(tietNome.getText().toString());
        c.setFoneContato(tietFone.getText().toString());
        c.setEmailContato(tietEmail.getText().toString());

        if(c.getNomeContato().isEmpty() || c.getFoneContato().isEmpty() || c.getEmailContato().isEmpty())
        {
            Toast.makeText(Editar.this, "Preencher todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        dbSqlite.editarContato(c);
        AlertDialog.Builder builder = new AlertDialog.Builder(Editar.this);
        builder.setTitle("Os dados foram salvos!\n")
                .setMessage("Alterações realizadas com sucesso.")
                .setPositiveButton("OK", (dialog, which) -> view.notify());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void voltar() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}