package org.competition.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Data
@Accessors(chain = true)
@Table(name = "information")
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id;
    @Column(name = "source")
    private String source;
    @Column(name = "company")
    private String company;
    @Column(name = "product")
    private String product;
    @Column(name = "summary")
    private String summary;
    @Column(name = "mdContent")
    private String mdContent;
    @Column(name = "htmlContent")
    private String htmlContent;
    @Column(name = "title")
    private String title;
    @Column(name = "publishDate")
    private Timestamp publishDate;
    @Column(name = "editTime")
    private Timestamp editTime;
    @Column(name = "informationFile")
    private String informationFile;

    @Column(name = "alarm")
    private String alarm;

    @Column(name = "state")
    private Integer state;
    @Column(name = "uid")
    private Long uid;
    @Column(name = "cid")
    private Long cid;

    @Transient
    private String[] dynamicTags;
    @Transient
    private List<Tags> tags;

    @Column(name = "pageView")
    private Integer pageView;


    @Transient
    private String tagName;
    @Transient
    private String nickname;
    @Transient
    private String cateName;
    @Transient
    private Long tid;


}
