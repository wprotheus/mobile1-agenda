package com.wprotheus.mobile1agenda.utils;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.wprotheus.mobile1agenda.R;
import com.wprotheus.mobile1agenda.model.Contato;
import com.wprotheus.mobile1agenda.model.DBSqlite;
import com.wprotheus.mobile1agenda.view.Editar;

import java.util.List;

public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.ContatosHolder> {
    private Context contexto;
    private DBSqlite dbSqlite;
    private List<Contato> contatos;

    public ContatosAdapter(Context contexto, DBSqlite dbSqlite) {
        this.contexto = contexto;
        this.dbSqlite = dbSqlite;
        this.contatos = dbSqlite.contatosList();
    }

    public class ContatosHolder extends RecyclerView.ViewHolder {
        private ImageView ivFoto;
        private TextInputEditText tietNome;
        private TextInputEditText tietFone;
        private TextInputEditText tietEmail;
        private Button btnApagar = itemView.findViewById(R.id.btnCancelar);
        private Button btnEditar = itemView.findViewById(R.id.btnSalvar);

        public ContatosHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivContato);
            tietNome = itemView.findViewById(R.id.tietNome);
            tietFone = itemView.findViewById(R.id.tietFone);
            tietEmail = itemView.findViewById(R.id.tietEmail);
        }
    }

    @NonNull
    @Override
    public ContatosAdapter.ContatosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContatosHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itens_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContatosAdapter.ContatosHolder holder, @SuppressLint("RecyclerView") int position) {
        if (position != RecyclerView.NO_POSITION) {
            holder.ivFoto.setImageResource(contatos.get(position).getFotoContato());
            holder.tietNome.setText(contatos.get(position).getNomeContato());
            holder.tietFone.setText(contatos.get(position).getFoneContato());
            holder.tietEmail.setText(contatos.get(position).getEmailContato());

            holder.btnApagar.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Apagando contato.\n")
                        .setMessage("Deseja realmente apagar este contato?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            dbSqlite.apagarContato(contatos.get(position).getId());
                            contatos.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, contatos.size());
                        })
                        .setNegativeButton("Cancelar", (dialog, id) -> {
                            // User cancelled the dialog
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            });

            holder.btnEditar.setOnClickListener(v -> {
                Intent intent = new Intent(this.contexto, Editar.class);
                intent.putExtra("position", position);
                intent.putExtra("contato", contatos.get(position));
                startActivity(v.getContext(), intent, null);
            });
        }
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }
}