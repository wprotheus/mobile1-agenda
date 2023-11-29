package com.wprotheus.mobile1agenda.view;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wprotheus.mobile1agenda.R;
import com.wprotheus.mobile1agenda.model.DBSqlite;
import com.wprotheus.mobile1agenda.utils.ContatosAdapter;

public class MainActivity extends Activity {
   private DBSqlite dbSqlite;
    private RecyclerView rvContatos;
    private ContatosAdapter contatosAdapter;
    private Button btnExit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvContatos = findViewById(R.id.rvContatos);
        btnExit = findViewById(R.id.btnSair);
        dbSqlite = new DBSqlite(this);

        mostraRecycleView();
    }

    private void mostraRecycleView() {
        rvContatos.setLayoutManager(new LinearLayoutManager(this));
        contatosAdapter = new ContatosAdapter(this, dbSqlite);
        rvContatos.setAdapter(contatosAdapter);
    }

    public void cadastro(View view) {
        Intent intent = new Intent(getApplicationContext(), Cadastrar.class);
        startActivity(intent);
    }

    public void exit(View view)
    {
        this.finishAffinity();
    }
}