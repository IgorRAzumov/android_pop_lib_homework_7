package ru.geekbrains.android3_7.di;


import javax.inject.Singleton;

import dagger.Component;
import ru.geekbrains.android3_7.UserRepoInstrumentedTest;
import ru.geekbrains.android3_7.di.modules.RepoModule;

@Singleton
@Component(modules = {RepoModule.class})
public interface TestComponent
{
    void inject(UserRepoInstrumentedTest test);
}
