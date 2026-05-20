package com.marcptr.cine.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcptr.cine.document.AppSetting;

public interface AppSettingRepository extends MongoRepository<AppSetting, String> {

    Optional<AppSetting> findByConfigKey(String key);
        boolean existsByConfigKey(String key);


}