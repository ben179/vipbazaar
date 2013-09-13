@org.hibernate.annotations.TypeDef(name="timestampAsJavaUtilDate",
        defaultForType = java.util.Date.class,
        typeClass = com.plainvanilla.database.TimestampAsDateType.class)
package com.plainvanilla.vipbazaar.model;