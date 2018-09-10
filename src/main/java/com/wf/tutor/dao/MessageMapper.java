package com.wf.tutor.dao;

import com.wf.tutor.domain.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {
    int insert(Message record);

    //更新为已读
    int updateById(@Param("id") Integer id);

    //查询发送的消息
    List<Message> selectByFromId(@Param("fromId") String fromId, @Param("status") Integer status);

    //根据status查询两用户之间的聊天记录
    List<Message> selectByFromIdAndToId(@Param("fromId") String fromId, @Param("toId") String toId, @Param("status") Integer status);

    //查询接收的消息
    List<Message> selectByToId(@Param("toId") String toId);
    //查询和某人的聊天记录
    @Select("select * from message where" +
            "(status = 1 and from_id = #{fromId} and to_id = #{toId}) or (status = 1 and from_id = #{toId} and to_id = #{fromId}) " +
            "order by id desc limit 50")
    List<Message> getHistoryMessage(@Param("fromId") String fromId, @Param("toId") String toId);
}
