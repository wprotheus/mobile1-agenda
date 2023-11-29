package com.wprotheus.mobile1agenda.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DBSqlite extends SQLiteOpenHelper {
    private static final String DB_NAME = "dbsqlite";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "tb_contato";
    private static final String ID_COL = "ID";
    private static final String FOTO_COL = "FOTO";
    private static final String NOME_COL = "NOME";
    private static final String FONE_COL = "FONE";
    private static final String EMAIL_COL = "EMAIL";

    public DBSqlite(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE tb_contato (")
                .append("ID INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append("FOTO TEXT NOT NULL, ")
                .append("NOME TEXT NOT NULL, ")
                .append("FONE TEXT NOT NULL, ")
                .append("EMAIL TEXT NOT NULL)");
        db.execSQL(query.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void adicionarContato(Contato contato)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(FOTO_COL, String.valueOf(contato.getFotoContato()));
            values.put(NOME_COL, contato.getNomeContato());
            values.put(FONE_COL, contato.getFoneContato());
            values.put(EMAIL_COL, contato.getEmailContato());
            db.insert(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    @SuppressLint("Range")
    public List<Contato> contatosList()
    {
        SQLiteDatabase database = getReadableDatabase();
        String[] colunas = {ID_COL, FOTO_COL, NOME_COL, FONE_COL, EMAIL_COL};
        List<Contato> contatos = new ArrayList<>();

        try (Cursor cursor = database.query(TABLE_NAME, colunas, null, null, null, null, null))
        {
            while (cursor.moveToNext())
            {
                Contato c = new Contato();
                c.setId(cursor.getInt(cursor.getColumnIndex(ID_COL)));
                c.setFotoContato(cursor.getInt(cursor.getColumnIndex(FOTO_COL)));
                c.setNomeContato(cursor.getString(cursor.getColumnIndex(NOME_COL)));
                c.setFoneContato(cursor.getString(cursor.getColumnIndex(FONE_COL)));
                c.setEmailContato(cursor.getString(cursor.getColumnIndex(EMAIL_COL)));
                contatos.add(c);
            }
        } catch (Exception e)
        {
            throw new RuntimeException();
        } finally {
            database.close();
        }
        return contatos;
    }

    public void apagarContato(int id)
    {
        SQLiteDatabase database = getReadableDatabase();
        String contatoID = "ID LIKE ?";
        String[] IDSelecionado = {String.valueOf(id)};
        database.delete(TABLE_NAME, contatoID, IDSelecionado);
    }

    public void editarContato(Contato contato)
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FOTO_COL, String.valueOf(contato.getFotoContato()));
        values.put(NOME_COL, contato.getNomeContato());
        values.put(FONE_COL, contato.getFoneContato());
        values.put(EMAIL_COL, contato.getEmailContato());

        String selection = "ID LIKE ?";
        String[] selectionId = {String.valueOf(contato.getId())};

        int count = database.update(TABLE_NAME, values, selection, selectionId);
    }
}