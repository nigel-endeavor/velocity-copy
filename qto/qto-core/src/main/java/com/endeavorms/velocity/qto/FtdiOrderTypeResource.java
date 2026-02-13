package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.ftdi.FtdiOrderType;
import com.endeavorms.velocity.qto.ftdi.FtdiOrderTypeManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author rcasey
 * @since 4/12/2023
 */
@RestController
@RequestMapping("/api/ftdiOrderTypes")
public class FtdiOrderTypeResource extends AbstractResource<FtdiOrderType> {

    @Override
    protected String getResourcePath() {
        return "/ftdiOrderTypes";
    }

    @Autowired
    private FtdiOrderTypeManager manager;

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieve(@PathVariable("id") final Long id) {
        FtdiOrderType ftdiOrderType = manager.retrieve(id);
        return ResponseEntity.ok(ftdiOrderType);
    }

    @GetMapping
    public ResponseEntity<?> findByVendor(@RequestParam("vendor") final String vendor) {
        List<FtdiOrderType> ftdiOrderTypes = manager.findByVendor(vendor);
        return ResponseEntity.ok(ftdiOrderTypes);
    }

    @GetMapping("/sort1")
    public ResponseEntity<?> findSort1ByVendor(@RequestParam("vendor") final String vendor) {
        List<String> ftdiOrderTypes = manager.findSort1ByVendor(vendor);
        return ResponseEntity.ok(ftdiOrderTypes);
    }

    @GetMapping("/sort2")
    public ResponseEntity<?> findSort2(@RequestParam("sort1") final String sort1) {
        List<String> ftdiOrderTypes = manager.findSort2(sort1);
        return ResponseEntity.ok(ftdiOrderTypes);
    }

    @GetMapping("/orderType")
    public ResponseEntity<?> findOrderType(@RequestParam("sort1") final String sort1, @RequestParam("sort2") final String sort2) {
        List<FtdiOrderType> ftdiOrderTypes = manager.findOrderType(sort1, sort2);
        return ResponseEntity.ok(ftdiOrderTypes);
    }
}
