package ru.geekbrains.android3_7.model.entity.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by stanislav on 3/12/2018.
 */

@Table(name = "users")
public class AAUser extends Model
{
    @Column(name = "avatar_url")
    public String avatarUrl;

    @Column(name = "login")
    public String login;

    public List<AAUserRepository> repositories()
    {
        return getMany(AAUserRepository.class, "user");
    }
}
