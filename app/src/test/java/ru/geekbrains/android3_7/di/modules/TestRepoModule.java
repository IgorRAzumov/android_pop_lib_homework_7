package ru.geekbrains.android3_7.di.modules;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3_7.model.repo.UsersRepo;

@Module
public class TestRepoModule
{
    @Provides
    public UsersRepo usersRepo()
    {
        return Mockito.mock(UsersRepo.class);
    }
}
