package com.example.yucong.conn;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yucong.httpclient.Client;
import com.example.yucong.internet.R;

public class conn extends AppCompatActivity {
    private EditText pathText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn);

        pathText = (EditText) this.findViewById(R.id.imagepath);
        imageView = (ImageView) this.findViewById(R.id.imageView);
        Button button = (Button) this.findViewById(R.id.button);
        button.setOnClickListener(new ButtonClickListener());
    }
    private final class ButtonClickListener implements View.OnClickListener{

        public void onClick(View v) {
            final String path = pathText.getText().toString();
            try{

                if(TextUtils.isEmpty(path)){
                    Toast.makeText(conn.this,"路径不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(){
                        @Override
                        public void run() {

                            byte[] data = new byte[0];
                            try {

                                data = ImageService.getImage(path);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                imageView.setImageBitmap(bitmap);//ÏÔÊŸÍŒÆ¬
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }.start();
                }



            }catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
