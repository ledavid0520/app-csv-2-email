# CHALLENGE DESARROLLO

**Importante**
*En src/main/resources*

Se encuentra la colecci�n usada en Postman para probar la App
	-CSV Email.postman_collection.json

Ademas de: 
	-Archivo users.csv: Del cual se carga la informaci�n

**Requests**
http://localhost:8080/users/load
Carga el .csv que est� en resources con nombre users.csv usando batch en caso de que el registro sea muy grande.
Lee los registros del .csv, para luego generar en el Processor del batch un password aleatrio que mas tarde es encriptado, y finalmente es salvado en la DB MySQL
Finalizado el Job se env�an los correos con el password.

http://localhost:8080/login
Valida las credenciales. Si no se ha cambiado el pw se pide generar uno nuevo.

http://localhost:8080/login/updateCredentials
Actualiza credenciales del usuario. Encriptando el nuevo pw 

