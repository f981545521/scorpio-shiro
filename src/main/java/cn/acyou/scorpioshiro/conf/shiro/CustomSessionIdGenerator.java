package cn.acyou.scorpioshiro.conf.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * 自定义SessionID
 * @author youfang
 * @version [1.0.0, 2020-6-27 上午 11:22]
 **/
public class CustomSessionIdGenerator implements SessionIdGenerator {
    @Override
    public Serializable generateId(Session session) {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
