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

    @Select("select OR03001 as [order],OR03002 as line,OR03003 as subLine,OR03046 as warehouse,OR03005 as item,OR03011 as qty from OR03JX00 with(nolock) WHERE CAST(OR03001 AS BIGINT)=#{order} and CAST(OR03002 AS INT)=#{line} order by OR03003")
    List<Forecast> getParentAndSubOrderLine(@Param("order") Long order, @Param("line") Integer line);

    @Select("select MP65002 as rowNumber,MP65001 as [order],MP65011 as warehouse,MP65003 as line,'000000' as subline,MP65004 as item,(MP65007*MP64004-MP65016) as qty,MP65015 as planDate FROM MP65JX00 with(nolock) " +
            "inner join MP64JX00 with(nolock) ON MP64001=MP65001 " +
            "where MP64088<8 AND MP64088>2 and (MP65007*MP64004-MP65016)>0")
    List<Forecast> getWorkOrderMaterials();

    @Select("select TOP 1 OR03046 as warehouse from OR03JX00 with(nolock) WHERE CAST(OR03001 AS BIGINT)=#{order} and CAST(OR03002 AS INT)=#{line} and OR03003='000000' AND OR03005=#{item}")
    String getWarehouse(@Param("order") Long order, @Param("line") Integer line, @Param("item") String item);

}
