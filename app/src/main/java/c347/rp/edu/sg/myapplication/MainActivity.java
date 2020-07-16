package c347.rp.edu.sg.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String folderLocation;
    Button btnWrite, btnRead;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWrite = findViewById(R.id.btnWrite);
        btnRead = findViewById(R.id.btnRead);
        tv = findViewById(R.id.tv);

        if (checkPermission() == true) {

            folderLocation = getExternalFilesDir(null) + "/MyFolder";

            File folder = new File(folderLocation);
            if (folder.exists() == false) {
                boolean result = folder.mkdir();
                if (result == true) {
                    Log.d("File Read/Write", "Folder created");
                }
            }

            btnWrite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        File targetFile = new File(folderLocation, "data.txt");
                        FileWriter writer = new FileWriter(targetFile, true);
                        writer.write("Hello Qiao" + "\n");
                        writer.flush();
                        writer.close();
                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this, "Failed to write!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            });

            btnRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File targetFile = new File(folderLocation, "data.txt");
                    if (targetFile.exists() == true){
                        String data = "";
                        try{
                            FileReader reader = new FileReader(targetFile);
                            BufferedReader br = new BufferedReader(reader);

                            String line = br.readLine();
                            while(line != null){
                                data += line + "\n";
                                line = br.readLine();
                            }

                            br.close();
                            reader.close();

                        } catch (FileNotFoundException e) {
                            Toast.makeText(MainActivity.this, "Failed to read!", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        } catch (IOException e) {
                            Toast.makeText(MainActivity.this, "Failed to read!", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        Log.d("Content", data);
                        tv.setText(data);
                    }
                }
            });

        }

    }

    private boolean checkPermission(){
        int permissionCheck_write = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck_read = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck_write == PermissionChecker.PERMISSION_GRANTED
                || permissionCheck_read == PermissionChecker.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
}
