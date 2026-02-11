package com.endeavorms.velocity.qto.ftdi;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
public class FtdiNoteManager extends StandardManager<FtdiNote> {

      @Inject
    public FtdiNoteJpaDao dao;

    @Override
    public FtdiNoteJpaDao getDao() { return dao; }


    /**
     * Get notes for a Dispatch item.
     *
     * @param id Vendor Dispatch Id .
     * @return Equipment.
     */
    public List<FtdiNote> findByDispathVendorId(final Long id) {
        return dao.findByDispathVendorId(id);
    }
}
