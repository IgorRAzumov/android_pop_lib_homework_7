package ru.geekbrains.android3_7.model.entity.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by stanislav on 3/12/2018.
 */


public class RealmRepository extends RealmObject
{
    @PrimaryKey
    private String id;
    private String name;


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
