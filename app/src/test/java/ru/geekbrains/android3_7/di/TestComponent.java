package ru.geekbrains.android3_7.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.android3_7.di.modules.TestRepoModule;
import ru.geekbrains.android3_7.presenter.MainPresenter;

@Singleton
@Component(modules = {TestRepoModule.class})
public interface TestComponent
{
    void inject(MainPresenter presenter);
}
