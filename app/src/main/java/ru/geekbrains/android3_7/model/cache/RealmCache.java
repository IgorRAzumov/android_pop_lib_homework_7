package ru.geekbrains.android3_7.model.cache;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import ru.geekbrains.android3_7.model.entity.User;
import ru.geekbrains.android3_7.model.entity.Repository;
import ru.geekbrains.android3_7.model.entity.realm.RealmRepository;
import ru.geekbrains.android3_7.model.entity.realm.RealmUser;

public class RealmCache implements ICache
{
    @Override
    public void putUser(User user)
    {
        Realm realm = Realm.getDefaultInstance();
        final RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", user.getLogin()).findFirst();
        realm.executeTransaction(innerRealm ->
        {
            if(realmUser == null)
            {
                RealmUser newRealmUser = realm.createObject(RealmUser.class, user.getLogin());
                newRealmUser.setAvatarUrl(user.getAvatarUrl());
            }
            else
            {
                realmUser.setAvatarUrl(user.getAvatarUrl());
            }
        });

        realm.close();
    }

    @Override
    public Observable<User> getUser(String username)
    {
        return  Observable.create(e -> {
            Realm realm = Realm.getDefaultInstance();
            RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", username).findFirst();
            if(realmUser == null)
            {
                e.onError(new RuntimeException("No user in cache"));
            }
            else
            {
                e.onNext(new User(realmUser.getLogin(), realmUser.getAvatarUrl()));
            }
            e.onComplete();
        });
    }

    @Override
    public void putUserRepos(User user, List<Repository> repositories)
    {
        Realm realm = Realm.getDefaultInstance();
        RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", user.getLogin()).findFirst();
        if(realmUser == null)
        {
            realm.executeTransaction(innerRealm ->
            {
                RealmUser newRealmUser = realm.createObject(RealmUser.class, user.getLogin());
                newRealmUser.setAvatarUrl(user.getAvatarUrl());
            });
        }

        realmUser = realm.where(RealmUser.class).equalTo("login", user.getLogin()).findFirst();

        RealmUser finalRealmUser = realmUser;
        realm.executeTransaction(innerRealm -> {
            finalRealmUser.getRepositories().deleteAllFromRealm();
            for (Repository repo : repositories)
            {
                RealmRepository realmRepository = realm.createObject(RealmRepository.class, repo.getId());
                realmRepository.setName(repo.getName());
                finalRealmUser.getRepositories().add(realmRepository);
            }

        });
        realm.close();
    }

    @Override
    public Observable<List<Repository>> getUserRepos(User user)
    {
        return Observable.create(e ->
        {
            Realm realm = Realm.getDefaultInstance();
            RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", user.getLogin()).findFirst();
            if (realmUser == null)
            {
                e.onError(new RuntimeException("No user in cache"));
            } else
            {
                List<Repository> repos = new ArrayList<>();
                for (RealmRepository realmRepository : realmUser.getRepositories())
                {
                    repos.add(new Repository(realmRepository.getId(), realmRepository.getName()));
                }
                e.onNext(repos);
            }
            e.onComplete();
        });
    }
}
