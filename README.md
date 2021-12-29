# intro-web-2019
Repository per il progetto del corso "Introduzione alla programmazione per il web". Contiene una web app sviluppata con Servlet Java e un database SQL per il back-end e il framework Bootstrap per il front-end.

(English)
This is the repository for the "Intro to web programming" project course. It contains a web application developed using Java Servlets and an SQL database for the back-end and the Bootstrap framework for the front-end. The rest of the documentation is provided in italian since that was the requirement for the course.

## Documentazione
La documentazione del progetto è disponibile a [questo link](https://docs.google.com/document/d/1Fa3DuwpcoFoz8SzSYSbgl9fuGLV4APD07iAmDPsJSa8/edit?usp=sharing).

## Creazione e popolazione delle tabelle
Per creare le tabelle all'interno del database è sufficiente importare il file sql/create_tables.sql con questo comando
```
psql [nome db] < sql/create_tables.sql
```
Per inserire i dati basta rieseguire lo stesso comando importando però il file sql/populate_tables.sql
```
psql [nome db] < sql/populate_tables.sql
```

In alternativa è possibile importare il dump del database presente in sql/dump_db con
```
psql [nome db] < sql/dump_db
```

## Impostare dati di connessione database
Per il corretto funzionamento dell'applicazione è necessario impostare i dati di connessione al database. La configurazione si trova nel file /app/src/main/webapp/WEB-INF/web.xml ed ha questo aspetto:
```
    <context-param>
        <description>The url to the database</description>
        <param-name>dbUrl</param-name>
        <param-value>jdbc:postgresql://localhost/[nome database]</param-value>
    </context-param>
    <context-param>
        <description>The jdbc driver</description>
        <param-name>dbDriver</param-name>
        <param-value>org.postgresql.Driver</param-value>
    </context-param>
    <context-param>
        <description>The user for the connection</description>
        <param-name>dbUser</param-name>
        <param-value>[nome utente]</param-value>
    </context-param>
    <context-param>
        <description>The password for the connection</description>
        <param-name>dbPassword</param-name>
        <param-value>[password]</param-value>
    </context-param>
```
È sufficiente sostituire i campi [parametro] con il valore corretto.
