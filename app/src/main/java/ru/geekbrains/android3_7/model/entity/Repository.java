package ru.geekbrains.android3_7.model.entity;

/**
 * Created by stanislav on 3/15/2018.
 */

public class Repository
{
    private String id;
    private String name;

    public Repository(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

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
