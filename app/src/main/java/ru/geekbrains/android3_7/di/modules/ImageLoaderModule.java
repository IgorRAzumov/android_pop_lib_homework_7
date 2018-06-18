package ru.geekbrains.android3_7.di.modules;

import android.widget.ImageView;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3_7.model.cache.AAImageCache;
import ru.geekbrains.android3_7.model.cache.IImageCache;
import ru.geekbrains.android3_7.model.cache.RealmImageCache;
import ru.geekbrains.android3_7.model.image.ImageLoader;
import ru.geekbrains.android3_7.model.image.android.ImageLoaderGlide;

@Singleton
@Module
public class ImageLoaderModule
{
    @Named("imageRealm")
    @Provides
    public IImageCache cacheRealm()
    {
        return new RealmImageCache();
    }

    @Named("imageActiveAndroid")
    @Provides
    public IImageCache cacheAA()
    {
        return new AAImageCache();
    }

    @Provides
    public ImageLoader<ImageView> imageLoader(@Named("imageActiveAndroid") IImageCache imageCache)
    {
        return new ImageLoaderGlide(imageCache);
    }
}
