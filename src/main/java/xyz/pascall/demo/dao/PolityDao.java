package xyz.pascall.demo.dao;

import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;
import xyz.pascall.demo.pojo.Polity;

import java.util.List;

/**
 * dao层：
 *      1：在service层中直接使用SQLManager类
 *      2：在dao层中直接使用继承BaseMapper类
 *      2：使用sql文件(/sql/*.md)
 *
 */
@SqlResource("polity")
public interface PolityDao extends BaseMapper<Polity> {
    /**
     * 通过Id获取政治面貌
     * @param polity
     * @return
     */
    List<Polity> getPolityById(Polity polity);

    /**
     * 铜鼓状态获取政治面貌
     * @param polity
     * @return
     */
    List<Polity> getPolityByStatus(Polity polity);
}
