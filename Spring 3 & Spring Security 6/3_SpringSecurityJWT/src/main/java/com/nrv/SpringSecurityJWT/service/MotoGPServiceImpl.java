package com.nrv.SpringSecurityJWT.service;

import com.nrv.SpringSecurityJWT.model.MotoGP;
import com.nrv.SpringSecurityJWT.repository.MotoGPRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MotoGPServiceImpl implements MotoGPService {

    @Autowired
    MotoGPRepo repo;

    @Override
    public List<MotoGP> fetchAllRiders() {
        return repo.findAll();
    }
}
