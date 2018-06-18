package ru.geekbrains.android3_7.di.modules;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3_7.model.cache.AACache;
import ru.geekbrains.android3_7.model.cache.ICache;
import ru.geekbrains.android3_7.model.cache.RealmCache;

@Singleton
@Module
public class CacheModule
{
    @Named("aa")
    @Provides
    public ICache cacheAA()
    {
        return new AACache();
    }

    @Named("realm")
    @Provides
    public ICache cacheRealm()
    {
        return new RealmCache();
    }
}
