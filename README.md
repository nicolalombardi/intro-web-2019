# intro-web-2019-backend
Repository per il backend del progetto del corso "Introduzione alla programmazione per il web"

## Creazione e popolazione delle tabelle
Per creare le tabelle all'interno del database è sufficiente importare il file sql/create_tables.sql con questo comando
```
psql [nome db] < [...]sql/create_tables.sql
```
Per inserire i dati basta rieseguire lo stesso comando importando però il file sql/populate_tables.sql
```
psql [nome db] < [...]sql/populate_tables.sql
```
