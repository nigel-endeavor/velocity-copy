package com.vertek.corporate.qto.message;

import com.google.common.collect.Iterables;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.notification.NotificationManager;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rcasey
 * @since 4/26/2023
 */
@Stateless
public class MessageThreadManager extends StandardManager<MessageThread> {

    @Inject
    private MessageThreadJpaDao dao;

    @Inject
    private SubjectManager subjectManager;

    @Inject
    private LocationManager locationManager;

    @Inject
    private NotificationManager notificationManager;

    @Override
    protected MessageThreadJpaDao getDao() {
        return dao;
    }

    @Override
    public MessageThread create(final MessageThread entity) {
        List<Integer> subjectIds = new ArrayList<>(entity.getSubjectIds());
        Location location = locationManager.retrieve(entity.getLocationId());
        entity.setTenantId(location.getTenantId());
        entity.setMasterCustomerId(location.getMasterCustomerId());
        String currentUser = SecurityUtils.getLoggedInUser();
        Subject subject = subjectManager.findByUsername(currentUser);
        entity.setCreatedBy(subject == null ? null : subject.getId());
        entity.setCreatedDate(new Date());
        for (Message message : entity.getMessages()) {
            message.setCreatedBy(subject == null ? null : subject.getId());
            message.setCreatedDate(new Date());
        }
        MessageThread created = super.create(entity);
        return setSubjectDisplayNames(setSubjects(created.getId(), subjectIds));
    }

    public MessageThread createMessage(final Long messageThreadId, final Message message) {
        message.setMessageThreadId(messageThreadId);
        String currentUser = SecurityUtils.getLoggedInUser();
        Subject subject = subjectManager.findByUsername(currentUser);
        message.setCreatedBy(subject == null ? null : subject.getId());
        message.setCreatedDate(new Date());

        MessageThread thread = retrieve(message.getMessageThreadId());
        thread.getMessages().add(message);
        MessageThread updated = edit(thread);

        Location location = locationManager.retrieve(thread.getLocationId());
        String header = "New Message - " + thread.getTitle();
        String linkTo = "/order/" + location.getOrderId()
                + "/location/" + thread.getLocationId()
                + "/communication/"
                + "?threadId=" + thread.getId()
                + "&messageId=" + Iterables.getLast(updated.getMessages()).getId();
        if (thread.getCreatedBy() != message.getCreatedBy()) {
            notificationManager.create(thread.getCreatedBy(), header, message.getBody(), "mail", linkTo);
        }

        for (MessageThreadWatcher watcher : thread.getSubjects()) {
            if (watcher.getSubjectId() != message.getCreatedBy()) {
                notificationManager.create(watcher.getSubjectId(), "New Message", message.getBody(), "mail", linkTo);
            }
        }

        return setSubjectDisplayNames(updated);
    }

    public MessageThread setSubjects(final Long messageThreadId, final List<Integer> subjectIds) {
        List<MessageThreadWatcher> messageThreadWatchers = new ArrayList<>();
        for (Integer subjectId : subjectIds) {
            MessageThreadWatcher messageThreadWatcher = new MessageThreadWatcher();
            messageThreadWatcher.setMessageThreadId(messageThreadId);
            messageThreadWatcher.setSubjectId(Long.valueOf(subjectId));
            messageThreadWatchers.add(messageThreadWatcher);
        }
        MessageThread thread = retrieve(messageThreadId);
        thread.getSubjects().clear();
        thread.getSubjects().addAll(messageThreadWatchers);
        return edit(thread);
    }

    public List<MessageThread> findByLocationId(final Long locationId) {
        List<MessageThread> threads = dao.findByLocationId(locationId);
        //populate display names for threads and messages
        Map<Long, String> subjectDisplayNames = new HashMap<>();
        for (MessageThread thread : threads) {
            if (subjectDisplayNames.containsKey(thread.getCreatedBy())) {
                thread.setCreatedByDisplayName(subjectDisplayNames.get(thread.getCreatedBy()));
            } else {
                Subject subject = subjectManager.retrieve(thread.getCreatedBy());
                subjectDisplayNames.put(thread.getCreatedBy(), subject.getDisplayName());
                thread.setCreatedByDisplayName(subject.getDisplayName());
            }
            for (Message message : thread.getMessages()) {
                if (subjectDisplayNames.containsKey(message.getCreatedBy())) {
                    message.setCreatedByDisplayName(subjectDisplayNames.get(message.getCreatedBy()));
                } else {
                    Subject subject = subjectManager.retrieve(message.getCreatedBy());
                    subjectDisplayNames.put(message.getCreatedBy(), subject.getDisplayName());
                    message.setCreatedByDisplayName(subject.getDisplayName());
                }
            }
        }
        return threads;
    }

    public MessageThread setSubjectDisplayNames(final MessageThread thread) {
        Map<Long, String> subjectDisplayNames = new HashMap<>();
        Subject subject = subjectManager.retrieve(thread.getCreatedBy());
        subjectDisplayNames.put(thread.getCreatedBy(), subject.getDisplayName());
        thread.setCreatedByDisplayName(subject.getDisplayName());
        for (Message message : thread.getMessages()) {
            if (subjectDisplayNames.containsKey(message.getCreatedBy())) {
                message.setCreatedByDisplayName(subjectDisplayNames.get(message.getCreatedBy()));
            } else {
                Subject messageSubject = subjectManager.retrieve(message.getCreatedBy());
                subjectDisplayNames.put(message.getCreatedBy(), messageSubject.getDisplayName());
                message.setCreatedByDisplayName(messageSubject.getDisplayName());
            }
        }
        return thread;
    }
}
