package io.github.pixzarpg.core.impl.spigot.database;

public interface SQLDatabaseQuery {

    String toSQLWhereClause();

    Object[] getParameters();

}
