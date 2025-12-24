package com.nrv.SpringSecurityV2.service;

import com.nrv.SpringSecurityV2.model.MotoGP;
import com.nrv.SpringSecurityV2.repository.MotoGPRepo;
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
