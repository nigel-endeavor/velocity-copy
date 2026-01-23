package com.vertek.corporate.qto.inventory;

import com.vertek.corporate.qto.common.StandardBaseEntity;
import com.vertek.corporate.qto.service.Service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(
        name = "pending_disconnect"
)
public class PendingDisconnect extends StandardBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pending_disconnect_id")
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "parent_service_id",
            referencedColumnName = "service_id"
    )
    private Service parentService;

    @OneToOne
    @JoinColumn(
            name = "new_service_id",
            referencedColumnName = "service_id"
    )
    private Service newService;

    @OneToOne
    @JoinColumn(
            name = "child_service_id",
            referencedColumnName = "service_id"
    )
    private Service childService;

    @Column(name = "new_service_complete_date")
    private Date newServiceCompleteDate;

    @Column(name = "disconnect_reason")
    private String disconnectReason;

    @Override
    public Long getId() {
        return id;
    }

    public Service getParentService() {
        return parentService;
    }

    public void setParentService(final Service parentService) {
        this.parentService = parentService;
    }

    public Service getNewService() {
        return newService;
    }

    public void setNewService(final Service newService) {
        this.newService = newService;
    }

    public Service getChildService() {
        return childService;
    }

    public void setChildService(final Service childService) {
        this.childService = childService;
    }

    public Date getNewServiceCompleteDate() {
        return newServiceCompleteDate;
    }

    public void setNewServiceCompleteDate(final Date newServiceCompleteDate) {
        this.newServiceCompleteDate = newServiceCompleteDate;
    }

    public String getDisconnectReason() {
        return disconnectReason;
    }

    public void setDisconnectReason(final String disconnectReason) {
        this.disconnectReason = disconnectReason;
    }
}




