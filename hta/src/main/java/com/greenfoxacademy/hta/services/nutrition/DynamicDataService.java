package com.greenfoxacademy.hta.services.nutrition;

import com.greenfoxacademy.hta.models.nutrition.DynamicData;
import com.greenfoxacademy.hta.repositories.nutrition.IDynamicDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicDataService implements IDynamicDataService{
    private final IDynamicDataRepository iDynamicDataRepository;

    @Autowired
    public DynamicDataService(IDynamicDataRepository iDynamicDataRepository) {
        this.iDynamicDataRepository = iDynamicDataRepository;
    }

    @Override
    public List<DynamicData> findAllData() {
        return iDynamicDataRepository.findAll();
    }

    @Override
    public void deleteAllData() {
        List<DynamicData> allData = iDynamicDataRepository.findAll();
        iDynamicDataRepository.deleteAll(allData);
    }

    @Override
    public void deleteData(DynamicData dynamicData) {
        iDynamicDataRepository.delete(dynamicData);
    }

    @Override
    public DynamicData saveData(DynamicData dynamicData) {
        iDynamicDataRepository.save(dynamicData);
        return dynamicData;
    }

}