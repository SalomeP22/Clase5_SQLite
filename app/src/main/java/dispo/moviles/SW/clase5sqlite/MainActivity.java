package dispo.moviles.SW.clase5sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etid, etnombre, ettel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etid=(EditText) findViewById(R.id.etid);
        etnombre=(EditText) findViewById(R.id.etnombre);
        ettel=(EditText) findViewById(R.id.ettel);
    }

    public void registrar(View view) {
        AdminBD admin = new AdminBD(this, "BaseDatos", null, 1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();
        String cedula = etid.getText().toString();
        String nombre = etnombre.getText().toString();
        String telefono = ettel.getText().toString();

        if(!cedula.isEmpty() && !nombre.isEmpty() && !telefono.isEmpty())
        {
            ContentValues registro = new ContentValues();
            registro.put("cedula", cedula);
            registro.put("nombre", nombre);
            registro.put("telefono", telefono);

            BaseDatos.insert("usuario", null, registro);
            BaseDatos.close();

            etid.setText("");
            etnombre.setText("");
            ettel.setText("");

            Toast.makeText(this, "Datos almacenados exitosamente", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Ingrese todos los datos solicitados", Toast.LENGTH_LONG).show();
        }
    }

    public void consultar (View view){
        AdminBD admin = new AdminBD(this, "BaseDatos", null, 1);
        SQLiteDatabase BaseD = admin.getWritableDatabase();
        String cedula1 = etid.getText().toString();
        if(!cedula1.isEmpty())
        {
            Cursor fila = BaseD.rawQuery("select nombre, telefono from usuario where cedula =" +cedula1, null);

            if(fila.moveToFirst())
            {
                etnombre.setText(fila.getString(0));
                ettel.setText(fila.getString(1));
                BaseD.close();
            }
            else
            {
                Toast.makeText(this, "No se encontró el usuario", Toast.LENGTH_LONG).show();
            }
        }
    }


}