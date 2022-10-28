package com.example.demo.configuration;

import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

public class SqlServerDialect extends SQLServer2012Dialect {
    public SqlServerDialect() {
        registerHibernateType(Types.NCHAR, StandardBasicTypes.CHARACTER.getName());
        registerHibernateType(Types.NCHAR, 1, StandardBasicTypes.CHARACTER.getName());
        registerHibernateType(Types.NCHAR, 255, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.TEXT.getName());
        registerHibernateType(Types.NCLOB, StandardBasicTypes.CLOB.getName());
    }

}
