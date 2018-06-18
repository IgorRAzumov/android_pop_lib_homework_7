package ru.geekbrains.android3_7.model.cache;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.realm.Realm;
import ru.geekbrains.android3_7.App;
import ru.geekbrains.android3_7.model.entity.realm.CachedImage;
import timber.log.Timber;

public interface IImageCache
{
    File getFile(String url);
    boolean contains(String url);
    void clear();
    File saveImage(final String url, Bitmap bitmap);
    File getImageDir();
    String MD5(String s);
    float getSizeKb();
    void deleteFileOrDirRecursive(File fileOrDirectory);
    long getFileOrDirSize(File f);
}
