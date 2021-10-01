package br.edu.ifsp.scl.sdm.intents;

import static android.net.Uri.parse;
import androidx.activity.result.ActivityResultLauncher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.URLUtil;
import android.widget.Toast;

import br.edu.ifsp.scl.sdm.intents.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        activityMainBinding.mainTb.appTb.setTitle("Tratando Intents");
        activityMainBinding.mainTb.appTb.setSubtitle("Principais tipos");
        setSupportActionBar(activityMainBinding.mainTb.appTb);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.viewMi:
                String url;
                url = activityMainBinding.parameterEt.getText().toString();
                boolean isURL = URLUtil.isValidUrl( url );
                if (isURL) {
                    Intent siteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(siteIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"URL Inválida",Toast.LENGTH_SHORT).show();
                }
                return(true);
            case R.id.dialMi:
                String numerotel = activityMainBinding.parameterEt.getText().toString();
                Intent ligacaoIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numerotel, null));
                startActivity(ligacaoIntent);
                return(true);
            case R.id.callMi:
                if (permitirLigacao()) {
                    discarTelefone();
                } else {
                    Toast.makeText(getApplicationContext(),"Você precisa permitir para realizar ligações",Toast.LENGTH_SHORT).show();
                }
                return(true);
            case R.id.chooserMi:
                return (true);
            case R.id.exitMi:
                finish();
                return (true);
            case R.id.actionMi:
                Intent actionIntent = new Intent("OPEN_ACTION_ACTIVITY").putExtra(
                    Intent.EXTRA_TEXT,
                    activityMainBinding.parameterEt.getText().toString()
                );
                startActivity(actionIntent);
                return(true);
        } return (false);
    }

    private void discarTelefone() {
        Intent discarIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + activityMainBinding.parameterEt.getText().toString()));
        startActivity(discarIntent);
    }

    private boolean permitirLigacao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},1);
                return(false);
            }
            else
            {
                return(true);
            }
        }
        else
        {
            return(true);
        }
    }

}