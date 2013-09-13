package com.plainvanilla.database;

import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.TimestampType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 11/09/13
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */
public class TimestampAsDateType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[] {Types.DATE, Types.TIMESTAMP};
    }

    @Override
    public Class returnedClass() {
        return java.util.Date.class;
    }

    @Override
    public boolean equals(Object o, Object o2) throws HibernateException {
        return ObjectUtils.equals(o, o2);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        if (o != null) {
            return o.hashCode();
        } else {
            return 0;
        }
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        // extract Timestamp from the result set
        Timestamp timestamp = (Timestamp) TimestampType.INSTANCE.nullSafeGet(resultSet, strings[0], sessionImplementor);

        // return the value as a java.util.Date (dropping the nanoseconds)
        if( timestamp==null )
        {
            return null;
        }
        else
        {
            return new Date(timestamp.getTime());
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor) throws HibernateException, SQLException {

        // handle the NULL special case immediately
        if (o == null)
        {
            preparedStatement.setTimestamp(i, null);
            return;
        }


        // make sure the received value is of the right type
        if( ! Date.class.isAssignableFrom(o.getClass()) ) {
            throw new IllegalArgumentException("Received value is not a [java.util.Date] but [" + o.getClass() + "]");
        }

        // set the value into the resultset
        Timestamp tstamp = null;
        if( (o instanceof Timestamp) )
        {
            tstamp = (Timestamp) o;
        }
        else
        {
            tstamp = new Timestamp( ((Date) o).getTime() );
        }

        preparedStatement.setTimestamp(i, tstamp);


    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        if (o == null) {
            return null;
        } else {
            return ((Date)o).clone();
        }
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return null;
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return null;
    }

    @Override
    public Object replace(Object o, Object o2, Object o3) throws HibernateException {
        return null;
    }
}
