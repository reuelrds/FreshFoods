sed "s/\${DATABASE_NAME}/$DATABASE_NAME/g" /sql_scripts/database.sql > /sql_scripts/modified_database.sql
mv /sql_scripts/modified_database.sql /docker-entrypoint-initdb.d/database.sql