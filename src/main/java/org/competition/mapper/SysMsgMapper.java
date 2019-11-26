package org.competition.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.competition.bean.MsgContent;
import org.competition.bean.User;
import org.competition.bean.MsgContent;
import org.competition.bean.SysMsg;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface SysMsgMapper {

    int sendMsg(MsgContent msg);

    int addMsg2AllHr(@Param("user") List<User> hrs, @Param("mid") Long mid);

    List<SysMsg> getSysMsg(@Param("start") int start, @Param("size") Integer size, @Param("hrid") Long hrid);

    int markRead(@Param("flag") Long flag, @Param("hrid") Long hrid);
}
