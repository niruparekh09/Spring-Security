package com.nrv.SpringSecurityJWT.service;

import com.nrv.SpringSecurityJWT.model.MotoGP;

import java.util.List;

public interface MotoGPService {
    List<MotoGP> fetchAllRiders();
}
