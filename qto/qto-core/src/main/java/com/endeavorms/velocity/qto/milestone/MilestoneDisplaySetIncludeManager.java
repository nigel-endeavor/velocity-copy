package com.endeavorms.velocity.qto.milestone;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
public class MilestoneDisplaySetIncludeManager extends StandardManager<MilestoneDisplaySetInclude> {

    @Inject
    private MilestoneDisplaySetIncludeJpaDao dao;

    @Override
    protected MilestoneDisplaySetIncludeJpaDao getDao() {
        return dao;
    }
}
