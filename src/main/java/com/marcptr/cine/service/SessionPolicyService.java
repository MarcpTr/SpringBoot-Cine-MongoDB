package com.marcptr.cine.service;

import org.springframework.stereotype.Service;

import com.marcptr.cine.model.AppSetting;
import com.marcptr.cine.repository.AppSettingRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionPolicyService {

    private final AppSettingRepository repository;

    public int getMaxSessions() {
        return repository.findByConfigKey("security.max_sessions")
                .map(setting -> Integer.parseInt(setting.getConfigValue()))
                .orElse(1);
    }

    @Transactional
    public void updateMaxSessions(int value) {
        if (value < 1) {
            throw new IllegalArgumentException("error.MIN_SESSIONS");
        }

        AppSetting setting = repository.findByConfigKey("security.max_sessions")
                .orElseThrow(() -> new RuntimeException("Setting not found"));

        setting.setConfigValue(String.valueOf(value));
        repository.save(setting);
    }
}