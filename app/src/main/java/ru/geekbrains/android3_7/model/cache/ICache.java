package ru.geekbrains.android3_7.model.cache;

import java.util.List;

import io.reactivex.Observable;
import ru.geekbrains.android3_7.model.entity.User;
import ru.geekbrains.android3_7.model.entity.Repository;

public interface ICache
{
    void putUser(User user);
    Observable<User> getUser(String username);

    void putUserRepos(User user, List<Repository> repositories);
    Observable<List<Repository>> getUserRepos(User user);
}
