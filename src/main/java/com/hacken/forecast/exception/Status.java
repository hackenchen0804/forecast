package com.hacken.forecast.exception;

public  enum Status {
    WRONGFILEFORMAT(100,"文件格式不正确"),
    FILENOTEXISTS(101,"文件不存在"),
    FILECANNOTWRITE(102,"文件不能写入，可能已经打开"),
    SHEETCANNOTREAD(103,"Excel文件中的sheet无法读取"),
    CELLFORMAT(105,"单元格格式不正确"),
    EXCELTITLE(106,"Excel Title设置不正确"),
    ITEMNOTINSCALA(107,"物料不存在于Scala"),
    ORDERLINENOTINSCALA(108,"不存在于Scala"),
    DATEBEFORETODAY(109,"日期在今天之前"),
    ITEMNOTEQUALINSCALA(110,"物料与Scala中的不一致"),
    NOTWRITETOPRN(110,"无法写入Prn文件"),
    NOTWRITETORESULTSHEET(110,"无法写入Result Sheet")
    ;

    private Integer code;
    private String desc;

    Status(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public String msgHead(){
        return "错误代码:"+ String.valueOf(this.code);
    }

    public String msgContent(){
        return "错误描述："+ this.desc;
    }
}
