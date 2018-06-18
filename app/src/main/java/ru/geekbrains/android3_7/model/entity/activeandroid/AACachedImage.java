package ru.geekbrains.android3_7.model.entity.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by stanislav on 3/12/2018.
 */

@Table(name = "image")
public class AACachedImage extends Model
{
    @Column(name = "url")
    public String url;

    @Column(name = "path")
    public String path;
}
