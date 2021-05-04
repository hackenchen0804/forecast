package com.hacken.forecast.mapper;

import com.hacken.forecast.model.Forecast;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScalaMapper {
    @Select("select count(*) from OR03JX00 with(nolock) WHERE CAST(OR03001 AS BIGINT)=#{order} and CAST(OR03002 AS INT)=#{line} and OR03003='000000'")
    boolean hasOrderLine(@Param("order") Long order, @Param("line") Integer line);

    @Select("select count(*) from OR03JX00 with(nolock) WHERE CAST(OR03001 AS BIGINT)=#{order} and CAST(OR03002 AS INT)=#{line} and OR03003='000000' AND OR03005=#{item}")
    boolean hasSameItemInOrderLine(@Param("order") Long order, @Param("line") Integer line, @Param("item") String item);

    @Select("select OR03001 as [order],OR03002 as line,OR03003 as subLine,OR03005 as item,OR03011 as qty from OR03JX00 with(nolock) WHERE CAST(OR03001 AS BIGINT)=#{order} and CAST(OR03002 AS INT)=#{line} order by OR03003")
    List<Forecast> getParentAndSubOrderLine(@Param("order") Long order, @Param("line") Integer line);

}
