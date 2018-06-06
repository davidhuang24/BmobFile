package com.dh.exam.bmobfiledemo;

import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;


/** 文件上传+批量更新数据
 *
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    Button tv_one_one;
    Button tv_one_many;
    Button tv_many_one;
    Button tv_many_many;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }
    @Override
    public void setContentView() {
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_main);
    }
    @Override
    public void initViews() {
        // TODO Auto-generated method stub
        tv_one_one = (Button) findViewById(R.id.tv_one_one);
        tv_one_many = (Button) findViewById(R.id.tv_one_many);
        tv_many_one = (Button) findViewById(R.id.tv_many_one);
        tv_many_many = (Button) findViewById(R.id.tv_many_many);
    }
    @Override
    public void initListeners() {
        // TODO Auto-generated method stub
        tv_one_one.setOnClickListener(this);
        tv_one_many.setOnClickListener(this);
        tv_many_one.setOnClickListener(this);
        tv_many_many.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tv_one_one://插入单条数据（一个BmobFile列）
                insertDataWithOne();
                break;
            case R.id.tv_many_one://批量插入多条数据-且每条数据都存在一个BmobFile列
                insertBatchDatasWithOne();
                break;
            case R.id.tv_one_many://插入单条数据（多个BmobFile列）
                insertDataWithMany();
                break;
            case R.id.tv_many_many://批量插入多条数据-且每条数据都存在多个BmobFile列
                insertBatchDatasWithMany();
                break;
        }
    }

    /**
     * 注：以下的测试文件路径仅供测试所用，程序若完整运行，请自行替换成sd卡内部文件路径
     */
    String filePath1 = Environment.getExternalStorageDirectory().getPath()+"/bmobfile/爱情公寓大电影.jpg";
    String filePath2 = Environment.getExternalStorageDirectory().getPath()+"/bmobfile/生化危机1.jpg";
    String filePath3 = Environment.getExternalStorageDirectory().getPath()+"/bmobfile/生化危机2.jpg";
    String filePath4 = Environment.getExternalStorageDirectory().getPath()+"/bmobfile/起风了.mp3";
    String filePath5 = Environment.getExternalStorageDirectory().getPath()+"/bmobfile/起风了.lrc";
    String filePath6 = Environment.getExternalStorageDirectory().getPath()+"/bmobfile/烟火里的尘埃.mp3";
    String filePath7 = Environment.getExternalStorageDirectory().getPath()+"/bmobfile/烟火里的尘埃.lrc";
    String filePath8 = Environment.getExternalStorageDirectory().getPath()+"/bmobfile/纸短情长.mp3";
    String filePath9 = Environment.getExternalStorageDirectory().getPath()+"/bmobfile/纸短情长.lrc";
    List<BmobObject> movies = new ArrayList<BmobObject>();
    List<BmobObject> songs = new ArrayList<BmobObject>();

    /**
     *一行一列
     * 一条数据，一个BmobFile列
     */
    private void insertDataWithOne(){
        File mp3 = new File(filePath1);
        uploadFile(mp3);
    }

    /**
     * 多行一列
     */
    public void insertBatchDatasWithOne(){
        String[] filePaths = new String[2];
        filePaths[0] = filePath2;
        filePaths[1] = filePath3;
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
                if(list1.size()==1){//如果第一个文件上传完成
                    Movie movie =new Movie("生化危机1",list.get(0));
                    movies.add(movie);
                }else if(list1.size()==2){//第二个文件上传成功
                    Movie movie1 =new Movie("生化危机2",list.get(1));
                    movies.add(movie1);
                    insertBatch(movies);
                }
            }
            @Override
            public void onProgress(int i, int i1, int i2, int i3) {

            }
            @Override
            public void onError(int i, String s) {
                Toast.makeText(MainActivity.this, "错误码:"+i+"错误描述:"+s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 一行多列
     */
    private void insertDataWithMany() {
        final String[] filePaths = new String[2];
        filePaths[0] = filePath4;
        filePaths[1] = filePath5;
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
                if(list1.size()==filePaths.length){//文件全部上传成功
                    Song song=new Song("起风了","买辣椒也用券",list.get(0),list.get(1));
                    insertObject(song);
                }else {//文件部分上传成功

                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {

            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(MainActivity.this, "全部上传失败：错误码:"+i+"错误描述:"+s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 多行多列
     */
    private void insertBatchDatasWithMany() {
        final String[] filePaths = new String[4];
        filePaths[0] = filePath6;
        filePaths[1] = filePath7;
        filePaths[2] = filePath8;
        filePaths[3] = filePath9;
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
                if(list1.size()==filePaths.length){//文件全部上传成功
                    Song song1=new Song("烟火里的尘埃","华晨宇",list.get(0),list.get(1));
                    Song song2=new Song("纸短情长","烟把儿",list.get(2),list.get(3));
                    songs.add(song1);
                    songs.add(song2);
                    insertBatch(songs);
                }else {//文件部分上传成功

                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {

            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(MainActivity.this, "全部上传失败：错误码:"+i+"错误描述:"+s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *上传文件
     *
     * @param file 待上传文件
     */
    private void uploadFile(File file) {
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i(TAG, "done: 文件上传成功，返回后台url："+bmobFile.getFileUrl());
                    insertObject(new Movie("爱情公寓大电影",bmobFile));
                }else{
                    Toast.makeText(MainActivity.this,
                            "上传文件失败，错误码："+e.getErrorCode()+",错误信息："+e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * 插入一条记录
     *
     * @param obj 待插入对象
     */
    private void insertObject(final BmobObject obj){
        obj.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(MainActivity.this, "插入记录成功，objectId:"+s, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,
                            "插入记录失败，错误码："+e.getErrorCode()+",错误信息："+e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void insertBatch(List<BmobObject> files){
        for(BmobObject object :files){
            insertObject(object);
        }
    }

}
