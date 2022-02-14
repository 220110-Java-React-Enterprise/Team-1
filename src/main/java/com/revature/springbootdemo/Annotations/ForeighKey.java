package com.revature.springbootdemo.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Annotation for class fields that are the primary key of a related table
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ForeighKey {
    String ReferencedTableName();
    String ReferencingTableName();
    String PrimaryKey();
}
