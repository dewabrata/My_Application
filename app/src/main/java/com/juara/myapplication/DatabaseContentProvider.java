package com.juara.myapplication;

import com.activeandroid.Configuration;
import com.activeandroid.content.ContentProvider;
import com.juara.myapplication.model.Inventory;

public class DatabaseContentProvider extends ContentProvider {

    @Override
    protected Configuration getConfiguration() {
        Configuration.Builder builder = new Configuration.Builder(getContext());
        builder.addModelClass(Inventory.class);
        return builder.create();
    }
}