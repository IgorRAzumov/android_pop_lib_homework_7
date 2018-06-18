package ru.geekbrains.android3_7.model.cache;

import android.graphics.Bitmap;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ru.geekbrains.android3_7.App;
import ru.geekbrains.android3_7.model.entity.activeandroid.AACachedImage;
import ru.geekbrains.android3_7.model.entity.activeandroid.AAUserRepository;
import timber.log.Timber;

public class AAImageCache implements IImageCache
{
    private final String IMAGE_FOLDER_NAME = "image";

    public File getFile(String url)
    {
        AACachedImage cachedImage = new Select()
                .from(AACachedImage.class)
                .where("url = ?", url)
                .executeSingle();

        if (cachedImage != null)
        {
            return new File(cachedImage.path);
        }
        return null;
    }

    public boolean contains(String url)
    {
        return new Select()
                .from(AACachedImage.class)
                .where("url = ?", url)
                .executeSingle() != null;
    }

    public void clear()
    {
        new Delete().from(AACachedImage.class).execute();
        deleteFileOrDirRecursive(getImageDir());
    }

    public File saveImage(final String url, Bitmap bitmap)
    {
        if (!getImageDir().exists() && !getImageDir().mkdirs())
        {
            throw new RuntimeException("Failed to create directory: " + getImageDir().toString());
        }

        final String fileFormat = url.contains(".jpg") ? ".jpg" : ".png";
        final File imageFile = new File(getImageDir(), MD5(url) + fileFormat);
        FileOutputStream fos;

        try
        {
            fos = new FileOutputStream(imageFile);
            bitmap.compress(fileFormat.equals("jpg") ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e)
        {
            Timber.d("Failed to save image");
            return null;
        }

        AACachedImage image = new AACachedImage();
        image.url = url;
        image.path = imageFile.toString();
        image.save();

        return imageFile;
    }

    public File getImageDir()
    {
        return new File(App.getInstance().getExternalFilesDir(null) + "/" + IMAGE_FOLDER_NAME);
    }

    public String MD5(String s)
    {
        MessageDigest m = null;

        try
        {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        m.update(s.getBytes(), 0, s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }

    public float getSizeKb()
    {
        return getFileOrDirSize(getImageDir()) / 1024f;
    }

    public void deleteFileOrDirRecursive(File fileOrDirectory)
    {
        if (fileOrDirectory.isDirectory())
        {
            for (File child : fileOrDirectory.listFiles())
            {
                deleteFileOrDirRecursive(child);
            }
        }
        fileOrDirectory.delete();
    }

    public long getFileOrDirSize(File f)
    {
        long size = 0;
        if (f.isDirectory())
        {
            for (File file : f.listFiles())
            {
                size += getFileOrDirSize(file);
            }
        } else
        {
            size = f.length();
        }
        return size;
    }
}
