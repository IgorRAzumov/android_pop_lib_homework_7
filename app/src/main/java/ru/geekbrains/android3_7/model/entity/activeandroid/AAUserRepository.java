package ru.geekbrains.android3_7.model.entity.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by stanislav on 3/12/2018.
 */

@Table(name = "user_repositories")
public class AAUserRepository extends Model
{
    @Column(name = "github_id")
    public String id;

    @Column(name = "name")
    public String name;

    @Column(name = "user")
    public AAUser user;

}
