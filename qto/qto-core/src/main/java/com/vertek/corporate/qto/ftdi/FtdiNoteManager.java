package com.vertek.corporate.qto.ftdi;

import com.vertek.corporate.qto.common.StandardManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
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
