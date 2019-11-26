package org.competition.bean;

import java.sql.Timestamp;
import java.util.Date;

public class MsgContent {
//      `id` int(11) NOT NULL AUTO_INCREMENT,
//  `title` varchar(64) DEFAULT NULL,
//  `message` varchar(255) DEFAULT NULL,
//  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    private Long id;
    private String title;
    private String message;
    private Timestamp createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}
