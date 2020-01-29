# intro-web-2019
Repository per il progetto del corso "Introduzione alla programmazione per il web"

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
