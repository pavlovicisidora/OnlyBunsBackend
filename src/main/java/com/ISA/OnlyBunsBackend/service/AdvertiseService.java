package com.ISA.OnlyBunsBackend.service;

import java.io.IOException;
import java.time.LocalDateTime;

public interface AdvertiseService {

    void sendPostForAd(String description, String username, LocalDateTime timestamp) throws IOException;
}
