<?php 
$facturas = array();
$costos = array();
$utilidad = array();
$idCosto = array();
$idUtilida = array();
$Cantidad = array();
$Costo = array();
$Utilidad = array();
$i = 0;
$Result = 0;
if (!empty($_POST)) { 
    $username = "ReadApp"; 
    $password = "MarketPymesApp"; 
    $servername = "localhost";
    $dbname = $_POST['DB_name'];
    try {
        //consulta inicial, retorna el id_factura en base a la fecha y tipo de factura indicado
        $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $sql = $conn->prepare("SELECT cp_id_factura_993284234 FROM lp_factura_i092002992 WHERE cp_fecha_99832423 = :fecha and cp_tipo_factura_92384 = :tipo");
        $sql->execute(array('fecha' => $_POST['fecha'], 'tipo' => $_POST['tipo']));
        $resultado = $sql->fetchAll();
        foreach ($resultado as $row) {
            array_push($facturas, $row["cp_id_factura_993284234"]);
        }

        //consulta de los id bases para obtener datos reales
        foreach ($facturas as $fac) {
            $sql = $conn->prepare("SELECT `cp_id_factura_993284234`,`cp_id_costo_92389483984`,`cp_id_utilidad_987823784234`,`cp_cantidad_productos_9872384` FROM `lp_facturacion_i092002992` WHERE `cp_id_factura_993284234` = :id_factura");
            $sql->execute(array('id_factura' => $fac));
            $resultado = $sql->fetchAll();

            foreach ($resultado as $row) {
            //array_push($facturasH, $row["cp_id_factura_993284234"]);
            array_push($idCosto, $row["cp_id_costo_92389483984"]);
            array_push($idUtilida, $row["cp_id_utilidad_987823784234"]);
            array_push($Cantidad, $row["cp_cantidad_productos_9872384"]);
            }
        }

        //consulta id producto para obtener el valor de costo 
        foreach ($idCosto as $id) {
            $sql = $conn->prepare("SELECT `cp_id_producto_919819828828` FROM `lp_productos_costos_historial_i092002992` WHERE `cp_id_costo_92389483984` = :id_Producto");
            $sql->execute(array('id_Producto' => $id));
            $result = $sql->fetchAll();

            foreach ($result as $row) {
            array_push($costos, $row["cp_id_producto_919819828828"]);
            }
        }

        //consulta del costo del producto
        foreach ($costos as $id) {
            $sql = $conn->prepare("SELECT `cp_id_producto_919819828828`, `cp_producto_costo_92939898234` FROM `lp_productos_costos_i092002992` WHERE `cp_id_producto_919819828828` = :id_Producto");
            $sql->execute(array('id_Producto' => $id));
            $result = $sql->fetchAll();

            foreach ($result as $row) {
            array_push($Costo, $row["cp_producto_costo_92939898234"]);
            }
        }

        //consulta id producto para obtener el valor de la utilidad 
        foreach ($idUtilida as $id) {
            $sql = $conn->prepare("SELECT `cp_id_producto_919819828828` FROM `lp_productos_utilidades_historial_i092002992` WHERE `cp_id_utilidad_987823784234` = :id_Producto");
            $sql->execute(array('id_Producto' => $id));
            $result = $sql->fetchAll();

            foreach ($result as $row) {
            array_push($utilidad, $row["cp_id_producto_919819828828"]);
            }
        }

        //consulta de la utilidad del producto
        foreach ($utilidad as $id) {
            $sql = $conn->prepare("SELECT `cp_id_producto_919819828828`, `cp_producto_utilidad_1_92939898234` FROM `lp_productos_utilidades_i092002992` WHERE `cp_id_producto_919819828828` = :id_Producto");
            $sql->execute(array('id_Producto' => $id));
            $result = $sql->fetchAll();

            foreach ($result as $row) {
            array_push($Utilidad, $row["cp_producto_utilidad_1_92939898234"]);
            }
        }

        foreach ($Cantidad as $cant) {
        $Result = $Result + (($Costo[$i ] + $Utilidad[$i ]) * $cant);
        $i = $i + 1;
        }

        echo json_encode($Result);
    }
    catch(PDOException $e) {
        echo "ERROR: " . $e->getMessage();
        die(json_encode($response));
    }
}
?>