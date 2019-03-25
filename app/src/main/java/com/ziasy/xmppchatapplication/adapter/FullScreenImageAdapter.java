package com.ziasy.xmppchatapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.JsonObject;
import com.ziasy.xmppchatapplication.R;
import com.ziasy.xmppchatapplication.ViewFullImageActivity;
import com.ziasy.xmppchatapplication.activity.ForwardActivity;
import com.ziasy.xmppchatapplication.activity.FullScreenViewActivity;
import com.ziasy.xmppchatapplication.common.Confiq;
import com.ziasy.xmppchatapplication.model.JsonModelForChat;
import com.ziasy.xmppchatapplication.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;

public class FullScreenImageAdapter extends PagerAdapter {

    private Activity _activity;
    private List<JsonModelForChat> _imagePaths;
    private LayoutInflater inflater;
    private FullScreenViewActivity fullScreenViewActivity;

    // constructor
    public FullScreenImageAdapter(Activity activity,
                                  List<JsonModelForChat> imagePaths) {
        this.fullScreenViewActivity = (FullScreenViewActivity) activity;
        this._activity = activity;
        this._imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView imgDisplay;
        Button btnClose;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);

        imgDisplay = (PhotoView) viewLayout.findViewById(R.id.imageView);
        //  btnClose = (Button) viewLayout.findViewById(R.id.btnClose);
        
       /* BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
      *//*  Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position).getMessage(), options);
        imgDisplay.setImageBitmap(bitmap);*/
        Glide.with(_activity)
                .load(new File(_imagePaths.get(position).getMessage()))
                .into(imgDisplay);

        // close button click event
        imgDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullScreenViewActivity.clickStatus();
            }
        });

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    public void saveImage(Context context, int i) {
        String strFileName = _imagePaths.get(i).getMessage();
        String dataStr = strFileName.replace(Confiq.RETURN_IMAGE_URL, "");
        Utils.downloadFile(context, _imagePaths.get(i).getMessage(), dataStr);
        Toast.makeText(context, "Image Saved", Toast.LENGTH_SHORT).show();

    }

    public void forwardData( int i) {
        ArrayList<String> strImagePath = new ArrayList<>();
        ArrayList<String> type = new ArrayList<>();
        strImagePath.add(_imagePaths.get(i).getMessage());
        type.add(_imagePaths.get(i).getType());
        Intent intent = new Intent(_activity, ForwardActivity.class);
        intent.putStringArrayListExtra("forwardString", strImagePath);
        intent.putStringArrayListExtra("type", type);
        _activity.startActivityForResult(intent,88);


    }
  public void deleteData(Context context, int i,String  id, Socket mSocket) {

      JsonObject jsonObject1 = new JsonObject();
      jsonObject1.addProperty("id", id);
      jsonObject1.addProperty("uid","," + "'" + _imagePaths.get(i).getMid() + "'" );
      Log.d("MIDMID", jsonObject1 + "");
      mSocket.emit("deleteMessage", jsonObject1);
      _imagePaths.remove(i);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}
