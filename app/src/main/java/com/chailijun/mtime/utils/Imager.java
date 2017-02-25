package com.chailijun.mtime.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.chailijun.mtime.MtimeApp;
import com.chailijun.mtime.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;


/**
 * loading img encapsulation.
 */
public class Imager {

    public static void load(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .into(view);
    }

    public static void load(Context context, String url, ImageView view, int defaultImgId) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(defaultImgId)
                .error(defaultImgId)
                .crossFade()
                .into(view);
    }

    public static void load(Context context, String url, ImageView view, int width, int height) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .override(width, height)
                .centerCrop()
                .crossFade()
                .into(view);
    }

    public static void load(Context context, int resourceId, ImageView view) {
        Glide.with(context)
                .load(resourceId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(view);
    }

    public static void loadCircleImage(Context context, String url, ImageView view, int radius) {
        Glide.with(context)
                .load(url)
//                .placeholder(R.drawable.head_portrait)
//                .error(R.drawable.head_portrait)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .transform(new GlideRoundTransform(context, radius))
                .into(view);
    }

    public static void loadGif(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }
    /*public static void loadGif(Context mContext, String url, ImageView view,int width,int height){
        Glide.with(mContext)
                .load(url)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .override(width,height)
                .into(view);
    }*/

    public static void loadCircleImage(final Context context, String url, final ImageView imageView) {
        Glide.with(context).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void loadWithHighPriority(String url, ImageView view) {
        Glide.with(MtimeApp.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .into(view);
    }

    public static void load(String url, int animationId, ImageView view) {
        Glide.with(MtimeApp.getContext())
                .load(url)
                .animate(animationId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }


    public static class GlideRoundTransform extends BitmapTransformation {

        private static float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }


    /**
     * 下载图片
     *
     * @param url       图片url
     * @param imageFile 图片本地保存路径
     */
    public static void downLoad(String url, File imageFile) {
        try {
            Bitmap bitmap = Glide.with(MtimeApp.getContext())
                    .load(url)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            if (bitmap != null) {
                // 在这里执行图片保存方法
                saveImage(bitmap, imageFile);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void saveImage(Bitmap bitmap, File imageFile) {


        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 最后通知图库更新
        MtimeApp.getContext()
                .sendBroadcast(
                        new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.fromFile(new File(imageFile.getPath()))));
    }

    /**
     * 设置图片的路径(保存、获取图片时按照统一规则设置图片的路径)
     *
     * @param url
     * @return
     */
    public static File setImagePath(String url) {
        File appDir = getImageDir(MtimeApp.getContext().getString(R.string.app_cache));
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = url.replace("http://", "").replace('/', '-');
        return new File(appDir, fileName);
    }

    /**
     * 获取图片的路径
     * @param url
     * @return
     */
    public static File getImagePath(String url) {
        File appDir = getImageDir(MtimeApp.getContext().getString(R.string.app_cache));
        if (appDir.exists()){
            String fileName = url.replace("http://", "").replace('/', '-');
            return new File(appDir, fileName);
        }else {
            return null;
        }
    }

    /**
     * 获取图片文件夹的路径
     *
     * @param dirName 文件夹名字
     * @return
     */
    public static File getImageDir(String dirName) {
        File file = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .getAbsoluteFile();

        File appDir = new File(file, dirName);
        return appDir;
    }

    /**
     * 删除文件
     * @param path
     */
    public static void deleteFile(String path){
        deleteFile(new File(path));
    }

    /**
     * 删除文件
     * @param oldPath
     */
    public static void deleteFile(File oldPath) {
        try {
            if (oldPath.isDirectory()) {
                File[] files = oldPath.listFiles();
                for (File file : files) {
                    deleteFile(file);
                    file.delete();
                }
            } else {
                oldPath.delete();
            }
        } catch (Exception e) {

        }
    }

}
