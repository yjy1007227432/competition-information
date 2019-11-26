package org.competition.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@Table(name = "strategy")
public class Strategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id;

    @Column(name = "strTitle")
    private String strTitle;
    @Column(name = "mdContent")
    private String mdContent;
    @Column(name = "htmlContent")
    private String htmlContent;
    @Column(name = "summary")
    private String summary;
    @Column(name = "state")
    private Integer state;
    @Column(name = "income")
    private String income;
    @Column(name = "iid")
    private Long iid;
    @Column(name = "uid")
    private Long uid;
    @Column(name = "editTime")
    private Date editTime;
    @Column(name = "publishDate")
    private Date publishDate;
    @Column(name = "strategyFile")
    private String strategyFile;
    @Transient
    private List<User> userId;

}
