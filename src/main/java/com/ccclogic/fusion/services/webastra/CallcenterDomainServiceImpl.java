package com.ccclogic.fusion.services.webastra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CallcenterDomainServiceImpl implements CallcenterDomainService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Long> getDomainsOfCallcenters() {

        List<Long> callcenterDomains = new ArrayList<>();

        //status(4) = Disabled
        callcenterDomains = jdbcTemplate.query(
                "SELECT id as callCenterId, domainaddress AS msAddress FROM webastra.accountdefaults, webastra.callcenters WHERE callcenters.owner = accountdefaults.accountid AND callcenters.status != 4",
                (resultSet, i) -> resultSet.getLong("callCenterId"));


        return callcenterDomains;
    }
}
