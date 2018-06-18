package ru.geekbrains.android3_7.model.repo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3_7.NetworkStatus;
import ru.geekbrains.android3_7.model.api.ApiService;
import ru.geekbrains.android3_7.model.cache.ICache;
import ru.geekbrains.android3_7.model.entity.Repository;
import ru.geekbrains.android3_7.model.entity.User;

public class UsersRepo
{
    ApiService api;
    ICache cache;

    public UsersRepo(ApiService api, ICache cache)
    {
        this.cache = cache;
        this.api = api;
    }

    public Observable<User> getUser(String username)
    {
        if (NetworkStatus.isOnline())
        {
            return api.getUser(username).subscribeOn(Schedulers.io()).map(user ->
            {
                cache.putUser(user);
                return user;
            });
        } else
        {
            return cache.getUser(username).subscribeOn(Schedulers.io());
        }
    }

    public Observable<List<Repository>> getUserRepos(User user)
    {
        if (NetworkStatus.isOnline())
        {
            return api.getUserRepos(user.getLogin()).subscribeOn(Schedulers.io()).map(repos ->
            {
                cache.putUserRepos(user, repos);
                return repos;
            });
        } else
        {
            return cache.getUserRepos(user).subscribeOn(Schedulers.io());
        }
    }
}
