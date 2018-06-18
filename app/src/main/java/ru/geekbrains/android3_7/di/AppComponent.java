package ru.geekbrains.android3_7.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.android3_7.di.modules.AppModule;
import ru.geekbrains.android3_7.di.modules.ImageLoaderModule;
import ru.geekbrains.android3_7.di.modules.RepoModule;
import ru.geekbrains.android3_7.presenter.MainPresenter;
import ru.geekbrains.android3_7.view.MainActivity;

@Singleton
@Component(modules = {AppModule.class, RepoModule.class, ImageLoaderModule.class})
public interface AppComponent
{
    void inject(MainActivity activity);
    void inject(MainPresenter presenter);
}
