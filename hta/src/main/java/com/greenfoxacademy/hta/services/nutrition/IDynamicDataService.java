package com.greenfoxacademy.hta.services.nutrition;

import com.greenfoxacademy.hta.models.nutrition.DynamicData;

import java.util.List;

public interface IDynamicDataService {

    public List<DynamicData> findAllData();
    public void deleteAllData();

    void deleteData(DynamicData dynamicData);

    public  DynamicData saveData(DynamicData dynamicData);

}