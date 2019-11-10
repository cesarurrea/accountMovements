#### URL Ejercicio 2

- Para cada ejercicio se generea un objeto respuesta que entrega las validaciones solicitadas
- Manteniendo la integridad relacional y las reglas de negocio.

Crear cuenta
curl -X POST -H "Content-Type:application/json" -H "Accept:application/json" -d "{\"accountNumber\":\"010091\",\"currency\":\"DOLAR\",\"balance\":\"200\"}" http://localhost:8080/api/account

Consultar Todas las Cuentas
curl -X GET "http://localhost:8080/api/account/list"

Eliminar cuenta
curl -X DELETE "http://localhost:8080/api/account?accountNumber=010090"

Crear Movimiento (La fecha del movimiento debe ser enviada en milisegundos
curl -X POST -H "Content-Type:application/json" -H "Accept:application/json" -d "{\"accountNumber\":\"010091\",\"movementDate\":\"1573077293027\",\"movementType\":\"DEBIT\",\"description\":\"Compra de Varios\",\"ammount\":\"5\"}" http://localhost:8080/api/accountMovement

Consultar por cuenta en orden desc
curl -X GET "http://localhost:8080/api/accountMovement/movement/account?accountNumber=010091"


#### URL Ejercicio 1

- Para cada ejercicio se generea un objeto respuesta que entrega las validaciones solicitadas
- Manteniendo la integridad relacional y las reglas de negocio.

Crear Titular
curl -X POST -H "Content-Type:application/json" -H "Accept:application/json" -d "{\"rut\":\"9800124\",\"cc\":\"9800123\",\"name\":\"Cesar\",\"lastName\":\"Urrea\",\"bussinesName\":null,\"establishmentDate\":null,\"legalEntity\":\"NATURAL\"}" http://localhost:8080/api/accountHolder

Actualizar Titular
curl -X PUT -H "Content-Type:application/json" -H "Accept:application/json" -d "{\"rut\":\"9800124\",\"cc\":\"9800123\",\"name\":\"CesarAuguto\",\"lastName\":\"Urrea\",\"bussinesName\":null,\"establishmentDate\":null,\"legalEntity\":\"NATURAL\"}" http://localhost:8080/api/accountHolder

Eliminar Titular
curl -X DELETE "http://localhost:8080/api/accountHolder?rut=9800124"

Consultar Titular por rut
curl -X GET "http://localhost:8080/api/accountHolder?rut=9800124"

Consular Todos los titulares
curl -X GET "http://localhost:8080/api/accountHolder/list"











